package com.example.infohub_telas

import TelaLogin
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.telas.TelaCadastro
import com.example.infohub_telas.telas.TelaCadastroEndereco
import com.example.infohub_telas.telas.TelaCadastroJuridico
import com.example.infohub_telas.telas.TelaConfirmarCodigo
import com.example.infohub_telas.telas.TelaCriarNovaSenha
import com.example.infohub_telas.telas.TelaLocalizacao
import com.example.infohub_telas.telas.TelaProduto
import com.example.infohub_telas.telas.TelaRedefinicaoSenha
import com.example.infohub_telas.telas.TelaChatDePrecos
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
                    startDestination = "telaCadastroEndereco"
                ) {
                    composable("tela_cadastro") { TelaCadastro(navController) }
                    composable("cadastro_juridico") { TelaCadastroJuridico(navController) }
                    composable("redefinicao_senha") { TelaRedefinicaoSenha(navController) }
                    composable("confirmar_codigo") { TelaConfirmarCodigo(navController) }
                    composable("criar_senha") { TelaCriarNovaSenha(navController) }
                    composable("localizacao") { TelaLocalizacao(navController) }
                    composable("chat_precos") { TelaChatDePrecos(navController) }
                    composable("login") { TelaLogin(navController) }
                    composable("produto") { TelaProduto(navController) }
                    composable(route = "telaCadastroEndereco"){ TelaCadastroEndereco(navController) }

                    // Telas simples de menu (exemplo)
                    composable("inicio") { androidx.compose.material3.Text("Tela de Início") }
                    composable("promocoes") { androidx.compose.material3.Text("Tela de Promoções") }
                    composable("infocash") { androidx.compose.material3.Text("Tela de InfoCash") }
                    composable("perfil") { androidx.compose.material3.Text("Tela de Perfil") }
                }
            }
        }
    }
}
