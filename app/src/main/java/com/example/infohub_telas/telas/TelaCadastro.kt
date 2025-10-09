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
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro() {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }

    // Estado para controlar a aba selecionada (Pessoa Física por padrão)
    var isPessoaFisicaSelected by remember { mutableStateOf(true) }

    // Cor principal laranja
    val primaryOrange = Color(0xFFFF8C00) // Cor laranja padrão
    val darkerOrange = Color(0xFFE67E22) // Um tom mais escuro para destaque
    val lightGray = Color(0xFFF0F0F0) // Cor de fundo clara
    val textFieldBackground = Color.White // Fundo dos campos de texto

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        // --- Bolas Decorativas no Topo ---
        // Ajuste os offsets e sizes para ficarem mais parecidos com a nova imagem
        Image(
            painter = painterResource(id = R.drawable.bola_cadastro_vermelho),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-40).dp, y = 30.dp) // Ajustado
                .size(70.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.bola_laranja_cadastro),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = 0.dp) // Ajustado
                .size(130.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem do topo
            Image(
                painter = painterResource(id = R.drawable.cadastro),
                contentDescription = "Cadastro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Seleção de tipo de pessoa com traços laranjas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Aba Pessoa Física
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Física",
                        fontSize = 18.sp,
                        fontWeight = if (isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = true }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (isPessoaFisicaSelected) {
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }
                // Aba Pessoa Jurídica
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Pessoa Jurídica",
                        fontSize = 18.sp,
                        fontWeight = if (!isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (!isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = false }
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (!isPessoaFisicaSelected) {
                        Box(
                            modifier = Modifier
                                .width(60.dp)
                                .height(3.dp)
                                .background(primaryOrange, RoundedCornerShape(2.dp))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de entrada
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            ) {
                CustomTextField(
                    value = nome,
                    onValueChange = { nome = it },
                    placeholder = "Nome Completo*",
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                CustomTextField(
                    value = cpf,
                    onValueChange = { cpf = it },
                    placeholder = "CPF*",
                    keyboardType = KeyboardType.Number,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                CustomTextField(
                    value = telefone,
                    onValueChange = { telefone = it },
                    placeholder = "Telefone*",
                    keyboardType = KeyboardType.Phone,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = "E-mail*",
                    keyboardType = KeyboardType.Email,
                    textFieldColors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                // Campo Senha
                SenhaTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    mostrarSenha = mostrarSenha,
                    onMostrarSenhaChange = { mostrarSenha = it },
                    label = "Senha*",
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                // Campo Confirmar Senha
                SenhaTextField(
                    value = confirmarSenha,
                    onValueChange = { confirmarSenha = it },
                    mostrarSenha = mostrarConfirmarSenha,
                    onMostrarSenhaChange = { mostrarConfirmarSenha = it },
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

            // Botão de Cadastro
            Button(
                onClick = { /* TODO: Implementar ação de cadastro */ },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)), // Cor verde do botão de entrar/cadastrar
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Cadastrar", // Alterado de "Entrar" para "Cadastrar"
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto de login
            Row {
                Text(text = "Tem uma conta? ", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = "Faça login",
                    fontSize = 14.sp,
                    color = primaryOrange, // Usando a cor laranja
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { /* TODO: Navegar para tela de login */ }
                )
            }
        }

        // --- Detalhe Laranja na parte inferior ---
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = 0.dp) // Ajustado para ficar bem na base
                .fillMaxWidth()
                .height(20.dp)
                .background(primaryOrange, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        )
    }
}

// Helper Composable para campos de texto padrão
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors() // Permite customizar as cores
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

// Helper Composable para campos de senha com ícone de olho
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SenhaTextField(
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
        label = { Text(label) }, // Usando label em vez de placeholder para mais clareza
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
fun TelaCadastroPreview() {
    InfoHub_telasTheme {
        TelaCadastro()
    }
}//