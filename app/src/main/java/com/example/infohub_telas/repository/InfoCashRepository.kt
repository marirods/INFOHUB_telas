package com.example.infohub_telas.repository

import com.example.infohub_telas.model.*
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
    suspend fun getSaldoInfoCash(userId: Int): Result<SaldoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getSaldoInfoCash(userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar saldo: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar histórico de transações
     */
    suspend fun getHistoricoInfoCash(userId: Int, limite: Int? = null): Result<HistoricoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getHistoricoInfoCash(userId, limite)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar histórico: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar resumo por tipo de ação
     */
    suspend fun getResumoInfoCash(userId: Int): Result<ResumoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getResumoInfoCash(userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar resumo: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar perfil completo (saldo + resumo)
     */
    suspend fun getPerfilInfoCash(userId: Int): Result<PerfilInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getPerfilInfoCash(userId)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar perfil: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar ranking de usuários
     */
    suspend fun getRankingInfoCash(limite: Int? = null): Result<RankingInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getRankingInfoCash(limite)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar ranking: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar estatísticas gerais (Admin)
     */
    suspend fun getEstatisticasInfoCash(): Result<EstatisticasInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getEstatisticasInfoCash()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar estatísticas: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar transações por período
     */
    suspend fun getTransacoesPorPeriodo(
        userId: Int,
        dataInicio: String,
        dataFim: String
    ): Result<HistoricoInfoCashResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getTransacoesPorPeriodo(userId, dataInicio, dataFim)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao buscar transações por período: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Conceder pontos manualmente (Admin)
     */
    suspend fun concederPontos(request: ConcederPontosRequest): Result<ConcederPontosResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.concederPontos(request)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao conceder pontos: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

