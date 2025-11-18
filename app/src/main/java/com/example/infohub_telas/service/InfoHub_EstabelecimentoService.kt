package com.example.infohub_telas.service

import com.example.infohub_telas.model.Estabelecimento
import retrofit2.Call
import retrofit2.http.*

interface InfoHub_EstabelecimentoService {

    @POST("estabelecimento")
    @Headers("Content-Type: application/json")
    fun cadastrarEstabelecimento(
        @Header("Authorization") token: String,
        @Body estabelecimento: Estabelecimento
    ): Call<Estabelecimento>

    @GET("estabelecimentos")
    fun listarEstabelecimentos(): Call<List<Estabelecimento>>

    @GET("estabelecimento/{id}")
    fun buscarEstabelecimentoPorId(
        @Path("id") id: Int
    ): Call<Estabelecimento>

    @PUT("estabelecimento/{id}")
    @Headers("Content-Type: application/json")
    fun atualizarEstabelecimento(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body estabelecimento: Estabelecimento
    ): Call<Estabelecimento>

    @DELETE("estabelecimento/{id}")
    fun deletarEstabelecimento(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Estabelecimento>
}
