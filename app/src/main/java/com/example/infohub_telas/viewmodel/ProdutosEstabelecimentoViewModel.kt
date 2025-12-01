package com.example.infohub_telas.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.model.EstabelecimentoComEndereco
import com.example.infohub_telas.model.Produto
import com.example.infohub_telas.repository.EstabelecimentoLocalizacaoRepository
import com.example.infohub_telas.repository.ProdutoApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados possíveis para a lista de produtos do estabelecimento
 */
sealed class ProdutosEstabelecimentoUiState {
    object Loading : ProdutosEstabelecimentoUiState()
    data class Success(val produtos: List<Produto>) : ProdutosEstabelecimentoUiState()
    data class Error(val message: String) : ProdutosEstabelecimentoUiState()
}

/**
 * ViewModel para gerenciar produtos de um estabelecimento específico
 */
class ProdutosEstabelecimentoViewModel : ViewModel() {

    private val estabelecimentoRepository = EstabelecimentoLocalizacaoRepository()
    private val produtoRepository = ProdutoApiRepository()

    private val _produtosState = MutableStateFlow<ProdutosEstabelecimentoUiState>(
        ProdutosEstabelecimentoUiState.Loading
    )
    val produtosState: StateFlow<ProdutosEstabelecimentoUiState> = _produtosState.asStateFlow()

    private val _estabelecimento = MutableStateFlow<EstabelecimentoComEndereco?>(null)
    val estabelecimento: StateFlow<EstabelecimentoComEndereco?> = _estabelecimento.asStateFlow()

    companion object {
        private const val TAG = "ProdutosEstabelecVM"
    }

    /**
     * Carrega dados do estabelecimento e seus produtos
     */
    fun carregarDados(idEstabelecimento: Int) {
        viewModelScope.launch {
            _produtosState.value = ProdutosEstabelecimentoUiState.Loading

            Log.d(TAG, "═══════════════════════════════════════")
            Log.d(TAG, "🏪 Carregando dados do estabelecimento ID: $idEstabelecimento")

            // Primeiro carrega os dados do estabelecimento
            val estabelecimentoResult = estabelecimentoRepository.buscarPorId(idEstabelecimento)

            estabelecimentoResult.fold(
                onSuccess = { estab ->
                    Log.d(TAG, "✅ Estabelecimento carregado: ${estab.nome}")
                    _estabelecimento.value = estab
                },
                onFailure = { error ->
                    Log.e(TAG, "❌ Erro ao carregar estabelecimento: ${error.message}")
                }
            )

            // Depois carrega os produtos
            carregarProdutos(idEstabelecimento)
        }
    }

    /**
     * Carrega produtos do estabelecimento
     */
    private suspend fun carregarProdutos(idEstabelecimento: Int) {
        Log.d(TAG, "📦 Buscando produtos do estabelecimento...")

        val result = produtoRepository.listarProdutos()

        result.fold(
            onSuccess = { todosProdutos ->
                // Filtrar produtos do estabelecimento específico
                val produtosDoEstabelecimento = todosProdutos.filter { produto ->
                    produto.idEstabelecimento == idEstabelecimento
                }

                Log.d(TAG, "✅ ${produtosDoEstabelecimento.size} produto(s) encontrado(s)")

                produtosDoEstabelecimento.forEachIndexed { index, produto ->
                    Log.d(TAG, "  [$index] ${produto.nome} - ${com.example.infohub_telas.utils.AppUtils.formatarMoeda(produto.preco)}")
                }

                _produtosState.value = ProdutosEstabelecimentoUiState.Success(produtosDoEstabelecimento)

                Log.d(TAG, "═══════════════════════════════════════")
            },
            onFailure = { error ->
                val errorMessage = error.message ?: "Erro ao carregar produtos"
                Log.e(TAG, "❌ $errorMessage")
                Log.e(TAG, "═══════════════════════════════════════")

                _produtosState.value = ProdutosEstabelecimentoUiState.Error(errorMessage)
            }
        )
    }

    /**
     * Recarrega os dados
     */
    fun recarregar(idEstabelecimento: Int) {
        carregarDados(idEstabelecimento)
    }
}

