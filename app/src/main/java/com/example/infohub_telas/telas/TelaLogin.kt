import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
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
import com.example.infohub_telas.model.LoginResponse
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.telas.TelaCadastroJuridico
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(navController: NavHostController?) {

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

    val primaryOrange = Color(0xFFF9A01B)
    val buttonGreen = Color(0xFF25992E)
    val textColor = Color.Black
    val linkColor = Color(0xFF25992E)

    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().weight(0.4f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.login_comprass),
                    contentDescription = "LOGIN",
                    modifier = Modifier.size(240.dp)
                )
            }

            // --- Formulário ---
            Column(
                modifier = Modifier
                    .background(primaryOrange)
                    .fillMaxWidth()
                    .weight(0.6f)
                    .padding(horizontal = 32.dp, vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "BEM VINDO DE VOLTA!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(top = 20.dp)
                )

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("E-mail", color = Color.DarkGray) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        focusedContainerColor = Color.White
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = senha,
                    onValueChange = { senha = it },
                    placeholder = { Text("Senha", color = Color.DarkGray) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
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
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = textColor,
                        unfocusedBorderColor = textColor,
                        focusedContainerColor = Color.White
                    )
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End

                ){
                    TextButton(
                        onClick = {
                            navController?.navigate("redefinicao_senha")
                        }
                    ) {
                        Text(
                            text = "Recuperar senha",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black

                        )
                    }

                    Spacer(modifier = Modifier
                        .height(12.dp)

                    )
                }



                Spacer(modifier = Modifier.height(32.dp))



                Button(
                    onClick = {
                        if (validar()) {
                            isLoading = true
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
                                            // Salvar token
                                            val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
                                            prefs.edit().putString("token", body.token).apply()

                                            println("Token salvo: ${body.token}")
                                            // Navegar para home
                                            navController?.navigate("home") {
                                                popUpTo("login") { inclusive = true }
                                            }
                                        }
                                    } else {
                                        println("Erro no login: ${response.code()}")
                                    }
                                }

                                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                    isLoading = false
                                    println("Falha: ${t.message}")
                                }
                            })
                        }
                    },
                    modifier = Modifier.width(220.dp).height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = buttonGreen),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Entrar", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Medium)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Text("Não tem uma conta? ", color = textColor, fontSize = 14.sp)
                    Text(
                        "Cadastre-se aqui",
                        color = linkColor,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController?.navigate("tela_cadastro")
                        }
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaLoginPreview() {
    InfoHub_telasTheme {
        TelaLogin(null)}}