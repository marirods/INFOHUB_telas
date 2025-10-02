package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.infohub_telas.R
import com.example.infohub_telas.model.ValidarCodigoRequest
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaConfirmarCodigo(navController: NavHostController?) {

    var codigo1 by remember { mutableStateOf("") }
    var codigo2 by remember { mutableStateOf("") }
    var codigo3 by remember { mutableStateOf("") }
    var codigo4 by remember { mutableStateOf("") }

    val codigo by remember { derivedStateOf { codigo1 + codigo2 + codigo3 + codigo4 } }
    var isLoading by remember { mutableStateOf(false) }

    val retrofitFactory = RetrofitFactory()
    val userApi = retrofitFactory.getInfoHub_UserService()

    // mari, aqui vou puxar o email da outra tela para voce usar no seu componente
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val emailSalvo = prefs.getString("email", "") ?: ""
    // calmaa, tem aqla interrogacao para se nao tiver nada vai vim nulo

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.elipse_vermelha),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp)
                .offset(x = (-41).dp, y = 80.dp)
                .align(Alignment.TopStart)
        )

        Image(
            painter = painterResource(id = R.drawable.elipse_laranja),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .offset(x = (6).dp, y = (-10).dp)
                .align(Alignment.TopEnd)
        )

        Image(
            painter = painterResource(id = R.drawable.mulher_lendo),
            contentDescription = "",
            modifier = Modifier
                .size(230.dp)
                .align(Alignment.TopCenter)
                .offset(y = 70.dp)
        )

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
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                OutlinedTextField(
                    value = codigo2,
                    onValueChange = { if (it.length <= 1) codigo2 = it },
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                OutlinedTextField(
                    value = codigo3,
                    onValueChange = { if (it.length <= 1) codigo3 = it },
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )

                OutlinedTextField(
                    value = codigo4,
                    onValueChange = { if (it.length <= 1) codigo4 = it },
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp),
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = {
                    // Navegação direta opcional
                    navController?.navigate("criar_senha")

                    // Somente se o código tiver 4 dígitos, chama a API
                    if (codigo.length == 4) {
                        isLoading = true
                        GlobalScope.launch(Dispatchers.IO) {
                            val call = userApi.validarCodigo(
                                ValidarCodigoRequest(emailSalvo, codigo)
                            )
                            val resposta = call.execute()
                            launch(Dispatchers.Main) {
                                isLoading = false
                                if (resposta.isSuccessful && resposta.body()?.sucesso == true) {
                                    println("Código válido")
                                    navController?.navigate("criar_senha") // navegação aqui
                                } else {
                                    println("Código inválido ou erro: ${resposta.body()?.mensagem}")
                                }
                            }
                        }
                    } else {
                        println("****************** PREENCHA TODOS OS CAMPOS CORRETAMENTE ********************")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B))
            ) {
                // Conteúdo visual do botão
                Text(
                    text = "Continuar",
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Não recebeu código? ", color = Color.Gray, fontSize = 14.sp)
                Text(
                    "Enviar novamente",
                    color = Color(color = 0xFFF25992E),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaConfirmarCodigoPreview() {
    InfoHub_telasTheme {
        TelaConfirmarCodigo(null)
    }
}

