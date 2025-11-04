package com.example.infohub_telas.model

import java.util.Date

enum class TipoRelatorio {
    VENDAS,
    FINANCEIRO,
    ESTOQUE,
    PROMOCOES,
    USUARIOS,
    PRODUTOS,
    DESEMPENHO,
    MARKETING,
    OPERACIONAL
}

enum class PeriodoRelatorio {
    DIARIO,
    SEMANAL,
    MENSAL,
    TRIMESTRAL,
    ANUAL,
    PERSONALIZADO
}

data class RelatorioMetrics(
    val totalEmpresas: Int = 0,
    val empresasAtivas: Int = 0,
    val totalPromocoesAtivas: Int = 0,
    val faturamentoTotal: Double = 0.0,
    val usuariosAtivos: Int = 0,
    val taxaCrescimento: Double = 0.0,
    val desempenhoMensal: List<Double> = emptyList(),
    val distribuicaoStatus: Map<String, Int> = emptyMap()
)

data class Relatorio(
    val id: String,
    val nome: String,
    val descricao: String,
    val dataGeracao: Date,
    val tipo: TipoRelatorio,
    val periodo: PeriodoRelatorio,
    val autor: String,
    val empresaId: String? = null,
    val metricas: Map<String, Double> = emptyMap(),
    val tags: List<String> = emptyList()
)

