package com.example.infohub_telas.utils

/**
 * Constantes da aplicação InfoHub
 */
object Constants {

    // API Configuration
    const val BASE_URL = "http://10.0.2.2:8080/v1/infohub/"
    const val TIMEOUT_SECONDS = 30L

    // SharedPreferences Keys
    const val PREFS_NAME = "InfoHub_Prefs"
    const val KEY_JWT_TOKEN = "jwt_token"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_NAME = "user_name"
    const val KEY_USER_EMAIL = "user_email"
    const val KEY_USER_PHONE = "user_phone"
    const val KEY_USER_BIRTH_DATE = "user_birth_date"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_FIRST_RUN = "first_run"

    // Navigation Routes
    object Routes {
        const val SPLASH = "splash"
        const val ONBOARDING = "onboarding"
        const val LOGIN = "login"
        const val CADASTRO = "cadastro"
        const val RECUPERAR_SENHA = "recuperar_senha"
        const val HOME = "home"
        const val PRODUTOS = "produtos"
        const val PRODUTO_DETAIL = "produto_detail"
        const val CARRINHO = "carrinho"
        const val CHECKOUT = "checkout"
        const val PEDIDOS = "pedidos"
        const val PROMOCOES = "promocoes"
        const val FAVORITOS = "favoritos"
        const val PERFIL = "perfil"
        const val CONFIGURACOES = "configuracoes"
        const val CHAT_IA = "chat_ia"
        const val INFOCASH = "infocash"
        const val INFOCASH_HISTORICO = "infocash_historico"
        const val INFOCASH_RANKING = "infocash_ranking"
        const val REDE_SOCIAL = "rede_social"
        const val AVALIACOES = "avaliacoes"
        const val NOTIFICACOES = "notificacoes"
        const val ENDERECOS = "enderecos"
        const val ESTABELECIMENTOS = "estabelecimentos"
    }

    // Request Codes
    const val REQUEST_LOCATION_PERMISSION = 1001
    const val REQUEST_CAMERA_PERMISSION = 1002
    const val REQUEST_STORAGE_PERMISSION = 1003

    // InfoCash Configuration
    object InfoCash {
        const val PONTOS_AVALIACAO_PROMOCAO = 15
        const val PONTOS_AVALIACAO_EMPRESA = 10
        const val PONTOS_CADASTRO_PRODUTO = 5
        const val PONTOS_PRIMEIRA_COMPRA = 20
        const val PONTOS_COMPARTILHAR_APP = 25

        const val LIMITE_PONTOS_DIARIOS = 100
        const val LIMITE_AVALIACOES_DIARIAS = 10

        val TIPOS_ACAO = listOf(
            "avaliacao_promocao",
            "avaliacao_empresa",
            "cadastro_produto",
            "manual"
        )
    }

    // Chat IA Configuration
    object ChatIA {
        const val MAX_MENSAGENS_HISTORICO = 50
        const val TIMEOUT_RESPOSTA_MS = 30000

        val SUGESTOES_PADRAO = listOf(
            "Quais as promoções de hoje?",
            "Produtos mais baratos",
            "Melhores ofertas perto de mim",
            "Como funciona o InfoCash?",
            "Produtos em promoção",
            "Estabelecimentos parceiros",
            "Como usar o app?",
            "Onde encontrar mais descontos?"
        )
    }

    // UI Configuration
    object UI {
        const val ITEMS_PER_PAGE = 20
        const val MAX_IMAGE_SIZE_MB = 5
        const val DEBOUNCE_SEARCH_MS = 500L
        const val ANIMATION_DURATION_MS = 300L

        // Cores em hexadecimal
        const val COLOR_PRIMARY = "#1976D2"
        const val COLOR_SECONDARY = "#FFC107"
        const val COLOR_SUCCESS = "#4CAF50"
        const val COLOR_ERROR = "#F44336"
        const val COLOR_WARNING = "#FF9800"
        const val COLOR_INFO = "#2196F3"
    }

    // Validation Rules
    object Validation {
        const val MIN_PASSWORD_LENGTH = 6
        const val MAX_PASSWORD_LENGTH = 50
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 100
        const val MAX_DESCRIPTION_LENGTH = 1000
        const val MAX_COMMENT_LENGTH = 500
        const val CODIGO_RECUPERACAO_LENGTH = 6

        const val CEP_PATTERN = "^\\d{5}-?\\d{3}$"
        const val TELEFONE_PATTERN = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$"
        const val CNPJ_PATTERN = "^\\d{2}\\.\\d{3}\\.\\d{3}\\/\\d{4}-\\d{2}$"
    }

    // File and Storage
    object Storage {
        const val IMAGE_DIRECTORY = "InfoHub/Images"
        const val CACHE_DIRECTORY = "InfoHub/Cache"
        const val MAX_CACHE_SIZE_MB = 50
        const val CACHE_EXPIRY_DAYS = 7
    }

    // Network Configuration
    object Network {
        const val MAX_RETRY_ATTEMPTS = 3
        const val RETRY_DELAY_MS = 1000L
        const val CACHE_MAX_AGE_SECONDS = 300 // 5 minutos
        const val CACHE_MAX_STALE_DAYS = 7
    }

    // Feature Flags
    object Features {
        const val ENABLE_CHAT_IA = true
        const val ENABLE_INFOCASH = true
        const val ENABLE_REDE_SOCIAL = true
        const val ENABLE_PUSH_NOTIFICATIONS = true
        const val ENABLE_LOCATION_SERVICES = true
        const val ENABLE_ANALYTICS = false // Desabilitado por padrão
        const val ENABLE_DEBUG_MODE = true // Apenas em desenvolvimento
    }

    // Error Messages
    object ErrorMessages {
        const val GENERIC_ERROR = "Ocorreu um erro inesperado. Tente novamente."
        const val NETWORK_ERROR = "Sem conexão com a internet. Verifique sua conexão."
        const val SERVER_ERROR = "Erro no servidor. Tente novamente mais tarde."
        const val UNAUTHORIZED_ERROR = "Sessão expirada. Faça login novamente."
        const val NOT_FOUND_ERROR = "Recurso não encontrado."
        const val VALIDATION_ERROR = "Dados inválidos. Verifique as informações."
        const val TIMEOUT_ERROR = "Tempo limite excedido. Tente novamente."
    }

    // Success Messages
    object SuccessMessages {
        const val LOGIN_SUCCESS = "Login realizado com sucesso!"
        const val CADASTRO_SUCCESS = "Cadastro realizado com sucesso!"
        const val UPDATE_SUCCESS = "Informações atualizadas com sucesso!"
        const val DELETE_SUCCESS = "Item removido com sucesso!"
        const val ADD_TO_CART_SUCCESS = "Produto adicionado ao carrinho!"
        const val ADD_TO_FAVORITES_SUCCESS = "Produto adicionado aos favoritos!"
        const val ORDER_SUCCESS = "Pedido realizado com sucesso!"
        const val REVIEW_SUCCESS = "Avaliação enviada com sucesso!"
    }

    // Date Formats
    object DateFormats {
        const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        const val DISPLAY_DATE_FORMAT = "dd/MM/yyyy HH:mm"
        const val DISPLAY_DATE_SIMPLE = "dd/MM/yyyy"
        const val API_DATE_SIMPLE = "yyyy-MM-dd"
    }

    // Notification Types
    object NotificationTypes {
        const val PROMOCAO = "promocao"
        const val PEDIDO = "pedido"
        const val INFOCASH = "infocash"
        const val SOCIAL = "social"
        const val SISTEMA = "sistema"
    }

    // Analytics Events (se habilitado)
    object Analytics {
        const val LOGIN_EVENT = "user_login"
        const val CADASTRO_EVENT = "user_register"
        const val PRODUCT_VIEW = "product_view"
        const val ADD_TO_CART = "add_to_cart"
        const val PURCHASE = "purchase"
        const val SEARCH = "search"
        const val SHARE = "share"
    }
}
