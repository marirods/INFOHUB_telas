package com.example.infohub_telas.service

import com.example.infohub_telas.model.Produto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface InfoHub_ProdutoService {

    @POST("/produtos")
    fun cadastrarProduto(
        @Header("Authorization") token: String,
        @Body produto: Produto
    ): Call<Produto>
}
