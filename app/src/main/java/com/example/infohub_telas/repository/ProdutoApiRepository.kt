package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.model.toInternalModel
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para operações de produtos usando a API correta
 * Implementa o padrão da documentação com suspend functions
 */
class ProdutoApiRepository {

    private val apiService = RetrofitFactory().getProdutoApiService()

    /**
     * Listar todos os produtos
     * @return Result com lista de produtos ou erro
     */
    suspend fun listarProdutos(): Result<List<com.example.infohub_telas.model.Produto>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ProdutoApiRepository", "🔍 Iniciando requisição para listar produtos...")

                val response = apiService.listarProdutos()

                Log.d("ProdutoApiRepository", "📡 Response Code: ${response.code()}")
                Log.d("ProdutoApiRepository", "📡 Response Message: ${response.message()}")

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("ProdutoApiRepository", "✅ Response Body: $body")

                    if (body != null && body.status) {
                        val produtos = body.produtos ?: emptyList()
                        Log.d("ProdutoApiRepository", "✅ ${produtos.size} produtos recebidos da API")

                        // Converter para modelo interno
                        val produtosInternos = produtos.map { it.toInternalModel() }

                        Result.success(produtosInternos)
                    } else {
                        val errorMsg = body?.message ?: "Resposta vazia da API"
                        Log.e("ProdutoApiRepository", "❌ Erro: $errorMsg")
                        Result.failure(Exception(errorMsg))
                    }
                } else {
                    val errorMsg = "Erro HTTP: ${response.code()} - ${response.message()}"
                    Log.e("ProdutoApiRepository", "❌ $errorMsg")

                    // Tentar ler o corpo do erro
                    val errorBody = response.errorBody()?.string()
                    Log.e("ProdutoApiRepository", "❌ Error Body: $errorBody")

                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e("ProdutoApiRepository", "💥 Exceção ao listar produtos: ${e.message}", e)
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar produto por ID
     * @param id ID do produto
     * @return Result com produto ou erro
     */
    suspend fun buscarProdutoPorId(id: Int): Result<com.example.infohub_telas.model.Produto> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("ProdutoApiRepository", "🔍 Buscando produto ID: $id")

                val response = apiService.buscarProdutoPorId(id)

                Log.d("ProdutoApiRepository", "📡 Response Code: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null && body.status && body.produto != null) {
                        Log.d("ProdutoApiRepository", "✅ Produto encontrado: ${body.produto.nome}")

                        // Converter para modelo interno
                        val produtoInterno = body.produto.toInternalModel()

                        Result.success(produtoInterno)
                    } else {
                        val errorMsg = body?.message ?: "Produto não encontrado"
                        Log.e("ProdutoApiRepository", "❌ Erro: $errorMsg")
                        Result.failure(Exception(errorMsg))
                    }
                } else {
                    val errorMsg = "Erro HTTP: ${response.code()} - ${response.message()}"
                    Log.e("ProdutoApiRepository", "❌ $errorMsg")
                    Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                Log.e("ProdutoApiRepository", "💥 Exceção ao buscar produto: ${e.message}", e)
                Result.failure(e)
            }
        }
    }
}

