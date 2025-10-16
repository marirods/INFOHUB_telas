package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.infohub_telas.model.RecuperarSenhaRequest
import com.example.infohub_telas.service.InfoHubClient
import com.example.infohub_telas.util.SettingsFactory
import com.russhwolf.settings.set
import kotlinx.coroutines.launch

class TelaRedefinicaoSenhaScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TelaRedefinicaoSenha(onGoToConfirmarCodigo = {
            navigator.push(TelaConfirmarCodigoScreen())
        })
    }
}

@Composable
fun TelaRedefinicaoSenha(onGoToConfirmarCodigo: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val client = InfoHubClient()
    val settings = remember { SettingsFactory().create() }

    fun validarEmail(email: String): Boolean {
        return email.isNotBlank() && email.matches(Regex("^[\\w-.]+@([\\w-]+\\.){1,2}[\\w-]{2,4}$"))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .background(Color(0xFFFFD966))
            ) {
                // TODO: Adicionar imagens
                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Recuperar Senha",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 23.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Preencha o campo abaixo com o seu e-mail:",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        placeholder = { Text("Email*") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                        ),
                        shape = RoundedCornerShape(28.dp),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Email,
                                contentDescription = "Ícone do e-mail",
                                tint = Color.Gray,
                                modifier = Modifier.padding(start = 18.dp).size(24.dp)
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (validarEmail(email)) {
                        isLoading = true
                        val request = RecuperarSenhaRequest(email)

                        coroutineScope.launch {
                            try {
                                val response = client.recuperarSenha(request)
                                settings["email_recuperacao"] = email
                                println("Resposta da API: ${response.mensagem}")
                                onGoToConfirmarCodigo()
                            } catch (e: Exception) {
                                errorMessage = "E-mail não encontrado ou falha na conexão."
                                showErrorDialog = true
                                println("Erro na API: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    } else {
                        errorMessage = "Por favor, insira um e-mail válido."
                        showErrorDialog = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Recuperar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
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