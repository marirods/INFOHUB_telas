package com.example.infohub_telas.telas

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroJuridico(navController: NavHostController?) {
    var nome by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }

    var tipoPessoa by remember { mutableStateOf("juridica") } // "fisica" ou "juridica"

    val primaryOrange = Color(0xFFFF8C00)
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bola_cadastro_vermelho),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-40).dp, y = 30.dp)
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

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cadastro),
                contentDescription = "Cadastro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- SEÇÃO DE SELEÇÃO PESSOA FÍSICA / JURÍDICA ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Física",
                        fontSize = 18.sp,
                        fontWeight = if (tipoPessoa == "fisica") FontWeight.Bold else FontWeight.Medium,
                        color = if (tipoPessoa == "fisica") Color.Gray else Color.Gray,
                        modifier = Modifier.clickable { tipoPessoa = "fisica" }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (tipoPessoa == "fisica") {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Jurídica",
                        fontSize = 18.sp,
                        fontWeight = if (tipoPessoa == "juridica") FontWeight.Bold else FontWeight.Medium,
                        color =  Color.Black,
                        modifier = Modifier.clickable { tipoPessoa = "juridica" }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (tipoPessoa == "juridica") {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                MeuCustomTextField(
                    value = nome,
                    onValueChange = { novoNome -> nome = novoNome },
                    placeholder = "Nome da empresa*",
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                MeuCustomTextField(
                    value = cnpj,
                    onValueChange = { novoCpf -> cnpj = novoCpf },
                    placeholder = "CNPJ*",
                    keyboardType = KeyboardType.Number,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                MeuCustomTextField(
                    value = telefone,
                    onValueChange = { novoTelefone -> telefone = novoTelefone },
                    placeholder = "Telefone*",
                    keyboardType = KeyboardType.Number,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                MeuCustomTextField(
                    value = email,
                    onValueChange = { novoEmail -> email = novoEmail },
                    placeholder = "E-mail*",
                    keyboardType = KeyboardType.Email,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                NovaSenhaTextField(
                    value = senha,
                    onValueChange = { novaSenha -> senha = novaSenha },
                    mostrarSenha = mostrarSenha,
                    onMostrarSenhaChange = { novoEstado -> mostrarSenha = novoEstado },
                    label = "Senha*",
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                NovaSenhaTextField(
                    value = confirmarSenha,
                    onValueChange = { novaSenha -> confirmarSenha = novaSenha },
                    mostrarSenha = mostrarConfirmarSenha,
                    onMostrarSenhaChange = { novoEstado -> mostrarConfirmarSenha = novoEstado },
                    label = "Confirme a senha*",
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .width(220.dp)
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

            Row {
                Text(text = "Tem uma conta? ", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = "Faça login",
                    fontSize = 14.sp,
                    color = primaryOrange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {  }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(20.dp)
                .background(primaryOrange, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeuCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = textFieldColors,
        shape = RoundedCornerShape(28.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NovaSenhaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: (Boolean) -> Unit,
    label: String,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        singleLine = true,
        visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = { onMostrarSenhaChange(!mostrarSenha) }) {
                Icon(
                    painter = painterResource(
                        id = if (mostrarSenha) R.drawable.olho_aberto else R.drawable.olho_fechado
                    ),
                    contentDescription = "Mostrar/Ocultar senha"
                )
            }
        },
        colors = colors,
        shape = RoundedCornerShape(28.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
fun TelaCadastroJuridicoPreview() {
    InfoHub_telasTheme {
        TelaCadastroJuridico(null)
    }
}
