package com.example.infohub_telas.telas

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.infohub_telas.R
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(navController: NavHostController?) {


    //Variaveis para usar o outlined

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }


    //Variaveis para a entrada do usuario
    var mostrarSenha by remember { mutableStateOf(false) }


    fun validar(): Boolean {
        val emailValido = Patterns.EMAIL_ADDRESS.matcher(email).matches()  // Verifica se o email é válido
        val senhaValida = senha.length >= 1  // Verifica se a senha tem pelo menos 1 caractere

        // Retorna true se ambos forem válidos
        return emailValido && senhaValida
    }


    var mostrarTelaSucesso by remember {
        mutableStateOf(value = false)
    }

    val UserApi = RetrofitFactory().getInfoHub_UserService()

    // Cores do layout
    val primaryOrange = Color(0xFFF9A01B) // Cor laranja ajustada para F9A01B
    val buttonGreen = Color(0xFF25992E)
    val textColor = Color.Black
    val linkColor = Color(0xFF25992E) // Cor do "Cadastre-se aqui" também ajustada

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // --- Imagens de Elipses (ícones) no topo ---
        Image(
            painter = painterResource(id = R.drawable.elipse2),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-30).dp, y = (-20).dp) // Ajuste a posição para fora da tela
                .size(100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.elipse1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 30.dp, y = (-20).dp) // Ajuste a posição para fora da tela
                .size(150.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Seção superior (Imagem da pessoa com carrinho) ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_comprass), // Nome do seu drawable
                    contentDescription = "LOGIN",
                    modifier = Modifier
                        .size(240.dp),
                    contentScale = ContentScale.Fit
                )
            }

            // --- Seção inferior (Fundo laranja com campos e botões) ---
            Column(
                modifier = Modifier
                    .background(color = primaryOrange)
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Bem vindo de volta!",
                    fontSize = 28.sp,
                    color = textColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text("E-mail ou CPF", color = Color.DarkGray)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = { Text("Senha", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        cursorColor = textColor,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true,
                    // A lógica para mostrar/ocultar a senha já estava correta,
                    // o estado 'mostrarSenha' controla se a transformação é aplicada.
                    visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                            Icon(
                                painter = painterResource(
                                    // O ícone correto é selecionado baseado no estado mostrarSenha
                                    id = if (mostrarSenha) R.drawable.olho_aberto else R.drawable.olho_fechado
                                ),
                                contentDescription = "Mostrar/Ocultar senha",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recuperar senha",
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp),
                    onClick = {
                        //Criar um cliente com dados informados
                        if(validar()){
                            val user = LoginUsuario(
                                email = email,
                                senha_hash = senha
                            )


                            //requisição POST para a API

                            GlobalScope.launch(Dispatchers.IO){
                                val novoCliente = UserApi.cadastrarUsuario(user).await()
                                mostrarTelaSucesso = true
                            }
                        }else{
                            print("***************** DADOS INCORRETOS **************")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = buttonGreen
                    ),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        text = "Entrar",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Não tem uma conta? ",
                        color = textColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Cadastre-se aqui",
                        color = linkColor, // Usa a cor laranja ajustada
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { /* TODO: Navegar para tela de cadastro */ }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TelaLoginPreview() {
    InfoHub_telasTheme {
        TelaLogin(navController = null )
    }
}