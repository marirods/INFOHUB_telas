package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroEdicaoEmpresaScreen(
    navController: NavController,
    empresaId: Int? = null,
    empresa: Empresa? = null
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = prefs.getString("token", "") ?: ""

    var nome by remember { mutableStateOf(empresa?.nome ?: "") }
    var cnpj by remember { mutableStateOf(empresa?.cnpj ?: "") }
    var telefone by remember { mutableStateOf(empresa?.telefone ?: "") }

    // Estados de controle
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Estados de erro
    var nomeError by remember { mutableStateOf(false) }
    var cnpjError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val estabelecimentoApi = remember { RetrofitFactory().getInfoHub_EstabelecimentoService() }

    fun validarCampos(): Boolean {
        nomeError = nome.isBlank()
        cnpjError = cnpj.isBlank() || cnpj.filter { it.isDigit() }.length != 14

        return !(nomeError || cnpjError)
    }

    fun formatarCNPJ(text: String): String {
        val digits = text.filter { it.isDigit() }
        return when {
            digits.length <= 2 -> digits
            digits.length <= 5 -> "${digits.substring(0, 2)}.${digits.substring(2)}"
            digits.length <= 8 -> "${digits.substring(0, 2)}.${digits.substring(2, 5)}.${digits.substring(5)}"
            digits.length <= 12 -> "${digits.substring(0, 2)}.${digits.substring(2, 5)}.${digits.substring(5, 8)}/${digits.substring(8)}"
            else -> "${digits.substring(0, 2)}.${digits.substring(2, 5)}.${digits.substring(5, 8)}/${digits.substring(8, 12)}-${digits.substring(12, 14)}"
        }
    }

    fun formatarTelefone(text: String): String {
        val digits = text.filter { it.isDigit() }
        return when {
            digits.length <= 2 -> "($digits"
            digits.length <= 6 -> "(${digits.substring(0, 2)}) ${digits.substring(2)}"
            else -> "(${digits.substring(0, 2)}) ${digits.substring(2, 7)}-${digits.substring(7, minOf(digits.length, 11))}"
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (empresaId == null) "Cadastrar Empresa" else "Editar Empresa",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nome da Empresa
            OutlinedTextField(
                value = nome,
                onValueChange = {
                    nome = it
                    nomeError = false
                },
                label = { Text("Nome da Empresa") },
                leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                isError = nomeError,
                supportingText = if (nomeError) {
                    { Text("Campo obrigatório") }
                } else null
            )

            // CNPJ
            OutlinedTextField(
                value = cnpj,
                onValueChange = {
                    if (it.filter { c -> c.isDigit() }.length <= 14) {
                        cnpj = formatarCNPJ(it)
                        cnpjError = false
                    }
                },
                label = { Text("CNPJ") },
                leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                isError = cnpjError,
                supportingText = if (cnpjError) {
                    { Text("CNPJ inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Telefone (opcional)
            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    if (it.filter { c -> c.isDigit() }.length <= 11) {
                        telefone = formatarTelefone(it)
                    }
                },
                label = { Text("Telefone (opcional)") },
                placeholder = { Text("(11) 3333-4444") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botões
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        when {
                            nome.isBlank() -> {
                                nomeError = true
                                errorMessage = "Nome é obrigatório"
                                showErrorDialog = true
                            }
                            cnpj.isBlank() -> {
                                cnpjError = true
                                errorMessage = "CNPJ é obrigatório"
                                showErrorDialog = true
                            }
                            cnpj.filter { it.isDigit() }.length != 14 -> {
                                cnpjError = true
                                errorMessage = "CNPJ deve ter 14 dígitos"
                                showErrorDialog = true
                            }
                            else -> {
                                isLoading = true

                                val estabelecimento = Estabelecimento(
                                    nome = nome,
                                    cnpj = cnpj,
                                    telefone = telefone.ifBlank { null }
                                )

                                Log.d("CadastroEmpresa", "📤 Enviando estabelecimento: $estabelecimento")

                                val authToken = "Bearer $token"
                                estabelecimentoApi.cadastrarEstabelecimento(authToken, estabelecimento).enqueue(
                                    object : Callback<Estabelecimento> {
                                        override fun onResponse(
                                            call: Call<Estabelecimento>,
                                            response: Response<Estabelecimento>
                                        ) {
                                            isLoading = false
                                            if (response.isSuccessful) {
                                                Log.d("CadastroEmpresa", "✅ Estabelecimento cadastrado: ${response.body()}")
                                                showSuccessDialog = true
                                            } else {
                                                Log.e("CadastroEmpresa", "❌ Erro: ${response.code()} - ${response.errorBody()?.string()}")
                                                errorMessage = "Erro ao cadastrar: ${response.message()}"
                                                showErrorDialog = true
                                            }
                                        }

                                        override fun onFailure(call: Call<Estabelecimento>, t: Throwable) {
                                            isLoading = false
                                            Log.e("CadastroEmpresa", "💥 Falha: ${t.message}", t)
                                            errorMessage = "Erro de conexão: ${t.message}"
                                            showErrorDialog = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.weight(1f),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(if (empresaId == null) "Cadastrar" else "Atualizar")
                    }
                }
            }
        }

        // Diálogo de Sucesso
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    navController.popBackStack()
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF25992E),
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = { Text("Sucesso!") },
                text = { Text("Estabelecimento cadastrado com sucesso!") },
                confirmButton = {
                    Button(
                        onClick = {
                            showSuccessDialog = false
                            navController.popBackStack()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E))
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        // Diálogo de Erro
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(48.dp)
                    )
                },
                title = { Text("Erro") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(
                        onClick = { showErrorDialog = false },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CadastroEdicaoEmpresaScreenPreview() {
    InfoHub_telasTheme {
        CadastroEdicaoEmpresaScreen(rememberNavController())
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun CadastroEdicaoEmpresaScreenPreview2() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CadastroEdicaoEmpresaScreen(
                navController = rememberNavController()
            )
        }
    }
}
