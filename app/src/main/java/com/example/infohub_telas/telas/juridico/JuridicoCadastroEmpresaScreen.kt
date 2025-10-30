package com.example.infohub_telas.telas.juridico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoCadastroEmpresaScreen(
    onNavigateBack: () -> Unit,
    onCadastroSuccess: () -> Unit,
    empresaId: String? = null,
    modifier: Modifier = Modifier
) {
    var nomeEmpresa by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var inscricaoEstadual by remember { mutableStateOf("") }
    var razaoSocial by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var responsavel by remember { mutableStateOf("") }
    var cargo by remember { mutableStateOf("") }

    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var showSuccessDialog by remember { mutableStateOf(false) }

    // Estados de erro
    var nomeError by remember { mutableStateOf(false) }
    var cnpjError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun validarCampos(): Boolean {
        if (nomeEmpresa.isBlank()) {
            errorMessage = "Nome da empresa é obrigatório"
            showErrorDialog = true
            return false
        }
        if (cnpj.isBlank() || !cnpj.matches(Regex("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$"))) {
            errorMessage = "CNPJ inválido"
            showErrorDialog = true
            return false
        }
        if (email.isBlank() || !email.contains("@")) {
            errorMessage = "Email inválido"
            showErrorDialog = true
            return false
        }
        return true
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

    fun handleSubmit() {
        if (validarCampos()) {
            // TODO: Implement API call to save empresa
            showSuccessDialog = true
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = if (empresaId == null) "Cadastrar Empresa" else "Editar Empresa",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = onNavigateBack
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Dados Básicos
            Text(
                "Dados Básicos",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = nomeEmpresa,
                onValueChange = { nomeEmpresa = it },
                label = { Text("Nome da Empresa*") },
                isError = nomeError,
                supportingText = if (nomeError) {
                    { Text("Campo obrigatório") }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cnpj,
                onValueChange = {
                    if (it.length <= 18) {
                        cnpj = formatarCNPJ(it)
                    }
                },
                label = { Text("CNPJ*") },
                isError = cnpjError,
                supportingText = if (cnpjError) {
                    { Text("CNPJ inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = razaoSocial,
                onValueChange = { razaoSocial = it },
                label = { Text("Razão Social") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = inscricaoEstadual,
                onValueChange = { inscricaoEstadual = it },
                label = { Text("Inscrição Estadual") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            // Contato
            Text(
                "Contato",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email*") },
                isError = emailError,
                supportingText = if (emailError) {
                    { Text("Email inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    if (it.length <= 15) {
                        telefone = formatarTelefone(it)
                    }
                },
                label = { Text("Telefone") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço") },
                modifier = Modifier.fillMaxWidth()
            )

            // Responsável
            Text(
                "Responsável",
                style = MaterialTheme.typography.titleMedium
            )

            OutlinedTextField(
                value = responsavel,
                onValueChange = { responsavel = it },
                label = { Text("Nome do Responsável") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cargo,
                onValueChange = { cargo = it },
                label = { Text("Cargo") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = { handleSubmit() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(if (empresaId == null) "Cadastrar" else "Salvar Alterações")
            }
        }

        // Error Dialog
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Erro") },
                text = { Text(errorMessage) },
                confirmButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }

        // Success Dialog
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    onCadastroSuccess()
                },
                title = { Text("Sucesso") },
                text = { Text(if (empresaId == null) "Empresa cadastrada com sucesso!" else "Empresa atualizada com sucesso!") },
                confirmButton = {
                    TextButton(onClick = {
                        showSuccessDialog = false
                        onCadastroSuccess()
                    }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JuridicoCadastroEmpresaScreenPreview() {
    InfoHub_telasTheme {
        JuridicoCadastroEmpresaScreen(
            onNavigateBack = {},
            onCadastroSuccess = {}
        )
    }
}
