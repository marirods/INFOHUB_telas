package com.example.infohub_telas.model

import kotlinx.serialization.Serializable

@Serializable
data class RecuperarSenhaRequest(
    val email: String
)
