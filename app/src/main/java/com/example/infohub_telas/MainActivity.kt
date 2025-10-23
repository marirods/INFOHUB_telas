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
import com.example.infohub_telas.telas.TelaPerfil
import com.example.infohub_telas.telas.TelaRedefinicaoSenha
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

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
                    composable (
                        route = "Localização"
                    ) {
                        TelaLocalizacao(navController)
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