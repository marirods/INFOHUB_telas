package com.example.infohub_telas.telas

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.LoginResponse
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userApi = RetrofitFactory().getInfoHub_UserService()

    fun validar(): Boolean {
        val emailValido = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val senhaValida = senha.isNotEmpty()
        return emailValido && senhaValida
    }

    // Credenciais de teste
    fun isTestUser(): Boolean {
        return (email == "teste@infohub.com" && senha == "123456") ||
               (email == "admin@infohub.com" && senha == "admin123")
    }

    fun navigateToHome() {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.LOGIN) { inclusive = true }
        }
    }

    fun navigateToForgotPassword() {
        navController.navigate(Routes.REDEFINICAO_SENHA)
    }

    fun navigateToSignUp() {
        navController.navigate(Routes.CADASTRO)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

                // Logo Section
                Card(
                    modifier = Modifier
                        .size(220.dp)
                        .clip(RoundedCornerShape(24.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.login_comprass),
                            contentDescription = "LOGIN",
                            modifier = Modifier.size(280.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                // Welcome Text
                Text(
                    text = "Bem-vindo de volta!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = PrimaryOrange,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Entre na sua conta para continuar",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Login Form Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // Email Field
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("E-mail", color = Color(0xFF666666)) },
                            placeholder = { Text("Digite seu e-mail", color = Color(0xFF999999)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedLabelColor = PrimaryOrange
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password Field
                        OutlinedTextField(
                            value = senha,
                            onValueChange = { senha = it },
                            label = { Text("Senha", color = Color(0xFF666666)) },
                            placeholder = { Text("Digite sua senha", color = Color(0xFF999999)) },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick = { mostrarSenha = !mostrarSenha }) {
                                    Icon(
                                        painter = painterResource(
                                            id = if (mostrarSenha) R.drawable.olho_aberto else R.drawable.olho_fechado
                                        ),
                                        contentDescription = "Mostrar/Ocultar senha",
                                        tint = Color(0xFF666666)
                                    )
                                }
                            },
                            shape = RoundedCornerShape(16.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PrimaryOrange,
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color(0xFFFAFAFA),
                                focusedLabelColor = PrimaryOrange
                            )
                        )

                        // Forgot Password
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(
                                onClick = { navigateToForgotPassword() }
                            ) {
                                Text(
                                    text = "Esqueceu a senha?",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = PrimaryOrange
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Login Button
                        Button(
                            onClick = {
                                if (validar()) {
                                    isLoading = true
                                    
                                    // Verificar se é usuário de teste
                                    if (isTestUser()) {
                                        // Login de teste - navegar diretamente
                                        isLoading = false
                                        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                                        prefs.edit().putString("token", "test_token_123").apply()
                                        navigateToHome()
                                    } else {
                                        // Login real via API
                                        val loginReq = LoginUsuario(email, senha)

                                        userApi.logarUsuario(loginReq).enqueue(object : Callback<LoginResponse> {
                                            override fun onResponse(
                                                call: Call<LoginResponse>,
                                                response: Response<LoginResponse>
                                            ) {
                                                isLoading = false
                                                if (response.isSuccessful) {
                                                    val body = response.body()
                                                    if (body != null && body.status) {
                                                        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                                                        prefs.edit().putString("token", body.token).apply()
                                                        navigateToHome()
                                                    }
                                                }
                                            }

                                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                                isLoading = false
                                            }
                                        })
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryOrange,
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )
                            } else {
                                Text(
                                    "ENTRAR",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Sign up link
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Não tem uma conta? ",
                                fontSize = 14.sp,
                                color = Color(0xFF666666)
                            )
                            TextButton(
                                onClick = { navigateToSignUp() }
                            ) {
                                Text(
                                    text = "Cadastre-se",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryOrange
                                )
                            }
                        }
                    }
                }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaLoginPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            TelaLogin(
                navController = rememberNavController()
            )
        }
    }
}
