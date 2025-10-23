package com.example.infohub_telas.model

data class Estabelecimento(
    val id: Int? = null,
    val nome: String,
    val cnpj: String,
    val endereco: String,
    val telefone: String,
    val email: String,
    val categoria: String
)
