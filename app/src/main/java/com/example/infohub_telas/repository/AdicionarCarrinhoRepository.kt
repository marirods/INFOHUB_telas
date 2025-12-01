package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.model.AdicionarItemCarrinhoRequest
import com.example.infohub_telas.model.ApiCarrinhoResponse
import com.example.infohub_telas.model.ItemCarrinhoApi
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para adicionar item ao carrinho
 * IMPLEMENTAÇÃO CONFORME DOCUMENTAÇÃO OFICIAL DA API
 */
class AdicionarCarrinhoRepository {

    private val apiService = RetrofitFactory().getCarrinhoApiServiceV2()

    companion object {
        private const val TAG = "AdicionarCarrinhoRepo"
    }

    /**
     * Adicionar item ao carrinho
     *
     * Conforme documentação:
     * - Endpoint: POST /carrinho
     * - Header: Authorization: Bearer {token}
     * - Header: Content-Type: application/json
     * - Body: { id_usuario, id_produto, quantidade }
     * - Response 201: Sucesso
     * - Response 400: Erro de validação
     * - Response 401: Token inválido
     * - Response 404: Usuário/Produto não encontrado
     *
     * @param token Token de autenticação (SEM "Bearer")
     * @param idUsuario ID do usuário
     * @param idProduto ID do produto
     * @param quantidade Quantidade (default 1)
     * @return Result com ApiCarrinhoResponse ou erro
     */
    suspend fun adicionarItem(
        token: String,
        idUsuario: Int,
        idProduto: Int,
        quantidade: Int = 1
    ): Result<ApiCarrinhoResponse<ItemCarrinhoApi>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "═══════════════════════════════════════════")
                Log.d(TAG, "🛒 ADICIONAR ITEM AO CARRINHO")
                Log.d(TAG, "═══════════════════════════════════════════")
                Log.d(TAG, "📋 Parâmetros:")
                Log.d(TAG, "   - ID Usuário: $idUsuario (Tipo: ${idUsuario.javaClass.simpleName})")
                Log.d(TAG, "   - ID Produto: $idProduto")
                Log.d(TAG, "   - Quantidade: $quantidade")
                Log.d(TAG, "   - Token: Bearer ${token.take(20)}...")
                Log.d(TAG, "   ")
                Log.d(TAG, "⚠️ VERIFICAÇÃO CRÍTICA:")
                Log.d(TAG, "   - idUsuario é zero? ${idUsuario == 0}")
                Log.d(TAG, "   - idUsuario é negativo? ${idUsuario < 0}")
                Log.d(TAG, "   - idUsuario é válido? ${idUsuario > 0}")

                // Criar request conforme documentação
                val request = AdicionarItemCarrinhoRequest(
                    id_usuario = idUsuario,
                    id_produto = idProduto,
                    quantidade = quantidade
                )

                Log.d(TAG, "📤 Request Body:")
                Log.d(TAG, "   {")
                Log.d(TAG, "     \"id_usuario\": $idUsuario,")
                Log.d(TAG, "     \"id_produto\": $idProduto,")
                Log.d(TAG, "     \"quantidade\": $quantidade")
                Log.d(TAG, "   }")

                // Fazer chamada conforme documentação
                val response = apiService.adicionarItem(
                    token = "Bearer $token",
                    contentType = "application/json",
                    item = request
                )

                val statusCode = response.code()
                Log.d(TAG, "📡 Response Status: $statusCode")

                when (statusCode) {
                    201 -> {
                        // Sucesso conforme documentação
                        val body = response.body()
                        if (body != null && body.status) {
                            Log.d(TAG, "✅ SUCESSO!")
                            Log.d(TAG, "   Message: ${body.message}")
                            Log.d(TAG, "   Item: ${body.data?.nome_produto}")
                            Log.d(TAG, "═══════════════════════════════════════════")
                            Result.success(body)
                        } else {
                            Log.e(TAG, "❌ Body nulo ou status false")
                            Log.e(TAG, "═══════════════════════════════════════════")
                            Result.failure(Exception("Resposta inválida da API"))
                        }
                    }
                    400 -> {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "❌ Erro 400 - Bad Request")
                        Log.e(TAG, "   Error Body: $errorBody")
                        Log.e(TAG, "═══════════════════════════════════════════")
                        Result.failure(Exception("Dados inválidos: $errorBody"))
                    }
                    401 -> {
                        Log.e(TAG, "❌ Erro 401 - Unauthorized")
                        Log.e(TAG, "   Token inválido ou expirado")
                        Log.e(TAG, "═══════════════════════════════════════════")
                        Result.failure(Exception("Token inválido. Faça login novamente."))
                    }
                    404 -> {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "❌ Erro 404 - Not Found")
                        Log.e(TAG, "   Error Body: $errorBody")
                        Log.e(TAG, "═══════════════════════════════════════════")
                        Result.failure(Exception("Usuário ou produto não encontrado"))
                    }
                    else -> {
                        val errorBody = response.errorBody()?.string()
                        Log.e(TAG, "❌ Erro $statusCode")
                        Log.e(TAG, "   Error Body: $errorBody")
                        Log.e(TAG, "═══════════════════════════════════════════")
                        Result.failure(Exception("Erro $statusCode: ${response.message()}"))
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 EXCEÇÃO ao adicionar item!")
                Log.e(TAG, "   Tipo: ${e.javaClass.simpleName}")
                Log.e(TAG, "   Mensagem: ${e.message}")
                Log.e(TAG, "   Stack trace:", e)
                Log.e(TAG, "═══════════════════════════════════════════")
                Result.failure(e)
            }
        }
    }
}

