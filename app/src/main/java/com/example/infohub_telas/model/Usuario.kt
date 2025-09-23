package com.example.infohub_telas.model

data class Usuario(
    val nome: String,
    val email: String,
    val senha_hash: String,
    val perfil: String,
    val cpf: String?,
    val cnpj: String?,
    val data_nascimento: String
)

