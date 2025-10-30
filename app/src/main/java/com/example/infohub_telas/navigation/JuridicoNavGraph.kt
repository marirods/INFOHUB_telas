package com.example.infohub_telas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.infohub_telas.telas.juridico.*

@Composable
fun JuridicoNavGraph(
    navController: NavHostController,
    startDestination: String = JuridicoScreens.Home.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Home Screen
        composable(JuridicoScreens.Home.route) {
            JuridicoHomeScreen(
                onNavigateToGerenciamentoEmpresas = {
                    navController.navigate(JuridicoScreens.GerenciamentoEmpresas.route)
                },
                onNavigateToProdutos = {
                    navController.navigate(JuridicoScreens.Produtos.route)
                },
                onNavigateToRelatorios = {
                    navController.navigate(JuridicoScreens.Relatorios.route)
                },
                onNavigateToDocumentos = {
                    navController.navigate(JuridicoScreens.Documentos.route)
                },
                onNavigateToConfiguracoes = {
                    navController.navigate(JuridicoScreens.Configuracoes.route)
                },
                onNavigateToCadastroEmpresa = {
                    navController.navigate(JuridicoScreens.CadastroEmpresa.route)
                }
            )
        }

        // Gerenciamento de Empresas Screen
        composable(JuridicoScreens.GerenciamentoEmpresas.route) {
            JuridicoGerenciamentoEmpresasScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToCadastroEmpresa = {
                    navController.navigate(JuridicoScreens.CadastroEmpresa.route)
                },
                onEmpresaClick = { empresaId ->
                    navController.navigate(JuridicoScreens.DetalhesEmpresa.createRoute(empresaId))
                }
            )
        }

        // Produtos Screen
        composable(JuridicoScreens.Produtos.route) {
            JuridicoProdutosScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onProdutoClick = { produtoId ->
                    navController.navigate(JuridicoScreens.DetalhesProduto.createRoute(produtoId))
                }
            )
        }

        // Cadastro de Empresa Screen
        composable(JuridicoScreens.CadastroEmpresa.route) {
            JuridicoCadastroEmpresaScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onCadastroSuccess = {
                    navController.popBackStack()
                }
            )
        }

        // Detalhes da Empresa Screen
        composable(
            route = JuridicoScreens.DetalhesEmpresa.route,
            arguments = listOf(
                navArgument("empresaId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val empresaId = backStackEntry.arguments?.getString("empresaId")
            // TODO: Create and implement DetalhesEmpresaScreen
            // DetalhesEmpresaScreen(
            //     empresaId = empresaId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Detalhes do Produto Screen
        composable(
            route = JuridicoScreens.DetalhesProduto.route,
            arguments = listOf(
                navArgument("produtoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val produtoId = backStackEntry.arguments?.getString("produtoId")
            // TODO: Create and implement DetalhesProdutoScreen
            // DetalhesProdutoScreen(
            //     produtoId = produtoId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Relatórios Screen
        composable(JuridicoScreens.Relatorios.route) {
            // TODO: Create and implement RelatoriosScreen
            // RelatoriosScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Detalhes do Relatório Screen
        composable(
            route = JuridicoScreens.DetalhesRelatorio.route,
            arguments = listOf(
                navArgument("relatorioId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val relatorioId = backStackEntry.arguments?.getString("relatorioId")
            // TODO: Create and implement DetalhesRelatorioScreen
            // DetalhesRelatorioScreen(
            //     relatorioId = relatorioId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Documentos Screen
        composable(JuridicoScreens.Documentos.route) {
            // TODO: Create and implement DocumentosScreen
            // DocumentosScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Detalhes do Documento Screen
        composable(
            route = JuridicoScreens.DetalhesDocumento.route,
            arguments = listOf(
                navArgument("documentoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val documentoId = backStackEntry.arguments?.getString("documentoId")
            // TODO: Create and implement DetalhesDocumentoScreen
            // DetalhesDocumentoScreen(
            //     documentoId = documentoId,
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }

        // Configurações Screen
        composable(JuridicoScreens.Configuracoes.route) {
            // TODO: Create and implement ConfiguracoesScreen
            // ConfiguracoesScreen(
            //     onNavigateBack = { navController.popBackStack() }
            // )
        }
    }
}
