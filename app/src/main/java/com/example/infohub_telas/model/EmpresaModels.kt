package com.example.infohub_telas.model

import java.time.LocalDate
import java.util.*

data class EmpresaDetalhada(
    val id: Int,
    val nome: String,
    val cnpj: String,
    val email: String,
    val telefone: String,
    val endereco: String,
    val status: StatusEmpresa,
    val dataCadastro: LocalDate,
    val metricas: MetricasEmpresa
)

data class MetricasEmpresa(
    val faturamentoTotal: Double,
    val totalVendas: Int,
    val totalPromocoes: Int,
    val avaliacaoMedia: Float,
    val clientesAtivos: Int,
    val historicoFaturamento: List<FaturamentoMensal>
)

data class FaturamentoMensal(
    val mes: LocalDate,
    val valor: Double,
    val quantidadeVendas: Int
)

enum class StatusEmpresa {
    ATIVA,
    INATIVA,
    PENDENTE
}
