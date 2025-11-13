package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelos para o sistema InfoCash baseados na API documentada
 */

// ===============================
// TRANSAÇÕES E HISTÓRICO
// ===============================

data class TransacaoInfoCash(
    @SerializedName("id_transacao")
    val idTransacao: Int,
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("tipo_acao")
    val tipoAcao: String,
    @SerializedName("pontos")
    val pontos: Int,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("data_transacao")
    val dataTransacao: String,
    @SerializedName("referencia_id")
    val referenciaId: Int?
)

data class HistoricoInfoCashResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<TransacaoInfoCash>
)

// ===============================
// RESUMO POR TIPO DE AÇÃO
// ===============================

data class ResumoAcaoInfoCash(
    @SerializedName("tipo_acao")
    val tipoAcao: String,
    @SerializedName("total_transacoes")
    val totalTransacoes: Int,
    @SerializedName("total_pontos")
    val totalPontos: Int
)

data class ResumoInfoCashResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ResumoAcaoInfoCash>
)

// ===============================
// RANKING
// ===============================

data class RankingInfoCash(
    @SerializedName("posicao")
    val posicao: Int,
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("nome_usuario")
    val nomeUsuario: String,
    @SerializedName("email_usuario")
    val emailUsuario: String,
    @SerializedName("saldo_total")
    val saldoTotal: Int
)

data class RankingInfoCashResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<RankingInfoCash>
)

// ===============================
// PERFIL COMPLETO
// ===============================

data class PerfilInfoCash(
    @SerializedName("saldo")
    val saldo: SaldoInfoCashResponse,
    @SerializedName("resumo")
    val resumo: List<ResumoAcaoInfoCash>
)

data class PerfilInfoCashResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: PerfilInfoCash
)

// ===============================
// ESTATÍSTICAS
// ===============================

data class TransacaoPorTipo(
    @SerializedName("tipo_acao")
    val tipoAcao: String,
    @SerializedName("count")
    val count: Int
)

data class EstatisticasInfoCash(
    @SerializedName("total_usuarios_ativos")
    val totalUsuariosAtivos: Int,
    @SerializedName("total_pontos_distribuidos")
    val totalPontosDistribuidos: Int,
    @SerializedName("total_transacoes")
    val totalTransacoes: Int,
    @SerializedName("media_pontos_usuario")
    val mediaPontosUsuario: Double,
    @SerializedName("transacoes_por_tipo")
    val transacoesPorTipo: List<TransacaoPorTipo>
)

data class EstatisticasInfoCashResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: EstatisticasInfoCash
)

// ===============================
// REQUESTS
// ===============================

data class ConcederPontosRequest(
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("tipo_acao")
    val tipoAcao: String,
    @SerializedName("pontos")
    val pontos: Int,
    @SerializedName("descricao")
    val descricao: String,
    @SerializedName("referencia_id")
    val referenciaId: Int? = null
)

// ===============================
// RESPONSES ESPECIAIS
// ===============================

data class ConcederPontosData(
    @SerializedName("id_transacao")
    val idTransacao: Int
)

data class ConcederPontosResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ConcederPontosData
)
