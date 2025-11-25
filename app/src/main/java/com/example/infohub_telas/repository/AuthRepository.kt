package com.example.infohub_telas.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.infohub_telas.network.ApiConfig
import com.example.infohub_telas.network.models.*
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * Repository para operações de autenticação
 * Implementa padrão Repository para separar a lógica de acesso aos dados
 */
class AuthRepository(private val context: Context) {

    private val apiConfig = ApiConfig.getInstance(context)
    private val authService = apiConfig.authService

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)

    /**
     * Fazer login
     */
    suspend fun login(email: String, senha: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LoginRequest(email, senha)

                // Login request criado com sucesso

                val response = authService.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    // Debug: verificar se o usuario veio na resposta
                    println("=== LOGIN RESPONSE DEBUG ===")
                    println("Token: ${loginResponse.token}")
                    println("User: ${loginResponse.user}")
                    println("User ID: ${loginResponse.user.id}")
                    println("===========================")

                    // Salvar token e dados do usuário
                    saveUserData(loginResponse.token, loginResponse.user)

                    Result.success(loginResponse)
                } else {
                    val errorMsg = try {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            // Tentar parsear JSON de erro
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } else {
                            "Erro no login"
                        }
                    } catch (e: Exception) {
                        "Erro no login: ${response.code()}"
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Cadastrar usuário
     */
    suspend fun cadastro(
        nome: String,
        email: String,
        senha: String,
        dataNascimento: String? = null,
        telefone: String? = null
    ): Result<UsuarioResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = CadastroRequest(nome, email, senha, dataNascimento, telefone)
                val response = authService.cadastro(request)

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    val errorMsg = try {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } else {
                            "Erro no cadastro"
                        }
                    } catch (e: Exception) {
                        "Erro no cadastro: ${response.code()}"
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Recuperar senha
     */
    suspend fun recuperarSenha(email: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RecuperacaoSenhaRequest(email)
                val response = authService.recuperarSenha(request)

                if (response.isSuccessful) {
                    Result.success("Código enviado para o email")
                } else {
                    val errorMsg = try {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } else {
                            "Erro ao solicitar recuperação"
                        }
                    } catch (e: Exception) {
                        "Erro ao solicitar recuperação: ${response.code()}"
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Validar código de recuperação
     */
    suspend fun validarCodigo(email: String, codigo: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val request = ValidarCodigoRequest(email, codigo)
                val response = authService.validarCodigo(request)

                if (response.isSuccessful) {
                    Result.success("Código válido")
                } else {
                    val errorMsg = try {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } else {
                            "Código inválido"
                        }
                    } catch (e: Exception) {
                        "Código inválido: ${response.code()}"
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Redefinir senha
     */
    suspend fun redefinirSenha(email: String, codigo: String, novaSenha: String): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                val request = RedefinirSenhaRequest(email, codigo, novaSenha)
                val response = authService.redefinirSenha(request)

                if (response.isSuccessful) {
                    Result.success("Senha redefinida com sucesso")
                } else {
                    val errorMsg = try {
                        val errorBody = response.errorBody()?.string()
                        if (errorBody != null) {
                            val gson = Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } else {
                            "Erro ao redefinir senha"
                        }
                    } catch (e: Exception) {
                        "Erro ao redefinir senha: ${response.code()}"
                    }
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Salvar dados do usuário após login
     */
    private fun saveUserData(token: String, user: Usuario) {
        try {
            println("=== SAVING USER DATA ===")
            println("Token: ${token.take(20)}...")
            println("User ID: ${user.id}")
            println("User Nome: ${user.nome}")
            println("User Email: ${user.email}")
            println("User Perfil: ${user.perfil}")
            println("========================")

            // Salvar em InfoHub_Prefs (principal)
            with(sharedPreferences.edit()) {
                putString("jwt_token", token)
                putInt("user_id", user.id ?: 0)
                putString("user_name", user.nome)
                putString("user_email", user.email)
                putString("user_phone", user.telefone)
                putString("user_birth_date", user.dataNascimento)
                putString("user_perfil", user.perfil)
                putBoolean("is_logged_in", true)
                apply()
            }

            // Também salvar em auth para compatibilidade com telas antigas
            val authPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
            with(authPrefs.edit()) {
                putString("token", token)
                putInt("userId", user.id ?: 0)
                putString("userName", user.nome)
                putString("userEmail", user.email)
                putString("perfil", user.perfil)
                // Definir flags de tipo de usuário
                putBoolean("isAdmin", user.perfil?.lowercase() == "juridico" || user.perfil?.lowercase() == "jurídico")
                putBoolean("isEstabelecimento", user.perfil?.lowercase() == "estabelecimento")
                putBoolean("isConsumidor", user.perfil?.lowercase() == "consumidor")
                apply()
            }

            println("User data saved successfully in both preferences")
        } catch (e: Exception) {
            println("Error saving user data: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }

    /**
     * Obter usuário logado
     */
    fun getLoggedUser(): Usuario? {
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        if (!isLoggedIn) return null

        val id = sharedPreferences.getInt("user_id", 0)
        val nome = sharedPreferences.getString("user_name", "") ?: ""
        val email = sharedPreferences.getString("user_email", "") ?: ""
        val telefone = sharedPreferences.getString("user_phone", null)
        val dataNascimento = sharedPreferences.getString("user_birth_date", null)
        val perfil = sharedPreferences.getString("user_perfil", null)

        return Usuario(id, nome, email, null, dataNascimento, telefone, perfil)
    }

    /**
     * Obter token JWT
     */
    fun getToken(): String? {
        return sharedPreferences.getString("jwt_token", null)
    }

    /**
     * Obter perfil do usuário logado
     */
    fun getUserPerfil(): String? {
        return sharedPreferences.getString("user_perfil", null)
    }

    /**
     * Verificar se usuário está logado
     */
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean("is_logged_in", false) && getToken() != null
    }

    /**
     * Logout - limpar dados salvos
     */
    fun logout() {
        // Limpar InfoHub_Prefs
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }

        // Limpar auth prefs também
        val authPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        with(authPrefs.edit()) {
            clear()
            apply()
        }
    }
}
