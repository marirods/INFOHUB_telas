package com.example.infohub_telas.model

import kotlinx.serialization.Serializable

@Serializable
data class ValidarCodigoResponse(
    val status: Boolean,
    val status_code: Int, // Mantido como Int para consistência, mas o original era String. Ajuste se necessário.
    val id_usuario: Int,
    val message: String
)
