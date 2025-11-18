package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Model que representa um Estabelecimento conforme API
 * Campos obrigatórios: nome, cnpj
 * Campo opcional: telefone
 */
data class Estabelecimento(
    @SerializedName("id")
    val id: Int? = null, // ID gerado pelo backend (opcional no cadastro)

    @SerializedName("nome")
    val nome: String,

    @SerializedName("cnpj")
    val cnpj: String,

    @SerializedName("telefone")
    val telefone: String? = null,

    @SerializedName("dataCadastro")
    val dataCadastro: String? = null, // Data de cadastro (gerada pelo backend)

    @SerializedName("ativo")
    val ativo: Boolean = true // Se o estabelecimento está ativo
)