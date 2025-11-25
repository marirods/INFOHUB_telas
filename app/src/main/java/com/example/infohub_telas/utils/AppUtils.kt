package com.example.infohub_telas.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe utilitária para tratamento de erros e formatações
 */
object AppUtils {

    /**
     * Formatar valor monetário para Real brasileiro
     */
    fun formatarMoeda(valor: Double): String {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatador.format(valor)
    }

    /**
     * Formatar data para exibição
     */
    fun formatarData(timestamp: String): String {
        return try {
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val formatoSaida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val data = formatoEntrada.parse(timestamp)
            formatoSaida.format(data ?: Date())
        } catch (e: Exception) {
            timestamp
        }
    }

    /**
     * Formatar data simples (apenas data)
     */
    fun formatarDataSimples(timestamp: String): String {
        return try {
            val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val formatoSaida = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val data = formatoEntrada.parse(timestamp)
            formatoSaida.format(data ?: Date())
        } catch (e: Exception) {
            timestamp
        }
    }

    /**
     * Formatar tempo relativo (há X minutos, horas, etc.)
     */
    fun formatarTempoRelativo(timestamp: Long): String {
        val agora = System.currentTimeMillis()
        val diferenca = agora - timestamp

        return when {
            diferenca < 60_000 -> "Agora mesmo"
            diferenca < 3600_000 -> "${diferenca / 60_000} min atrás"
            diferenca < 86400_000 -> "${diferenca / 3600_000}h atrás"
            diferenca < 2592000_000 -> "${diferenca / 86400_000} dias atrás"
            else -> {
                val data = Date(timestamp)
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(data)
            }
        }
    }

    /**
     * Calcular porcentagem de desconto
     */
    fun calcularDesconto(precoOriginal: Double, precoPromocional: Double): Int {
        if (precoOriginal <= 0) return 0
        val desconto = ((precoOriginal - precoPromocional) / precoOriginal) * 100
        return desconto.toInt()
    }

    /**
     * Validar CEP brasileiro
     */
    fun isValidCEP(cep: String): Boolean {
        val cepPattern = "^\\d{5}-?\\d{3}$"
        return cep.matches(Regex(cepPattern))
    }

    /**
     * Formatar CEP (adicionar hífen se necessário)
     */
    fun formatarCEP(cep: String): String {
        val apenasNumeros = cep.replace(Regex("[^0-9]"), "")
        return if (apenasNumeros.length == 8) {
            "${apenasNumeros.substring(0, 5)}-${apenasNumeros.substring(5)}"
        } else {
            cep
        }
    }

    /**
     * Validar telefone brasileiro
     */
    fun isValidTelefone(telefone: String): Boolean {
        val telefonePattern = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$"
        return telefone.matches(Regex(telefonePattern))
    }

    /**
     * Formatar telefone brasileiro
     */
    fun formatarTelefone(telefone: String): String {
        val apenasNumeros = telefone.replace(Regex("[^0-9]"), "")
        return when (apenasNumeros.length) {
            10 -> "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 6)}-${apenasNumeros.substring(6)}"
            11 -> "(${apenasNumeros.substring(0, 2)}) ${apenasNumeros.substring(2, 7)}-${apenasNumeros.substring(7)}"
            else -> telefone
        }
    }

    /**
     * Validar CNPJ
     */
    fun isValidCNPJ(cnpj: String): Boolean {
        val apenasNumeros = cnpj.replace(Regex("[^0-9]"), "")
        if (apenasNumeros.length != 14) return false

        // Validação básica (todos os dígitos iguais)
        if (apenasNumeros.all { it == apenasNumeros.first() }) return false

        return true // Implementação básica - pode ser expandida com algoritmo completo
    }

    /**
     * Formatar CNPJ
     */
    fun formatarCNPJ(cnpj: String): String {
        val apenasNumeros = cnpj.replace(Regex("[^0-9]"), "")
        return if (apenasNumeros.length == 14) {
            "${apenasNumeros.substring(0, 2)}.${apenasNumeros.substring(2, 5)}.${apenasNumeros.substring(5, 8)}/${apenasNumeros.substring(8, 12)}-${apenasNumeros.substring(12)}"
        } else {
            cnpj
        }
    }

    /**
     * Mostrar toast de erro
     */
    fun showErrorToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    /**
     * Mostrar toast de sucesso
     */
    fun showSuccessToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Obter mensagem de erro amigável
     */
    fun getErrorMessage(throwable: Throwable?): String {
        return when (throwable?.message) {
            "HTTP 401 Unauthorized" -> "Sessão expirada. Faça login novamente."
            "HTTP 400 Bad Request" -> "Dados inválidos. Verifique as informações."
            "HTTP 404 Not Found" -> "Recurso não encontrado."
            "HTTP 500 Internal Server Error" -> "Erro no servidor. Tente novamente mais tarde."
            "java.net.UnknownHostException",
            "java.net.ConnectException" -> "Sem conexão com a internet."
            "java.net.SocketTimeoutException" -> "Timeout na conexão. Tente novamente."
            else -> throwable?.message ?: "Erro desconhecido"
        }
    }

    /**
     * Converter timestamp em milissegundos para data formatada
     */
    fun timestampToDate(timestamp: Long): String {
        val data = Date(timestamp)
        val formato = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return formato.format(data)
    }

    /**
     * Obter data atual formatada
     */
    fun getCurrentDate(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return formato.format(Date())
    }

    /**
     * Obter data atual formatada para exibição
     */
    fun getCurrentDateFormatted(): String {
        val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formato.format(Date())
    }

    /**
     * Converter String de data para timestamp
     */
    fun dateStringToTimestamp(dateString: String, pattern: String = "yyyy-MM-dd"): Long {
        return try {
            val formato = SimpleDateFormat(pattern, Locale.getDefault())
            formato.parse(dateString)?.time ?: 0L
        } catch (e: Exception) {
            0L
        }
    }

    /**
     * Verificar se string é um número válido
     */
    fun isValidNumber(value: String): Boolean {
        return try {
            value.toDouble()
            true
        } catch (e: NumberFormatException) {
            false
        }
    }

    /**
     * Truncar texto com reticências
     */
    fun truncateText(text: String, maxLength: Int): String {
        return if (text.length <= maxLength) {
            text
        } else {
            "${text.substring(0, maxLength - 3)}..."
        }
    }

    /**
     * Capitalizar primeira letra de cada palavra
     */
    fun capitalizeWords(text: String): String {
        return text.split(" ").joinToString(" ") { palavra ->
            if (palavra.isNotEmpty()) {
                palavra.first().uppercaseChar() + palavra.drop(1).lowercase()
            } else {
                palavra
            }
        }
    }

    /**
     * Dialog de erro
     */
    @Composable
    fun ErrorDialog(
        title: String = "Erro",
        message: String,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }

    /**
     * Dialog de sucesso
     */
    @Composable
    fun SuccessDialog(
        title: String = "Sucesso",
        message: String,
        onDismiss: () -> Unit
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )
    }

    /**
     * Dialog de confirmação
     */
    @Composable
    fun ConfirmationDialog(
        title: String,
        message: String,
        onConfirm: () -> Unit,
        onDismiss: () -> Unit,
        confirmText: String = "Sim",
        dismissText: String = "Não"
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(text = title) },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(confirmText)
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(dismissText)
                }
            }
        )
    }
}
