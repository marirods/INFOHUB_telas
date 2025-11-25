package com.example.infohub_telas.model

import com.example.infohub_telas.network.models.SaldoInfoCash
import java.util.Locale

data class HubCoinData(
    val saldo: Double = 1285.0,
    val nivel: Int = 3,
    val progressoAtual: Float = 0.8f,
    val pontosParaProximoNivel: Int = 215,
    val proximoNivel: Int = 4
) {
    fun getSaldoComVirgula(): String =
        String.format(Locale.getDefault(), "%,.0f HC", saldo).replace(",", ".")

    fun getMensagemProximoNivel(): String =
        "Faltam $pontosParaProximoNivel HC para o próximo nível"

    fun getNivelAtual(): String = "Level $nivel"

    companion object {
        fun getDefault() = HubCoinData()

        // Conversão de SaldoInfoCash para HubCoinData (para compatibilidade)
        fun fromSaldoInfoCash(saldoInfoCash: SaldoInfoCash): HubCoinData {
            return HubCoinData(
                saldo = saldoInfoCash.saldoTotal.toDouble(),
                nivel = saldoInfoCash.getNivelAtual(),
                progressoAtual = saldoInfoCash.getProgressoAtual(),
                pontosParaProximoNivel = saldoInfoCash.getPontosParaProximoNivel(),
                proximoNivel = saldoInfoCash.getNivelAtual() + 1
            )
        }
    }
}
