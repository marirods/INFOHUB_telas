package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var errorMessage by remember { mutableStateOf<String?>(null) }

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        
        Image(
            painter = painterResource(id = R.drawable.login_comprass),
            contentDescription = "LOGIN",
            modifier = Modifier.size(320.dp)
        )
        
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "BEM VINDO DE VOLTA!",
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Informações de teste (ocultas da tela)
        // Para teste use: teste@infohub.com / 123456 ou admin@infohub.com / admin123
        
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("E-mail", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            placeholder = { Text("Senha", color = Color.Gray) },
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
                        contentDescription = "Mostrar/Ocultar senha"
                    )
                }
            },
            shape = RoundedCornerShape(15.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryOrange,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { navigateToForgotPassword() }
            ) {
                Text(
                    text = "Recuperar senha",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryOrange
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (validar()) {
                    isLoading = true
                    errorMessage = null

                    // Verificar se é usuário de teste
                    if (isTestUser()) {
                        // Login de teste - navegar diretamente
                        isLoading = false
                        Log.d("TelaLogin", "🧪 Login de teste detectado")
                        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                        prefs.edit().apply {
                            putString("token", "test_token_123")
                            putBoolean("isAdmin", email == "admin@infohub.com")
                            putInt("user_id", 1) // ID de teste
                            apply()
                        }
                        Log.d("TelaLogin", "✅ Token de teste salvo")
                        navigateToHome()
                    } else {
                        // Login real via API
                        val loginReq = LoginUsuario(email, senha)
                        Log.d("TelaLogin", "🚀 Enviando requisição de login para: $email")

                        userApi.logarUsuario(loginReq).enqueue(object : Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                isLoading = false
                                Log.d("TelaLogin", "📥 Resposta recebida - Code: ${response.code()}")

                                if (response.isSuccessful) {
                                    val body = response.body()
                                    Log.d("TelaLogin", "📦 Body: $body")

                                    if (body != null && body.status) {
                                        Log.d("TelaLogin", "✅ Login bem-sucedido!")
                                        Log.d("TelaLogin", "🔑 Token recebido: ${body.token}")
                                        Log.d("TelaLogin", "👤 User ID: ${body.usuario.id}")
                                        Log.d("TelaLogin", "📧 Email: ${body.usuario.email}")
                                        Log.d("TelaLogin", "👔 Perfil: ${body.usuario.perfil}")

                                        val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                                        
                                        // Determinar se é admin baseado no perfil retornado pela API
                                        val isAdminUser = body.usuario.perfil == "estabelecimento"

                                        prefs.edit().apply {
                                            putString("token", body.token)
                                            putBoolean("isAdmin", isAdminUser)
                                            putInt("user_id", body.usuario.id)
                                            putString("userEmail", body.usuario.email)
                                            putString("userName", body.usuario.nome)
                                            apply()
                                        }

                                        Log.d("TelaLogin", "💾 Dados salvos no SharedPreferences:")
                                        Log.d("TelaLogin", "   - Token: ${prefs.getString("token", "ERRO")}")
                                        Log.d("TelaLogin", "   - User ID: ${prefs.getInt("user_id", -1)}")
                                        Log.d("TelaLogin", "   - IsAdmin: ${prefs.getBoolean("isAdmin", false)}")

                                        navigateToHome()
                                    } else {
                                        errorMessage = "Login falhou: status inválido"
                                        Log.e("TelaLogin", "❌ Status false ou body null")
                                    }
                                } else {
                                    val errorBody = response.errorBody()?.string()
                                    errorMessage = "Erro ${response.code()}: ${response.message()}"
                                    Log.e("TelaLogin", "❌ Erro HTTP ${response.code()}")
                                    Log.e("TelaLogin", "❌ Error body: $errorBody")
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                isLoading = false
                                errorMessage = "Falha na conexão: ${t.message}"
                                Log.e("TelaLogin", "💥 Falha na requisição: ${t.message}", t)
                            }
                        })
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
        ) {
            if (isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("ENTRAR", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
        
        // Mostrar mensagem de erro se houver
        errorMessage?.let { msg ->
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = msg,
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
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
