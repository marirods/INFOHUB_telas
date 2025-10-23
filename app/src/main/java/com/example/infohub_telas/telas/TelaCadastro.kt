package com.example.infohub_telas.telas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BotaoPessoaDeslizante
import com.example.infohub_telas.components.FormPessoaFisica
import com.example.infohub_telas.components.FormPessoaFisicaData
import com.example.infohub_telas.components.FormPessoaJuridica
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ========================== VALIDAÇÕES ==========================
fun validarCPF(cpf: String, cpfsCadastrados: List<String> = emptyList()): Pair<Boolean, String> {
    val cpfComum = cpf.filter { it.isDigit() }
    if (cpfComum.isEmpty()) return false to "CPF não pode ser vazio"
    if (cpfComum.length != 11) return false to "CPF deve ter 11 dígitos"
    if (cpfComum.all { it == cpfComum.first() }) return false to "CPF não pode ter todos números iguais"
    if (cpfsCadastrados.any { it.filter(Char::isDigit) == cpfComum }) return false to "CPF já cadastrado"
    return true to ""
}

fun validarTelefone(telefone: String, telefonesCadastrados: List<String> = emptyList()): Pair<Boolean, String> {
    val telefoneLimpo = telefone.filter { it.isDigit() }
    if (telefoneLimpo.isEmpty()) return false to "Telefone não pode ser vazio"
    if (telefoneLimpo.length !in 10..11) return false to "Telefone deve ter 10 ou 11 dígitos"
    if (telefoneLimpo.all { it == telefoneLimpo.first() }) return false to "Telefone não pode ter todos números iguais"
    if (telefoneLimpo in telefonesCadastrados.map { it.filter(Char::isDigit) }) return false to "Telefone já cadastrado"
    return true to ""
}

fun validarEmail(email: String, emailsCadastrados: List<String> = emptyList()): Boolean {
    if (email.isBlank()) return false
    val regex = Regex("^[\\w.+-]+@(?:gmail\\.com|hotmail\\.com|yahoo\\.com)$")
    if (!email.matches(regex)) return false
    if (emailsCadastrados.any { it.equals(email, ignoreCase = true) }) return false
    return true
}

// ========================== TELA CADASTRO ==========================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController?) {
    var isPessoaFisicaSelected by remember { mutableStateOf(true) }
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }
    var mensagem by remember { mutableStateOf("") }
    var sucesso by remember { mutableStateOf(false) }

    val primaryOrange = Color(0xFFFF8C00)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Imagens de fundo
        Image(
            painter = painterResource(id = R.drawable.bola_cadastro_vermelho),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-20).dp, y = 30.dp)
                .size(70.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.bola_laranja_cadastro),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = 0.dp)
                .size(130.dp)
        )

        // Conteúdo rolável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // permite rolagem
                .padding(horizontal = 24.dp, vertical = 35.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo/Cadastro
            Image(
                painter = painterResource(id = R.drawable.cadastro),
                contentDescription = "Cadastro",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(8.dp))

            BotaoPessoaDeslizante(
                isPessoaFisica = isPessoaFisicaSelected,
                onPessoaFisicaClick = { isPessoaFisicaSelected = true },
                onPessoaJuridicaClick = { isPessoaFisicaSelected = false }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Formulário dinâmico
            if (isPessoaFisicaSelected) {
                FormPessoaFisica(
                    data = FormPessoaFisicaData(
                        nome = nome,
                        cpf = cpf,
                        telefone = telefone,
                        email = email,
                        senha = senha,
                        confirmarSenha = confirmarSenha,
                        mostrarSenha = mostrarSenha,
                        mostrarConfirmarSenha = mostrarConfirmarSenha
                    ),
                    onDataChange = { updated ->
                        nome = updated.nome
                        cpf = updated.cpf
                        telefone = updated.telefone
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
                    cnpj = cnpj,
                    onCnpjChange = { cnpj = it },
                    telefone = telefone,
                    onTelefoneChange = { telefone = it },
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

            // Botão Cadastrar
            Button(
                onClick = { /* validações e cadastro */ },
                modifier = Modifier
                    .fillMaxWidth()
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

            Spacer(modifier = Modifier.height(12.dp))

            // Texto "Já tem uma conta?"
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Já tem uma conta? ", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = "Faça login",
                    fontSize = 14.sp,
                    color = primaryOrange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController?.navigate("login") }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensagem de erro ou sucesso
            if (mensagem.isNotEmpty()) {
                Text(
                    text = mensagem,
                    color = if (sucesso) Color(0xFF25992E) else Color.Red,
                    fontSize = 14.sp
                )
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
