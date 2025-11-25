package com.example.infohub_telas.repository

import android.content.Context
import com.example.infohub_telas.network.ApiConfig
import com.example.infohub_telas.network.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repository para operações de produtos
 */
class ProdutoRepository(context: Context) {

    private val apiConfig = ApiConfig.getInstance(context)
    private val produtoService = apiConfig.produtoService
    private val categoriaService = apiConfig.categoriaService

    /**
     * Listar todos os produtos
     */
    suspend fun listarProdutos(): Result<List<Produto>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = produtoService.listarProdutos()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.produtos)
                } else {
                    Result.failure(Exception("Erro ao carregar produtos"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar produto por ID
     */
    suspend fun buscarProduto(id: Int): Result<Produto> {
        return withContext(Dispatchers.IO) {
            try {
                val response = produtoService.buscarProduto(id)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.produto)
                } else {
                    Result.failure(Exception("Produto não encontrado"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Criar produto
     */
    suspend fun criarProduto(produto: ProdutoRequest): Result<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val response = produtoService.criarProduto(produto)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.id ?: 0)
                } else {
                    Result.failure(Exception("Erro ao criar produto"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Listar categorias
     */
    suspend fun listarCategorias(): Result<List<Categoria>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = categoriaService.listarCategorias()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.categorias)
                } else {
                    Result.failure(Exception("Erro ao carregar categorias"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Listar categorias com produtos
     */
    suspend fun listarCategoriasComProdutos(): Result<List<CategoriaComProdutos>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = categoriaService.listarCategoriasComProdutos()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.categorias)
                } else {
                    Result.failure(Exception("Erro ao carregar categorias com produtos"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}



/**
 * Repository para operações de promoções
 */
class PromocaoRepository(context: Context) {

    private val apiConfig = ApiConfig.getInstance(context)
    private val promocaoService = apiConfig.promocaoService

    /**
     * Listar promoções ativas
     */
    suspend fun listarPromocoes(): Result<List<Promocao>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = promocaoService.listarPromocoes()
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.promocoes)
                } else {
                    Result.failure(Exception("Erro ao carregar promoções"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Listar melhores promoções
     */
    suspend fun melhoresPromocoes(limit: Int? = null): Result<List<Promocao>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = if (limit != null) {
                    promocaoService.melhoresPromocoesComLimite(limit)
                } else {
                    promocaoService.melhoresPromocoes()
                }

                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.promocoes)
                } else {
                    Result.failure(Exception("Erro ao carregar melhores promoções"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Listar promoções de um produto
     */
    suspend fun promocoesProduto(idProduto: Int): Result<List<Promocao>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = promocaoService.promocoesProduto(idProduto)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.promocoes)
                } else {
                    Result.failure(Exception("Erro ao carregar promoções do produto"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    /**
     * Buscar promoção por ID
     */
    suspend fun buscarPromocao(idPromocao: Int): Result<Promocao> {
        return withContext(Dispatchers.IO) {
            try {
                val response = promocaoService.buscarPromocao(idPromocao)
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!.promocao)
                } else {
                    Result.failure(Exception("Promoção não encontrada"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
