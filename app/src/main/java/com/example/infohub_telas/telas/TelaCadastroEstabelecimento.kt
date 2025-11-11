package com.example.infohub_telas.telas

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
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    val categorias = listOf("Alimentação", "Varejo", "Serviços", "Saúde", "Educação", "Outros")
    var categoriaSelecionada by remember { mutableStateOf(categorias[0]) }

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
            OutlinedTextField(
                value = nomeEstabelecimento,
                onValueChange = { nomeEstabelecimento = it },
                label = { Text("Nome do Estabelecimento*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = cnpj,
                onValueChange = { cnpj = it },
                label = { Text("CNPJ*") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço*") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone*") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail*") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            ExposedDropdownMenuBox(
                expanded = categoriaExpandida,
                onExpandedChange = { categoriaExpandida = !categoriaExpandida },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = categoriaSelecionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria do Negócio*") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpandida) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                ExposedDropdownMenu(
                    expanded = categoriaExpandida,
                    onDismissRequest = { categoriaExpandida = false }
                ) {
                    categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                categoriaSelecionada = cat
                                categoriaExpandida = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    when {
                        nomeEstabelecimento.isBlank() -> errorMessage = "Nome é obrigatório"
                        cnpj.isBlank() -> errorMessage = "CNPJ é obrigatório"
                        endereco.isBlank() -> errorMessage = "Endereço é obrigatório"
                        telefone.isBlank() -> errorMessage = "Telefone é obrigatório"
                        email.isBlank() -> errorMessage = "E-mail é obrigatório"
                        else -> {
                            isLoading = true
                            
                            val estabelecimento = Estabelecimento(
                                nome = nomeEstabelecimento,
                                cnpj = cnpj.filter { it.isDigit() },
                                endereco = endereco,
                                telefone = telefone.filter { it.isDigit() },
                                email = email,
                                categoria = categoriaSelecionada
                            )
                            
                            Log.d("ESTABELECIMENTO", "Cadastrando: $estabelecimento")
                            
                            estabelecimentoApi.cadastrarEstabelecimento(estabelecimento).enqueue(
                                object : Callback<Estabelecimento> {
                                    override fun onResponse(
                                        call: Call<Estabelecimento>,
                                        response: Response<Estabelecimento>
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
                                    
                                    override fun onFailure(call: Call<Estabelecimento>, t: Throwable) {
                                        isLoading = false
                                        Log.e("ESTABELECIMENTO", "Falha: ${t.message}")
                                        errorMessage = "Erro de conexão: ${t.message}"
                                        showErrorDialog = true
                                    }
                                }
                            )
                        }
                    }
                    
                    if (errorMessage.isNotEmpty()) {
                        showErrorDialog = true
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
