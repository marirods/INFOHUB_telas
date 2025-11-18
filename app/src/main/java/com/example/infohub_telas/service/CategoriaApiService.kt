package com.example.infohub_telas.service

import com.example.infohub_telas.model.Categoria
import retrofit2.Call
import retrofit2.http.*

interface CategoriaApiService {

    @GET("categorias")
    fun listarCategorias(): Call<List<Categoria>>

    @GET("categorias-produtos")
    fun listarCategoriasComProdutos(): Call<List<Categoria>>

    @GET("categoria-produtos/{id}")
    fun buscarCategoriaPorId(
        @Path("id") id: Int
    ): Call<Categoria>
}

