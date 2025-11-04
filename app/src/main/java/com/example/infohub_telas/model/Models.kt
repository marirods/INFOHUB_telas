package com.example.infohub_telas.model

import java.time.LocalDate
import java.util.Date

// Dashboard e Promoções
data class DashboardData(
    val totalVendasMes: Double,
    val totalPromocoesAtivas: Int,
    val totalProdutos: Int,
    val avaliacaoMedia: Float,
    val ultimasPromocoes: List<Promocao>
) {
    fun getFormattedVendas(): String = "R$ %.2f".format(totalVendasMes)
    fun getFormattedAvaliacao(): String = "%.1f".format(avaliacaoMedia)

    companion object {
        fun getPreview() = DashboardData(
            totalVendasMes = 15420.50,
            totalPromocoesAtivas = 8,
            totalProdutos = 143,
            avaliacaoMedia = 4.5f,
            ultimasPromocoes = listOf(
                Promocao(
                    nome = "Promoção de Verão",
                    data = LocalDate.now(),
                    status = StatusPromocao.ATIVA,
                    desconto = 20
                ),
                Promocao(
                    nome = "Black Friday",
                    data = LocalDate.now().plusDays(30),
                    status = StatusPromocao.AGENDADA,
                    desconto = 50
                ),
                Promocao(
                    nome = "Liquidação de Inverno",
                    data = LocalDate.now().minusDays(15),
                    status = StatusPromocao.ENCERRADA,
                    desconto = 30
                )
            )
        )
    }
}

enum class StatusPromocao {
    ATIVA,
    AGENDADA,
    ENCERRADA
}

data class Promocao(
    val nome: String,
    val data: LocalDate,
    val status: StatusPromocao,
    val desconto: Int
)

// A classe PromocaoProduto foi movida para seu próprio arquivo: PromocaoProduto.kt
