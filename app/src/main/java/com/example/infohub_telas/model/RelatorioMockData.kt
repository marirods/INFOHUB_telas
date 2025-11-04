package com.example.infohub_telas.model

import java.util.*

object RelatorioMockData {
    val sampleMetrics = RelatorioMetrics(
        totalEmpresas = 156,
        empresasAtivas = 132,
        totalPromocoesAtivas = 43,
        faturamentoTotal = 1567895.50,
        usuariosAtivos = 2453,
        taxaCrescimento = 15.7,
        desempenhoMensal = listOf(45000.0, 52000.0, 48000.0, 55000.0, 62000.0, 58000.0),
        distribuicaoStatus = mapOf(
            "Ativos" to 132,
            "Inativos" to 15,
            "Pendentes" to 9
        )
    )

    val sampleRelatorios = listOf(
        Relatorio(
            id = "1",
            nome = "Relatório de Vendas Mensal",
            descricao = "Análise detalhada das vendas do último mês",
            dataGeracao = Date(),
            tipo = TipoRelatorio.VENDAS,
            periodo = PeriodoRelatorio.MENSAL,
            autor = "Sistema",
            metricas = mapOf(
                "Crescimento" to 15.7,
                "Satisfação" to 4.5,
                "Conversão" to 68.3
            ),
            tags = listOf("Vendas", "Mensal", "KPIs")
        ),
        Relatorio(
            id = "2",
            nome = "Análise Financeira Q3",
            descricao = "Relatório financeiro do terceiro trimestre",
            dataGeracao = Date(),
            tipo = TipoRelatorio.FINANCEIRO,
            periodo = PeriodoRelatorio.TRIMESTRAL,
            autor = "Sistema",
            metricas = mapOf(
                "ROI" to 22.5,
                "Margem" to 35.8,
                "Custos" to -12.3
            ),
            tags = listOf("Financeiro", "Trimestral", "ROI")
        ),
        Relatorio(
            id = "3",
            nome = "Relatório de Usuários Ativos",
            descricao = "Análise do engajamento de usuários",
            dataGeracao = Date(),
            tipo = TipoRelatorio.USUARIOS,
            periodo = PeriodoRelatorio.MENSAL,
            autor = "Sistema",
            metricas = mapOf(
                "Ativos" to 2453.0,
                "Crescimento" to 8.5,
                "Retenção" to 92.3
            ),
            tags = listOf("Usuários", "Engajamento", "Retenção")
        ),
        Relatorio(
            id = "4",
            nome = "Inventário de Produtos",
            descricao = "Relatório completo do estoque atual",
            dataGeracao = Date(),
            tipo = TipoRelatorio.PRODUTOS,
            periodo = PeriodoRelatorio.DIARIO,
            autor = "Sistema",
            metricas = mapOf(
                "Total" to 1567.0,
                "Disponível" to 1243.0,
                "Rotatividade" to 4.2
            ),
            tags = listOf("Produtos", "Estoque", "Inventário")
        )
    )
}
