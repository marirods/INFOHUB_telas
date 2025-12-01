package com.example.infohub_telas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.model.EstabelecimentoComEndereco
import com.example.infohub_telas.repository.EstabelecimentoLocalizacaoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados possíveis para a lista de estabelecimentos
 */
sealed class EstabelecimentosUiState {
    object Loading : EstabelecimentosUiState()
    data class Success(val estabelecimentos: List<EstabelecimentoComEndereco>) : EstabelecimentosUiState()
    data class Error(val message: String) : EstabelecimentosUiState()
}

/**
 * ViewModel para gerenciar estabelecimentos registrados
 */
class EstabelecimentoLocalizacaoViewModel : ViewModel() {

    private val repository = EstabelecimentoLocalizacaoRepository()

    private val _estabelecimentosState = MutableStateFlow<EstabelecimentosUiState>(EstabelecimentosUiState.Loading)
    val estabelecimentosState: StateFlow<EstabelecimentosUiState> = _estabelecimentosState.asStateFlow()

    /**
     * Carrega lista de todos os estabelecimentos registrados
     */
    fun carregarEstabelecimentos() {
        viewModelScope.launch {
            _estabelecimentosState.value = EstabelecimentosUiState.Loading

            val result = repository.listarEstabelecimentos()

            _estabelecimentosState.value = result.fold(
                onSuccess = { estabelecimentos ->
                    EstabelecimentosUiState.Success(estabelecimentos)
                },
                onFailure = { error ->
                    EstabelecimentosUiState.Error(error.message ?: "Erro ao carregar estabelecimentos")
                }
            )
        }
    }

    /**
     * Recarrega a lista de estabelecimentos
     */
    fun recarregar() {
        carregarEstabelecimentos()
    }
}

