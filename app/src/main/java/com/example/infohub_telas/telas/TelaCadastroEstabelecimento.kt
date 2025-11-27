package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEstabelecimento(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = prefs.getString("token", "") ?: ""

    // Log para verificar se o token está disponível (caso seja necessário no futuro)
    LaunchedEffect(Unit) {
        Log.d("TelaCadastroEstabelecimento", "🔑 Token disponível: ${if (token.isNotEmpty()) "Sim (${token.take(20)}...)" else "NÃO"}")
        Log.d("TelaCadastroEstabelecimento", "ℹ️ Nota: Este endpoint atualmente não requer autenticação")
    }

    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val estabelecimentoApi = RetrofitFactory().getInfoHub_EstabelecimentoService()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = "Cadastro de Estabelecimento",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Dados do Estabelecimento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25992E),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = nomeEstabelecimento,
                onValueChange = { nomeEstabelecimento = it },
                label = { Text("Nome do Estabelecimento*") },
                placeholder = { Text("Ex: Supermercado Central") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = cnpj,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.length <= 14) {
                        cnpj = when {
                            digitsOnly.length <= 2 -> digitsOnly
                            digitsOnly.length <= 5 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2)}"
                            digitsOnly.length <= 8 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5)}"
                            digitsOnly.length <= 12 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5, 8)}/${digitsOnly.substring(8)}"
                            else -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5, 8)}/${digitsOnly.substring(8, 12)}-${digitsOnly.substring(12)}"
                        }
                    }
                },
                label = { Text("CNPJ*") },
                placeholder = { Text("12.345.678/0001-90") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = telefone,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.length <= 11) {
                        telefone = when {
                            digitsOnly.length <= 2 -> "($digitsOnly"
                            digitsOnly.length <= 6 -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2)}"
                            else -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2, 7)}-${digitsOnly.substring(7)}"
                        }
                    }
                },
                label = { Text("Telefone (opcional)") },
                placeholder = { Text("(11) 3333-4444") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    when {
                        nomeEstabelecimento.isBlank() -> {
                            errorMessage = "Nome do estabelecimento é obrigatório"
                            showErrorDialog = true
                        }
                        cnpj.isBlank() -> {
                            errorMessage = "CNPJ é obrigatório"
                            showErrorDialog = true
                        }
                        cnpj.filter { it.isDigit() }.length != 14 -> {
                            errorMessage = "CNPJ inválido. Digite os 14 dígitos"
                            showErrorDialog = true
                        }
                        else -> {
                            isLoading = true
                            
                            val estabelecimento = Estabelecimento(
                                nome = nomeEstabelecimento,
                                cnpj = cnpj,
                                telefone = telefone.ifBlank { null }
                            )
                            
                            Log.d("ESTABELECIMENTO", "Cadastrando: $estabelecimento")
                            
                            val authToken = "Bearer $token"
                            estabelecimentoApi.cadastrarEstabelecimento(authToken, estabelecimento).enqueue(
                                                                                                                                                                                                                                                                                                             object : Callback<com.example.infohub_telas.model.EstabelecimentoResponse> {
                                    override fun onResponse(
                                        call: Call<com.example.infohub_telas.model.EstabelecimentoResponse>,
                                        response: Response<com.example.infohub_telas.model.EstabelecimentoResponse>
                                    ) {
                                        isLoading = false
                                        if (response.isSuccessful) {
                                            Log.d("ESTABELECIMENTO", "Sucesso: ${response.body()}")
                                            showSuccessDialog = true
                                        } else {
                                            Log.e("ESTABELECIMENTO", "Erro: ${response.code()} - ${response.errorBody()?.string()}")
                                            errorMessage = "Erro ao cadastrar: ${response.message()}"
                                            showErrorDialog = true
                                        }
                                    }
                                    
                                    override fun onFailure(call: Call<com.example.infohub_telas.model.EstabelecimentoResponse>, t: Throwable) {
                                        isLoading = false
                                        Log.e("ESTABELECIMENTO", "Falha: ${t.message}")
                                        errorMessage = "Erro de conexão: ${t.message}"
                                        showErrorDialog = true
                                    }
                                }
                            )
                        }
                    }
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Cadastrar",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
    
    // Diálogo de sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController.popBackStack()
                }) {
                    Text("OK", color = Color(0xFF25992E))
                }
            },
            title = { Text("Sucesso!", fontWeight = FontWeight.Bold) },
            text = { Text("Estabelecimento cadastrado com sucesso!") },
            containerColor = Color.White
        )
    }
    
    // Diálogo de erro
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showErrorDialog = false
                    errorMessage = ""
                }) {
                    Text("OK", color = Color.Red)
                }
            },
            title = { Text("Erro", fontWeight = FontWeight.Bold, color = Color.Red) },
            text = { Text(errorMessage) },
            containerColor = Color.White
        )
    }
}
