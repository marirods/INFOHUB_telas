package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Request para adicionar item ao carrinho
 * CONFORME DOCUMENTAÇÃO OFICIAL DA API
 *
 * Endpoint: POST /carrinho
 * Headers:
 *   - Authorization: Bearer {token}
 *   - Content-Type: application/json
 */
data class AdicionarItemCarrinhoRequest(
    @SerializedName("id_usuario")
    val id_usuario: Int,

    @SerializedName("id_produto")
    val id_produto: Int,

    @SerializedName("quantidade")
    val quantidade: Int = 1
)

/**
 * Modelo de ItemCarrinho retornado pela API
 * CONFORME DOCUMENTAÇÃO OFICIAL
 */
data class ItemCarrinhoApi(
    @SerializedName("id_carrinho")
    val id_carrinho: Int? = null,

    @SerializedName("id_usuario")
    val id_usuario: Int,

    @SerializedName("id_produto")
    val id_produto: Int,

    @SerializedName("quantidade")
    val quantidade: Int,

    @SerializedName("data_adicionado")
    val data_adicionado: String? = null,

    @SerializedName("nome_produto")
    val nome_produto: String? = null,

    @SerializedName("descricao")
    val descricao: String? = null,

    @SerializedName("imagem")
    val imagem: String? = null,

    @SerializedName("categoria")
    val categoria: String? = null,

    @SerializedName("preco_atual")
    val preco_atual: Double? = null,

    @SerializedName("preco_promocional")
    val preco_promocional: Double? = null,

    @SerializedName("promocao_valida_ate")
    val promocao_valida_ate: String? = null
)

/**
 * Response genérico da API conforme documentação
 */
data class ApiCarrinhoResponse<T>(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val status_code: Int,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: T? = null
)

