package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.infohub_telas.model.ValidarCodigoRequest
import com.example.infohub_telas.service.InfoHubClient
import com.example.infohub_telas.util.SettingsFactory
import com.example.infohub_telas.util.showToast
import com.russhwolf.settings.set
import kotlinx.coroutines.launch

class TelaConfirmarCodigoScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        TelaConfirmarCodigo(onGoToCriarNovaSenha = {
            navigator.push(TelaCriarNovaSenhaScreen())
        })
    }
}

@Composable
fun TelaConfirmarCodigo(onGoToCriarNovaSenha: () -> Unit) {
    var codigo1 by remember { mutableStateOf("") }
    var codigo2 by remember { mutableStateOf("") }
    var codigo3 by remember { mutableStateOf("") }
    var codigo4 by remember { mutableStateOf("") }
    val codigo by remember { derivedStateOf { codigo1 + codigo2 + codigo3 + codigo4 } }
    var isLoading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val client = InfoHubClient()
    val settings = remember { SettingsFactory().create() }
    val emailSalvo = settings.getString("email_recuperacao", "")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // TODO: Adicionar imagens
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(90.dp))

            Text(
                text = "Confirme seu e-mail",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 23.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Código enviado para: $emailSalvo",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(7.dp))
            Text(
                text = "Digite seu código:",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(3.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedTextField(
                    value = codigo1,
                    onValueChange = { if (it.length <= 1) codigo1 = it },
                    modifier = Modifier.width(60.dp).height(60.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                OutlinedTextField(
                    value = codigo2,
                    onValueChange = { if (it.length <= 1) codigo2 = it },
                    modifier = Modifier.width(60.dp).height(60.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                OutlinedTextField(
                    value = codigo3,
                    onValueChange = { if (it.length <= 1) codigo3 = it },
                    modifier = Modifier.width(60.dp).height(60.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                OutlinedTextField(
                    value = codigo4,
                    onValueChange = { if (it.length <= 1) codigo4 = it },
                    modifier = Modifier.width(60.dp).height(60.dp),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val codigoDigits = codigo.filter { it.isDigit() }
                    if (codigoDigits.length != 4) {
                        showToast("O código deve ter 4 dígitos numéricos")
                        return@Button
                    }

                    isLoading = true
                    scope.launch {
                        try {
                            val response = client.validarCodigo(ValidarCodigoRequest(codigoDigits))
                            if (response.status) {
                                settings["codigo_validado"] = codigoDigits
                                settings["id_usuario_recuperacao"] = response.id_usuario
                                showToast("✅ Código válido!")
                                onGoToCriarNovaSenha()
                            } else {
                                showToast("❌ ${response.message}")
                            }
                        } catch (e: Exception) {
                            showToast("Falha na chamada: ${e.message}")
                        } finally {
                            isLoading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B))
            ) {
                 if (isLoading) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                } else {
                    Text("Continuar", color = Color.Black, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}