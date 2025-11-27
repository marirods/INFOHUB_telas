package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Resposta da API para listar categorias
 * Conforme documentação: /categorias
 */
data class CategoriasResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("categorias")
    val categorias: List<Categoria>,

    @SerializedName("message")
    val message: String? = null
)
