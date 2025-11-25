package com.example.infohub_telas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.network.models.Produto
import com.example.infohub_telas.network.models.Categoria
import com.example.infohub_telas.repository.ProdutoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para a lista de produtos
 * Gerencia produtos, categorias e estados da UI
 */
class ListaProdutosViewModel(application: Application) : AndroidViewModel(application) {

    private val produtoRepository = ProdutoRepository(application.applicationContext)

    // Estados da UI
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _produtos = MutableLiveData<List<Produto>>(emptyList())
    val produtos: LiveData<List<Produto>> = _produtos

    private val _categorias = MutableLiveData<List<Categoria>>(emptyList())
    val categorias: LiveData<List<Categoria>> = _categorias

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Estados de filtros
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery

    private val _selectedCategoria = MutableLiveData<Int?>(null)
    val selectedCategoria: LiveData<Int?> = _selectedCategoria

    init {
        carregarDados()
    }

    /**
     * Carregar produtos e categorias da API
     */
    fun carregarDados() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                // Carregar produtos e categorias em paralelo
                val produtosResult = produtoRepository.listarProdutos()
                val categoriasResult = produtoRepository.listarCategorias()

                // Processar resultado dos produtos (com mensagem de erro se falhar)
                if (produtosResult.isSuccess) {
                    _produtos.value = produtosResult.getOrNull() ?: emptyList()
                } else {
                    _errorMessage.value = "Erro ao carregar produtos: ${produtosResult.exceptionOrNull()?.message}"
                    _produtos.value = emptyList()
                }

                // Processar resultado das categorias (sem mostrar erro se falhar)
                if (categoriasResult.isSuccess) {
                    _categorias.value = categoriasResult.getOrNull() ?: emptyList()
                } else {
                    // Falha silenciosa: apenas define lista vazia sem mostrar erro ao usuário
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
     * Atualizar filtro de busca
     */
    fun atualizarBusca(query: String) {
        _searchQuery.value = query
    }

    /**
     * Selecionar/deselecionar categoria
     */
    fun selecionarCategoria(categoriaId: Int?) {
        _selectedCategoria.value = if (_selectedCategoria.value == categoriaId) null else categoriaId
    }

    /**
     * Obter produtos filtrados
     */
    fun getProdutosFiltrados(): List<Produto> {
        val query = _searchQuery.value ?: ""
        val categoriaId = _selectedCategoria.value

        return _produtos.value?.filter { produto ->
            val matchesSearch = produto.nome.contains(query, ignoreCase = true) ||
                               (produto.descricao?.contains(query, ignoreCase = true) == true)
            val matchesCategoria = categoriaId == null || produto.idCategoria == categoriaId

            matchesSearch && matchesCategoria
        } ?: emptyList()
    }

    /**
     * Limpar mensagens de erro
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    /**
     * Recarregar dados
     */
    fun refresh() {
        carregarDados()
    }


}
