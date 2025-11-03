package com.example.infohub_telas.telas

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroEdicaoEmpresaScreen(
    navController: NavController,
    empresaId: Int? = null,
    empresa: Empresa? = null
) {
    var nome by remember { mutableStateOf(empresa?.nome ?: "") }
    var cnpj by remember { mutableStateOf(empresa?.cnpj ?: "") }
    var email by remember { mutableStateOf(empresa?.email ?: "") }
    var telefone by remember { mutableStateOf(empresa?.telefone ?: "") }
    var endereco by remember { mutableStateOf(empresa?.endereco ?: "") }
    var ativa by remember { mutableStateOf(empresa?.let { true } ?: true) }

    // Estados de erro
    var nomeError by remember { mutableStateOf(false) }
    var cnpjError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var telefoneError by remember { mutableStateOf(false) }
    var enderecoError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun validarCampos(): Boolean {
        nomeError = nome.isBlank()
        cnpjError = cnpj.isBlank() || !cnpj.matches(Regex("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$"))
        emailError = email.isBlank() || !email.contains("@")
        telefoneError = telefone.isBlank()
        enderecoError = endereco.isBlank()

        return !(nomeError || cnpjError || emailError || telefoneError || enderecoError)
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

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("E-mail Corporativo") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError,
                supportingText = if (emailError) {
                    { Text("E-mail inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Telefone
            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    if (it.filter { c -> c.isDigit() }.length <= 11) {
                        telefone = formatarTelefone(it)
                        telefoneError = false
                    }
                },
                label = { Text("Telefone") },
                leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                isError = telefoneError,
                supportingText = if (telefoneError) {
                    { Text("Campo obrigatório") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Endereço
            OutlinedTextField(
                value = endereco,
                onValueChange = {
                    endereco = it
                    enderecoError = false
                },
                label = { Text("Endereço Completo") },
                leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                isError = enderecoError,
                supportingText = if (enderecoError) {
                    { Text("Campo obrigatório") }
                } else null
            )

            // Status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Status da Empresa")
                Switch(
                    checked = ativa,
                    onCheckedChange = { ativa = it },
                    thumbContent = if (ativa) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null,
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF4CAF50),
                        checkedIconColor = Color(0xFF4CAF50)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

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
                        if (validarCampos()) {
                            // TODO: Implementar salvamento
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) {
                    Text("Salvar")
                }
            }
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
