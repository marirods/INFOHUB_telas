package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelos para o endpoint /groq da API InfoHub
 */

data class GroqRequest(
    @SerializedName("pergunta")
    val pergunta: String
)

data class GroqResponse(
    @SerializedName("resposta")
    val resposta: String,
    @SerializedName("fonte")
    val fonte: String,
    @SerializedName("tempo_resposta")
    val tempoResposta: String
)
