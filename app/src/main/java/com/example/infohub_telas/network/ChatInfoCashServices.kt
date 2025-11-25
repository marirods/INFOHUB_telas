package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para Chat IA
 */
interface ChatIAService {

    /**
     * Interagir com IA (principal)
     * POST /interagir
     */
    @POST("interagir")
    suspend fun interagir(@Body request: ChatRequest): Response<ChatResponse>

    /**
     * Chat com IA Groq
     * POST /groq
     */
    @POST("groq")
    suspend fun chatGroq(@Body request: GroqRequest): Response<GroqResponse>

    /**
     * Chat com IA Groq (alternativo)
     * POST /chat-groq
     */
    @POST("chat-groq")
    suspend fun chatGroqAlternativo(@Body request: GroqRequest): Response<GroqResponse>

    /**
     * Resposta Ollama (Legacy)
     * POST /respostaOllama
     */
    @POST("respostaOllama")
    suspend fun respostaOllama(@Body request: ChatRequest): Response<ChatResponse>
}

// InfoCashService is defined in InfoCashService.kt file to avoid redeclaration
