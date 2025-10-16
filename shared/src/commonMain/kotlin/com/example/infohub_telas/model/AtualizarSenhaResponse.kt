package com.example.infohub_telas.model

import kotlinx.serialization.Serializable

@Serializable
data class AtualizarSenhaResponse(
    val status: Boolean,
    val status_code: Int, // O original era String, ajustei para Int
    val message: String
)
