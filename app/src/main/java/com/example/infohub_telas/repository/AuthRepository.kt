package com.example.infohub_telas.repository

import com.example.infohub_telas.model.AtualizarSenhaRequest
import com.example.infohub_telas.model.AtualizarSenhaResponse
import com.example.infohub_telas.model.LoginResponse
import com.example.infohub_telas.model.LoginUsuario
import com.example.infohub_telas.model.Usuario
import com.example.infohub_telas.model.ValidarCodigoRequest
import com.example.infohub_telas.model.ValidarCodigoResponse
import com.example.infohub_telas.model.RecuperarSenhaRequest
import com.example.infohub_telas.model.RecuperarSenhaResponse
import com.example.infohub_telas.service.InfoHub_User
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

/**
 * Repository para autenticação de usuários
 */
class AuthRepository {

    private val apiService: InfoHub_User = RetrofitFactory().getInfoHub_UserService()

    /**
     * Fazer login
     */
    suspend fun login(email: String, senha: String): Result<LoginResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val loginRequest = LoginUsuario(email = email, senha = senha)
                val response = apiService.logarUsuario(loginRequest).awaitResponse()

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro no login: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Cadastrar usuário
     */
    suspend fun cadastrarUsuario(usuario: Usuario): Result<Usuario> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.cadastrarUsuario(usuario).awaitResponse()

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro no cadastro: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Recuperar senha (temporariamente desativado até padronização completa)
     */
//    suspend fun recuperarSenha(email: String): Result<RecuperarSenhaResponse> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val request = RecuperarSenhaRequest(email = email)
//                val response = apiService.recuperarSenha(request).awaitResponse()
//
//                if (response.isSuccessful && response.body() != null) {
//                    Result.success(response.body()!!)
//                } else {
//                    Result.failure(Exception("Erro ao recuperar senha: ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//    }

    /**
     * Validar código (temporariamente desativado)
     */
//    suspend fun validarCodigo(email: String, codigo: String): Result<ValidarCodigoResponse> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val request = ValidarCodigoRequest(email = email, codigo = codigo)
//                val response = apiService.validarCodigo(request).awaitResponse()
//
//                if (response.isSuccessful && response.body() != null) {
//                    Result.success(response.body()!!)
//                } else {
//                    Result.failure(Exception("Erro ao validar código: ${response.message()}"))
//                }
//            } catch (e: Exception) {
//                Result.failure(e)
//            }
//        }
//    }

    /**
     * Atualizar senha
     */
    suspend fun atualizarSenha(request: AtualizarSenhaRequest): Result<AtualizarSenhaResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.atualizarSenha(request).awaitResponse()

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Erro ao atualizar senha: ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
