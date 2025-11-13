package com.example.infohub_telas.model

data class ValidarCodigoResponse(
    val status: Boolean,
    val status_code: Int,
    val id_usuario: Int,
    val message: String
)
