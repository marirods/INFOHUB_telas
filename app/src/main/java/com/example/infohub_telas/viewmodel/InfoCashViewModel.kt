package com.example.infohub_telas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.model.SaldoInfoCash
import com.example.infohub_telas.service.InfoCashApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Estados possíveis para o InfoCash
 */
sealed class InfoCashUiState {
    object Loading : InfoCashUiState()
    data class Success(val saldoInfoCash: SaldoInfoCash) : InfoCashUiState()
    data class Error(val message: String) : InfoCashUiState()
}

/**
 * ViewModel para gerenciar os dados do InfoCash
 */
class InfoCashViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<InfoCashUiState>(InfoCashUiState.Loading)
    val uiState: StateFlow<InfoCashUiState> = _uiState.asStateFlow()

    // TODO: Substituir pela URL base real da sua API
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://sua-api-base-url.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(InfoCashApiService::class.java)

    /**
     * Carrega o saldo do InfoCash para um usuário específico
     */
    fun carregarSaldoInfoCash(idUsuario: Int) {
        viewModelScope.launch {
            _uiState.value = InfoCashUiState.Loading

            try {
                val response = apiService.getSaldoInfoCash(idUsuario)

                if (response.isSuccessful && response.body() != null) {
                    val saldoInfoCash = SaldoInfoCash.fromResponse(response.body()!!)
                    _uiState.value = InfoCashUiState.Success(saldoInfoCash)
                } else {
                    _uiState.value = InfoCashUiState.Error("Erro ao carregar saldo: ${response.message()}")
                }
            } catch (e: Exception) {
                // Por enquanto, vamos usar dados mock em caso de erro de rede
                _uiState.value = InfoCashUiState.Success(SaldoInfoCash.getMockData())

                // Em produção, descomente a linha abaixo para mostrar o erro real:
                // _uiState.value = InfoCashUiState.Error("Erro de conectividade: ${e.message}")

                // Log do erro para debug
                println("InfoCashViewModel - Erro ao carregar dados: ${e.message}")
            }
        }
    }

    /**
     * Recarrega os dados
     */
    fun recarregarDados(idUsuario: Int) {
        carregarSaldoInfoCash(idUsuario)
    }

    /**
     * Carrega dados mock para desenvolvimento
     */
    fun carregarDadosMock() {
        _uiState.value = InfoCashUiState.Success(SaldoInfoCash.getMockData())
    }
}

