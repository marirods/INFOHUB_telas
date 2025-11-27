package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de Categoria conforme API
 */
data class Categoria(
    @SerializedName("id_categoria")
    val id: Int? = null,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("descricao")
    val descricao: String? = null,

    @SerializedName("total_produtos")
    val totalProdutos: Int? = null,

    @SerializedName("produtos")
    val produtos: List<Produto>? = null
)

