package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.network.models.*
import com.example.infohub_telas.service.InfoCashApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Repository para InfoCash que encapsula as chamadas da API
 */
class InfoCashRepository {

    private val apiService: InfoCashApiService by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/v1/infohub/")
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(InfoCashApiService::class.java)
    }

    /**
     * Buscar saldo do usuário
     */
    suspend fun getSaldoInfoCash(token: String, userId: Int): Result<SaldoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("InfoCashRepository", "🔑 Chamando getSaldoInfoCash com token: ${token.take(20)}...")
                Log.d("InfoCashRepository", "👤 User ID: $userId")

                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getSaldoInfoCash(authToken, userId)

                Log.d("InfoCashRepository", "📈 HTTP Status: ${response.code()}")
                Log.d("InfoCashRepository", "✅ Success: ${response.isSuccessful}")

                if (response.isSuccessful && response.body() != null) {
                    Log.d("InfoCashRepository", "📦 Response body: ${response.body()}")
                    Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("InfoCashRepository", "❌ Erro HTTP ${response.code()}: ${response.message()}")
                    Log.e("InfoCashRepository", "❌ Error body: $errorBody")
                    Result.failure(Exception("Erro ao buscar saldo: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("InfoCashRepository", "💥 Exceção ao buscar saldo: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar histórico de transações
     */
    suspend fun getHistoricoInfoCash(token: String, userId: Int, limite: Int? = null): Result<HistoricoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("InfoCashRepository", "🔑 Chamando getHistoricoInfoCash com token: ${token.take(20)}...")
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getHistoricoInfoCash(authToken, userId, limite)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar histórico: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Log.e("InfoCashRepository", "💥 Exceção ao buscar histórico: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar resumo por tipo de ação
     */
    suspend fun getResumoInfoCash(token: String, userId: Int): Result<ResumoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getResumoInfoCash(authToken, userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar resumo: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar perfil completo (saldo + resumo)
     */
    suspend fun getPerfilInfoCash(token: String, userId: Int): Result<PerfilInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getPerfilInfoCash(authToken, userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar perfil: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar ranking de usuários
     */
    suspend fun getRankingInfoCash(token: String, limite: Int? = null): Result<RankingInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getRankingInfoCash(authToken, limite)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar ranking: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar transações por período
     */
    suspend fun getTransacoesPorPeriodo(token: String, userId: Int, dataInicio: String, dataFim: String): Result<HistoricoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.getTransacoesPorPeriodo(authToken, userId, dataInicio, dataFim)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar transações por período: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Conceder pontos manualmente (função admin)
     */
    suspend fun concederPontos(token: String, request: ConcederPontosRequest): Result<ConcederPontosResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val authToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                val response = apiService.concederPontos(authToken, request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao conceder pontos: HTTP ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
