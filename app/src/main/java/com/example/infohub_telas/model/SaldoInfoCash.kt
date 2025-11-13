package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Modelo de resposta da API SaldoInfoCash
 */
data class SaldoInfoCashResponse(
    @SerializedName("id_usuario")
    val idUsuario: Int,

    @SerializedName("saldo_total")
    val saldoTotal: Int,

    @SerializedName("ultima_atualizacao")
    val ultimaAtualizacao: String? // ✅ Agora pode ser null
)

/**
 * Modelo local com funcionalidades adicionais
 */
data class SaldoInfoCash(
    val idUsuario: Int,
    val saldoTotal: Int,
    val ultimaAtualizacao: String? // ✅ Agora pode ser null
) {
    fun getSaldoFormatado(): String = "$saldoTotal HC"

    fun getSaldoComVirgula(): String {
        return if (saldoTotal >= 1000) {
            String.format("%,.0f HC", saldoTotal.toDouble()).replace(",", ".")
        } else {
            "$saldoTotal HC"
        }
    }

    // Calcula progresso baseado no saldo (exemplo: a cada 500 HC = 1 nível)
    fun getNivelAtual(): Int = (saldoTotal / 500) + 1

    fun getProgressoAtual(): Float {
        val nivelAtual = getNivelAtual()
        val pontosPorNivel = 500
        val pontosNivelAtual = saldoTotal % pontosPorNivel
        return pontosNivelAtual.toFloat() / pontosPorNivel.toFloat()
    }

    fun getPontosParaProximoNivel(): Int {
        val pontosPorNivel = 500
        val pontosNivelAtual = saldoTotal % pontosPorNivel
        return pontosPorNivel - pontosNivelAtual
    }

    fun getNivelTexto(): String = "Level ${getNivelAtual()}"

    fun getMensagemProximoNivel(): String =
        "Faltam ${getPontosParaProximoNivel()} HC para o próximo nível"

    companion object {
        fun fromResponse(response: SaldoInfoCashResponse): SaldoInfoCash {
            return SaldoInfoCash(
                idUsuario = response.idUsuario,
                saldoTotal = response.saldoTotal,
                ultimaAtualizacao = response.ultimaAtualizacao
            )
        }
    }
}
