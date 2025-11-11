package com.example.infohub_telas.service

import com.example.infohub_telas.model.SaldoInfoCashResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface do serviço InfoCash API
 */
interface InfoCashApiService {

    @GET("saldo-infocash/{id_usuario}")
    suspend fun getSaldoInfoCash(@Path("id_usuario") idUsuario: Int): Response<SaldoInfoCashResponse>

    // Outros endpoints futuros podem ser adicionados aqui
    // @POST("saldo-infocash/atualizar")
    // suspend fun atualizarSaldo(@Body request: AtualizarSaldoRequest): Response<SaldoInfoCashResponse>
}
