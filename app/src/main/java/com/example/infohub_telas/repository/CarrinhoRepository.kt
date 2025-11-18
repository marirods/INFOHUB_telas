package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.model.*
import com.example.infohub_telas.service.CarrinhoApiService
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para gerenciamento do Carrinho de Compras
 *
 * ✅ Implementação conforme documentação oficial da API:
 * - Tratamento adequado de códigos HTTP (200, 201, 400, etc.)
 * - Logs detalhados para debugging
 * - Validação de parâmetros de entrada
 * - Tratamento de erros robusto
 */
class CarrinhoRepository {

    private val apiService: CarrinhoApiService by lazy {
        RetrofitFactory().getCarrinhoApiService()
    }

    companion object {
        private const val TAG = "CarrinhoRepository"

        // Códigos de status esperados conforme documentação
        private const val HTTP_SUCCESS = 200
        private const val HTTP_CREATED = 201
        private const val HTTP_BAD_REQUEST = 400
        private const val HTTP_UNAUTHORIZED = 401
        private const val HTTP_NOT_FOUND = 404
    }

    /**
     * Adicionar item ao carrinho
     *
     * @param token Token de autenticação (sem "Bearer")
     * @param idUsuario ID do usuário (> 0)
     * @param idProduto ID do produto (> 0)
     * @param idEstabelecimento ID do estabelecimento (> 0)
     * @param quantidade Quantidade desejada (> 0)
     * @return Result com CarrinhoOperationResponse ou erro
     */
    suspend fun adicionarItem(
        token: String,
        idUsuario: Int,
        idProduto: Int,
        idEstabelecimento: Int,
        quantidade: Int
    ): Result<CarrinhoOperationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Validações de entrada
                require(token.isNotBlank()) { "Token não pode estar vazio" }
                require(idUsuario > 0) { "ID do usuário deve ser maior que zero" }
                require(idProduto > 0) { "ID do produto deve ser maior que zero" }
                require(idEstabelecimento > 0) { "ID do estabelecimento deve ser maior que zero" }
                require(quantidade > 0) { "Quantidade deve ser maior que zero" }

                Log.d(TAG, "🛒 Adicionando item ao carrinho")
                Log.d(TAG, "   📋 Parâmetros:")
                Log.d(TAG, "      - User ID: $idUsuario")
                Log.d(TAG, "      - Produto ID: $idProduto")
                Log.d(TAG, "      - Estabelecimento ID: $idEstabelecimento")
                Log.d(TAG, "      - Quantidade: $quantidade")

                val request = AdicionarCarrinhoRequest(
                    idUsuario = idUsuario,
                    idProduto = idProduto,
                    idEstabelecimento = idEstabelecimento,
                    quantidade = quantidade
                )

                Log.d(TAG, "📤 Enviando request: $request")
                // Log do JSON que será enviado
                val gson = com.google.gson.Gson()
                val jsonRequest = gson.toJson(request)
                Log.d(TAG, "📝 JSON sendo enviado: $jsonRequest")
                Log.d(TAG, "🔑 Token: Bearer ${token.take(20)}...")

                val response = apiService.adicionarItem("Bearer $token", request)
                val responseCode = response.code()

                Log.d(TAG, "📈 HTTP Status: $responseCode")
                Log.d(TAG, "📨 Response headers: ${response.headers()}")

                when (responseCode) {
                    HTTP_CREATED -> {
                        val body = response.body()
                        if (body != null) {
                            Log.d(TAG, "✅ Item adicionado com sucesso: ${body.message}")
                            Result.success(body)
                        } else {
                            Log.e(TAG, "❌ Response body nulo mesmo com status 201")
                            Result.failure(Exception("Resposta vazia da API"))
                        }
                    }
                    HTTP_BAD_REQUEST -> {
                        val errorMsg = "Dados inválidos fornecidos para adicionar item"
                        Log.e(TAG, "❌ $errorMsg - Code: $responseCode")
                        Result.failure(Exception(errorMsg))
                    }
                    HTTP_UNAUTHORIZED -> {
                        val errorMsg = "Token de autenticação inválido"
                        Log.e(TAG, "❌ $errorMsg - Code: $responseCode")
                        Result.failure(Exception(errorMsg))
                    }
                    415 -> { // Unsupported Media Type
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = "Erro de formato: Servidor não aceitou JSON"
                        Log.e(TAG, "❌ $errorMsg - Code: 415 (Unsupported Media Type)")
                        Log.e(TAG, "❌ Error body: $errorBody")
                        Log.e(TAG, "⚠️ DICA: Verifique se o Content-Type está como 'application/json'")
                        Log.e(TAG, "⚠️ Request headers enviados:")
                        response.raw().request.headers.forEach { (name, value) ->
                            Log.e(TAG, "   🔧 $name: $value")
                        }
                        Result.failure(Exception("$errorMsg - O servidor esperava JSON mas recebeu outro formato"))
                    }
                    else -> {
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = "Erro inesperado ao adicionar item: ${response.message()}"
                        Log.e(TAG, "❌ $errorMsg - Code: $responseCode")
                        Log.e(TAG, "❌ Error body: $errorBody")
                        Result.failure(Exception("$errorMsg${if (errorBody != null) " - $errorBody" else ""}"))
                    }
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "⚠️ Erro de validação: ${e.message}")
                Result.failure(e)
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção inesperada ao adicionar item: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Listar itens do carrinho
     *
     * @param token Token de autenticação (sem "Bearer")
     * @param idUsuario ID do usuário (> 0)
     * @return Result com CarrinhoResponse contendo lista de itens e valor total
     */
    suspend fun listarCarrinho(
        token: String,
        idUsuario: Int
    ): Result<CarrinhoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                // Validações de entrada
                require(token.isNotBlank()) { "Token não pode estar vazio" }
                require(idUsuario > 0) { "ID do usuário deve ser maior que zero" }

                Log.d(TAG, "📋 Listando carrinho do usuário: $idUsuario")

                val response = apiService.listarCarrinho("Bearer $token", idUsuario)
                val responseCode = response.code()

                Log.d(TAG, "📈 HTTP Status: $responseCode")

                when (responseCode) {
                    HTTP_SUCCESS -> {
                        val body = response.body()
                        if (body != null) {
                            Log.d(TAG, "✅ Carrinho carregado:")
                            Log.d(TAG, "   📦 Itens: ${body.carrinho.size}")
                            Log.d(TAG, "   💰 Total: R$ ${String.format("%.2f", body.valorTotal)}")
                            Log.d(TAG, "   📊 Status: ${if (body.status) "Sucesso" else "Com problemas"}")
                            Result.success(body)
                        } else {
                            Log.e(TAG, "❌ Response body nulo mesmo com status 200")
                            Result.failure(Exception("Resposta vazia da API"))
                        }
                    }
                    HTTP_UNAUTHORIZED -> {
                        val errorMsg = "Token de autenticação inválido"
                        Log.e(TAG, "❌ $errorMsg - Code: $responseCode")
                        Result.failure(Exception(errorMsg))
                    }
                    HTTP_NOT_FOUND -> {
                        Log.d(TAG, "📭 Carrinho vazio ou usuário não encontrado")
                        // Retorna carrinho vazio em vez de erro
                        val emptyCarrinho = CarrinhoResponse(
                            status = true,
                            carrinho = emptyList(),
                            valorTotal = 0.0
                        )
                        Result.success(emptyCarrinho)
                    }
                    else -> {
                        val errorBody = response.errorBody()?.string()
                        val errorMsg = "Erro ao carregar carrinho: ${response.message()}"
                        Log.e(TAG, "❌ $errorMsg - Code: $responseCode")
                        Log.e(TAG, "❌ Error body: $errorBody")
                        Result.failure(Exception("$errorMsg${if (errorBody != null) " - $errorBody" else ""}"))
                    }
                }
            } catch (e: IllegalArgumentException) {
                Log.e(TAG, "⚠️ Erro de validação: ${e.message}")
                Result.failure(e)
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção inesperada ao listar carrinho: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Atualizar quantidade de um item
     */
    suspend fun atualizarQuantidade(
        token: String,
        idCarrinho: Int,
        novaQuantidade: Int
    ): Result<CarrinhoOperationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "🔄 Atualizando quantidade - Item: $idCarrinho, Nova Qtd: $novaQuantidade")

                val request = AtualizarQuantidadeRequest(quantidade = novaQuantidade)
                val response = apiService.atualizarQuantidade("Bearer $token", idCarrinho, request)

                Log.d(TAG, "📈 HTTP Status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d(TAG, "✅ Quantidade atualizada: ${body.message}")
                    Result.success(body)
                } else {
                    val errorMsg = "Erro ao atualizar quantidade: ${response.message()}"
                    Log.e(TAG, "❌ $errorMsg - Code: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao atualizar quantidade: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Remover item do carrinho
     */
    suspend fun removerItem(
        token: String,
        idCarrinho: Int
    ): Result<CarrinhoOperationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "🗑️ Removendo item do carrinho: $idCarrinho")

                val response = apiService.removerItem("Bearer $token", idCarrinho)

                Log.d(TAG, "📈 HTTP Status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d(TAG, "✅ Item removido: ${body.message}")
                    Result.success(body)
                } else {
                    val errorMsg = "Erro ao remover item: ${response.message()}"
                    Log.e(TAG, "❌ $errorMsg - Code: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao remover item: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Limpar carrinho completamente
     */
    suspend fun limparCarrinho(
        token: String,
        idUsuario: Int
    ): Result<CarrinhoOperationResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "🧹 Limpando carrinho do usuário: $idUsuario")

                val response = apiService.limparCarrinho("Bearer $token", idUsuario)

                Log.d(TAG, "📈 HTTP Status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d(TAG, "✅ Carrinho limpo: ${body.message}")
                    Result.success(body)
                } else {
                    val errorMsg = "Erro ao limpar carrinho: ${response.message()}"
                    Log.e(TAG, "❌ $errorMsg - Code: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao limpar carrinho: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}

