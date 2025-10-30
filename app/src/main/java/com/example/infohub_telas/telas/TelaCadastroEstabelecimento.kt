package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEstabelecimento(navController: NavController, id: Int?, categoria: String?) {
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    val categorias = listOf("Alimentação", "Varejo", "Serviços", "Saúde", "Educação", "Outros")
    var categoriaSelecionada by remember { mutableStateOf(categoria ?: categorias[0]) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val estabelecimentoApi = RetrofitFactory().getInfoHub_EstabelecimentoService()

    // Se um ID for fornecido, carregue os dados do estabelecimento
    LaunchedEffect(id) {
        if (id != null && id != 0) {
            // TODO: Implement fetch establishment by ID when API endpoint is available
            // For now, we'll show an error message
            errorMessage = "Funcionalidade de edição ainda não disponível"
            showErrorDialog = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = if (id != null) "Editar Estabelecimento" else "Cadastro de Estabelecimento",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
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
                onValueChange = { if (it.length <= 14) cnpj = it.filter { char -> char.isDigit() } },
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
                onValueChange = { if (it.length <= 11) telefone = it.filter { char -> char.isDigit() } },
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
                label = { Text("Email*") },
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
                    if (validarCampos(nomeEstabelecimento, cnpj, endereco, telefone, email, { errorMessage = it }, { showErrorDialog = it })) {
                        val estabelecimento = Estabelecimento(
                            id = id ?: 0,
                            nome = nomeEstabelecimento,
                            cnpj = cnpj,
                            endereco = endereco,
                            telefone = telefone,
                            email = email,
                            categoria = categoriaSelecionada
                        )

                        // For now, we only have the cadastrar (create) endpoint
                        estabelecimentoApi.cadastrarEstabelecimento(estabelecimento).enqueue(object : Callback<Estabelecimento> {
                            override fun onResponse(
                                call: Call<Estabelecimento>,
                                response: Response<Estabelecimento>
                            ) {
                                if (response.isSuccessful) {
                                    showSuccessDialog = true
                                } else {
                                    errorMessage = "Erro ao cadastrar estabelecimento"
                                    showErrorDialog = true
                                }
                            }

                            override fun onFailure(call: Call<Estabelecimento>, t: Throwable) {
                                errorMessage = "Erro de conexão: ${t.message}"
                                showErrorDialog = true
                            }
                        })
                    }
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = if (id != null) "Salvar Alterações" else "Cadastrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = {
                    showSuccessDialog = false
                    navController.popBackStack()
                },
                title = { Text("Sucesso") },
                text = { Text(if (id != null) "Estabelecimento atualizado com sucesso!" else "Estabelecimento cadastrado com sucesso!") },
                confirmButton = {
                    Button(onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    }) {
                        Text("OK")
                    }
                }
            )
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                title = { Text("Erro") },
                text = { Text(errorMessage) },
                confirmButton = {
                    Button(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}

private fun validarCampos(
    nomeEstabelecimento: String,
    cnpj: String,
    endereco: String,
    telefone: String,
    email: String,
    setErrorMessage: (String) -> Unit,
    setShowErrorDialog: (Boolean) -> Unit
): Boolean {
    if (nomeEstabelecimento.isBlank()) {
        setErrorMessage("Nome do estabelecimento é obrigatório")
        setShowErrorDialog(true)
        return false
    }
    if (cnpj.length != 14) {
        setErrorMessage("CNPJ deve ter 14 dígitos")
        setShowErrorDialog(true)
        return false
    }
    if (endereco.isBlank()) {
        setErrorMessage("Endereço é obrigatório")
        setShowErrorDialog(true)
        return false
    }
    if (telefone.length < 10) {
        setErrorMessage("Telefone deve ter pelo menos 10 dígitos")
        setShowErrorDialog(true)
        return false
    }
    if (!email.contains("@") || !email.contains(".")) {
        setErrorMessage("Email inválido")
        setShowErrorDialog(true)
        return false
    }
    return true
}

@Preview(showBackground = true)
@Composable
fun TelaCadastroEstabelecimentoPreview() {
    InfoHub_telasTheme {
        TelaCadastroEstabelecimento(NavController(LocalContext.current), null, null)
    }
}
