package com.example.infohub_telas.telas

import android.util.Log
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastro(navController: NavController?) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var confirmarSenha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var mostrarConfirmarSenha by remember { mutableStateOf(false) }

    var isPessoaFisicaSelected by remember { mutableStateOf(true) }

    // controla se mostra o diálogo de sucesso
    var showSuccessDialog by remember { mutableStateOf(false) }

    val primaryOrange = Color(0xFFFF8C00)
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val userApi = RetrofitFactory().getInfoHub_UserService()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        // Decoração
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
            modifier = Modifier.fillMaxSize(),
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

            // Seleção PF / PJ
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
                        fontWeight = if (isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = true }
                    )
                    if (isPessoaFisicaSelected) {
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
                        fontWeight = if (!isPessoaFisicaSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (!isPessoaFisicaSelected) Color.Black else Color.Gray,
                        modifier = Modifier.clickable { isPessoaFisicaSelected = false }
                    )
                    if (!isPessoaFisicaSelected) {
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

            // Formulário
            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
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
                if (isPessoaFisicaSelected) {
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
                } else {
                    CustomTextField(
                        value = cnpj,
                        onValueChange = { cnpj = it },
                        placeholder = "CNPJ*",
                        keyboardType = KeyboardType.Number,
                        textFieldColors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = textFieldBackground,
                            unfocusedContainerColor = textFieldBackground
                        )
                    )
                }
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

            // Botão
            Button(
                onClick = {
                    val usuario = Usuario(
                        nome = nome,
                        email = email,
                        senha_hash = senha,
                        perfil = when {
                            isPessoaFisicaSelected -> "consumidor"
                            !isPessoaFisicaSelected -> "estabelecimento"
                            else -> "admin"
                        },
                        cpf = if (isPessoaFisicaSelected) cpf else null,
                        cnpj = if (!isPessoaFisicaSelected) cnpj else null,
                        data_nascimento = "1900-01-01"
                    )

                    Log.d("DEBUG", "Enviando usuário -> $usuario")

                    userApi.cadastrarUsuario(usuario).enqueue(object : Callback<Usuario> {
                        override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                            if (response.isSuccessful) {
                                Log.d("API", "Usuário cadastrado: ${response.body()}")
                                showSuccessDialog = true
                            } else {
                                Log.e("API", "Erro: ${response.code()} - ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<Usuario>, t: Throwable) {
                            Log.e("API", "Falha na requisição: ${t.message}")
                        }
                    })
                },
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
                    modifier = Modifier.clickable { navController?.navigate("login") }
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

    // diálogo de sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController?.navigate("login") {
                        popUpTo("cadastro") { inclusive = true }
                    }
                }) {
                    Text("OK")
                }
            },
            title = { Text("Sucesso") },
            text = { Text("Usuário cadastrado com sucesso!") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
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
fun TelaCadastroPreview() {
    InfoHub_telasTheme {
        TelaCadastro(null)
    }
}


