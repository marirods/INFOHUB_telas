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
import com.example.infohub_telas.telas.*
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
                    composable (
                        route = "tela_cadastro"
                    ) {
                        TelaCadastro(navController)
                    }
                    }

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