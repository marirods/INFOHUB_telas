package com.example.infohub_telas

import TelaLogin
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.telas.OpenScreen
import com.example.infohub_telas.telas.WelcomeScreen
import com.example.infohub_telas.telas.TelaCadastro
import com.example.infohub_telas.telas.TelaCadastroJuridico
import com.example.infohub_telas.telas.TelaChatDePrecos
import com.example.infohub_telas.telas.TelaConfirmarCodigo
import com.example.infohub_telas.telas.TelaCriarNovaSenha
import com.example.infohub_telas.telas.TelaLocalizacao
import com.example.infohub_telas.telas.TelaLoginCadastro
import com.example.infohub_telas.telas.TelaPerfil
import com.example.infohub_telas.telas.TelaProduto
import com.example.infohub_telas.telas.TelaRedefinicaoSenha
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import org.osmdroid.config.Configuration

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
                    startDestination = "splash"
                ) {

                    composable(
                        route = "splash")
                    {
                        OpenScreen(navController)

                    }
                    composable(
                        route = "welcome")
                    {
                        WelcomeScreen(navController)

                    }
                    composable(
                        route = "loginCadastro")
                    {
                        TelaLoginCadastro(navController)

                    }

                    composable(
                        route = "login")
                    {
                        TelaLogin(navController)

                    }

                    composable(
                        route = "tela_cadastro")
                    {
                        TelaCadastro(navController)

                    }

//                    composable(
//                        route = "tela_cadastro")
//                    {
//                        TelaCadastro(navController)
//
//                    }

                }
            }
        }
    }
}

// 🔹 Função simples de exemplo (pode remover se não usar)
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
