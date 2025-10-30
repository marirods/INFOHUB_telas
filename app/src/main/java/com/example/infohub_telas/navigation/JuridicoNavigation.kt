package com.example.infohub_telas.navigation

sealed class JuridicoScreens(val route: String) {
    object Home : JuridicoScreens("juridico_home")
    object GerenciamentoEmpresas : JuridicoScreens("juridico_gerenciamento_empresas")
    object Produtos : JuridicoScreens("juridico_produtos")
    object CadastroEmpresa : JuridicoScreens("juridico_cadastro_empresa")
    object Relatorios : JuridicoScreens("juridico_relatorios")
    object Documentos : JuridicoScreens("juridico_documentos")
    object Configuracoes : JuridicoScreens("juridico_configuracoes")

    // Rotas com parâmetros
    object DetalhesEmpresa : JuridicoScreens("juridico_detalhes_empresa/{empresaId}") {
        fun createRoute(empresaId: String) = "juridico_detalhes_empresa/$empresaId"
    }

    object DetalhesProduto : JuridicoScreens("juridico_detalhes_produto/{produtoId}") {
        fun createRoute(produtoId: String) = "juridico_detalhes_produto/$produtoId"
    }

    object DetalhesRelatorio : JuridicoScreens("juridico_detalhes_relatorio/{relatorioId}") {
        fun createRoute(relatorioId: String) = "juridico_detalhes_relatorio/$relatorioId"
    }

    object DetalhesDocumento : JuridicoScreens("juridico_detalhes_documento/{documentoId}") {
        fun createRoute(documentoId: String) = "juridico_detalhes_documento/$documentoId"
    }
}

object JuridicoGraph {
    const val ROUTE = "juridico"

    // Graph names for nested navigation
    const val HOME = "home"
    const val EMPRESAS = "empresas"
    const val PRODUTOS = "produtos"
    const val RELATORIOS = "relatorios"
    const val DOCUMENTOS = "documentos"
}
