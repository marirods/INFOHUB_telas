package com.example.infohub_telas.service

import com.example.infohub_telas.model.EstabelecimentoApiResponse
import com.example.infohub_telas.model.EstabelecimentoComEndereco
import retrofit2.Response
import retrofit2.http.*

/**
 * Service da API de Estabelecimentos
 * Conforme documentação oficial
 */
interface EstabelecimentoApiService {

    /**
     * Listar todos os estabelecimentos
     * GET /estabelecimentos
     */
    @GET("estabelecimentos")
    suspend fun listarEstabelecimentos(): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Listar todos os estabelecimentos (endpoint alternativo)
     * GET /estabelecimentos/todos
     */
    @GET("estabelecimentos/todos")
    suspend fun listarTodosEstabelecimentos(): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Buscar estabelecimento por ID
     * GET /estabelecimento/{id}
     */
    @GET("estabelecimento/{id}")
    suspend fun buscarEstabelecimentoPorId(
        @Path("id") id: Int
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Buscar estabelecimento por usuário
     * GET /estabelecimento/usuario/{id_usuario}
     */
    @GET("estabelecimento/usuario/{id_usuario}")
    suspend fun buscarEstabelecimentoPorUsuario(
        @Path("id_usuario") idUsuario: Int
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Buscar estabelecimento por CNPJ
     * GET /estabelecimento/cnpj/{cnpj}
     */
    @GET("estabelecimento/cnpj/{cnpj}")
    suspend fun buscarEstabelecimentoPorCnpj(
        @Path("cnpj") cnpj: String
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Pesquisar estabelecimentos por CEP
     * GET /estabelecimentos/pesquisar/cep/{cep}
     */
    @GET("estabelecimentos/pesquisar/cep/{cep}")
    suspend fun pesquisarPorCep(
        @Path("cep") cep: String
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Pesquisar estabelecimentos por termo (local)
     * GET /estabelecimentos/pesquisar/local/{termo}
     */
    @GET("estabelecimentos/pesquisar/local/{termo}")
    suspend fun pesquisarPorLocal(
        @Path("termo") termo: String
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>

    /**
     * Pesquisar estabelecimentos próximos
     * GET /estabelecimentos/pesquisar/proximos
     */
    @GET("estabelecimentos/pesquisar/proximos")
    suspend fun pesquisarProximos(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("raio") raio: Double = 10.0
    ): Response<EstabelecimentoApiResponse<EstabelecimentoComEndereco>>
}

