package com.example.infohub_telas.navigation

object JuridicoRoutes {
    const val HOME = "juridicoHome"
    const val CADASTRO_EMPRESA = "juridicoCadastroEmpresa"
    const val GERENCIAMENTO_EMPRESAS = "juridicoGerenciamentoEmpresas"
    const val RELATORIOS = "juridicoRelatorios"
    const val DETALHES_RELATORIO = "juridicoDetalhesRelatorio"
    const val PERFIL_EMPRESA = "juridicoPerfilEmpresa"
    const val CONFIGURACOES = "juridicoConfiguracoes"
    const val PRODUTOS = "juridicoProdutos"

    // Helper functions for navigation
    fun cadastroEmpresa(empresaId: String? = null) = when (empresaId) {
        null -> CADASTRO_EMPRESA
        else -> "$CADASTRO_EMPRESA?empresaId=$empresaId"
    }

    fun relatorios(empresaId: String? = null) = when (empresaId) {
        null -> RELATORIOS
        else -> "$RELATORIOS/$empresaId"
    }

    fun detalhesRelatorio(relatorioId: String) = "$DETALHES_RELATORIO/$relatorioId"

    fun produtos(empresaId: String? = null) = when (empresaId) {
        null -> PRODUTOS
        else -> "$PRODUTOS/$empresaId"
    }
}
