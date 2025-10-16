package com.example.infohub_telas.telas

import androidx.compose.foundation.background
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
import com.example.infohub_telas.model.AtualizarSenhaRequest
import com.example.infohub_telas.service.InfoHubClient
import com.example.infohub_telas.util.SettingsFactory
import com.example.infohub_telas.util.showToast
import kotlinx.coroutines.launch

class TelaCriarNovaSenhaScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TelaCriarNovaSenha(onSenhaRedefinida = {
            navigator.popUntil { it is TelaLoginScreen } // Volta para a tela de login
        })
    }
}

@Composable
fun TelaCriarNovaSenha(onSenhaRedefinida: () -> Unit) {
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()
    val client = InfoHubClient()
    val settings = remember { SettingsFactory().create() }
    val codigoSalvo = settings.getString("codigo_validado", "")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(560.dp)
                    .background(Color(0xFFFFD966))
            ) {
                // TODO: Adicionar imagens
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(horizontal = 24.dp, vertical = 20.dp)
                ) {
                    Text(
                        text = "Criar nova senha",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 23.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Preencha o campo abaixo com a sua nova senha:",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        placeholder = { Text("Nova senha*") },
                        modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth(),
                        visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                                Icon(
                                    imageVector = if (senhaVisivel) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Mostrar/Ocultar senha"
                                )
                            }
                        },
                        shape = RoundedCornerShape(28.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Confirmar nova senha",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 23.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Confirme sua nova senha:",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    OutlinedTextField(
                        value = confirmarSenha,
                        onValueChange = { confirmarSenha = it },
                        placeholder = { Text("Confirmar nova senha*") },
                        modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth(),
                        visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { confirmarSenhaVisivel = !confirmarSenhaVisivel }) {
                                Icon(
                                    imageVector = if (confirmarSenhaVisivel) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = "Mostrar/Ocultar senha"
                                )
                            }
                        },
                        shape = RoundedCornerShape(28.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (senha.isEmpty() || confirmarSenha.isEmpty()) {
                        showToast("⚠️ Preencha todos os campos corretamente")
                        return@Button
                    }
                    if (senha != confirmarSenha) {
                        showToast("⚠️ As senhas não coincidem")
                        return@Button
                    }

                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val response = client.atualizarSenha(AtualizarSenhaRequest(codigoSalvo, senha))
                            if (response.status) {
                                showToast("✅ Senha cadastrada com sucesso!")
                                onSenhaRedefinida()
                            } else {
                                showToast("❌ ${response.message}")
                            }
                        } catch (e: Exception) {
                            showToast("❌ Erro na requisição: ${e.message}")
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Entrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}