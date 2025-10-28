package com.example.infohub_telas

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.infohub_telas.telas.TelaLogin
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import org.osmdroid.config.Configuration
import java.util.Date
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.model.PromocaoProduto
import com.example.infohub_telas.telas.TelaCadastro
import com.example.infohub_telas.telas.TelaCadastroEstabelecimento
import com.example.infohub_telas.telas.TelaCadastroPromocao
import com.example.infohub_telas.telas.TelaChatDePrecos
import com.example.infohub_telas.telas.TelaHome
import com.example.infohub_telas.telas.TelaListaProdutos
import com.example.infohub_telas.telas.TelaMeuEstabelecimento

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

                val sampleProdutos = listOf(
                    PromocaoProduto(
                        nomeProduto = "Hambúrguer de Picanha",
                        categoria = "Alimentação",
                        precoPromocional = "29.90",
                        dataInicio = Date(),
                        dataTermino = Date(System.currentTimeMillis() + 86400000),
                        descricao = "",
                        imagemUrl = "https://picsum.photos/seed/1/200"
                    ),
                    PromocaoProduto(
                        nomeProduto = "Tênis de Corrida",
                        categoria = "Varejo",
                        precoPromocional = "249.99",
                        dataInicio = Date(System.currentTimeMillis() - 86400000),
                        dataTermino = Date(System.currentTimeMillis() - 1000),
                        descricao = "",
                        imagemUrl = "https://picsum.photos/seed/2/200"
                    ),
                    PromocaoProduto(
                        nomeProduto = "Corte de Cabelo",
                        categoria = "Serviços",
                        precoPromocional = "45.00",
                        dataInicio = Date(),
                        dataTermino = Date(System.currentTimeMillis() + 5 * 86400000),
                        descricao = "",
                        imagemUrl = "https://picsum.photos/seed/3/200"
                    )
                )

                NavHost(
                    navController = navController,
                    startDestination = "listaProdutos" 
                ) {
                    composable(route = "home") {
                        TelaHome(navController)
                    }
                    composable(
                        route = "welcome")
                    {
                        WelcomeScreen(navController)

                    composable(
                        route = "cadastroEstabelecimento?id={id}&categoria={categoria}",
                        arguments = listOf(
                            navArgument("id") {
                                type = NavType.IntType
                                defaultValue = 0
                            },
                            navArgument("categoria") {
                                type = NavType.StringType
                                nullable = true
                            }
                        )
                    ) {
                        val id = it.arguments?.getInt("id")
                        val categoria = it.arguments?.getString("categoria")
                        TelaCadastroEstabelecimento(navController, id, categoria)
                    }

                    composable(route = "meuEstabelecimento") {
                        val sampleEstabelecimento = Estabelecimento(
                            id = 1,
                            nome = "Padaria do Zé",
                            cnpj = "12.345.678/0001-99",
                            endereco = "Rua das Flores, 123",
                            telefone = "(11) 99999-8888",
                            email = "contato@padariadoze.com",
                            categoria = "Alimentação"
                        )
                        TelaMeuEstabelecimento(navController, sampleEstabelecimento)
                    }

                    composable(route = "tela_cadastro") {
                        TelaCadastro(navController)
                    }

                    composable(route = "chat_precos") {
                        TelaChatDePrecos(navController)
                    }

                    // Rotas adicionadas para navegação da TelaMeuEstabelecimento
                    composable(route = "homeJuridico") { Text(text = "Tela Home Jurídico") }
                    composable(route = "listaProdutos") { TelaListaProdutos(navController, sampleProdutos) }
                    composable(route = "promocoes") { Text(text = "Tela de Promoções") }
                    composable(route = "criarPromocao") { TelaCadastroPromocao(navController) }
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
