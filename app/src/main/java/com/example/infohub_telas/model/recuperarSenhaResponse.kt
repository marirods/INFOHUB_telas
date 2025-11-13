package com.example.infohub_telas.model

// Mantido por compatibilidade; usar RecuperarSenhaResponse
@Deprecated("Use RecuperarSenhaResponse")
typealias recuperarSenhaResponse = RecuperarSenhaResponse

data class RecuperarSenhaResponse(
    val mensagem: String
)
