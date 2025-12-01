package com.example.infohub_telas.service

import com.example.infohub_telas.model.Produto
import retrofit2.Call
import retrofit2.http.*

interface InfoHub_ProdutoService {

    @POST("produtos")
    fun cadastrarProduto(
        @Header("Authorization") token: String,
        @Body produto: Produto
    ): Call<Produto>

    @GET("produtos")
    fun listarProdutos(): Call<List<Produto>>

    @GET("produtos/{id}")
    fun buscarProdutoPorId(
        @Path("id") id: Int
    ): Call<Produto>

    @PUT("produtos/{id}")
    fun atualizarProduto(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body produto: Produto
    ): Call<Produto>

    @DELETE("produtos/{id}")
    fun deletarProduto(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<Produto>
}
