package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

// ===============================
// MODELS DO CARRINHO
// ===============================

/**
 * Item do carrinho
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
    val dataAdicao: String,
    @SerializedName("produto")
    val produto: Produto? = null,
    @SerializedName("valor_total")
    val valorTotal: Double
)

/**
 * Request para adicionar item ao carrinho
 */
data class AdicionarCarrinhoRequest(
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("id_produto")
    val idProduto: Int,
    @SerializedName("id_estabelecimento")
    val idEstabelecimento: Int,
    @SerializedName("quantidade")
    val quantidade: Int
)

/**
 * Request para atualizar quantidade
 */
data class AtualizarQuantidadeRequest(
    @SerializedName("quantidade")
    val quantidade: Int
)

/**
 * Response da lista do carrinho
 */
data class CarrinhoResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("carrinho")
    val carrinho: List<CarrinhoItem>,
    @SerializedName("valor_total")
    val valorTotal: Double
)

/**
 * Response de operações simples do carrinho
 */
data class CarrinhoOperationResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int? = null,
    @SerializedName("message")
    val message: String
)

