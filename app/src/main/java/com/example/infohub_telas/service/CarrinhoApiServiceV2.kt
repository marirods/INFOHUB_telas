package com.example.infohub_telas.service

import com.example.infohub_telas.model.AdicionarItemCarrinhoRequest
import com.example.infohub_telas.model.ApiCarrinhoResponse
import com.example.infohub_telas.model.ItemCarrinhoApi
import retrofit2.Response
import retrofit2.http.*

/**
 * Service da API de Carrinho - CONFORME DOCUMENTAÇÃO OFICIAL
 *
 * Base URL configurada no RetrofitFactory: http://10.0.2.2:8080/v1/infohub/
 *
 * Endpoints implementados conforme documentação:
 * - POST /carrinho - Adicionar item
 */
interface CarrinhoApiServiceV2 {

    /**
     * Adicionar item ao carrinho
     *
     * Endpoint: POST /carrinho
     *
     * Headers obrigatórios:
     * - Authorization: Bearer {token}
     * - Content-Type: application/json
     *
     * Body:
     * {
     *   "id_usuario": 1,
     *   "id_produto": 5,
     *   "quantidade": 1
     * }
     *
     * Response esperado (201):
     * {
     *   "status": true,
     *   "status_code": 201,
     *   "message": "Item adicionado ao carrinho com sucesso.",
     *   "data": { ...item... }
     * }
     *
     * @param token Bearer token (formato: "Bearer seu_token_aqui")
     * @param contentType application/json
     * @param item Dados do item a adicionar
     * @return Response com ApiCarrinhoResponse contendo ItemCarrinhoApi
     */
    @POST("carrinho")
    suspend fun adicionarItem(
        @Header("Authorization") token: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body item: AdicionarItemCarrinhoRequest
    ): Response<ApiCarrinhoResponse<ItemCarrinhoApi>>
}

