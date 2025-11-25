package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para Rede Social (Posts)
 */
interface PostService {

    /**
     * Criar post
     * POST /posts
     */
    @POST("posts")
    suspend fun criarPost(@Body request: CriarPostRequest): Response<ApiResponse<Any>>

    /**
     * Feed de posts
     * GET /posts/feed
     */
    @GET("posts/feed")
    suspend fun feedPosts(): Response<PostsListResponse>

    /**
     * Feed com paginação
     * GET /posts/feed/{page}/{limit}
     */
    @GET("posts/feed/{page}/{limit}")
    suspend fun feedPostsPaginado(
        @Path("page") page: Int,
        @Path("limit") limit: Int
    ): Response<PostsListResponse>

    /**
     * Posts de um usuário
     * GET /posts/usuario/{id_usuario}
     */
    @GET("posts/usuario/{id_usuario}")
    suspend fun postsUsuario(@Path("id_usuario") idUsuario: Int): Response<PostsListResponse>

    /**
     * Buscar post por ID
     * GET /post/{id_post}
     */
    @GET("post/{id_post}")
    suspend fun buscarPost(@Path("id_post") idPost: Int): Response<PostDetailResponse>

    /**
     * Atualizar post
     * PUT /post/{id_post}
     */
    @PUT("post/{id_post}")
    suspend fun atualizarPost(
        @Path("id_post") idPost: Int,
        @Body request: CriarPostRequest
    ): Response<ApiResponse<Any>>

    /**
     * Deletar post
     * DELETE /post/{id_post}
     */
    @DELETE("post/{id_post}")
    suspend fun deletarPost(@Path("id_post") idPost: Int): Response<ApiResponse<Any>>

    /**
     * Curtir/Descurtir post
     * POST /post/{id_post}/curtir
     */
    @POST("post/{id_post}/curtir")
    suspend fun curtirPost(
        @Path("id_post") idPost: Int,
        @Body request: CurtirPostRequest
    ): Response<ApiResponse<Any>>

    /**
     * Comentar post
     * POST /post/{id_post}/comentario
     */
    @POST("post/{id_post}/comentario")
    suspend fun comentarPost(
        @Path("id_post") idPost: Int,
        @Body request: ComentarPostRequest
    ): Response<ApiResponse<Any>>

    /**
     * Listar comentários
     * GET /post/{id_post}/comentarios
     */
    @GET("post/{id_post}/comentarios")
    suspend fun listarComentarios(@Path("id_post") idPost: Int): Response<ComentariosListResponse>
}

/**
 * Service para Avaliações
 */
interface AvaliacaoService {

    /**
     * Criar avaliação
     * POST /avaliacoes
     */
    @POST("avaliacoes")
    suspend fun criarAvaliacao(@Body request: CriarAvaliacaoRequest): Response<ApiResponse<Any>>

    /**
     * Listar avaliações de produto
     * GET /avaliacoes/produto/{id_produto}
     */
    @GET("avaliacoes/produto/{id_produto}")
    suspend fun avaliacoesProduto(@Path("id_produto") idProduto: Int): Response<AvaliacoesListResponse>

    /**
     * Produtos mais bem avaliados
     * GET /avaliacoes/produtos/mais-bem-avaliados
     */
    @GET("avaliacoes/produtos/mais-bem-avaliados")
    suspend fun produtosMaisBemAvaliados(): Response<ProdutosMaisAvaliadosResponse>

    /**
     * Produtos mais bem avaliados com limite
     * GET /avaliacoes/produtos/mais-bem-avaliados/{limit}
     */
    @GET("avaliacoes/produtos/mais-bem-avaliados/{limit}")
    suspend fun produtosMaisBemAvaliadosComLimite(@Path("limit") limit: Int): Response<ProdutosMaisAvaliadosResponse>
}

/**
 * Service para Favoritos
 */
interface FavoritoService {

    /**
     * Adicionar aos favoritos
     * POST /favoritos
     */
    @POST("favoritos")
    suspend fun adicionarFavorito(@Body request: AdicionarFavoritoRequest): Response<ApiResponse<Any>>

    /**
     * Listar favoritos do usuário
     * GET /favoritos/usuario/{id_usuario}
     */
    @GET("favoritos/usuario/{id_usuario}")
    suspend fun favoritosUsuario(@Path("id_usuario") idUsuario: Int): Response<FavoritosListResponse>

    /**
     * Remover dos favoritos
     * DELETE /favorito/{id}
     */
    @DELETE("favorito/{id}")
    suspend fun removerFavorito(@Path("id") id: Int): Response<ApiResponse<Any>>
}

/**
 * Service para Notificações
 */
interface NotificacaoService {

    /**
     * Listar notificações do usuário
     * GET /notificacoes/usuario/{id_usuario}
     */
    @GET("notificacoes/usuario/{id_usuario}")
    suspend fun notificacoesUsuario(@Path("id_usuario") idUsuario: Int): Response<NotificacoesListResponse>

    /**
     * Marcar notificação como lida
     * PUT /notificacao/{id}/marcar-lida
     */
    @PUT("notificacao/{id}/marcar-lida")
    suspend fun marcarComoLida(@Path("id") id: Int): Response<ApiResponse<Any>>
}
