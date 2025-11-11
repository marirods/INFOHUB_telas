package com.example.infohub_telas.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.infohub_telas.model.DashboardData
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.navigation.JuridicoRoutes
import com.example.infohub_telas.telas.*
import com.example.infohub_telas.telas.juridico.JuridicoHomeScreen
import com.example.infohub_telas.telas.juridico.JuridicoGerenciamentoEmpresasScreen
import com.example.infohub_telas.telas.juridico.JuridicoCadastroEmpresaScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Routes.OPEN
    ) {
        // Authentication & Onboarding
        composable(Routes.OPEN) {
            OpenScreen(navController)
        }

        composable(Routes.WELCOME) {
            WelcomeScreen(navController)
        }

        composable(Routes.LOGIN) {
            TelaLogin(navController)
        }

        composable(Routes.LOGIN_CADASTRO) {
            TelaLoginCadastro(navController)
        }

        composable(Routes.CADASTRO) {
            TelaCadastro(navController)
        }

        composable(Routes.CADASTRO_ENDERECO) {
            TelaCadastroEndereco(navController)
        }

        composable(Routes.TERMO_USO) {
            TelaTermoDeUso(navController)
        }

        composable(Routes.REDEFINICAO_SENHA) {
            TelaRedefinicaoSenha(navController)
        }

        composable(Routes.CONFIRMAR_CODIGO) {
            TelaConfirmarCodigo(navController)
        }

        composable(Routes.CRIAR_NOVA_SENHA) {
            TelaCriarNovaSenha(navController)
        }

        // Main App Screens
        composable(Routes.HOME) {
            TelaHome(navController)
        }

        composable(JuridicoRoutes.HOME) {
            JuridicoHomeScreen(navController)
        }

        composable(JuridicoRoutes.GERENCIAMENTO_EMPRESAS) {
            JuridicoGerenciamentoEmpresasScreen(navController)
        }

        composable(
            route = "${JuridicoRoutes.CADASTRO_EMPRESA}/{empresaId}",
            arguments = listOf(navArgument("empresaId") { 
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            JuridicoCadastroEmpresaScreen(
                navController = navController,
                empresaId = backStackEntry.arguments?.getString("empresaId")
            )
        }

        composable(JuridicoRoutes.CADASTRO_EMPRESA) {
            JuridicoCadastroEmpresaScreen(navController)
        }

        composable(
            route = "${JuridicoRoutes.RELATORIOS}/{empresaId}",
            arguments = listOf(navArgument("empresaId") { 
                type = NavType.StringType
            })
        ) { backStackEntry ->
            // TODO: Implementar tela de relatórios jurídicos
            // Por enquanto volta para a tela anterior
            navController.popBackStack()
        }

        composable(Routes.LOCALIZACAO) {
            TelaLocalizacao(navController)
        }

        composable(Routes.CHAT_PRECOS) {
            TelaChatDePrecos(navController)
        }

        composable(Routes.INFOCASH) {
            TelaInfoCash(navController)
        }

        // User Profile
        composable(Routes.PERFIL) {
            TelaPerfil(navController)
        }

        composable(Routes.PERFIL_PREVIEW) {
            TelaPerfilPreview(navController)
        }

        composable(Routes.CONFIGURACAO_PERFIL) {
            ConfiguracaoPerfil(
                navController = navController,
                empresa = Empresa(),
                onSaveChanges = { /* Implementar salvamento das alterações */ }
            )
        }

        // Shopping Flow
        composable(Routes.LISTA_PRODUTOS) {
            TelaListaProdutos(navController)
        }

        composable(
            route = Routes.PRODUTO,
            arguments = listOf(navArgument("produtoId") { type = NavType.StringType })
        ) {
            TelaProduto(
                navController = navController,
                id = it.arguments?.getString("produtoId") ?: ""
            )
        }

        composable(Routes.CARRINHO) {
            TelaCarrinho(navController)
        }

        composable(Routes.CHECKOUT) {
            TelaCheckout(navController)
        }

        composable(Routes.PAGAMENTO) {
            TelaPagamento(navController)
        }

        composable(Routes.PAGAMENTO_SUCESSO) {
            TelaPagamentoSucesso(navController)
        }

        // Establishment Management
        composable(Routes.MEU_ESTABELECIMENTO) {
            TelaMeuEstabelecimento(navController)
        }

        composable(Routes.CADASTRO_ESTABELECIMENTO) {
            TelaCadastroEstabelecimento(navController)
        }

        composable(Routes.CADASTRO_PROMOCAO) {
            TelaCadastroPromocao(navController)
        }

        composable(Routes.CADASTRO_PRODUTO) {
            TelaCadastroProduto(navController)
        }

        // Business Management & Reports
        composable(Routes.DASHBOARD_EMPRESA) {
            DashboardEmpresa(
                navController = navController,
                dashboardData = DashboardData.getPreview()
            )
        }

        composable(Routes.GERENCIAMENTO_EMPRESAS) {
            GerenciamentoEmpresasScreen(
                navController = navController
            )
        }

        composable(Routes.CADASTRO_EDICAO_EMPRESA) {
            CadastroEdicaoEmpresaScreen(
                navController = navController
            )
        }

        composable(Routes.PERFIL_EMPRESA) {
            PerfilEmpresa(
                navController = navController,
                empresa = Empresa()
            )
        }

        composable(Routes.RELATORIOS) {
            RelatoriosScreen(
                navController = navController
            )
        }

        composable(Routes.GERAR_RELATORIO) {
            GerarRelatorioScreen(
                navController = navController
            )
        }

        composable(Routes.VISUALIZAR_RELATORIO) {
            VisualizarRelatorioScreen(
                navController = navController
            )
        }

        composable(
            route = Routes.DETALHES_RELATORIO,
            arguments = listOf(navArgument("relatorioId") { type = NavType.StringType })
        ) { backStackEntry ->
            DetalhesRelatorioScreen(
                navController = navController,
                relatorioId = backStackEntry.arguments?.getString("relatorioId") ?: "1"
            )
        }
    }
}
