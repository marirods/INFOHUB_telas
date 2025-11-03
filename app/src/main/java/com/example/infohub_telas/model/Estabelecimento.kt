package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Model que representa um Estabelecimento
 * Usado para cadastro, edição e exibição de dados
 */
data class Estabelecimento(
    @SerializedName("id")
    val id: Int? = null, // ID gerado pelo backend (opcional no cadastro)

    @SerializedName("nome")
    val nome: String,

    @SerializedName("cnpj")
    val cnpj: String,

    @SerializedName("endereco")
    val endereco: String,

    @SerializedName("telefone")
    val telefone: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("categoria")
    val categoria: String,

    @SerializedName("dataCadastro")
    val dataCadastro: String? = null, // Data de cadastro (gerada pelo backend)

    @SerializedName("ativo")
    val ativo: Boolean = true // Se o estabelecimento está ativo
)