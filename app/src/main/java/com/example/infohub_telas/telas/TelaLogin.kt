package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin() {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Image(
            painter = painterResource(id = R.drawable.elipse1),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(100.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.elipse2),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(150.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.4f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(280.dp)
                        .background(Color.White, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login_comprass),
                        contentDescription = "LOGIN",
                        modifier = Modifier
                            .size(240.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }


            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    text = "Bem vindo de volta!",
                    fontSize = 28.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )


                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = {
                        Text(
                            "E-mail ou CPF",
                            color = Color.Black
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp),

                    )



                Spacer(modifier = Modifier.height(16.dp))


                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = { Text("Senha", color = Color.Black) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.Black,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(28.dp),
                    singleLine = true,
                    visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                            Icon(
                                painter = painterResource(
                                    id = if (mostrarSenha) R.drawable.olho_aberto else R.drawable.olho_fechado
                                ),
                                contentDescription = "Mostrar/Ocultar senha",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Recuperar senha",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.align(Alignment.End)
                )

                Spacer(modifier = Modifier.height(32.dp))


                Button(
                    onClick = { },
                    modifier = Modifier
                        .width(220.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(color = 0xFFFF25992E)
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
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Cadastre-se aqui",
                        color = Color(color = 0xFFFF25992E),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
//                    fontStyle = FontStyle.Italic
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
        TelaLogin()
    }
}
