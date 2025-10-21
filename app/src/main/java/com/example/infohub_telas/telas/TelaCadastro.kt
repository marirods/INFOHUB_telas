package com.example.infohub_telas.telas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.BotaoPessoaDeslizante
import com.example.infohub_telas.components.FormPessoaFisica
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

    // --- Estados ---
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
    val userApi = RetrofitFactory().getInfoHub_UserService()

    // --- Funções de alteração (sem lambda ::) ---
    fun alterarPessoaFisica() { isPessoaFisicaSelected = true }
    fun alterarPessoaJuridica() { isPessoaFisicaSelected = false }
    fun alterarNome(valor: String) { nome = valor }
    fun alterarCpf(valor: String) { cpf = valor }
    fun alterarCnpj(valor: String) { cnpj = valor }
    fun alterarTelefone(valor: String) { telefone = valor }
    fun alterarEmail(valor: String) { email = valor }
    fun alterarSenha(valor: String) { senha = valor }
    fun alterarConfirmarSenha(valor: String) { confirmarSenha = valor }
    fun alternarMostrarSenha(valor: Boolean) { mostrarSenha = valor }
    fun alternarMostrarConfirmarSenha(valor: Boolean) { mostrarConfirmarSenha = valor }

    // ==================== Layout ====================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {

        // 🔘 Botão deslizante
        BotaoPessoaDeslizante(
            isPessoaFisica = isPessoaFisicaSelected,
            onPessoaFisicaClick = ::alterarPessoaFisica,
            onPessoaJuridicaClick = ::alterarPessoaJuridica
        )


        Spacer(modifier = Modifier.height(20.dp))

        // Formulários
        if (isPessoaFisicaSelected) {
            FormPessoaFisica(
                nome = nome,
                onNomeChange = ::alterarNome,
                cpf = cpf,
                onCpfChange = ::alterarCpf,
                telefone = telefone,
                onTelefoneChange = ::alterarTelefone,
                email = email,
                onEmailChange = ::alterarEmail,
                senha = senha,
                onSenhaChange = ::alterarSenha,
                confirmarSenha = confirmarSenha,
                onConfirmarSenhaChange = ::alterarConfirmarSenha,
                mostrarSenha = mostrarSenha,
                onMostrarSenhaChange = ::alternarMostrarSenha,
                mostrarConfirmarSenha = mostrarConfirmarSenha,
                onMostrarConfirmarSenhaChange = ::alternarMostrarConfirmarSenha
            )
        } else {
            FormPessoaJuridica(
                nome = nome,
                onNomeChange = ::alterarNome,
                cnpj = cnpj,
                onCnpjChange = ::alterarCnpj,
                telefone = telefone,
                onTelefoneChange = ::alterarTelefone,
                email = email,
                onEmailChange = ::alterarEmail,
                senha = senha,
                onSenhaChange = ::alterarSenha,
                confirmarSenha = confirmarSenha,
                onConfirmarSenhaChange = ::alterarConfirmarSenha,
                mostrarSenha = mostrarSenha,
                onMostrarSenhaChange = ::alternarMostrarSenha,
                mostrarConfirmarSenha = mostrarConfirmarSenha,
                onMostrarConfirmarSenhaChange = ::alternarMostrarConfirmarSenha
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔒 Botão de cadastro
        Button(
            onClick = {
                if (nome.isBlank() || email.isBlank() || senha.isBlank() || confirmarSenha.isBlank()) {
                    mensagem = "Preencha todos os dados obrigatórios"
                    sucesso = false
                    return@Button
                }
                if (senha != confirmarSenha) {
                    mensagem = "As senhas não coincidem"
                    sucesso = false
                    return@Button
                }
                if (isPessoaFisicaSelected) {
                    val (cpfValido, cpfMsg) = validarCPF(cpf)
                    if (!cpfValido) {
                        mensagem = cpfMsg
                        sucesso = false
                        return@Button
                    }
                }
                val (telValido, telMsg) = validarTelefone(telefone)
                if (!telValido) {
                    mensagem = telMsg
                    sucesso = false
                    return@Button
                }
                if (!validarEmail(email)) {
                    mensagem = "E-mail inválido. Use Gmail, Hotmail ou Yahoo"
                    sucesso = false
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

                userApi.cadastrarUsuario(usuario).enqueue(object : Callback<Usuario> {
                    override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                        if (response.isSuccessful) {
                            mensagem = "Usuário cadastrado com sucesso!"
                            sucesso = true
                            navController?.navigate("login") {
                                popUpTo("cadastro") { inclusive = true }
                            }
                        } else {
                            mensagem = "Erro ao cadastrar usuário."
                            sucesso = false
                        }
                    }

                    override fun onFailure(call: Call<Usuario>, t: Throwable) {
                        mensagem = "Falha na conexão. Verifique sua internet."
                        sucesso = false
                    }
                })
            },
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

        Spacer(modifier = Modifier.height(16.dp))

        // Mensagem de retorno
        if (mensagem.isNotEmpty()) {
            Text(
                text = mensagem,
                color = if (sucesso) Color(0xFF25992E) else Color.Red,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row {
            Text(text = "Tem uma conta? ", fontSize = 14.sp, color = Color.Black)
            Text(
                text = "Faça login",
                fontSize = 14.sp,
                color = primaryOrange,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { navController?.navigate("login") }
            )
        }
    }

    // 🔶 Rodapé Laranja
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
            .background(primaryOrange, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
//            .align(Alignment.BottomCenter)
    )
}

@Preview(showSystemUi = true)
@Composable
fun TelaCadastroPreview() {
    InfoHub_telasTheme {
        TelaCadastro(null)
    }
}
