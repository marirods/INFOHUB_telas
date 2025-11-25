package com.example.infohub_telas.repository

import android.content.Context
import com.example.infohub_telas.network.ApiConfig
import com.example.infohub_telas.network.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para operações de Chat IA
 */
class ChatIARepository(context: Context) {

    private val apiConfig = ApiConfig.getInstance(context)
    private val chatService = apiConfig.chatIAService

    /**
     * Interagir com IA (endpoint principal)
     */
    suspend fun interagir(mensagem: String, idUsuario: Int): Result<ChatData> {
        return withContext(Dispatchers.IO) {
            try {
                val request = ChatRequest(mensagem, idUsuario)
                val response = chatService.interagir(request)

                if (response.isSuccessful && response.body() != null) {
                    val chatResponse = response.body()!!
                    if (chatResponse.status) {
                        Result.success(chatResponse.data)
                    } else {
                        Result.failure(Exception(chatResponse.message))
                    }
                } else {
                    Result.failure(Exception("Erro na comunicação com a IA"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Chat com IA Groq
     */
    suspend fun chatGroq(pergunta: String): Result<GroqResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = GroqRequest(pergunta)
                val response = chatService.chatGroq(request)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro na comunicação com Groq"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Chat rápido - para consultas simples
     */
    suspend fun chatRapido(mensagem: String, idUsuario: Int): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val result = interagir(mensagem, idUsuario)
                if (result.isSuccess) {
                    Result.success(result.getOrNull()?.reply ?: "Resposta não disponível")
                } else {
                    result.exceptionOrNull()?.let { Result.failure(it) } ?: Result.failure(Exception("Erro desconhecido"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}


