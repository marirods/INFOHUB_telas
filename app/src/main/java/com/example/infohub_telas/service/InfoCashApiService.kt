package com.example.infohub_telas.service

import com.example.infohub_telas.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface do serviço InfoCash API completa baseada na documentação
 */
interface InfoCashApiService {

    /**
     * Consultar saldo atual do usuário
     * GET /infocash/saldo/{id}
     */
    @GET("infocash/saldo/{id}")
    suspend fun getSaldoInfoCash(
        @Header("Authorization") token: String,
        @Path("id") idUsuario: Int
    ): Response<SaldoInfoCashResponse>

    /**
     * Consultar histórico de transações
     * GET /infocash/historico/{id}
     */
    @GET("infocash/historico/{id}")
    suspend fun getHistoricoInfoCash(
        @Header("Authorization") token: String,
        @Path("id") idUsuario: Int,
        @Query("limite") limite: Int? = null
    ): Response<HistoricoInfoCashResponse>

    /**
     * Consultar resumo por tipo de ação
     * GET /infocash/resumo/{id}
     */
    @GET("infocash/resumo/{id}")
    suspend fun getResumoInfoCash(
        @Header("Authorization") token: String,
        @Path("id") idUsuario: Int
    ): Response<ResumoInfoCashResponse>

    /**
     * Consultar perfil completo (saldo + resumo)
     * GET /infocash/perfil/{id}
     */
    @GET("infocash/perfil/{id}")
    suspend fun getPerfilInfoCash(
        @Header("Authorization") token: String,
        @Path("id") idUsuario: Int
    ): Response<PerfilInfoCashResponse>

    /**
     * Consultar ranking de usuários
     * GET /infocash/ranking
     */
    @GET("infocash/ranking")
    suspend fun getRankingInfoCash(
        @Header("Authorization") token: String,
        @Query("limite") limite: Int? = null
    ): Response<RankingInfoCashResponse>

    /**
     * Consultar estatísticas gerais (Admin)
     * GET /infocash/estatisticas
     */
    @GET("infocash/estatisticas")
    suspend fun getEstatisticasInfoCash(
        @Header("Authorization") token: String
    ): Response<EstatisticasInfoCashResponse>

    /**
     * Consultar transações por período
     * GET /infocash/periodo/{id}
     */
    @GET("infocash/periodo/{id}")
    suspend fun getTransacoesPorPeriodo(
        @Header("Authorization") token: String,
        @Path("id") idUsuario: Int,
        @Query("dataInicio") dataInicio: String,
        @Query("dataFim") dataFim: String
    ): Response<HistoricoInfoCashResponse>

    /**
     * Conceder pontos manualmente (Admin)
     * POST /infocash/conceder
     */
    @POST("infocash/conceder")
    suspend fun concederPontos(
        @Header("Authorization") token: String,
        @Body request: ConcederPontosRequest
    ): Response<ConcederPontosResponse>
}
