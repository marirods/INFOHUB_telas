package com.example.infohub_telas.network

import com.example.infohub_telas.network.models.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Service para autenticação
 */
interface AuthService {

    /**
     * Fazer login
     * POST /login
     */
    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    /**
     * Cadastrar usuário
     * POST /usuarios/cadastro
     */
    @POST("usuarios/cadastro")
    suspend fun cadastro(@Body request: CadastroRequest): Response<UsuarioResponse>

    /**
     * Solicitar recuperação de senha
     * POST /recuperar-senha
     */
    @POST("recuperar-senha")
    suspend fun recuperarSenha(@Body request: RecuperacaoSenhaRequest): Response<ApiResponse<Any>>

    /**
     * Validar código de recuperação
     * POST /validar-codigo
     */
    @POST("validar-codigo")
    suspend fun validarCodigo(@Body request: ValidarCodigoRequest): Response<ApiResponse<Any>>

    /**
     * Redefinir senha
     * POST /redefinir-senha
     */
    @POST("redefinir-senha")
    suspend fun redefinirSenha(@Body request: RedefinirSenhaRequest): Response<ApiResponse<Any>>
}

/**
 * Service para usuários
 */
interface UserService {

    /**
     * Listar todos os usuários
     * GET /usuarios
     */
    @GET("usuarios")
    suspend fun getUsuarios(): Response<UsuariosListResponse>

    /**
     * Buscar usuário por ID
     * GET /usuario/{id}
     */
    @GET("usuario/{id}")
    suspend fun getUsuario(@Path("id") id: Int): Response<UsuarioResponse>

    /**
     * Atualizar usuário
     * PUT /usuario/{id}
     */
    @PUT("usuario/{id}")
    suspend fun updateUsuario(
        @Path("id") id: Int,
        @Body usuario: Usuario
    ): Response<UsuarioResponse>

    /**
     * Deletar usuário
     * DELETE /usuario/{id}
     */
    @DELETE("usuario/{id}")
    suspend fun deleteUsuario(@Path("id") id: Int): Response<ApiResponse<Any>>
}
