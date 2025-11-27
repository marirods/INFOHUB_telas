package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Resposta da API para cadastro de estabelecimento
 * Conforme documentação: POST /estabelecimento
 */
data class EstabelecimentoResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("id")
    val id: Int? = null
)
