package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Resposta da API para listar estabelecimentos
 * Conforme documentação: /estabelecimentos
 */
data class EstabelecimentosResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("estabelecimentos")
    val estabelecimentos: List<Estabelecimento>,

    @SerializedName("message")
    val message: String? = null
)
