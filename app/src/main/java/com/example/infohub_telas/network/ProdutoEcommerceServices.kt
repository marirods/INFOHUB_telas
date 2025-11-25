package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para Produtos
 */
interface ProdutoService {

    /**
     * Criar novo produto
     * POST /produtos
     */
    @POST("produtos")
    suspend fun criarProduto(@Body request: ProdutoRequest): Response<ProdutoResponse>

    /**
     * Listar todos os produtos
     * GET /produtos
     */
    @GET("produtos")
    suspend fun listarProdutos(): Response<ProdutosListResponse>

    /**
     * Buscar produto por ID
     * GET /produto/{id}
     */
    @GET("produto/{id}")
    suspend fun buscarProduto(@Path("id") id: Int): Response<ProdutoDetailResponse>

    /**
     * Atualizar produto
     * PUT /produto/{id}
     */
    @PUT("produto/{id}")
    suspend fun atualizarProduto(
        @Path("id") id: Int,
        @Body request: ProdutoRequest
    ): Response<ProdutoResponse>

    /**
     * Deletar produto
     * DELETE /produto/{id}
     */
    @DELETE("produto/{id}")
    suspend fun deletarProduto(@Path("id") id: Int): Response<ProdutoResponse>
}

/**
 * Service para Carrinho de Compras
 */
interface CarrinhoService {

    /**
     * Adicionar item ao carrinho
     * POST /carrinho
     */
    @POST("carrinho")
    suspend fun adicionarItem(@Body request: AdicionarCarrinhoRequest): Response<ApiResponse<Any>>

    /**
     * Listar itens do carrinho
     * GET /carrinho
     */
    @GET("carrinho")
    suspend fun listarItens(@Query("id_usuario") idUsuario: Int): Response<CarrinhoResponse>

    /**
     * Atualizar quantidade no carrinho
     * PUT /carrinho/{id}
     */
    @PUT("carrinho/{id}")
    suspend fun atualizarQuantidade(
        @Path("id") id: Int,
        @Body request: AtualizarQuantidadeRequest
    ): Response<ApiResponse<Any>>

    /**
     * Remover item do carrinho
     * DELETE /carrinho/{id}
     */
    @DELETE("carrinho/{id}")
    suspend fun removerItem(@Path("id") id: Int): Response<ApiResponse<Any>>

    /**
     * Limpar carrinho
     * DELETE /carrinho/limpar/{id_usuario}
     */
    @DELETE("carrinho/limpar/{id_usuario}")
    suspend fun limparCarrinho(@Path("id_usuario") idUsuario: Int): Response<ApiResponse<Any>>
}

/**
 * Service para Pedidos
 */
interface PedidoService {

    /**
     * Finalizar compra (criar pedido)
     * POST /pedido
     */
    @POST("pedido")
    suspend fun criarPedido(@Body request: CriarPedidoRequest): Response<PedidoResponse>

    /**
     * Buscar pedido por ID
     * GET /pedido/{id}
     */
    @GET("pedido/{id}")
    suspend fun buscarPedido(@Path("id") id: Int): Response<PedidoResponse>

    /**
     * Listar pedidos do usuário
     * GET /pedidos/usuario/{id_usuario}
     */
    @GET("pedidos/usuario/{id_usuario}")
    suspend fun listarPedidosUsuario(@Path("id_usuario") idUsuario: Int): Response<PedidosListResponse>
}

/**
 * Service para Promoções
 */
interface PromocaoService {

    /**
     * Criar promoção
     * POST /promocoes
     */
    @POST("promocoes")
    suspend fun criarPromocao(@Body request: CriarPromocaoRequest): Response<ApiResponse<Any>>

    /**
     * Listar promoções ativas
     * GET /promocoes
     */
    @GET("promocoes")
    suspend fun listarPromocoes(): Response<PromocoesListResponse>

    /**
     * Listar melhores promoções
     * GET /promocoes/melhores
     */
    @GET("promocoes/melhores")
    suspend fun melhoresPromocoes(): Response<PromocoesListResponse>

    /**
     * Listar melhores promoções com limite
     * GET /promocoes/melhores/{limit}
     */
    @GET("promocoes/melhores/{limit}")
    suspend fun melhoresPromocoesComLimite(@Path("limit") limit: Int): Response<PromocoesListResponse>

    /**
     * Listar promoções de um produto
     * GET /promocoes/produto/{id_produto}
     */
    @GET("promocoes/produto/{id_produto}")
    suspend fun promocoesProduto(@Path("id_produto") idProduto: Int): Response<PromocoesListResponse>

    /**
     * Buscar promoção por ID
     * GET /promocao/{id_promocao}
     */
    @GET("promocao/{id_promocao}")
    suspend fun buscarPromocao(@Path("id_promocao") idPromocao: Int): Response<PromocaoDetailResponse>

    /**
     * Atualizar promoção
     * PUT /promocao/{id_promocao}
     */
    @PUT("promocao/{id_promocao}")
    suspend fun atualizarPromocao(
        @Path("id_promocao") idPromocao: Int,
        @Body request: CriarPromocaoRequest
    ): Response<ApiResponse<Any>>

    /**
     * Deletar promoção
     * DELETE /promocao/{id_promocao}
     */
    @DELETE("promocao/{id_promocao}")
    suspend fun deletarPromocao(@Path("id_promocao") idPromocao: Int): Response<ApiResponse<Any>>
}
