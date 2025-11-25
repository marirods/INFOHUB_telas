package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para Estabelecimentos
 */
interface EstabelecimentoService {

    /**
     * Criar novo estabelecimento
     * POST /estabelecimento
     */
    @POST("estabelecimento")
    suspend fun criarEstabelecimento(@Body request: EstabelecimentoRequest): Response<EstabelecimentoResponse>

    /**
     * Listar todos os estabelecimentos
     * GET /estabelecimentos
     */
    @GET("estabelecimentos")
    suspend fun listarEstabelecimentos(): Response<EstabelecimentosListResponse>

    /**
     * Buscar estabelecimento por ID
     * GET /estabelecimento/{id}
     */
    @GET("estabelecimento/{id}")
    suspend fun buscarEstabelecimento(@Path("id") id: Int): Response<EstabelecimentoDetailResponse>

    /**
     * Atualizar estabelecimento
     * PUT /estabelecimento/{id}
     */
    @PUT("estabelecimento/{id}")
    suspend fun atualizarEstabelecimento(
        @Path("id") id: Int,
        @Body request: EstabelecimentoRequest
    ): Response<EstabelecimentoResponse>

    /**
     * Deletar estabelecimento
     * DELETE /estabelecimento/{id}
     */
    @DELETE("estabelecimento/{id}")
    suspend fun deletarEstabelecimento(@Path("id") id: Int): Response<EstabelecimentoResponse>
}

/**
 * Service para Endereços de Usuário
 */
interface EnderecoService {

    /**
     * Criar novo endereço
     * POST /endereco-usuario
     */
    @POST("endereco-usuario")
    suspend fun criarEndereco(@Body request: EnderecoUsuarioRequest): Response<EnderecoUsuarioResponse>

    /**
     * Listar todos os endereços
     * GET /enderecos-usuario
     */
    @GET("enderecos-usuario")
    suspend fun listarEnderecos(): Response<EnderecosUsuarioListResponse>

    /**
     * Buscar endereços por usuário
     * GET /usuario/{id}/enderecos
     */
    @GET("usuario/{id}/enderecos")
    suspend fun buscarEnderecosPorUsuario(@Path("id") idUsuario: Int): Response<EnderecosUsuarioListResponse>

    /**
     * Buscar endereço por ID
     * GET /endereco-usuario/{id}
     */
    @GET("endereco-usuario/{id}")
    suspend fun buscarEndereco(@Path("id") id: Int): Response<EnderecoUsuarioDetailResponse>

    /**
     * Atualizar endereço
     * PUT /endereco-usuario/{id}
     */
    @PUT("endereco-usuario/{id}")
    suspend fun atualizarEndereco(
        @Path("id") id: Int,
        @Body request: EnderecoUsuarioRequest
    ): Response<EnderecoUsuarioResponse>

    /**
     * Deletar endereço
     * DELETE /endereco-usuario/{id}
     */
    @DELETE("endereco-usuario/{id}")
    suspend fun deletarEndereco(@Path("id") id: Int): Response<EnderecoUsuarioResponse>
}

/**
 * Service para Categorias
 */
interface CategoriaService {

    /**
     * Criar nova categoria
     * POST /categoria
     */
    @POST("categoria")
    suspend fun criarCategoria(@Body request: CategoriaRequest): Response<CategoriaResponse>

    /**
     * Listar todas as categorias
     * GET /categorias
     */
    @GET("categorias")
    suspend fun listarCategorias(): Response<CategoriasListResponse>

    /**
     * Listar categorias com produtos
     * GET /categorias-produtos
     */
    @GET("categorias-produtos")
    suspend fun listarCategoriasComProdutos(): Response<CategoriasComProdutosResponse>

    /**
     * Buscar categoria por ID
     * GET /categoria/{id}
     */
    @GET("categoria/{id}")
    suspend fun buscarCategoria(@Path("id") id: Int): Response<CategoriaDetailResponse>

    /**
     * Buscar categoria com produtos por ID
     * GET /categoria-produtos/{id}
     */
    @GET("categoria-produtos/{id}")
    suspend fun buscarCategoriaComProdutos(@Path("id") id: Int): Response<CategoriaComProdutosDetailResponse>

    /**
     * Atualizar categoria
     * PUT /categoria/{id}
     */
    @PUT("categoria/{id}")
    suspend fun atualizarCategoria(
        @Path("id") id: Int,
        @Body request: CategoriaRequest
    ): Response<CategoriaResponse>

    /**
     * Deletar categoria
     * DELETE /categoria/{id}
     */
    @DELETE("categoria/{id}")
    suspend fun deletarCategoria(@Path("id") id: Int): Response<CategoriaResponse>
}
