package com.example.infohub_telas.model

import kotlinx.serialization.Serializable

@Serializable
data class AtualizarSenhaRequest(
    val codigo: String,
    val novaSenha: String
)
