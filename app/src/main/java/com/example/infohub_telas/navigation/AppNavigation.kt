package com.example.infohub_telas.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.infohub_telas.model.DashboardData
import com.example.infohub_telas.screens.*
import com.example.infohub_telas.telas.*
import com.example.infohub_telas.telas.juridico.*

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME
    ) {
        // Common routes
        composable(Routes.HOME) {
            TelaHome(navController)
        }

        composable(Routes.LOGIN) {
            TelaLogin(navController)
        }

        // Normal user routes
        composable(Routes.USER_PROFILE) {
            TelaPerfil(navController)
        }

        composable(Routes.CART) {
            TelaCarrinho(navController)
        }

        // Admin routes
        composable(Routes.ADMIN_DASHBOARD) {
            DashboardEmpresa(
                navController = navController,
                dashboardData = DashboardData.getPreview() // Using preview data
            )
        }

        // Juridical routes
        composable(JuridicoRoutes.HOME) {
            JuridicoHomeScreen(navController)
        }

        composable(
            "${JuridicoRoutes.CADASTRO_EMPRESA}?empresaId={empresaId}",
            arguments = listOf(
                navArgument("empresaId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            JuridicoCadastroEmpresaScreen(
                navController = navController,
                empresaId = it.arguments?.getString("empresaId")
            )
        }

        composable(JuridicoRoutes.GERENCIAMENTO_EMPRESAS) {
            JuridicoGerenciamentoEmpresasScreen(navController)
        }

        composable(
            "${JuridicoRoutes.PRODUTOS}?empresaId={empresaId}",
            arguments = listOf(
                navArgument("empresaId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            JuridicoProdutosScreen(
                navController = navController,
                empresaId = it.arguments?.getString("empresaId")
            )
        }

        composable(
            "${JuridicoRoutes.RELATORIOS}/{empresaId}",
            arguments = listOf(
                navArgument("empresaId") {
                    type = NavType.StringType
                    nullable = true
                }
            )
        ) {
            RelatoriosScreen(
                navController = navController,
                empresaId = it.arguments?.getString("empresaId")
            )
        }

        composable(
            "${JuridicoRoutes.DETALHES_RELATORIO}/{relatorioId}",
            arguments = listOf(
                navArgument("relatorioId") {
                    type = NavType.StringType
                }
            )
        ) {
            DetalhesRelatorioScreen(
                navController = navController,
                relatorioId = it.arguments?.getString("relatorioId") ?: ""
            )
        }
    }
}
