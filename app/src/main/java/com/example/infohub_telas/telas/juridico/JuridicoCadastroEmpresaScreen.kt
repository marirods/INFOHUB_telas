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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoCadastroEmpresaScreen(
    navController: NavController,
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

    // Estados de erro
    var nomeError by remember { mutableStateOf(false) }
    var cnpjError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    fun validarCampos(): Boolean {
        nomeError = nomeEmpresa.isBlank()
        cnpjError = cnpj.isBlank() || !cnpj.matches(Regex("^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$"))
        emailError = email.isBlank() || !email.contains("@")
        return !(nomeError || cnpjError || emailError)
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
                onValueChange = {
                    nomeEmpresa = it
                    nomeError = false
                },
                label = { Text("Nome Fantasia") },
                leadingIcon = { Icon(Icons.Default.Business, null) },
                modifier = Modifier.fillMaxWidth(),
                isError = nomeError,
                supportingText = if (nomeError) {
                    { Text("Campo obrigatório") }
                } else null
            )

            OutlinedTextField(
                value = razaoSocial,
                onValueChange = { razaoSocial = it },
                label = { Text("Razão Social") },
                leadingIcon = { Icon(Icons.Default.Description, null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cnpj,
                onValueChange = {
                    if (it.filter { c -> c.isDigit() }.length <= 14) {
                        cnpj = formatarCNPJ(it)
                        cnpjError = false
                    }
                },
                label = { Text("CNPJ") },
                leadingIcon = { Icon(Icons.Default.Badge, null) },
                modifier = Modifier.fillMaxWidth(),
                isError = cnpjError,
                supportingText = if (cnpjError) {
                    { Text("CNPJ inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = inscricaoEstadual,
                onValueChange = { inscricaoEstadual = it },
                label = { Text("Inscrição Estadual") },
                leadingIcon = { Icon(Icons.Default.Article, null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Contato
            Text(
                "Contato",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = { Text("E-mail") },
                leadingIcon = { Icon(Icons.Default.Email, null) },
                modifier = Modifier.fillMaxWidth(),
                isError = emailError,
                supportingText = if (emailError) {
                    { Text("E-mail inválido") }
                } else null,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = telefone,
                onValueChange = {
                    if (it.filter { c -> c.isDigit() }.length <= 11) {
                        telefone = formatarTelefone(it)
                    }
                },
                label = { Text("Telefone") },
                leadingIcon = { Icon(Icons.Default.Phone, null) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço Completo") },
                leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                modifier = Modifier.fillMaxWidth()
            )

            // Responsável Legal
            Text(
                "Responsável Legal",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )

            OutlinedTextField(
                value = responsavel,
                onValueChange = { responsavel = it },
                label = { Text("Nome do Responsável") },
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = cargo,
                onValueChange = { cargo = it },
                label = { Text("Cargo") },
                leadingIcon = { Icon(Icons.Default.Work, null) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                            // TODO: Implementar integração com API para salvar estabelecimento
                            // Por enquanto apenas volta para a tela anterior
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) {
                    Text(if (empresaId == null) "Cadastrar" else "Atualizar")
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun JuridicoCadastroEmpresaScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            JuridicoCadastroEmpresaScreen(
                navController = rememberNavController()
            )
        }
    }
}
