package com.example.infohub_telas.telas

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavHostController
import com.example.infohub_telas.model.AtualizarSenhaRequest
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaCriarNovaSenha(navController: NavHostController?) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var confirmarSenhaVisivel by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    val retrofitFactory = RetrofitFactory()
    val userApi = retrofitFactory.getInfoHub_UserService()

    // mari, aqui vou puxar o email da outra tela para voce usar no seu componente
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val codigoSalvo = prefs.getString("codigo", "") ?: ""



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
                Image(
                    painter = painterResource(id = R.drawable.elipse_vermelha),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .offset(x = (-40).dp, y = 60.dp)
                        .align(Alignment.TopStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.amarela_elipse),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .offset(x = 0.dp, y = (-20).dp)
                        .align(Alignment.TopEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.elipse_branca),
                    contentDescription = null,
                    modifier = Modifier
                        .size(260.dp)
                        .offset(x = 0.dp, y = (-10).dp)
                        .align(Alignment.TopEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.mulher_lendo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 50.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = (0).dp)
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 24.dp)
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
                        onValueChange = { novoValor -> senha = novoValor },
                        placeholder = { Text("Nova senha*") },
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .fillMaxWidth(),
                        visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick =  {confirmarSenhaVisivel = !confirmarSenhaVisivel}){
                                Icon(
                                    painter = painterResource(
                                        id = if (confirmarSenhaVisivel) R.drawable.olho_aberto else R.drawable.olho_fechado
                                    ),
                                    contentDescription = "Mostrar/Ocultar senha"
                                )
                            }
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
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
                        onValueChange = { novoValor -> confirmarSenha = novoValor },
                        placeholder = { Text("Confirmar nova senha*") },
                        modifier = Modifier
                            .padding(vertical = 6.dp)
                            .fillMaxWidth(),
                        visualTransformation = if (confirmarSenhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick =  {confirmarSenhaVisivel = !confirmarSenhaVisivel}){
                                Icon(
                                    painter = painterResource(
                                        id = if (confirmarSenhaVisivel) R.drawable.olho_aberto else R.drawable.olho_fechado
                                    ),
                                    contentDescription = "Mostrar/Ocultar senha"
                                )
                            }
                        },

                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )


                }


            }
            TextButton(
                onClick = {
                    // Validações básicas
                    if (senha.isEmpty() || confirmarSenha.isEmpty()) {
                        Toast.makeText(context, "⚠️ Preencha todos os campos corretamente", Toast.LENGTH_SHORT).show()
                    } else if (senha.length < 10) {
                        Toast.makeText(context, "⚠️ A senha deve ter no mínimo 10 caracteres", Toast.LENGTH_SHORT).show()
                    } else if (senha != confirmarSenha) {
                        Toast.makeText(context, "⚠️ As senhas não coincidem", Toast.LENGTH_SHORT).show()
                    } else if (!senha.any { it.isUpperCase() }) {
                        Toast.makeText(context, "⚠️ A senha deve conter pelo menos 1 letra maiúscula", Toast.LENGTH_SHORT).show()
                    } else if (!senha.any { it.isLowerCase() }) {
                        Toast.makeText(context, "⚠️ A senha deve conter pelo menos 1 letra minúscula", Toast.LENGTH_SHORT).show()
                    } else if (!senha.any { it.isDigit() }) {
                        Toast.makeText(context, "⚠️ A senha deve conter pelo menos 1 número", Toast.LENGTH_SHORT).show()
                    } else if (!senha.any { !it.isLetterOrDigit() }) {
                        Toast.makeText(context, "⚠️ A senha deve conter pelo menos 1 caractere especial", Toast.LENGTH_SHORT).show()
                    } else {
                        // Se passou em todas as validações
                        isLoading = true

                        // 🔹 LOG de envio
                        println("📤 Enviando requisição de atualização de senha:")
                        println("Código: $codigoSalvo")
                        println("Senha: $senha")

                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val call = userApi.atualizarSenha(
                                    AtualizarSenhaRequest(
                                        codigoSalvo,
                                        senha
                                    )
                                )
                                val resposta = call.execute()

                                // 🔹 LOG da resposta bruta
                                println("📥 Resposta da API:")
                                println("HTTP code: ${resposta.code()}")
                                println("isSuccessful: ${resposta.isSuccessful}")
                                println("Body: ${resposta.body()}")
                                println("ErroBody: ${resposta.errorBody()?.string()}")

                                launch(Dispatchers.Main) {
                                    isLoading = false
                                    if (resposta.isSuccessful && resposta.body()?.status == true) {
                                        println("✅ API retornou sucesso: ${resposta.body()}")
                                        Toast.makeText(context, "✅ Senha cadastrada com sucesso!", Toast.LENGTH_SHORT).show()
                                        navController?.navigate("login")
                                    } else {
                                        println("❌ API retornou erro: ${resposta.body()?.message}")
                                        Toast.makeText(context, "❌ Erro: ${resposta.body()?.message ?: "Falha ao atualizar senha"}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } catch (e: Exception) {
                                // 🔹 LOG do erro
                                println("💥 Erro na requisição: ${e.message}")
                                e.printStackTrace()

                                launch(Dispatchers.Main) {
                                    isLoading = false
                                    Toast.makeText(context, "❌ Erro na requisição: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
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
                Text(
                    text = "Entrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }






//            Spacer(modifier = Modifier.height(32.dp))



        }

    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaCriarNovaSenhaPreview() {
    InfoHub_telasTheme {
        TelaCriarNovaSenha(null)
    }
}