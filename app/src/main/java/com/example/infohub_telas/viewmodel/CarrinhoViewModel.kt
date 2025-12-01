package com.example.infohub_telas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.model.CarrinhoItem
import com.example.infohub_telas.model.CarrinhoResponse
import com.example.infohub_telas.repository.CarrinhoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para gerenciamento do Carrinho de Compras
 */
class CarrinhoViewModel : ViewModel() {

    private val repository = CarrinhoRepository()

    // Estados do carrinho
    private val _carrinhoState = MutableStateFlow<CarrinhoUiState>(CarrinhoUiState.Loading)
    val carrinhoState: StateFlow<CarrinhoUiState> = _carrinhoState.asStateFlow()

    // Estado de operações (adicionar, remover, etc)
    private val _operationState = MutableStateFlow<OperationUiState>(OperationUiState.Idle)
    val operationState: StateFlow<OperationUiState> = _operationState.asStateFlow()

    /**
     * Carregar carrinho do usuário
     */
    fun carregarCarrinho(token: String, idUsuario: Int) {
        viewModelScope.launch {
            _carrinhoState.value = CarrinhoUiState.Loading

            val result = repository.listarCarrinho(token, idUsuario)

            _carrinhoState.value = result.fold(
                onSuccess = { response ->
                    CarrinhoUiState.Success(
                        itens = response.carrinho,
                        valorTotal = response.valorTotal
                    )
                },
                onFailure = { error ->
                    CarrinhoUiState.Error(error.message ?: "Erro ao carregar carrinho")
                }
            )
        }
    }

    /**
     * Adicionar item ao carrinho
     * IMPLEMENTAÇÃO CONFORME DOCUMENTAÇÃO OFICIAL DA API
     *
     * Usa novo repository que segue exatamente a documentação:
     * - POST /carrinho
     * - Body: { id_usuario, id_produto, quantidade }
     * - SEM id_estabelecimento (não está na documentação)
     */
    fun adicionarItem(
        token: String,
        idUsuario: Int,
        idProduto: Int,
        idEstabelecimento: Int, // Mantido para compatibilidade, mas não usado
        quantidade: Int
    ) {
        viewModelScope.launch {
            android.util.Log.d("CarrinhoViewModel", "🔵 adicionarItem chamado")
            android.util.Log.d("CarrinhoViewModel", "   - idUsuario: $idUsuario")
            android.util.Log.d("CarrinhoViewModel", "   - idProduto: $idProduto")
            android.util.Log.d("CarrinhoViewModel", "   - quantidade: $quantidade")

            _operationState.value = OperationUiState.Loading

            // Usar novo repository conforme documentação
            val novoRepository = com.example.infohub_telas.repository.AdicionarCarrinhoRepository()

            val result = novoRepository.adicionarItem(
                token = token,
                idUsuario = idUsuario,
                idProduto = idProduto,
                quantidade = quantidade
                // Note: SEM id_estabelecimento conforme documentação
            )

            _operationState.value = result.fold(
                onSuccess = { response ->
                    android.util.Log.d("CarrinhoViewModel", "✅ Item adicionado com sucesso!")
                    android.util.Log.d("CarrinhoViewModel", "   Message: ${response.message}")

                    // Recarregar carrinho após adicionar
                    carregarCarrinho(token, idUsuario)

                    OperationUiState.Success(response.message ?: "Item adicionado com sucesso")
                },
                onFailure = { error ->
                    android.util.Log.e("CarrinhoViewModel", "❌ Erro ao adicionar item: ${error.message}")
                    OperationUiState.Error(error.message ?: "Erro ao adicionar item")
                }
            )
        }
    }

    /**
     * Atualizar quantidade de um item
     */
    fun atualizarQuantidade(
        token: String,
        idUsuario: Int,
        idCarrinho: Int,
        novaQuantidade: Int
    ) {
        viewModelScope.launch {
            _operationState.value = OperationUiState.Loading

            val result = repository.atualizarQuantidade(token, idCarrinho, novaQuantidade)

            _operationState.value = result.fold(
                onSuccess = { response ->
                    // Recarregar carrinho após atualizar
                    carregarCarrinho(token, idUsuario)
                    OperationUiState.Success(response.message)
                },
                onFailure = { error ->
                    OperationUiState.Error(error.message ?: "Erro ao atualizar quantidade")
                }
            )
        }
    }

    /**
     * Remover item do carrinho
     */
    fun removerItem(
        token: String,
        idUsuario: Int,
        idCarrinho: Int
    ) {
        viewModelScope.launch {
            _operationState.value = OperationUiState.Loading

            val result = repository.removerItem(token, idCarrinho)

            _operationState.value = result.fold(
                onSuccess = { response ->
                    // Recarregar carrinho após remover
                    carregarCarrinho(token, idUsuario)
                    OperationUiState.Success(response.message)
                },
                onFailure = { error ->
                    OperationUiState.Error(error.message ?: "Erro ao remover item")
                }
            )
        }
    }

    /**
     * Limpar carrinho completamente
     */
    fun limparCarrinho(token: String, idUsuario: Int) {
        viewModelScope.launch {
            _operationState.value = OperationUiState.Loading

            val result = repository.limparCarrinho(token, idUsuario)

            _operationState.value = result.fold(
                onSuccess = { response ->
                    // Atualizar estado para vazio
                    _carrinhoState.value = CarrinhoUiState.Success(
                        itens = emptyList(),
                        valorTotal = 0.0
                    )
                    OperationUiState.Success(response.message)
                },
                onFailure = { error ->
                    OperationUiState.Error(error.message ?: "Erro ao limpar carrinho")
                }
            )
        }
    }

    /**
     * Resetar estado de operação
     */
    fun resetOperationState() {
        _operationState.value = OperationUiState.Idle
    }

    /**
     * Recarregar carrinho (refresh)
     */
    fun recarregarCarrinho(token: String, idUsuario: Int) {
        carregarCarrinho(token, idUsuario)
    }
}

/**
 * Estados da UI para o Carrinho
 */
sealed class CarrinhoUiState {
    object Loading : CarrinhoUiState()
    data class Success(
        val itens: List<CarrinhoItem>,
        val valorTotal: Double
    ) : CarrinhoUiState()
    data class Error(val message: String) : CarrinhoUiState()
}

/**
 * Estados das operações (adicionar, remover, etc)
 */
sealed class OperationUiState {
    object Idle : OperationUiState()
    object Loading : OperationUiState()
    data class Success(val message: String) : OperationUiState()
    data class Error(val message: String) : OperationUiState()
}

