package com.example.infohub_telas.service

import com.example.infohub_telas.model.AvaliacoesResponse
import com.example.infohub_telas.model.CriarAvaliacaoRequest
import com.example.infohub_telas.model.CriarAvaliacaoResponse
import retrofit2.Call
import retrofit2.http.*

interface AvaliacaoApiService {

    @POST("avaliacoes")
    @Headers("Content-Type: application/json")
    fun criarAvaliacao(
        @Header("Authorization") token: String,
        @Body request: CriarAvaliacaoRequest
    ): Call<CriarAvaliacaoResponse>

    @GET("avaliacoes/produto/{id_produto}")
    fun listarAvaliacoesProduto(
        @Path("id_produto") idProduto: Int
    ): Call<AvaliacoesResponse>

    @GET("avaliacoes/produtos/mais-bem-avaliados")
    fun listarProdutosMaisBemAvaliados(): Call<AvaliacoesResponse>

    @GET("avaliacoes/produtos/mais-bem-avaliados/{limit}")
    fun listarProdutosMaisBemAvaliadosComLimite(
        @Path("limit") limit: Int
    ): Call<AvaliacoesResponse>
}

