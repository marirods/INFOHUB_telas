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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.infohub_telas.model.LoginRequest
import com.example.infohub_telas.service.InfoHubClient
import com.example.infohub_telas.service.TokenStorage
import kotlinx.coroutines.launch

class TelaLoginScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TelaLogin(
            onGoToCadastro = { navigator.push(TelaCadastroScreen()) },
            onGoToRedefinicaoSenha = { navigator.push(TelaRedefinicaoSenhaScreen()) },
            onLoginSuccess = { /* TODO: Navegar para a tela principal */ }
        )
    }
}

@Composable
fun TelaLogin(onGoToCadastro: () -> Unit, onGoToRedefinicaoSenha: () -> Unit, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()
    val client = InfoHubClient()
    val tokenStorage = TokenStorage()

    val primaryOrange = Color(0xFFF9A01B)
    val buttonGreen = Color(0xFF25992E)
    val textColor = Color.Black
    val linkColor = Color(0xFF25992E)

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                // TODO: Adicionar imagem de login de forma multiplataforma
            }

            Column(
                modifier = Modifier
                    .background(primaryOrange)
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "BEM VINDO DE VOLTA!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-mail", color = Color.DarkGray) },
                    modifier = Modifier.fillMaxWidth().padding(top = 30.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = { Text("Senha", color = Color.DarkGray) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                            Icon(
                                imageVector = if (mostrarSenha) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Mostrar/Ocultar senha"
                            )
                        }
                    },
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                    )
                )
                 Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ){
                    TextButton(
                        onClick = onGoToRedefinicaoSenha
                    ) {
                        Text(
                            text = "Recuperar senha",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black

                        )
                    }

                    Spacer(modifier = Modifier
                        .height(12.dp)

                    )
                }

                 Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        isLoading = true
                        val loginReq = LoginRequest(email, senha)

                        coroutineScope.launch {
                            try {
                                val response = client.logarUsuario(loginReq)
                                if (response.status) {
                                    tokenStorage.saveToken(response.token)
                                    println("Token salvo: ${response.token}")
                                    onLoginSuccess()
                                } else {
                                    errorMessage = "E-mail ou senha inválidos."
                                    showErrorDialog = true
                                }
                            } catch (e: Exception) {
                                errorMessage = "Falha na conexão. Verifique sua internet."
                                showErrorDialog = true
                                println("Falha: ${e.message}")
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.width(220.dp).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonGreen),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Entrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text("Não tem uma conta? ", color = textColor, fontSize = 14.sp)
                    Text(
                        "Cadastre-se aqui",
                        color = linkColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onGoToCadastro() }
                    )
                }
            }
        }

        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = { TextButton(onClick = { showErrorDialog = false }) { Text("OK") } },
                title = { Text("Erro de Login") },
                text = { Text(errorMessage) }
            )
        }
    }
}