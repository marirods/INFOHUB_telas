package com.example.infohub_telas.model

import java.util.Date

data class PromocaoProduto(
    val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val preco: Double = 0.0,
    val precoPromocional: String,
    val quantidade: Int = 0,
    val categoria: String = "",
    val imagemUrl: String = "",
    val dataInicio: Date = Date(),
    val dataTermino: Date = Date(),
    val status: StatusPromocao = StatusPromocao.ATIVA
)
