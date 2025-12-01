package com.example.infohub_telas.service

import com.example.infohub_telas.model.ProdutoApiResponse
import retrofit2.Response
import retrofit2.http.*

/**
 * Interface da API de Produtos
 * Usando coroutines (suspend functions) para chamadas assíncronas
 * Corresponde exatamente à documentação da API
 */
interface ProdutoApiService {

    /**
     * Listar todos os produtos
     * GET /produtos
     * @return Response com ApiResponse contendo lista de produtos
     */
    @GET("produtos")
    suspend fun listarProdutos(): Response<ProdutoApiResponse>

    /**
     * Buscar produto por ID
     * GET /produto/{id}
     * @param id ID do produto
     * @return Response com ApiResponse contendo o produto
     */
    @GET("produto/{id}")
    suspend fun buscarProdutoPorId(
        @Path("id") id: Int
    ): Response<ProdutoApiResponse>
}

