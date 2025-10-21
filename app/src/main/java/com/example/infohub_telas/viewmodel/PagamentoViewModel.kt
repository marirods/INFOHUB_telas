package com.example.infohub_telas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PagamentoUiState(
    val etapa: Int = 1,
    val nomeCompleto: String = "",
    val numeroCartao: String = "",
    val validade: String = "",
    val cvv: String = "",
    val cep: String = "",
    val rua: String = "",
    val bairro: String = "",
    val cidade: String = "",
    val pagamentoRealizado: Boolean = false
)

class PagamentoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PagamentoUiState())
    val uiState: StateFlow<PagamentoUiState> = _uiState.asStateFlow()

    fun onNomeChange(nome: String) {
        _uiState.update { it.copy(nomeCompleto = nome) }
    }

    fun onNumeroCartaoChange(numero: String) {
        if (numero.length <= 16) {
            _uiState.update { it.copy(numeroCartao = numero) }
        }
    }

    fun onValidadeChange(validade: String) {
        if (validade.length <= 4) {
            _uiState.update { it.copy(validade = validade) }
        }
    }

    fun onCvvChange(cvv: String) {
        if (cvv.length <= 3) {
            _uiState.update { it.copy(cvv = cvv) }
        }
    }

    fun onCepChange(cep: String) {
        if (cep.length <= 8) {
            _uiState.update { it.copy(cep = cep) }
        }
    }

    fun onRuaChange(rua: String) {
        _uiState.update { it.copy(rua = rua) }
    }

    fun onBairroChange(bairro: String) {
        _uiState.update { it.copy(bairro = bairro) }
    }

    fun onCidadeChange(cidade: String) {
        _uiState.update { it.copy(cidade = cidade) }
    }

    fun proximaEtapa() {
        if (_uiState.value.etapa < 3) {
            _uiState.update { it.copy(etapa = it.etapa + 1) }
        }
    }

    fun etapaAnterior() {
        if (_uiState.value.etapa > 1) {
            _uiState.update { it.copy(etapa = it.etapa - 1) }
        }
    }

    fun confirmarPagamento() {
        viewModelScope.launch {
            // Lógica de pagamento aqui
            _uiState.update { it.copy(pagamentoRealizado = true) }
        }
    }

    fun resetarPagamento() {
        _uiState.value = PagamentoUiState()
    }
}
