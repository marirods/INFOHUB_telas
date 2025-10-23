package com.example.infohub_telas

import TelaLogin
import org.osmdroid.config.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.telas.TelaCadastro
import com.example.infohub_telas.telas.TelaCadastroJuridico
import com.example.infohub_telas.telas.TelaConfirmarCodigo
import com.example.infohub_telas.telas.TelaCriarNovaSenha
import com.example.infohub_telas.telas.TelaLocalizacao
<<<<<<< HEAD
import com.example.infohub_telas.telas.TelaPerfil
=======
import com.example.infohub_telas.telas.TelaProduto
>>>>>>> a5924d04fdcc47658b0b227dedca06b13057ca28
import com.example.infohub_telas.telas.TelaRedefinicaoSenha
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuração do OpenStreetMap
        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        setContent {
            InfoHub_telasTheme {
                val navController = rememberNavController()
                
                NavHost(
                    navController = navController,
<<<<<<< HEAD
                    startDestination = "perfil"
                ){
                    composable (
                        route = "login"
                    ){
                        TelaLogin(navController)
                    }
                    composable (
                        route = "tela_cadastro"
                    ){
                        TelaCadastro(navController)
                    }
                    composable (
                        route = "cadastro_juridico"
                    ){
                        TelaCadastroJuridico(navController)
                    }
                    composable (
                        route = "redefinicao_senha"
                    ){
                        TelaRedefinicaoSenha(navController)
                    }
                    composable (
                        route = "confirmar_codigo"
                    ){
                        TelaConfirmarCodigo(navController)
                    }
                    composable (
                        route = "criar_senha"
                    ){
                        TelaCriarNovaSenha(navController)
                    }
=======
                    startDestination = "tela_cadastro"
                ){
//                    composable (
//                        route = "login"
//                    ){
//                        TelaLogin(navController)
//                    }

                    // 🔸 Telas do fluxo de cadastro
//                    composable(route = "tela_cadastro") {
//                        TelaCadastro(navController)
//                    }
//
//                    composable(route = "cadastro_juridico") {
//                        TelaCadastroJuridico(navController)
//                    }
//
//                    composable(route = "redefinicao_senha") {
//                        TelaRedefinicaoSenha(navController)
//                    }
//
//                    composable(route = "confirmar_codigo") {
//                        TelaConfirmarCodigo(navController)
//                    }
//
//                    composable(route = "criar_senha") {
//                        TelaCriarNovaSenha(navController)
//                    }
>>>>>>> a5924d04fdcc47658b0b227dedca06b13057ca28
                    composable (
                        route = "tela_cadastro"
                    ) {
                        TelaCadastro(navController)
                    }
                    composable(
                       route = "perfil"
                    ){
                        TelaPerfil(navController)
                    }
                    composable(
                        route = "localizacao"
                    ){
                        TelaLocalizacao(navController)
                    }
<<<<<<< HEAD
=======

                    // 🔸 Tela de Localização
//                    composable(route = "localizacao") {
//                        TelaLocalizacao(navController)
//                    }

                    // 🔸 Rotas de menu inferior
//                    composable(route = "inicio") { Text(text = "Tela de Início") }
//                    composable(route = "promocoes") { Text(text = "Tela de Promoções") }
//                    composable(route = "infocash") { Text(text = "Tela de InfoCash") }
//                    composable(route = "perfil") { Text(text = "Tela de Perfil") }

                    // 🔸 Chat de Preços (para o botão flutuante)
                    composable(route = "chat_precos") {
                        TelaChatDePrecos(navController)
                    }
>>>>>>> a5924d04fdcc47658b0b227dedca06b13057ca28
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InfoHub_telasTheme {
        Greeting("Android")
    }
    }
}