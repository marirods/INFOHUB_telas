package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// ===============================
// MODELS DE AUTENTICAÇÃO
// ===============================

/**
 * Usuário
 */
data class Usuario(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String? = null,
    @SerializedName("data_nascimento")
    val dataNascimento: String? = null,
    @SerializedName("telefone")
    val telefone: String? = null,
    @SerializedName("perfil")
    val perfil: String? = null
)

// ===============================
// REQUESTS DE AUTENTICAÇÃO
// ===============================

/**
 * Request de login
 */
data class LoginRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String
)

/**
 * Request de cadastro
 */
data class CadastroRequest(
    @SerializedName("nome")
    val nome: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("senha")
    val senha: String,
    @SerializedName("data_nascimento")
    val dataNascimento: String? = null,
    @SerializedName("telefone")
    val telefone: String? = null
)

/**
 * Request de recuperação de senha
 */
data class RecuperacaoSenhaRequest(
    @SerializedName("email")
    val email: String
)

/**
 * Request para validar código
 */
data class ValidarCodigoRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("codigo")
    val codigo: String
)

/**
 * Request para redefinir senha
 */
data class RedefinirSenhaRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("codigo")
    val codigo: String,
    @SerializedName("nova_senha")
    val novaSenha: String
)

// ===============================
// RESPONSES DE AUTENTICAÇÃO
// ===============================

/**
 * Response do login
 */
data class LoginResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("token")
    val token: String,
    @SerializedName("usuario")
    val user: Usuario
)

/**
 * Response de usuário
 */
data class UsuarioResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Usuario? = null,
    @SerializedName("id")
    val id: Int? = null
)

/**
 * Response de lista de usuários
 */
data class UsuariosListResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<Usuario>
)


