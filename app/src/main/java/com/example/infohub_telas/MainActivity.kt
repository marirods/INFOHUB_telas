package com.example.infohub_telas

import TelaLogin
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.telas.*
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        setContent {
            InfoHub_telasTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "carrinho", // Rota inicial para teste do fluxo de e-commerce
                    enterTransition = { slideInHorizontally(animationSpec = tween(300), initialOffsetX = { it }) },
                    exitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { -it }) },
                    popEnterTransition = { slideInHorizontally(animationSpec = tween(300), initialOffsetX = { -it }) },
                    popExitTransition = { slideOutHorizontally(animationSpec = tween(300), targetOffsetX = { it }) }
                ) {
//                    composable("login") { TelaLogin(navController) }
//                    composable("tela_cadastro") { TelaCadastro(navController) }
//                    composable("cadastro_juridico") { TelaCadastroJuridico(navController) }
//                    composable("redefinicao_senha") { TelaRedefinicaoSenha(navController) }
//                    composable("confirmar_codigo") { TelaConfirmarCodigo(navController) }
//                    composable("criar_senha") { TelaCriarNovaSenha(navController) }
//                    composable("localizacao") { TelaLocalizacao(navController) }

                    // Fluxo de E-commerce
                    composable("carrinho") { TelaCarrinho(navController) }
                    composable("checkout") { TelaCheckout(navController) }
                    composable("pagamento") { TelaPagamento(navController) }
                    composable("pagamento_sucesso") { TelaPagamentoSucesso(navController) }

                    // Rotas do Menu Inferior
//                    composable("inicio") { TelaLogin(navController) } // Exemplo, pode ser outra tela
//                    composable("promocoes") { Text(text = "Tela de Promoções") }
//                    composable("infocash") { Text(text = "Tela de InfoCash") }
//                    composable("perfil") { Text(text = "Tela de Perfil") }
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
