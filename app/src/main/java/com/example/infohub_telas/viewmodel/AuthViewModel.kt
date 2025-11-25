package com.example.infohub_telas.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.infohub_telas.network.models.Usuario
import com.example.infohub_telas.network.models.LoginResponse
import com.example.infohub_telas.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para operações de autenticação
 * Implementa padrão MVVM para separar a lógica de negócios da UI
 */
class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = AuthRepository(application.applicationContext)

    // Estados da UI
    private val _isLoading = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _loginResult = MutableLiveData<Result<LoginResponse>>()
    val loginResult: LiveData<Result<LoginResponse>> = _loginResult

    private val _cadastroResult = MutableLiveData<Result<Boolean>>()
    val cadastroResult: LiveData<Result<Boolean>> = _cadastroResult

    private val _recuperacaoResult = MutableLiveData<Result<String>>()
    val recuperacaoResult: LiveData<Result<String>> = _recuperacaoResult

    private val _validacaoCodigoResult = MutableLiveData<Result<String>>()
    val validacaoCodigoResult: LiveData<Result<String>> = _validacaoCodigoResult

    private val _redefinirSenhaResult = MutableLiveData<Result<String>>()
    val redefinirSenhaResult: LiveData<Result<String>> = _redefinirSenhaResult

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _currentUser = MutableLiveData<Usuario?>()
    val currentUser: LiveData<Usuario?> = _currentUser

    init {
        // Verificar se há usuário logado ao inicializar
        _currentUser.value = authRepository.getLoggedUser()
    }

    /**
     * Fazer login
     */
    fun login(email: String, senha: String) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "Email inválido"
            return
        }

        if (senha.isBlank()) {
            _errorMessage.value = "Senha é obrigatória"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.login(email, senha)

                if (result.isSuccess) {
                    val loginResponse = result.getOrNull()
                    if (loginResponse != null) {
                        _currentUser.value = authRepository.getLoggedUser()
                        _loginResult.value = Result.success(loginResponse)
                    } else {
                        _errorMessage.value = "Erro ao processar resposta do login"
                        _loginResult.value = Result.failure(Exception("Erro ao processar resposta do login"))
                    }
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro no login"
                    _loginResult.value = Result.failure(result.exceptionOrNull() ?: Exception("Erro no login"))
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                _loginResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Cadastrar usuário
     */
    fun cadastro(
        nome: String,
        email: String,
        senha: String,
        confirmarSenha: String,
        dataNascimento: String? = null,
        telefone: String? = null
    ) {
        // Validações
        if (nome.isBlank()) {
            _errorMessage.value = "Nome é obrigatório"
            return
        }

        if (!isValidEmail(email)) {
            _errorMessage.value = "Email inválido"
            return
        }

        if (senha.length < 6) {
            _errorMessage.value = "Senha deve ter pelo menos 6 caracteres"
            return
        }

        if (senha != confirmarSenha) {
            _errorMessage.value = "Senhas não coincidem"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.cadastro(nome, email, senha, dataNascimento, telefone)

                if (result.isSuccess) {
                    _cadastroResult.value = Result.success(true)
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro no cadastro"
                    _cadastroResult.value = Result.failure(result.exceptionOrNull() ?: Exception("Erro no cadastro"))
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                _cadastroResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Solicitar recuperação de senha
     */
    fun recuperarSenha(email: String) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "Email inválido"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.recuperarSenha(email)
                _recuperacaoResult.value = result

                if (result.isFailure) {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro na recuperação"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                _recuperacaoResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Validar código de recuperação
     */
    fun validarCodigo(email: String, codigo: String) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "Email inválido"
            return
        }

        if (codigo.length != 6) {
            _errorMessage.value = "Código deve ter 6 dígitos"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.validarCodigo(email, codigo)
                _validacaoCodigoResult.value = result

                if (result.isFailure) {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Código inválido"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                _validacaoCodigoResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Redefinir senha
     */
    fun redefinirSenha(email: String, codigo: String, novaSenha: String, confirmarSenha: String) {
        if (!isValidEmail(email)) {
            _errorMessage.value = "Email inválido"
            return
        }

        if (codigo.length != 6) {
            _errorMessage.value = "Código inválido"
            return
        }

        if (novaSenha.length < 6) {
            _errorMessage.value = "Nova senha deve ter pelo menos 6 caracteres"
            return
        }

        if (novaSenha != confirmarSenha) {
            _errorMessage.value = "Senhas não coincidem"
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        viewModelScope.launch {
            try {
                val result = authRepository.redefinirSenha(email, codigo, novaSenha)
                _redefinirSenhaResult.value = result

                if (result.isFailure) {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Erro ao redefinir senha"
                }
            } catch (e: Exception) {
                _errorMessage.value = e.message ?: "Erro inesperado"
                _redefinirSenhaResult.value = Result.failure(e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Logout
     */
    fun logout() {
        authRepository.logout()
        _currentUser.value = null
    }

    /**
     * Verificar se usuário está logado
     */
    fun isLoggedIn(): Boolean {
        return authRepository.isLoggedIn()
    }

    /**
     * Limpar mensagem de erro
     */
    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    /**
     * Limpar resultados
     */
    fun clearResults() {
        // Os resultados são automaticamente limpos após uso
    }

    /**
     * Validação de email
     */
    private fun isValidEmail(email: String): Boolean {
        return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
