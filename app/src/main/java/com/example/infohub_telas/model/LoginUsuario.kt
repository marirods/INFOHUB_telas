package com.example.infohub_telas.model

import java.util.Date

data class LoginUsuario(
    val email: String,
    val senha_hash: String,
)