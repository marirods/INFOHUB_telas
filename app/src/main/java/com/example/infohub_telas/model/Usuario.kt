package com.example.infohub_telas.model

import java.util.Date

data class Usuario(
    val nome: String,
    val email: String,
    val senha_hash: String,
    val perfil: String,
    val cpf: String,
    val cnpj: String,
    val data_nascimento: Date
)
