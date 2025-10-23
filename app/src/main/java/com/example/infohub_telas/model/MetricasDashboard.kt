package com.example.infohub_telas.model

// Modelo para métricas do Dashboard
data class MetricasDashboard(
    val totalVendasMes: Double,
    val promocoesAtivas: Int,
    val produtosCadastrados: Int,
    val avaliacaoMedia: Float
)