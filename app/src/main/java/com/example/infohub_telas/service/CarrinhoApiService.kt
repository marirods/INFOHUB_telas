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
 *
 * ⚠️ ATENÇÃO: Implementação conforme documentação oficial da API
 * - POST /carrinho: Status 201 para criação
 * - GET /carrinho: Status 200 com dados do carrinho
 * - PUT /carrinho/{id}: Status 200 para atualização
 * - DELETE /carrinho/{id}: Status 200 para remoção
 * - DELETE /carrinho/limpar/{id_usuario}: Status 200 para limpeza
 */
interface CarrinhoApiService {

    /**
     * Adicionar item ao carrinho
     * POST /carrinho
     *
     * @param token Bearer token para autenticação
     * @param request Dados do item a ser adicionado
     * @return Status 201 quando item for adicionado com sucesso
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
     *
     * @param token Bearer token para autenticação
     * @param idUsuario ID do usuário
     * @return Lista de itens do carrinho com valor total
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
     *
     * @param token Bearer token para autenticação
     * @param idCarrinho ID do item no carrinho
     * @param request Nova quantidade (mínimo 1)
     * @return Status 200 quando quantidade for atualizada
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
     * Remover item específico do carrinho
     * DELETE /carrinho/{id}
     *
     * @param token Bearer token para autenticação
     * @param idCarrinho ID do item no carrinho
     * @return Status 200 quando item for removido
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
     * Limpar carrinho completamente
     * DELETE /carrinho/limpar/{id_usuario}
     *
     * @param token Bearer token para autenticação
     * @param idUsuario ID do usuário
     * @return Status 200 quando carrinho for limpo
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

