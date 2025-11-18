package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

// ===============================
// MODELS DO CARRINHO - CONFORME DOCUMENTAÇÃO API
// ===============================

/**
 * Item do carrinho conforme schema da API
 *
 * Representa um produto adicionado ao carrinho com todas as informações
 * necessárias para exibição e cálculo de valores
 */
data class CarrinhoItem(
    @SerializedName("id_carrinho")
    val idCarrinho: Int,

    @SerializedName("id_usuario")
    val idUsuario: Int,

    @SerializedName("id_produto")
    val idProduto: Int,

    @SerializedName("id_estabelecimento")
    val idEstabelecimento: Int,

    @SerializedName("quantidade")
    val quantidade: Int,

    @SerializedName("data_adicao")
    val dataAdicao: String, // ISO 8601 format: "2025-11-11T10:30:00Z"

    @SerializedName("produto")
    val produto: Produto? = null, // Detalhes do produto

    @SerializedName("valor_total")
    val valorTotal: Double // Valor total do item (preço * quantidade)
) {
    /**
     * Verifica se o item tem dados válidos
     */
    fun isValid(): Boolean {
        return idCarrinho > 0 &&
               idUsuario > 0 &&
               idProduto > 0 &&
               idEstabelecimento > 0 &&
               quantidade > 0 &&
               valorTotal > 0
    }
}

/**
 * Request para adicionar item ao carrinho
 *
 * ✅ Campos obrigatórios conforme documentação:
 * - id_usuario: ID do usuário
 * - id_produto: ID do produto
 * - id_estabelecimento: ID do estabelecimento
 * - quantidade: Quantidade (mínimo 1)
 */
data class AdicionarCarrinhoRequest(
    @SerializedName("id_usuario")
    val idUsuario: Int,

    @SerializedName("id_produto")
    val idProduto: Int,

    @SerializedName("id_estabelecimento")
    val idEstabelecimento: Int,

    @SerializedName("quantidade")
    val quantidade: Int // Mínimo 1
) {
    init {
        require(idUsuario > 0) { "ID do usuário deve ser maior que zero" }
        require(idProduto > 0) { "ID do produto deve ser maior que zero" }
        require(idEstabelecimento > 0) { "ID do estabelecimento deve ser maior que zero" }
        require(quantidade > 0) { "Quantidade deve ser maior que zero" }
    }
}

/**
 * Request para atualizar quantidade conforme documentação
 *
 * Usado no endpoint PUT /carrinho/{id}
 */
data class AtualizarQuantidadeRequest(
    @SerializedName("quantidade")
    val quantidade: Int // Mínimo 1
) {
    init {
        require(quantidade > 0) { "Quantidade deve ser maior que zero" }
    }
}

/**
 * Response da listagem do carrinho
 *
 * ✅ Formato conforme documentação:
 * - status: boolean indicando sucesso
 * - carrinho: array de itens do carrinho
 * - valor_total: valor total do carrinho
 */
data class CarrinhoResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("carrinho")
    val carrinho: List<CarrinhoItem>,

    @SerializedName("valor_total")
    val valorTotal: Double
) {
    /**
     * Verifica se o carrinho está vazio
     */
    fun isEmpty(): Boolean = carrinho.isEmpty()

    /**
     * Retorna a quantidade total de itens
     */
    fun getTotalItems(): Int = carrinho.sumOf { it.quantidade }
}

/**
 * Response padrão para operações do carrinho
 *
 * ⚠️ NOTA: Este modelo pode precisar ser ajustado conforme
 * resposta real da API, pois a documentação não especifica
 * o formato exato das respostas de operações
 */
data class CarrinhoOperationResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("status_code")
    val statusCode: Int? = null
)

