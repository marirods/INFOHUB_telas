package com.example.infohub_telas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.network.ApiConfig
import com.example.infohub_telas.network.models.*
import kotlinx.coroutines.launch

/**
 * ViewModel para gerenciar estabelecimentos (Pessoa Jurídica)
 */
class EstabelecimentoViewModel(application: Application) : AndroidViewModel(application) {

    private val apiConfig = ApiConfig.getInstance(application.applicationContext)
    private val estabelecimentoService = apiConfig.estabelecimentoService

    // Estados da UI
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

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
        carregarEstabelecimentos()
    }

    /**
     * Carregar lista de estabelecimentos
     */
    fun carregarEstabelecimentos() {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = estabelecimentoService.listarEstabelecimentos()

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.status) {
                        _estabelecimentos.value = responseBody.estabelecimentos
                    } else {
                        _errorMessage.value = responseBody.message ?: "Erro ao carregar estabelecimentos"
                        _estabelecimentos.value = emptyList()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            _errorMessage.value = errorResponse.message
                        } catch (e: Exception) {
                            _errorMessage.value = "Erro ao carregar estabelecimentos: ${response.code()}"
                        }
                    } else {
                        _errorMessage.value = "Erro ao carregar estabelecimentos"
                    }
                    _estabelecimentos.value = emptyList()
                }
            } catch (e: Exception) {
                _errorMessage.value = "Erro de conexão: ${e.message}"
                _estabelecimentos.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Criar novo estabelecimento
     */
    fun criarEstabelecimento(nome: String, cnpj: String, telefone: String?) {
        if (nome.isBlank()) {
            _errorMessage.value = "Nome do estabelecimento é obrigatório"
            return
        }

        if (cnpj.isBlank()) {
            _errorMessage.value = "CNPJ é obrigatório"
            return
        }

        if (!isValidCNPJ(cnpj)) {
            _errorMessage.value = "CNPJ inválido"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val request = EstabelecimentoRequest(
                    nome = nome.trim(),
                    cnpj = cnpj.replace(Regex("[^0-9]"), ""), // Remove formatação
                    telefone = telefone?.trim()?.takeIf { it.isNotBlank() }
                )

                val response = estabelecimentoService.criarEstabelecimento(request)

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.status) {
                        _createResult.value = Result.success(responseBody.id ?: 0)
                        // Recarregar lista
                        carregarEstabelecimentos()
                    } else {
                        _errorMessage.value = responseBody.message
                        _createResult.value = Result.failure(Exception(responseBody.message))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = if (errorBody != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } catch (e: Exception) {
                            "Erro ao criar estabelecimento: ${response.code()}"
                        }
                    } else {
                        "Erro ao criar estabelecimento"
                    }
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
     * Atualizar estabelecimento existente
     */
    fun atualizarEstabelecimento(id: Int, nome: String, cnpj: String, telefone: String?) {
        if (nome.isBlank()) {
            _errorMessage.value = "Nome do estabelecimento é obrigatório"
            return
        }

        if (cnpj.isBlank()) {
            _errorMessage.value = "CNPJ é obrigatório"
            return
        }

        if (!isValidCNPJ(cnpj)) {
            _errorMessage.value = "CNPJ inválido"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val request = EstabelecimentoRequest(
                    nome = nome.trim(),
                    cnpj = cnpj.replace(Regex("[^0-9]"), ""),
                    telefone = telefone?.trim()?.takeIf { it.isNotBlank() }
                )

                val response = estabelecimentoService.atualizarEstabelecimento(id, request)

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.status) {
                        _updateResult.value = Result.success(true)
                        carregarEstabelecimentos()
                    } else {
                        _errorMessage.value = responseBody.message
                        _updateResult.value = Result.failure(Exception(responseBody.message))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = if (errorBody != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } catch (e: Exception) {
                            "Erro ao atualizar estabelecimento: ${response.code()}"
                        }
                    } else {
                        "Erro ao atualizar estabelecimento"
                    }
                    _errorMessage.value = errorMsg
                    _updateResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                val errorMsg = "Erro de conexão: ${e.message}"
                _errorMessage.value = errorMsg
                _updateResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Deletar estabelecimento
     */
    fun deletarEstabelecimento(id: Int) {
        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val response = estabelecimentoService.deletarEstabelecimento(id)

                if (response.isSuccessful && response.body() != null) {
                    val responseBody = response.body()!!
                    if (responseBody.status) {
                        _deleteResult.value = Result.success(true)
                        carregarEstabelecimentos()
                    } else {
                        _errorMessage.value = responseBody.message
                        _deleteResult.value = Result.failure(Exception(responseBody.message))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = if (errorBody != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val errorResponse = gson.fromJson(errorBody, ErrorResponse::class.java)
                            errorResponse.message
                        } catch (e: Exception) {
                            "Erro ao deletar estabelecimento: ${response.code()}"
                        }
                    } else {
                        "Erro ao deletar estabelecimento"
                    }
                    _errorMessage.value = errorMsg
                    _deleteResult.value = Result.failure(Exception(errorMsg))
                }
            } catch (e: Exception) {
                val errorMsg = "Erro de conexão: ${e.message}"
                _errorMessage.value = errorMsg
                _deleteResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
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
     * Validação básica de CNPJ
     */
    private fun isValidCNPJ(cnpj: String): Boolean {
        val cleanCnpj = cnpj.replace(Regex("[^0-9]"), "")
        return cleanCnpj.length == 14 && !cleanCnpj.all { it == cleanCnpj.first() }
    }
}
