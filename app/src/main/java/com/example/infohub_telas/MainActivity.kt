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
                    startDestination = "termos_uso" // 🔹 Tela inicial ativa - TESTE
                ) {
                    // 🔸 Tela de Login - Desativada para teste
//                    composable(route = "login") {
//                        TelaLogin(navController)
//                    }

                    // 🔹 Tela de Termos de Uso - ATIVA PARA TESTE
                    composable(route = "termos_uso") {
                        TelaTermosDeUso(navController)
                    }

                    // 🔸 Telas do fluxo de cadastro — Ativar depois se quiser
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

                    // 🔸 Tela de localização — Ativar depois se quiser
//                    composable(route = "localizacao") {
//                        TelaLocalizacao(navController)
//                    }

                    // 🔸 Fluxo de e-commerce — Ativar depois se quiser
//                    composable(route = "carrinho") {
//                        TelaCarrinho(navController)
//                    }
//
//                    composable(route = "checkout") {
//                        TelaCheckout(navController)
//                    }
//
//                    composable(route = "pagamento") {
//                        TelaPagamento(navController)
//                    }
//
//                    composable(route = "pagamento_sucesso") {
//                        TelaPagamentoSucesso(navController)
//                    }

                    // 🔸 Rotas de menu inferior — Ativar depois se quiser
//                    composable(route = "inicio") { Text(text = "Tela de Início") }
//                    composable(route = "promocoes") { Text(text = "Tela de Promoções") }
//                    composable(route = "infocash") { Text(text = "Tela de InfoCash") }
//                    composable(route = "perfil") { Text(text = "Tela de Perfil") }
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