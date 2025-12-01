package com.example.infohub_telas.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.network.models.Promocao
import com.example.infohub_telas.network.models.TipoDesconto
import com.example.infohub_telas.repository.PromocaoRepository
import com.example.infohub_telas.repository.ProdutoApiRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela Home
 * Gerencia dados de promoções e produtos em destaque
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val promocaoRepository = PromocaoRepository(application.applicationContext)
    private val produtoRepository = ProdutoApiRepository()

    // Estados da UI
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _promocoes = MutableLiveData<List<Promocao>>(emptyList())
    val promocoes: LiveData<List<Promocao>> = _promocoes

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        carregarPromocoes()
    }

    /**
     * Carregar produtos com promoção para exibir na Home
     * Busca produtos diretamente e cria objetos Promocao com dados completos
     */
    fun carregarPromocoes() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                Log.d("HomeViewModel", "🏠 Iniciando carregamento de produtos para Home...")

                // Buscar produtos diretamente da API
                val result = produtoRepository.listarProdutos()

                if (result.isSuccess) {
                    val produtos = result.getOrNull() ?: emptyList()
                    Log.d("HomeViewModel", "✅ ${produtos.size} produtos recebidos da API")

                    // Converter produtos em promoções para exibição
                    val promocoes = produtos
                        .filter { produto ->
                            // Filtrar apenas produtos com promoção
                            produto.promocao != null && produto.promocao.precoPromocional < produto.preco
                        }
                        .take(10) // Limitar a 10 produtos
                        .mapIndexed { index, produto ->
                            val desconto = if (produto.promocao != null) {
                                ((produto.preco - produto.promocao.precoPromocional) / produto.preco * 100)
                            } else {
                                0.0
                            }

                            // Criar objeto Promocao com dados do produto
                            Promocao(
                                idPromocao = produto.id ?: index,
                                idProduto = produto.id ?: index,
                                idEstabelecimento = produto.idEstabelecimento,
                                tipoDesconto = TipoDesconto.PERCENTUAL,
                                valorDesconto = desconto,
                                dataInicio = produto.promocao?.dataInicio ?: "",
                                dataFim = produto.promocao?.dataFim ?: "",
                                ativa = true,
                                precoPromocional = produto.promocao?.precoPromocional ?: produto.preco,
                                produto = com.example.infohub_telas.network.models.Produto(
                                    idProduto = produto.id,
                                    nome = produto.nome,
                                    descricao = produto.descricao,
                                    idCategoria = produto.idCategoria,
                                    idEstabelecimento = produto.idEstabelecimento,
                                    preco = produto.preco,
                                    imagem = produto.imagem,
                                    categoria = null,
                                    precoPromocional = produto.promocao?.precoPromocional,
                                    dataInicio = produto.promocao?.dataInicio,
                                    dataFim = produto.promocao?.dataFim
                                )
                            )
                        }

                    if (promocoes.isNotEmpty()) {
                        _promocoes.value = promocoes
                        Log.d("HomeViewModel", "✅ ${promocoes.size} produtos com promoção encontrados")
                    } else {
                        Log.w("HomeViewModel", "⚠️ Nenhum produto com promoção encontrado, usando fallback")
                        _promocoes.value = criarPromocoesMockadas()
                    }
                } else {
                    val errorMsg = result.exceptionOrNull()?.message ?: "Erro ao carregar produtos"
                    Log.e("HomeViewModel", "❌ Erro ao buscar produtos: $errorMsg")
                    _errorMessage.value = errorMsg
                    // Usar dados mockados como fallback
                    _promocoes.value = criarPromocoesMockadas()
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "💥 Exceção ao carregar produtos: ${e.message}", e)
                _errorMessage.value = e.message ?: "Erro inesperado"
                // Usar dados mockados como fallback
                _promocoes.value = criarPromocoesMockadas()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Dados mockados como fallback quando a API falha
     */
    private fun criarPromocoesMockadas(): List<Promocao> {
        return listOf(
            Promocao(
                idPromocao = 1,
                idProduto = 1,
                idEstabelecimento = 1,
                tipoDesconto = TipoDesconto.PERCENTUAL,
                valorDesconto = 25.0,
                dataInicio = "2024-11-01T00:00:00.000Z",
                dataFim = "2024-12-31T23:59:59.000Z",
                ativa = true,
                precoPromocional = 7.99
            ),
            Promocao(
                idPromocao = 2,
                idProduto = 2,
                idEstabelecimento = 1,
                tipoDesconto = TipoDesconto.PERCENTUAL,
                valorDesconto = 30.0,
                dataInicio = "2024-11-01T00:00:00.000Z",
                dataFim = "2024-12-31T23:59:59.000Z",
                ativa = true,
                precoPromocional = 11.19
            ),
            Promocao(
                idPromocao = 3,
                idProduto = 3,
                idEstabelecimento = 1,
                tipoDesconto = TipoDesconto.PERCENTUAL,
                valorDesconto = 20.0,
                dataInicio = "2024-11-01T00:00:00.000Z",
                dataFim = "2024-12-31T23:59:59.000Z",
                ativa = true,
                precoPromocional = 10.39
            )
        )
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
        carregarPromocoes()
    }
}
