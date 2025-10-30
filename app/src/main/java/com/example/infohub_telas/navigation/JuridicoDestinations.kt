package com.example.infohub_telas.navigation

sealed class JuridicoDestinations(val route: String) {
    // Main screens
    object Home : JuridicoDestinations("juridico_home")
    object GerenciamentoEmpresas : JuridicoDestinations("juridico_gerenciamento_empresas")
    object Produtos : JuridicoDestinations("juridico_produtos")
    object CadastroEmpresa : JuridicoDestinations("juridico_cadastro_empresa")

    // Detail screens with parameters
    object DetalhesEmpresa : JuridicoDestinations("juridico_detalhes_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "juridico_detalhes_empresa/$empresaId"
    }

    object DetalhesProduto : JuridicoDestinations("juridico_detalhes_produto/{produtoId}") {
        fun createRoute(produtoId: String) = "juridico_detalhes_produto/$produtoId"
    }

    object Relatorios : JuridicoDestinations("juridico_relatorios")
    object DocumentosLegais : JuridicoDestinations("juridico_documentos")
    object ConfiguracoesJuridico : JuridicoDestinations("juridico_configuracoes")

    companion object {
        fun fromRoute(route: String): JuridicoDestinations {
            return when (route) {
                Home.route -> Home
                GerenciamentoEmpresas.route -> GerenciamentoEmpresas
                Produtos.route -> Produtos
                CadastroEmpresa.route -> CadastroEmpresa
                Relatorios.route -> Relatorios
                DocumentosLegais.route -> DocumentosLegais
                ConfiguracoesJuridico.route -> ConfiguracoesJuridico
                else -> Home
            }
        }
    }
}
