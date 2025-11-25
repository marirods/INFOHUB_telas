package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.navigation.JuridicoRoutes
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.utils.AppUtils

import com.example.infohub_telas.viewmodel.AuthViewModel
import com.example.infohub_telas.network.models.LoginResponse as NetworkLoginResponse
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaLogin(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val focusManager = LocalFocusManager.current
    val passwordFocusRequester = remember { FocusRequester() }

    // Estados da UI
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mostrarSenha by remember { mutableStateOf(false) }

    // Estados dos diálogos
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Observer dos estados do ViewModel
    val isLoading by authViewModel.isLoading.observeAsState(false)
    val loginResult by authViewModel.loginResult.observeAsState()
    val errorMessage by authViewModel.errorMessage.observeAsState()

    // Efeitos para tratar resultados
    LaunchedEffect(loginResult) {
        loginResult?.getOrNull()?.let { loginResponse ->
            // Verificar o perfil do usuário para redirecionar adequadamente
            val perfil = loginResponse.user.perfil

            println("=== LOGIN SUCCESS - Redirecionando ===")
            println("Perfil: $perfil")
            println("====================================")

            // Definir destino baseado no perfil
            val destination = when (perfil?.lowercase()) {
                "estabelecimento" -> {
                    println("Redirecionando para: MEU_ESTABELECIMENTO")
                    Routes.MEU_ESTABELECIMENTO
                }
                "juridico", "jurídico" -> {
                    println("Redirecionando para: HOME (Jurídico)")
                    Routes.HOME
                }
                else -> {
                    println("Redirecionando para: HOME (Consumidor)")
                    Routes.HOME
                }
            }

            // Navegar para o destino correto
            navController.navigate(destination) {
                // Limpa toda a pilha de navegação até o login
                popUpTo(Routes.LOGIN) {
                    inclusive = true
                }
                // Evita múltiplas instâncias da mesma tela
                launchSingleTop = true
            }

            authViewModel.clearResults()
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            dialogMessage = AppUtils.getErrorMessage(Exception(message))
            showErrorDialog = true
        }
    }

    // Função de login
    fun realizarLogin() {
        focusManager.clearFocus()
        authViewModel.clearErrorMessage()
        authViewModel.login(email, senha)
    }

    // Funções de navegação
    fun navigateToForgotPassword() {
        navController.navigate(Routes.REDEFINICAO_SENHA)
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
            onValueChange = {
                email = it
                authViewModel.clearErrorMessage()
            },
            placeholder = { Text("E-mail", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { passwordFocusRequester.requestFocus() }
            ),
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
            onValueChange = {
                senha = it
                authViewModel.clearErrorMessage()
            },
            placeholder = { Text("Senha", color = Color.Gray) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(passwordFocusRequester),
            visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { realizarLogin() }
            ),
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
            onClick = { realizarLogin() },
            enabled = isLoading.not(),
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
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = AppUtils.getErrorMessage(Exception(errorMessage)),
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    // Diálogos
    if (showErrorDialog) {
        AppUtils.ErrorDialog(
            message = dialogMessage,
            onDismiss = {
                showErrorDialog = false
                authViewModel.clearErrorMessage()
            }
        )
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
