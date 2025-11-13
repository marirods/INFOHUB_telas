package com.example.infohub_telas.service

import com.example.infohub_telas.model.AdicionarCarrinhoRequest
import com.example.infohub_telas.model.AtualizarQuantidadeRequest
import com.example.infohub_telas.model.CarrinhoOperationResponse
import com.example.infohub_telas.model.CarrinhoResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Service da API de Carrinho de Compras
 * Base URL: /v1/infohub
 */
interface CarrinhoApiService {

    /**
     * Adicionar item ao carrinho
     * POST /carrinho
     */
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @POST("carrinho")
    suspend fun adicionarItem(
        @Header("Authorization") token: String,
        @Body request: AdicionarCarrinhoRequest
    ): Response<CarrinhoOperationResponse>

    /**
     * Listar itens do carrinho
     * GET /carrinho?id_usuario={id}
     */
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @GET("carrinho")
    suspend fun listarCarrinho(
        @Header("Authorization") token: String,
        @Query("id_usuario") idUsuario: Int
    ): Response<CarrinhoResponse>

    /**
     * Atualizar quantidade de um item
     * PUT /carrinho/{id}
     */
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @PUT("carrinho/{id}")
    suspend fun atualizarQuantidade(
        @Header("Authorization") token: String,
        @Path("id") idCarrinho: Int,
        @Body request: AtualizarQuantidadeRequest
    ): Response<CarrinhoOperationResponse>

    /**
     * Remover item do carrinho
     * DELETE /carrinho/{id}
     */
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @DELETE("carrinho/{id}")
    suspend fun removerItem(
        @Header("Authorization") token: String,
        @Path("id") idCarrinho: Int
    ): Response<CarrinhoOperationResponse>

    /**
     * Limpar carrinho
     * DELETE /carrinho/limpar/{id_usuario}
     */
    @Headers(
        "Content-Type: application/json",
        "Accept: application/json"
    )
    @DELETE("carrinho/limpar/{id_usuario}")
    suspend fun limparCarrinho(
        @Header("Authorization") token: String,
        @Path("id_usuario") idUsuario: Int
    ): Response<CarrinhoOperationResponse>
}

