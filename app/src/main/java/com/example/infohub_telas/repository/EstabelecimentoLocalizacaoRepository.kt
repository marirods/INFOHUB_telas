package com.example.infohub_telas.repository

import android.util.Log
import com.example.infohub_telas.model.EstabelecimentoComEndereco
import com.example.infohub_telas.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para buscar estabelecimentos registrados no sistema
 * Conforme documentação da API de Estabelecimentos
 */
class EstabelecimentoLocalizacaoRepository {

    private val apiService by lazy {
        RetrofitFactory().getEstabelecimentoApiService()
    }

    companion object {
        private const val TAG = "EstabelecimentoRepo"
    }

    /**
     * Lista todos os estabelecimentos registrados no sistema
     * GET /estabelecimentos
     */
    suspend fun listarEstabelecimentos(): Result<List<EstabelecimentoComEndereco>> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "═══════════════════════════════════════")
                Log.d(TAG, "📍 BUSCANDO ESTABELECIMENTOS REGISTRADOS")
                Log.d(TAG, "═══════════════════════════════════════")
                Log.d(TAG, "🌐 URL: GET /estabelecimentos")

                val response = apiService.listarEstabelecimentos()

                Log.d(TAG, "📡 Response Code: ${response.code()}")

                if (response.isSuccessful) {
                    val body = response.body()

                    Log.d(TAG, "📦 Response body:")
                    Log.d(TAG, "   status: ${body?.status}")
                    Log.d(TAG, "   estabelecimentos: ${body?.estabelecimentos?.size}")
                    Log.d(TAG, "   data: ${body?.data?.size}")

                    if (body != null && body.status) {
                        // A API retorna em 'data' como array
                        val estabelecimentos = body.data
                            ?: body.estabelecimentos
                            ?: emptyList()

                        Log.d(TAG, "✅ ${estabelecimentos.size} estabelecimento(s) encontrado(s)")

                        estabelecimentos.forEachIndexed { index, est ->
                            Log.d(TAG, "  [$index] ${est.nome}")
                            Log.d(TAG, "      CNPJ: ${est.cnpj}")
                            Log.d(TAG, "      Telefone: ${est.telefone}")
                            Log.d(TAG, "      Cidade: ${est.cidade ?: "Não informada"}")
                            Log.d(TAG, "      Estado: ${est.estado ?: "Não informado"}")
                        }

                        Result.success(estabelecimentos)
                    } else {
                        val message = body?.message ?: "Resposta vazia da API"
                        Log.e(TAG, "❌ Erro na resposta: $message")
                        Result.failure(Exception(message))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val error = "Erro HTTP ${response.code()}: ${response.message()}"
                    Log.e(TAG, "❌ $error")
                    Log.e(TAG, "   Error body: $errorBody")
                    Result.failure(Exception(error))
                }
            } catch (e: java.net.UnknownHostException) {
                Log.e(TAG, "💥 Erro de conexão: Não foi possível conectar ao servidor")
                Log.e(TAG, "   Verifique se o servidor está rodando e a URL está correta")
                Result.failure(Exception("Erro de conexão: Servidor não encontrado"))
            } catch (e: java.net.SocketTimeoutException) {
                Log.e(TAG, "💥 Timeout: O servidor demorou muito para responder")
                Result.failure(Exception("Timeout: Servidor demorou para responder"))
            } catch (e: com.google.gson.JsonSyntaxException) {
                Log.e(TAG, "💥 Erro ao fazer parse do JSON da resposta")
                Log.e(TAG, "   Mensagem: ${e.message}")
                Result.failure(Exception("Erro ao processar resposta do servidor"))
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção inesperada ao buscar estabelecimentos")
                Log.e(TAG, "   Tipo: ${e.javaClass.simpleName}")
                Log.e(TAG, "   Mensagem: ${e.message}")
                e.printStackTrace()
                Result.failure(e)
            } finally {
                Log.d(TAG, "═══════════════════════════════════════")
            }
        }
    }

    /**
     * Busca estabelecimento por ID
     * GET /estabelecimento/{id}
     */
    suspend fun buscarPorId(id: Int): Result<EstabelecimentoComEndereco> {
        return withContext(Dispatchers.IO) {
            try {
                Log.d(TAG, "🔍 Buscando estabelecimento ID: $id")

                val response = apiService.buscarEstabelecimentoPorId(id)

                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null && body.status && body.estabelecimento != null) {
                        Log.d(TAG, "✅ Estabelecimento encontrado: ${body.estabelecimento.nome}")
                        Result.success(body.estabelecimento)
                    } else {
                        val message = body?.message ?: "Estabelecimento não encontrado"
                        Log.e(TAG, "❌ $message")
                        Result.failure(Exception(message))
                    }
                } else {
                    val error = "Erro HTTP ${response.code()}"
                    Log.e(TAG, "❌ $error")
                    Result.failure(Exception(error))
                }
            } catch (e: Exception) {
                Log.e(TAG, "💥 Exceção ao buscar estabelecimento", e)
                Result.failure(e)
            }
        }
    }
}

