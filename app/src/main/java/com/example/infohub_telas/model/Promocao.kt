package com.example.infohub_telas.model

import java.util.Date

data class Promocao(
    val id: String? = null,
    val nomeProduto: String,
    val precoPromocional: String,
    val dataInicio: Date,
    val dataTermino: Date,
    val descricao: String,
    val categoria: String,
    val imagemUrl: String? = null // Campo para a URL da imagem
)
