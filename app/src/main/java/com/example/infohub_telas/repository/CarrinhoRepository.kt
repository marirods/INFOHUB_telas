package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.model.*
import com.example.infohub_telas.service.CarrinhoApiService
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para gerenciamento do Carrinho de Compras
 */
class CarrinhoRepository {

    private val apiService: CarrinhoApiService by lazy {
        RetrofitFactory().getRetrofit().create(CarrinhoApiService::class.java)
    }

    companion object {
        private const val TAG = "CarrinhoRepository"
    }

    /**
     * Adicionar item ao carrinho
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
                Log.d(TAG, "🛒 Adicionando item ao carrinho - Produto: $idProduto, Qtd: $quantidade")

                val request = AdicionarCarrinhoRequest(
                    idUsuario = idUsuario,
                    idProduto = idProduto,
                    idEstabelecimento = idEstabelecimento,
                    quantidade = quantidade
                )

                val response = apiService.adicionarItem("Bearer $token", request)

                Log.d(TAG, "📈 HTTP Status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d(TAG, "✅ Item adicionado com sucesso: ${body.message}")
                    Result.success(body)
                } else {
                    val errorMsg = "Erro ao adicionar item: ${response.message()}"
                    Log.e(TAG, "❌ $errorMsg - Code: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao adicionar item: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Listar itens do carrinho
     */
    suspend fun listarCarrinho(
        token: String,
        idUsuario: Int
    ): Result<CarrinhoResponse> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "📋 Listando carrinho do usuário: $idUsuario")

                val response = apiService.listarCarrinho("Bearer $token", idUsuario)

                Log.d(TAG, "📈 HTTP Status: ${response.code()}")

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d(TAG, "✅ Carrinho carregado - ${body.carrinho.size} itens, Total: R$ ${body.valorTotal}")
                    Result.success(body)
                } else {
                    val errorMsg = "Erro ao carregar carrinho: ${response.message()}"
                    Log.e(TAG, "❌ $errorMsg - Code: ${response.code()}")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao listar carrinho: ${e.message}", e)
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

