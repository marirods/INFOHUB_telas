package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.service.InfoHubClient
import kotlinx.coroutines.launch

class TelaCadastroScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TelaCadastro(onGoToLogin = { navigator.pop() })
    }
}

fun validarCPF(cpf: String, cpfsCadastrados: List<String> = emptyList()): Pair<Boolean, String> {
    val cpfComum = cpf.filter { it.isDigit() }

    if (cpfComum.isEmpty())
        return false to "CPF não pode ser vazio"

    if (cpfComum.length != 11)
        return false to "CPF deve ter 11 dígitos"

    if (cpfComum.all { it == cpfComum.first() })
        return false to "CPF não pode ter todos números iguais"

    if (cpfsCadastrados.any { it.filter { c -> c.isDigit() } == cpfComum })
        return false to "CPF já cadastrado"

    return true to ""
}

fun validarTelefone(telefone: String, telefonesCadastrados: List<String> = emptyList()): Pair<Boolean, String> {
    val telefoneLimpo = telefone.filter { it.isDigit() }

    if (telefoneLimpo.isEmpty())
        return false to "Telefone não pode ser vazio"

    if (telefoneLimpo.length !in 10..11)
        return false to "Telefone deve ter 10 ou 11 dígitos"

    if (telefoneLimpo.all { it == telefoneLimpo.first() })
        return false to "Telefone não pode ter todos números iguais"

    if (telefoneLimpo in telefonesCadastrados.map { it.filter { c -> c.isDigit() } })
        return false to "Telefone já cadastrado"

    return true to ""
}

fun validarEmail(email: String, emailsCadastrados: List<String> = emptyList()): Boolean {
    if (email.isBlank())
        return false

    val regex = Regex("^[\\w.+-]+@(?:gmail\\.com|hotmail\\.com|yahoo\\.com)$")
    if (!email.matches(regex))
        return false

    if (emailsCadastrados.any { it.equals(email, ignoreCase = true) })
        return false
    return true
}

@Composable
fun TelaCadastro(onGoToLogin: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }

    var isPessoaFisicaSelected by remember { mutableStateOf(true) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val client = InfoHubClient()

    val primaryOrange = Color(0xFFFF8C00)
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Física",
                        fontSize = 18.sp,
                        fontWeight = if (isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = true }
                    )
                    if (isPessoaFisicaSelected) {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Jurídica",
                        fontSize = 18.sp,
                        fontWeight = if (!isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (!isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = false }
                    )
                    if (!isPessoaFisicaSelected) {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                CustomTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    placeholder = "Nome Completo*",
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                    )
                )
                if (isPessoaFisicaSelected) {
                    CustomTextField(
                        value = cpf,
                        onValueChange = { cpf = it.filter { c -> c.isDigit() }.take(11) },
                        placeholder = "CPF*",
                        keyboardType = KeyboardType.Number,
                        visualTransformation = VisualTransformation { text ->
                            val numbers = text.text.filter { it.isDigit() }
                            val cpfMascarado = buildString {
                                for (i in numbers.indices) {
                                    append(numbers[i])
                                    if (i == 2 || i == 5) append(".")
                                    if (i == 8) append("-")
                                }
                            }
                            TransformedText(
                                text = AnnotatedString(cpfMascarado),
                                offsetMapping = object : OffsetMapping {
                                    override fun originalToTransformed(offset: Int) = when {
                                        offset <= 2 -> offset
                                        offset <= 5 -> offset + 1
                                        offset <= 8 -> offset + 2
                                        offset <= 11 -> offset + 3
                                        else -> 14
                                    }

                                    override fun transformedToOriginal(offset: Int) = when {
                                        offset <= 3 -> offset
                                        offset <= 7 -> offset - 1
                                        offset <= 11 -> offset - 2
                                        offset <= 14 -> offset - 3
                                        else -> 11
                                    }
                                }
                            )
                        }
                    )
                } else {
                    CustomTextField(
                        value = cnpj,
                        onValueChange = { cnpj = it },
                        placeholder = "CNPJ*",
                        keyboardType = KeyboardType.Number
                    )
                }
                CustomTextField(
                    value = telefone,
                    onValueChange = { telefone = it.filter { c -> c.isDigit() }.take(11) },
                    placeholder = "Telefone*",
                    keyboardType = KeyboardType.Phone,
                    visualTransformation = VisualTransformation { text ->
                        val numbers = text.text.filter { it.isDigit() }
                        val masked = buildString {
                            numbers.forEachIndexed { i, c ->
                                when (i) {
                                    0 -> append("(").append(c)
                                    1 -> append(c).append(") ")
                                    6 -> append(c).append("-")
                                    else -> append(c)
                                }
                            }
                        }
                        TransformedText(AnnotatedString(masked), OffsetMapping.Identity)
                    }
                )
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "E-mail*",
                    keyboardType = KeyboardType.Email
                )
                SenhaTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    mostrarSenha = mostrarSenha,
                    onMostrarSenhaChange = { mostrarSenha = it },
                    label = "Senha*"
                )
                SenhaTextField(
                    value = confirmarSenha,
                    onValueChange = { confirmarSenha = it },
                    mostrarSenha = mostrarConfirmarSenha,
                    onMostrarSenhaChange = { mostrarConfirmarSenha = it },
                    label = "Confirme a senha*"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (nome.isBlank() || email.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
                        errorMessage = "Preencha todos os dados obrigatórios"
                        showErrorDialog = true
                        return@Button
                    }

                    if (senha != confirmarSenha) {
                        errorMessage = "As senhas não coincidem."
                        showErrorDialog = true
                        return@Button
                    }

                    if (isPessoaFisicaSelected) {
                        val (cpfValido, cpfMsg) = validarCPF(cpf)
                        if (!cpfValido) {
                            errorMessage = cpfMsg
                            showErrorDialog = true
                            return@Button
                        }
                    }

                    val (telValido, telMsg) = validarTelefone(telefone)
                    if (!telValido) {
                        errorMessage = telMsg
                        showErrorDialog = true
                        return@Button
                    }

                    if (!validarEmail(email)) {
                        errorMessage = "E-mail inválido. Use apenas Gmail, Hotmail ou Yahoo e não deixe vazio."
                        showErrorDialog = true
                        return@Button
                    }

                    val usuario = Usuario(
                        nome = nome,
                        email = email,
                        senha_hash = senha,
                        perfil = if (isPessoaFisicaSelected) "consumidor" else "estabelecimento",
                        cpf = if (isPessoaFisicaSelected) cpf else null,
                        cnpj = if (!isPessoaFisicaSelected) cnpj else null,
                        data_nascimento = "1900-01-01"
                    )

                    coroutineScope.launch {
                        try {
                            val response = client.cadastrarUsuario(usuario)
                            println("Usuário cadastrado: $response")
                            showSuccessDialog = true
                        } catch (e: Exception) {
                            println("Falha na requisição: ${e.message}")
                            errorMessage = "Falha na conexão. Verifique sua internet."
                            showErrorDialog = true
                        }
                    }
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(text = "Tem uma conta? ", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = "Faça login",
                    fontSize = 14.sp,
                    color = primaryOrange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { onGoToLogin() }
                )
            }
        }

        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        showSuccessDialog = false
                        onGoToLogin()
                    }) {
                        Text("OK")
                    }
                },
                title = { Text("Sucesso") },
                text = { Text("Usuário cadastrado com sucesso!") }
            )
        }
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = { TextButton(onClick = { showErrorDialog = false }) { Text("OK") } },
                title = { Text("Erro") },
                text = { Text(errorMessage) }
            )
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = textFieldColors,
        visualTransformation = visualTransformation,
        shape = RoundedCornerShape(28.dp)
    )
}

@Composable
fun SenhaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: (Boolean) -> Unit,
    label: String,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { onMostrarSenhaChange(!mostrarSenha) }) {
                Icon(
                    imageVector = if (mostrarSenha) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Mostrar/Ocultar senha"
                )
            }
        },
        colors = colors,
        shape = RoundedCornerShape(28.dp)
    )
}