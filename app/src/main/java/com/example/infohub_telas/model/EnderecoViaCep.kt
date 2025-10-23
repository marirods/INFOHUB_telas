package com.example.infohub_telas.model

data class EnderecoViaCep(
    val cep: String?,
    val logradouro: String?,
    val bairro: String?,
    val localidade: String?,
    val uf: String?,
    val erro: Boolean? = false  // 🔹 Adicione esta linha
)