package com.example.infohub_telas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.infohub_telas.telas.*
import com.example.infohub_telas.telas.juridico.*

sealed class AppScreens(val route: String) {
    // Main screens
    object JuridicoHome : AppScreens("juridico_home")
    object GerenciamentoEmpresas : AppScreens("juridico_gerenciamento_empresas")
    object CadastroEmpresa : AppScreens("juridico_cadastro_empresa")
    object Produtos : AppScreens("juridico_produtos")
    object Relatorios : AppScreens("juridico_relatorios")
    object Documentos : AppScreens("juridico_documentos")
    object Configuracoes : AppScreens("juridico_configuracoes")

    // Rotas parametrizadas
    object DetalhesEmpresa : AppScreens("juridico_detalhes_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "juridico_detalhes_empresa/$empresaId"
    }
    object DetalhesProduto : AppScreens("juridico_detalhes_produto/{produtoId}") {
        fun createRoute(produtoId: String) = "juridico_detalhes_produto/$produtoId"
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.JuridicoHome.route
    ) {
        // Home Screen
        composable(AppScreens.JuridicoHome.route) {
            TelaHomeJuridico(
                onNavigateToGerenciamentoEmpresas = {
                    navController.navigate(AppScreens.GerenciamentoEmpresas.route)
                },
                onNavigateToProdutos = {
                    navController.navigate(AppScreens.Produtos.route)
                },
                onNavigateToRelatorios = {
                    navController.navigate(AppScreens.Relatorios.route)
                },
                onNavigateToNovaEmpresa = {
                    navController.navigate(AppScreens.CadastroEmpresa.route)
                },
                onNavigateToDocumentos = {
                    navController.navigate(AppScreens.Documentos.route)
                },
                onNavigateToConfiguracoes = {
                    navController.navigate(AppScreens.Configuracoes.route)
                }
            )
        }

        // Gerenciamento de Empresas Screen
        composable(AppScreens.GerenciamentoEmpresas.route) {
            JuridicoGerenciamentoEmpresasScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCadastroEmpresa = {
                    navController.navigate(AppScreens.CadastroEmpresa.route)
                },
                onEmpresaClick = { empresaId ->
                    navController.navigate(AppScreens.DetalhesEmpresa.createRoute(empresaId))
                }
            )
        }

        // Produtos Screen
        composable(AppScreens.Produtos.route) {
            JuridicoProdutosScreen(
                onNavigateBack = { navController.popBackStack() },
                onProdutoClick = { produtoId ->
                    navController.navigate(AppScreens.DetalhesProduto.createRoute(produtoId))
                }
            )
        }

        // Cadastro de Empresa Screen
        composable(AppScreens.CadastroEmpresa.route) {
            JuridicoCadastroEmpresaScreen(
                onNavigateBack = { navController.popBackStack() },
                onCadastroSuccess = { navController.popBackStack() }
            )
        }

        // Detalhes da Empresa Screen
        composable(
            route = AppScreens.DetalhesEmpresa.route,
            arguments = listOf(navArgument("empresaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val empresaId = backStackEntry.arguments?.getString("empresaId")
            // TODO: Implement DetalhesEmpresaScreen
            // DetalhesEmpresaScreen(
            //     empresaId = empresaId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Detalhes do Produto Screen
        composable(
            route = AppScreens.DetalhesProduto.route,
            arguments = listOf(navArgument("produtoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val produtoId = backStackEntry.arguments?.getString("produtoId")
            // TODO: Implement DetalhesProdutoScreen
            // DetalhesProdutoScreen(
            //     produtoId = produtoId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Relatórios Screen
        composable(AppScreens.Relatorios.route) {
            // TODO: Implement RelatoriosScreen
            // RelatoriosScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Documentos Screen
        composable(AppScreens.Documentos.route) {
            // TODO: Implement DocumentosScreen
            // DocumentosScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Configurações Screen
        composable(AppScreens.Configuracoes.route) {
            // TODO: Implement ConfiguracoesScreen
            // ConfiguracoesScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }
    }
}
