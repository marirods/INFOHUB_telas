package com.example.infohub_telas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.repository.ProdutoRepository
import com.example.infohub_telas.network.models.*
import kotlinx.coroutines.launch

/**
 * ViewModel para gerenciar produtos (Pessoa Jurídica)
 */
class ProdutoJuridicoViewModel(application: Application) : AndroidViewModel(application) {

    private val produtoRepository = ProdutoRepository(application.applicationContext)

    // Estados da UI
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _produtos = MutableLiveData<List<Produto>>(emptyList())
    val produtos: LiveData<List<Produto>> = _produtos

    private val _categorias = MutableLiveData<List<Categoria>>(emptyList())
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _estabelecimentos = MutableLiveData<List<Estabelecimento>>(emptyList())
    val estabelecimentos: LiveData<List<Estabelecimento>> = _estabelecimentos

    private val _createResult = MutableLiveData<Result<Int>?>()
    val createResult: LiveData<Result<Int>?> = _createResult

    private val _updateResult = MutableLiveData<Result<Boolean>?>()
    val updateResult: LiveData<Result<Boolean>?> = _updateResult

    private val _deleteResult = MutableLiveData<Result<Boolean>?>()
    val deleteResult: LiveData<Result<Boolean>?> = _deleteResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        carregarDados()
    }

    /**
     * Carregar produtos, categorias e estabelecimentos
     */
    fun carregarDados() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                // Carregar dados em paralelo
                val produtosResult = produtoRepository.listarProdutos()
                val categoriasResult = produtoRepository.listarCategorias()

                // Processar produtos
                if (produtosResult.isSuccess) {
                    _produtos.value = produtosResult.getOrNull() ?: emptyList()
                } else {
                    _errorMessage.value = "Erro ao carregar produtos: ${produtosResult.exceptionOrNull()?.message}"
                    _produtos.value = emptyList()
                }

                // Processar categorias (falha silenciosa)
                if (categoriasResult.isSuccess) {
                    _categorias.value = categoriasResult.getOrNull() ?: emptyList()
                } else {
                    _categorias.value = emptyList()
                }

            } catch (e: Exception) {
                _errorMessage.value = "Erro inesperado: ${e.message}"
                _produtos.value = emptyList()
                _categorias.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Criar novo produto
     */
    fun criarProduto(
        nome: String,
        descricao: String?,
        categoriaId: Int,
        estabelecimentoId: Int,
        preco: Double,
        promocao: PromocaoRequest? = null
    ) {
        // Validações
        if (nome.isBlank()) {
            _errorMessage.value = "Nome do produto é obrigatório"
            return
        }

        if (preco <= 0) {
            _errorMessage.value = "Preço deve ser maior que zero"
            return
        }

        if (categoriaId <= 0) {
            _errorMessage.value = "Selecione uma categoria"
            return
        }

        if (estabelecimentoId <= 0) {
            _errorMessage.value = "Selecione um estabelecimento"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val request = ProdutoRequest(
                    nome = nome.trim(),
                    descricao = descricao?.trim()?.takeIf { it.isNotBlank() },
                    idCategoria = categoriaId,
                    idEstabelecimento = estabelecimentoId,
                    preco = preco,
                    promocao = promocao
                )

                val result = produtoRepository.criarProduto(request)

                if (result.isSuccess) {
                    _createResult.value = Result.success(result.getOrNull() ?: 0)
                    // Recarregar lista
                    carregarDados()
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Erro ao criar produto"
                    _errorMessage.value = errorMsg
                    _createResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                val errorMsg = "Erro de conexão: ${e.message}"
                _errorMessage.value = errorMsg
                _createResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Buscar produto por ID para edição
     */
    fun buscarProduto(id: Int, callback: (Produto?) -> Unit) {
        viewModelScope.launch {
            try {
                val result = produtoRepository.buscarProduto(id)
                if (result.isSuccess) {
                    callback(result.getOrNull())
                } else {
                    _errorMessage.value = "Erro ao buscar produto: ${result.exceptionOrNull()?.message}"
                    callback(null)
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão: ${e.message}"
                callback(null)
            }
        }
    }

    /**
     * Filtrar produtos por estabelecimento
     */
    fun getProdutosPorEstabelecimento(estabelecimentoId: Int): List<Produto> {
        return _produtos.value?.filter { it.idEstabelecimento == estabelecimentoId } ?: emptyList()
    }

    /**
     * Filtrar produtos por categoria
     */
    fun getProdutosPorCategoria(categoriaId: Int): List<Produto> {
        return _produtos.value?.filter { it.idCategoria == categoriaId } ?: emptyList()
    }

    /**
     * Buscar produtos por nome
     */
    fun buscarProdutosPorNome(query: String): List<Produto> {
        if (query.isBlank()) return _produtos.value ?: emptyList()

        return _produtos.value?.filter { produto ->
            produto.nome.contains(query, ignoreCase = true) ||
            (produto.descricao?.contains(query, ignoreCase = true) == true)
        } ?: emptyList()
    }

    /**
     * Limpar mensagens de erro
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    /**
     * Limpar resultados
     */
    fun clearResults() {
        _createResult.value = null
        _updateResult.value = null
        _deleteResult.value = null
    }

    /**
     * Recarregar dados
     */
    fun refresh() {
        carregarDados()
    }
}
