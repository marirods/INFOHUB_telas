package com.example.infohub_telas.service

import com.example.infohub_telas.model.Categoria
import com.example.infohub_telas.model.CategoriasResponse
import retrofit2.Call
import retrofit2.http.*

interface CategoriaApiService {

    @GET("categorias")
    fun listarCategorias(): Call<CategoriasResponse>

    @GET("categorias-produtos")
    fun listarCategoriasComProdutos(): Call<CategoriasResponse>

    @GET("categoria-produtos/{id}")
    fun buscarCategoriaPorId(
        @Path("id") id: Int
    ): Call<Categoria>
}

