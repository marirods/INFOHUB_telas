package com.example.infohub_telas.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.model.*
import com.example.infohub_telas.repository.InfoCashRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Estados possíveis para o InfoCash
 */
sealed class InfoCashUiState {
    object Loading : InfoCashUiState()
    data class Success(val saldoInfoCash: SaldoInfoCash) : InfoCashUiState()
    data class Error(val message: String) : InfoCashUiState()
}

/**
 * Estados para o histórico de transações
 */
sealed class HistoricoUiState {
    object Loading : HistoricoUiState()
    data class Success(val transacoes: List<TransacaoInfoCash>) : HistoricoUiState()
    data class Error(val message: String) : HistoricoUiState()
}

/**
 * Estados para o ranking
 */
sealed class RankingUiState {
    object Loading : RankingUiState()
    data class Success(val ranking: List<RankingInfoCash>) : RankingUiState()
    data class Error(val message: String) : RankingUiState()
}

/**
 * ViewModel para gerenciar os dados do InfoCash
 */
class InfoCashViewModel : ViewModel() {

    private val repository = InfoCashRepository()

    // Estados para saldo
    private val _uiState = MutableStateFlow<InfoCashUiState>(InfoCashUiState.Loading)
    val uiState: StateFlow<InfoCashUiState> = _uiState.asStateFlow()

    // Estados para histórico
    private val _historicoState = MutableStateFlow<HistoricoUiState>(HistoricoUiState.Loading)
    val historicoState: StateFlow<HistoricoUiState> = _historicoState.asStateFlow()

    // Estados para ranking
    private val _rankingState = MutableStateFlow<RankingUiState>(RankingUiState.Loading)
    val rankingState: StateFlow<RankingUiState> = _rankingState.asStateFlow()

    // Estados para resumo
    private val _resumoState = MutableStateFlow<List<ResumoAcaoInfoCash>>(emptyList())
    val resumoState: StateFlow<List<ResumoAcaoInfoCash>> = _resumoState.asStateFlow()

    /**
     * Carrega o saldo do InfoCash para um usuário específico
     */
    fun carregarSaldoInfoCash(idUsuario: Int) {
        viewModelScope.launch {
            _uiState.value = InfoCashUiState.Loading

            repository.getSaldoInfoCash(idUsuario)
                .onSuccess { response ->
                    val saldoInfoCash = SaldoInfoCash(
                        idUsuario = response.idUsuario,
                        saldoTotal = response.saldoTotal,
                        ultimaAtualizacao = response.ultimaAtualizacao
                    )
                    _uiState.value = InfoCashUiState.Success(saldoInfoCash)
                }
                .onFailure { exception ->
                    // Em caso de erro, usar dados mock para desenvolvimento
                    _uiState.value = InfoCashUiState.Success(SaldoInfoCash.getMockData())

                    // Para produção, descomente a linha abaixo:
                    // _uiState.value = InfoCashUiState.Error(exception.message ?: "Erro desconhecido")
                }
        }
    }

    /**
     * Carrega o histórico de transações
     */
    fun carregarHistoricoInfoCash(idUsuario: Int, limite: Int? = null) {
        viewModelScope.launch {
            _historicoState.value = HistoricoUiState.Loading

            repository.getHistoricoInfoCash(idUsuario, limite)
                .onSuccess { response ->
                    _historicoState.value = HistoricoUiState.Success(response.data)
                }
                .onFailure { exception ->
                    _historicoState.value = HistoricoUiState.Error(
                        exception.message ?: "Erro ao carregar histórico"
                    )
                }
        }
    }

    /**
     * Carrega o perfil completo (saldo + resumo)
     */
    fun carregarPerfilCompleto(idUsuario: Int) {
        viewModelScope.launch {
            _uiState.value = InfoCashUiState.Loading

            repository.getPerfilInfoCash(idUsuario)
                .onSuccess { response ->
                    val perfilData = response.data

                    // Atualiza saldo
                    val saldoInfoCash = SaldoInfoCash(
                        idUsuario = perfilData.saldo.idUsuario,
                        saldoTotal = perfilData.saldo.saldoTotal,
                        ultimaAtualizacao = perfilData.saldo.ultimaAtualizacao
                    )
                    _uiState.value = InfoCashUiState.Success(saldoInfoCash)

                    // Atualiza resumo
                    _resumoState.value = perfilData.resumo
                }
                .onFailure { exception ->
                    _uiState.value = InfoCashUiState.Error(
                        exception.message ?: "Erro ao carregar perfil"
                    )
                }
        }
    }

    /**
     * Carrega o ranking de usuários
     */
    fun carregarRankingInfoCash(limite: Int? = null) {
        viewModelScope.launch {
            _rankingState.value = RankingUiState.Loading

            repository.getRankingInfoCash(limite)
                .onSuccess { response ->
                    _rankingState.value = RankingUiState.Success(response.data)
                }
                .onFailure { exception ->
                    _rankingState.value = RankingUiState.Error(
                        exception.message ?: "Erro ao carregar ranking"
                    )
                }
        }
    }

    /**
     * Carrega transações por período
     */
    fun carregarTransacoesPorPeriodo(
        idUsuario: Int,
        dataInicio: String,
        dataFim: String
    ) {
        viewModelScope.launch {
            _historicoState.value = HistoricoUiState.Loading

            repository.getTransacoesPorPeriodo(idUsuario, dataInicio, dataFim)
                .onSuccess { response ->
                    _historicoState.value = HistoricoUiState.Success(response.data)
                }
                .onFailure { exception ->
                    _historicoState.value = HistoricoUiState.Error(
                        exception.message ?: "Erro ao carregar transações do período"
                    )
                }
        }
    }

    /**
     * Concede pontos manualmente (função admin)
     */
    fun concederPontos(
        idUsuario: Int,
        tipoAcao: String,
        pontos: Int,
        descricao: String,
        referenciaId: Int? = null,
        onSuccess: (Int) -> Unit = {},
        onError: (String) -> Unit = {}
    ) {
        viewModelScope.launch {
            val request = ConcederPontosRequest(
                idUsuario = idUsuario,
                tipoAcao = tipoAcao,
                pontos = pontos,
                descricao = descricao,
                referenciaId = referenciaId
            )

            repository.concederPontos(request)
                .onSuccess { response ->
                    onSuccess(response.data.idTransacao)
                    // Recarrega o saldo após conceder pontos
                    carregarSaldoInfoCash(idUsuario)
                }
                .onFailure { exception ->
                    onError(exception.message ?: "Erro ao conceder pontos")
                }
        }
    }

    /**
     * Recarrega todos os dados
     */
    fun recarregarTodosDados(idUsuario: Int) {
        carregarPerfilCompleto(idUsuario)
        carregarHistoricoInfoCash(idUsuario, 10)
        carregarRankingInfoCash(10)
    }

    /**
     * Carrega dados mock para desenvolvimento
     */
    fun carregarDadosMock() {
        _uiState.value = InfoCashUiState.Success(SaldoInfoCash.getMockData())
    }
}

