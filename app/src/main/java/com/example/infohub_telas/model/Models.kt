package com.example.infohub_telas.model

import java.time.LocalDate
import java.util.Date

enum class StatusPromocao {
    ATIVA,
    AGENDADA,
    ENCERRADA
}

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

data class Promocao(
    val nome: String,
    val data: LocalDate,
    val status: StatusPromocao,
    val desconto: Int
)

data class PromocaoProduto(
    val nomeProduto: String,
    val categoria: String,
    val precoPromocional: String,
    val dataInicio: Date,
    val dataTermino: Date,
    val descricao: String,
    val imagemUrl: String,
    val status: StatusPromocao = StatusPromocao.ATIVA
)
