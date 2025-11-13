package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelos para o Chat IA baseados na documentação da API
 */

// ===============================
// CHAT IA - REQUESTS
// ===============================

data class ChatIARequest(
    @SerializedName("mensagem")
    val mensagem: String,
    @SerializedName("idUsuario")
    val idUsuario: Int
)

data class FastChatRequest(
    @SerializedName("message")
    val message: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("session_id")
    val sessionId: String
)

// ===============================
// CHAT IA - RESPONSES
// ===============================

data class ChatIAResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ChatIAData
)

data class ChatIAData(
    @SerializedName("reply")
    val reply: String,
    @SerializedName("confidence")
    val confidence: Float,
    @SerializedName("response_time_ms")
    val responseTimeMs: Int,
    @SerializedName("toolsUsed")
    val toolsUsed: List<String>
)

data class FastChatResponse(
    @SerializedName("reply")
    val reply: String,
    @SerializedName("confidence")
    val confidence: Float,
    @SerializedName("response_time_ms")
    val responseTimeMs: Int,
    @SerializedName("method")
    val method: String,
    @SerializedName("toolsUsed")
    val toolsUsed: List<String>
)
