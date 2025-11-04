package com.example.infohub_telas.navigation

object Routes {
    // Authentication & Onboarding Routes
    const val OPEN = "open"
    const val LOGIN = "login"
    const val LOGIN_CADASTRO = "login_cadastro"
    const val CADASTRO = "cadastro"
    const val CADASTRO_ENDERECO = "cadastro_endereco"
    const val TERMO_USO = "termo_uso"
    const val REDEFINICAO_SENHA = "redefinicao_senha"
    const val CONFIRMAR_CODIGO = "confirmar_codigo"
    const val CRIAR_NOVA_SENHA = "criar_nova_senha"

    // Main App Routes
    const val HOME = "home"
    const val LOCALIZACAO = "localizacao"
    const val CHAT_PRECOS = "chat_precos"

    // User Profile Routes
    const val PERFIL = "perfil"
    const val PERFIL_PREVIEW = "perfil_preview"
    const val CONFIGURACAO_PERFIL = "configuracao_perfil"
    const val EDICAO_PERFIL = "edicao_perfil"
    const val INFO_CASH = "info_cash"

    // Shopping Routes
    const val LISTA_PRODUTOS = "lista_produtos"
    const val PRODUTO = "produto/{produtoId}"
    const val CARRINHO = "carrinho"
    const val CHECKOUT = "checkout"
    const val PAGAMENTO = "pagamento"
    const val PAGAMENTO_SUCESSO = "pagamento_sucesso"

    // Establishment Routes
    const val MEU_ESTABELECIMENTO = "meu_estabelecimento"
    const val CADASTRO_ESTABELECIMENTO = "cadastro_estabelecimento"
    const val CADASTRO_PROMOCAO = "cadastro_promocao"

    // Management & Reports Routes
    const val DASHBOARD_EMPRESA = "dashboard_empresa"
    const val GERENCIAMENTO_EMPRESAS = "gerenciamento_empresas"
    const val CADASTRO_EDICAO_EMPRESA = "cadastro_edicao_empresa"
    const val PERFIL_EMPRESA = "perfil_empresa"
    const val RELATORIOS = "relatorios"
    const val GERAR_RELATORIO = "gerar_relatorio"
    const val VISUALIZAR_RELATORIO = "visualizar_relatorio"
    const val DETALHES_RELATORIO = "detalhes_relatorio/{relatorioId}"
}

// JuridicoRoutes moved to JuridicoRoutes.kt
