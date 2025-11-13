package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para endpoints do InfoCash
 */
interface InfoCashService {

    /**
     * Consultar saldo atual do usuário
     * GET /infocash/saldo/{id}
     */
    @GET("infocash/saldo/{id}")
    suspend fun getSaldo(@Path("id") userId: Int): Response<SaldoInfoCashResponse>

    /**
     * Consultar histórico de transações
     * GET /infocash/historico/{id}
     */
    @GET("infocash/historico/{id}")
    suspend fun getHistorico(
        @Path("id") userId: Int,
        @Query("limite") limite: Int? = null
    ): Response<HistoricoInfoCashResponse>

    /**
     * Consultar resumo por tipo de ação
     * GET /infocash/resumo/{id}
     */
    @GET("infocash/resumo/{id}")
    suspend fun getResumo(@Path("id") userId: Int): Response<ResumoInfoCashResponse>

    /**
     * Consultar perfil completo (saldo + resumo)
     * GET /infocash/perfil/{id}
     */
    @GET("infocash/perfil/{id}")
    suspend fun getPerfil(@Path("id") userId: Int): Response<PerfilInfoCashResponse>

    /**
     * Consultar ranking de usuários
     * GET /infocash/ranking
     */
    @GET("infocash/ranking")
    suspend fun getRanking(@Query("limite") limite: Int? = null): Response<RankingInfoCashResponse>

    /**
     * Consultar estatísticas gerais (Admin)
     * GET /infocash/estatisticas
     */
    @GET("infocash/estatisticas")
    suspend fun getEstatisticas(): Response<EstatisticasInfoCashResponse>

    /**
     * Consultar transações por período
     * GET /infocash/periodo/{id}
     */
    @GET("infocash/periodo/{id}")
    suspend fun getTransacoesPorPeriodo(
        @Path("id") userId: Int,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String
    ): Response<HistoricoInfoCashResponse>

    /**
     * Conceder pontos manualmente (Admin)
     * POST /infocash/conceder
     */
    @POST("infocash/conceder")
    suspend fun concederPontos(@Body request: ConcederPontosRequest): Response<ConcederPontosResponse>
}
