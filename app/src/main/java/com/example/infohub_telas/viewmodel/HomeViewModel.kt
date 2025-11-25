package com.example.infohub_telas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.network.models.Promocao
import com.example.infohub_telas.network.models.TipoDesconto
import com.example.infohub_telas.repository.PromocaoRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para a tela Home
 * Gerencia dados de promoções e produtos em destaque
 */
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val promocaoRepository = PromocaoRepository(application.applicationContext)

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
     * Carregar promoções em destaque
     */
    fun carregarPromocoes() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = promocaoRepository.melhoresPromocoes(limit = 5)

                if (result.isSuccess) {
                    _promocoes.value = result.getOrNull() ?: emptyList()
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro ao carregar promoções"
                    // Manter dados mockados como fallback
                    _promocoes.value = criarPromocoesMockadas()
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                // Manter dados mockados como fallback
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
