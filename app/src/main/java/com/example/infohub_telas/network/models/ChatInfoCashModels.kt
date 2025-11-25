package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// ===============================
// CHAT IA
// ===============================

data class ChatRequest(
    @SerializedName("mensagem") val mensagem: String,
    @SerializedName("idUsuario") val idUsuario: Int
)

data class ChatResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ChatData
)

data class ChatData(
    @SerializedName("reply") val reply: String,
    @SerializedName("confidence") val confidence: Float,
    @SerializedName("response_time_ms") val responseTimeMs: Int,
    @SerializedName("toolsUsed") val toolsUsed: List<String>? = null
)

data class GroqRequest(
    @SerializedName("pergunta") val pergunta: String
)

data class GroqResponse(
    @SerializedName("resposta") val resposta: String,
    @SerializedName("fonte") val fonte: String,
    @SerializedName("tempo_resposta") val tempoResposta: String
)

data class FastChatRequest(
    @SerializedName("message") val message: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("session_id") val sessionId: String? = null
)

data class FastChatResponse(
    @SerializedName("reply") val reply: String,
    @SerializedName("confidence") val confidence: Float,
    @SerializedName("response_time_ms") val responseTimeMs: Int,
    @SerializedName("method") val method: String,
    @SerializedName("toolsUsed") val toolsUsed: List<String>
)

