package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.*
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ========================== FUNÇÕES DE FORMATAÇÃO ==========================
fun formatarCPF(cpf: String): String {
    val numeros = cpf.filter { it.isDigit() }.take(11)
    return buildString {
        for (i in numeros.indices) {
            append(numeros[i])
            when (i) {
                2, 5 -> append('.')
                8 -> append('-')
            }
        }
    }
}

fun formatarCNPJ(cnpj: String): String {
    val numeros = cnpj.filter { it.isDigit() }.take(14)
    return buildString {
        for (i in numeros.indices) {
            append(numeros[i])
            when (i) {
                1, 4 -> append('.')
                7 -> append('/')
                11 -> append('-')
            }
        }
    }
}

fun formatarTelefone(telefone: String): String {
    val numeros = telefone.filter { it.isDigit() }.take(11)
    return when {
        numeros.length <= 2 -> "(${numeros}"
        numeros.length <= 6 -> "(${numeros.substring(0, 2)}) ${numeros.substring(2)}"
        numeros.length <= 10 -> "(${numeros.substring(0, 2)}) ${numeros.substring(2, 6)}-${numeros.substring(6)}"
        else -> "(${numeros.substring(0, 2)}) ${numeros.substring(2, 7)}-${numeros.substring(7, 11)}"
    }
}

// ========================== VALIDAÇÕES ==========================
fun validarCPF(cpf: String): Pair<Boolean, String> {
    val cpfNumeros = cpf.filter { it.isDigit() }
    if (cpfNumeros.isEmpty()) return false to "CPF não pode ser vazio"
    if (cpfNumeros.length != 11) return false to "CPF deve ter 11 dígitos"
    if (cpfNumeros.all { it == cpfNumeros.first() }) return false to "CPF inválido (números repetidos)"

    val digitos = cpfNumeros.map { it.toString().toInt() }
    for (i in 9..10) {
        val soma = (0 until i).sumOf { digitos[it] * (i + 1 - it) }
        val resto = (soma * 10) % 11 % 10
        if (digitos[i] != resto) return false to "CPF inválido"
    }
    return true to ""
}

fun validarCNPJ(cnpj: String): Pair<Boolean, String> {
    val cnpjNumeros = cnpj.filter { it.isDigit() }
    if (cnpjNumeros.isEmpty()) return false to "CNPJ não pode ser vazio"
    if (cnpjNumeros.length != 14) return false to "CNPJ deve ter 14 dígitos"
    if (cnpjNumeros.all { it == cnpjNumeros.first() }) return false to "CNPJ inválido (números repetidos)"

    val pesosPrimeiro = intArrayOf(5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)
    val pesosSegundo = intArrayOf(6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2)

    try {
        val numeros = cnpjNumeros.map { it.toString().toInt() }
        val soma1 = (0..11).sumOf { numeros[it] * pesosPrimeiro[it] }
        val resto1 = soma1 % 11
        val digito1 = if (resto1 < 2) 0 else 11 - resto1

        val soma2 = (0..12).sumOf { numeros[it] * pesosSegundo[it] }
        val resto2 = soma2 % 11
        val digito2 = if (resto2 < 2) 0 else 11 - resto2

        if (numeros[12] != digito1 || numeros[13] != digito2) return false to "CNPJ inválido"
    } catch (e: Exception) {
        return false to "CNPJ inválido"
    }

    return true to ""
}

fun validarTelefone(telefone: String): Pair<Boolean, String> {
    val tel = telefone.filter { it.isDigit() }
    if (tel.isEmpty()) return false to "Telefone não pode ser vazio"
    if (tel.length !in 10..11) return false to "Telefone deve ter 10 ou 11 dígitos"
    if (tel.all { it == tel.first() }) return false to "Telefone inválido (números repetidos)"
    if (!tel.startsWith("9", 2) && tel.length == 11) return false to "Celular deve começar com 9"
    return true to ""
}

fun validarEmail(email: String): Pair<Boolean, String> {
    if (email.isBlank()) return false to "E-mail não pode ser vazio"
    val regex = Regex("^[\\w.+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")
    if (!regex.matches(email)) return false to "E-mail inválido"
    return true to ""
}

fun validarSenha(senha: String): Pair<Boolean, String> {
    if (senha.length < 6) return false to "Senha deve ter no mínimo 6 caracteres"
    val regex = Regex("^(?=.*[A-Za-z])(?=.*\\d).{6,}$")
    if (!regex.matches(senha)) return false to "Senha deve conter letras e números"
    return true to ""
}

fun validarConfirmacaoSenha(senha: String, confirmar: String): Pair<Boolean, String> {
    if (senha != confirmar) return false to "As senhas não coincidem"
    return true to ""
}

// ========================== TELA ==========================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController?) {
    val context = LocalContext.current
    val userApi = RetrofitFactory().getInfoHub_UserService()

    var isPessoaFisicaSelected by remember { mutableStateOf(true) }
    var nome by remember { mutableStateOf("") }
    var cpfState by remember { mutableStateOf(TextFieldValue("")) }
    var cnpjState by remember { mutableStateOf(TextFieldValue("")) }
    var telefoneState by remember { mutableStateOf(TextFieldValue("")) }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cadastro),
                contentDescription = "Cadastro",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotaoPessoaDeslizante(
                isPessoaFisica = isPessoaFisicaSelected,
                onPessoaFisicaClick = { isPessoaFisicaSelected = true },
                onPessoaJuridicaClick = { isPessoaFisicaSelected = false }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isPessoaFisicaSelected) {
                FormPessoaFisica(
                    data = FormPessoaFisicaData(
                        nome = nome,
                        cpf = cpfState.text,
                        telefone = telefoneState.text,
                        email = email,
                        senha = senha,
                        confirmarSenha = confirmarSenha,
                        mostrarSenha = mostrarSenha,
                        mostrarConfirmarSenha = mostrarConfirmarSenha
                    ),
                    onDataChange = { updated ->
                        nome = updated.nome

                        val formattedCpf = formatarCPF(updated.cpf)
                        if (formattedCpf != cpfState.text) {
                            cpfState =  cpfState.copy(
                                text = formattedCpf,
                                selection = TextRange(formattedCpf.length)
                            )
                        }

                        val formattedTel = formatarTelefone(updated.telefone)
                        if (formattedTel != telefoneState.text) {
                            telefoneState = telefoneState.copy(
                                text = formattedTel,
                                selection = TextRange(formattedTel.length)
                            )
                        }

                        email = updated.email
                        senha = updated.senha
                        confirmarSenha = updated.confirmarSenha
                        mostrarSenha = updated.mostrarSenha
                        mostrarConfirmarSenha = updated.mostrarConfirmarSenha
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                FormPessoaJuridica(
                    nome = nome,
                    onNomeChange = { nome = it },
                    cnpj = cnpjState.text,
                    onCnpjChange = {
                        val formatted = formatarCNPJ(it)
                            cnpjState = cnpjState.copy(
                                text = formatted,
                                selection = TextRange(formatted.length)
                            )
                    },
                    telefone = telefoneState.text,
                    onTelefoneChange = {
                        val formatted = formatarTelefone(it)
                            telefoneState = telefoneState.copy(
                                text = formatted,
                                selection = TextRange(formatted.length)
                            )

                    },
                    email = email,
                    onEmailChange = { email = it },
                    senha = senha,
                    onSenhaChange = { senha = it },
                    confirmarSenha = confirmarSenha,
                    onConfirmarSenhaChange = { confirmarSenha = it },
                    mostrarSenha = mostrarSenha,
                    onMostrarSenhaChange = { mostrarSenha = it },
                    mostrarConfirmarSenha = mostrarConfirmarSenha,
                    onMostrarConfirmarSenhaChange = { mostrarConfirmarSenha = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { /* validação e cadastro */ },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Cadastrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaCadastroPreview() {
    InfoHub_telasTheme {
        TelaCadastro(null)
    }
}
