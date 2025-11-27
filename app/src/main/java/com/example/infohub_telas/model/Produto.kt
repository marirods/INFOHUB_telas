package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de Produto para requisições da API
 */
data class Produto(
    @SerializedName("id")
    val id: Int? = null,
    
    @SerializedName("nome")
    val nome: String,
    
    @SerializedName("descricao")
    val descricao: String,
    
    @SerializedName("id_categoria")
    val idCategoria: Int,
    
    @SerializedName("id_estabelecimento")
    val idEstabelecimento: Int,
    
    @SerializedName("preco")
    val preco: Double,
    
    @SerializedName("imagem")
    val imagem: String? = null,

    @SerializedName("promocao")
    val promocao: PromocaoProdutoRequest? = null
)

data class PromocaoProdutoRequest(
    @SerializedName("preco_promocional")
    val precoPromocional: Double,
    
    @SerializedName("data_inicio")
    val dataInicio: String,
    
    @SerializedName("data_fim")
    val dataFim: String
)
