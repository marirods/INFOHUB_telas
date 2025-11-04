package com.example.infohub_telas.model

// Modelo para Empresa (usado nas telas de perfil e configuração)
data class Empresa(
    val id: String = "",
    val nome: String = "",
    val cnpj: String = "",
    val email: String = "",
    val telefone: String = "",
    val endereco: String = "",
    val setor: String = "",
    val categoria: String = "",
    val descricao: String = "",
    val logoUrl: String = "" // URL da logo
)
