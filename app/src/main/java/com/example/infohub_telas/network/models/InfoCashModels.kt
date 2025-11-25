package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// Transação InfoCash
data class TransacaoInfoCash(
    @SerializedName("id_transacao") val idTransacao: Int,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("tipo_acao") val tipoAcao: String,
    @SerializedName("pontos") val pontos: Int,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("data_transacao") val dataTransacao: String,
    @SerializedName("referencia_id") val referenciaId: Int?
)

data class SaldoInfoCash(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("saldo_total") val saldoTotal: Int,
    @SerializedName("ultima_atualizacao") val ultimaAtualizacao: String? // ✅ Agora pode ser null
) {
    /**
     * Calcula o nível atual baseado no saldo total
     * Exemplo: 0-999 = Nível 1, 1000-1999 = Nível 2, etc.
     */
    fun getNivelAtual(): Int {
        return (saldoTotal / PONTOS_POR_NIVEL) + 1
    }

    /**
     * Calcula o progresso atual para o próximo nível (0.0 a 1.0)
     */
    fun getProgressoAtual(): Float {
        val pontosNoNivelAtual = saldoTotal % PONTOS_POR_NIVEL
        return pontosNoNivelAtual.toFloat() / PONTOS_POR_NIVEL.toFloat()
    }

    /**
     * Calcula quantos pontos faltam para o próximo nível
     */
    fun getPontosParaProximoNivel(): Int {
        val pontosNoNivelAtual = saldoTotal % PONTOS_POR_NIVEL
        return PONTOS_POR_NIVEL - pontosNoNivelAtual
    }

    companion object {
        private const val PONTOS_POR_NIVEL = 1000 // 1000 pontos por nível
    }
}

data class ResumoAcaoInfoCash(
    @SerializedName("tipo_acao") val tipoAcao: String,
    @SerializedName("total_transacoes") val totalTransacoes: Int,
    @SerializedName("total_pontos") val totalPontos: Int
)

data class RankingInfoCash(
    @SerializedName("posicao") val posicao: Int,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("nome_usuario") val nomeUsuario: String,
    @SerializedName("email_usuario") val emailUsuario: String,
    @SerializedName("saldo_total") val saldoTotal: Int
)

data class PerfilInfoCash(
    @SerializedName("saldo") val saldo: SaldoInfoCash,
    @SerializedName("resumo") val resumo: List<ResumoAcaoInfoCash>
)

data class EstatisticasInfoCash(
    @SerializedName("total_usuarios_ativos") val totalUsuariosAtivos: Int,
    @SerializedName("total_pontos_distribuidos") val totalPontosDistribuidos: Int,
    @SerializedName("total_transacoes") val totalTransacoes: Int,
    @SerializedName("media_pontos_usuario") val mediaPontosUsuario: Double,
    @SerializedName("transacoes_por_tipo") val transacoesPorTipo: List<TransacaoPorTipo>
)

data class TransacaoPorTipo(
    @SerializedName("tipo_acao") val tipoAcao: String,
    @SerializedName("count") val count: Int
)

// Requests

data class ConcederPontosRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("tipo_acao") val tipoAcao: String,
    @SerializedName("pontos") val pontos: Int,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("referencia_id") val referenciaId: Int? = null
)

// Responses simples envelopes

data class SaldoInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: SaldoInfoCash
)

data class HistoricoInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<TransacaoInfoCash>
)

data class ResumoInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<ResumoAcaoInfoCash>
)

data class PerfilInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: PerfilInfoCash
)

data class RankingInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: List<RankingInfoCash>
)

data class EstatisticasInfoCashResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: EstatisticasInfoCash
)

data class ConcederPontosData(
    @SerializedName("id_transacao") val idTransacao: Int
)

data class ConcederPontosResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ConcederPontosData
)
