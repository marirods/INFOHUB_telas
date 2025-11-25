package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// Base API response models are defined in BaseModels.kt

data class ErrorResponse(
    @SerializedName("status") val status: Boolean = false,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("error") val error: String? = null
)

// Authentication models are defined in AuthModels.kt

// ===============================
// ESTABELECIMENTO
// ===============================

data class Estabelecimento(
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int? = null,
    @SerializedName("nome") val nome: String,
    @SerializedName("cnpj") val cnpj: String,
    @SerializedName("telefone") val telefone: String? = null,
    @SerializedName("data_cadastro") val dataCadastro: String? = null
)

data class EstabelecimentoRequest(
    @SerializedName("nome") val nome: String,
    @SerializedName("cnpj") val cnpj: String,
    @SerializedName("telefone") val telefone: String? = null
)

data class EstabelecimentoResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: Int? = null
)

data class EstabelecimentosListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("estabelecimentos") val estabelecimentos: List<Estabelecimento>,
    @SerializedName("message") val message: String? = null
)

data class EstabelecimentoDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("estabelecimento") val estabelecimento: Estabelecimento,
    @SerializedName("message") val message: String? = null
)

// ===============================
// ENDEREÇO DE USUÁRIO
// ===============================

data class EnderecoUsuario(
    @SerializedName("id_endereco") val idEndereco: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("cep") val cep: String,
    @SerializedName("logradouro") val logradouro: String? = null,
    @SerializedName("numero") val numero: String? = null,
    @SerializedName("complemento") val complemento: String? = null,
    @SerializedName("bairro") val bairro: String? = null,
    @SerializedName("cidade") val cidade: String? = null,
    @SerializedName("estado") val estado: String? = null,
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null,
    @SerializedName("nome_usuario") val nomeUsuario: String? = null,
    @SerializedName("email_usuario") val emailUsuario: String? = null
)

data class EnderecoUsuarioRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("cep") val cep: String,
    @SerializedName("logradouro") val logradouro: String? = null,
    @SerializedName("numero") val numero: String? = null,
    @SerializedName("complemento") val complemento: String? = null,
    @SerializedName("bairro") val bairro: String? = null,
    @SerializedName("cidade") val cidade: String? = null,
    @SerializedName("estado") val estado: String? = null,
    @SerializedName("latitude") val latitude: Double? = null,
    @SerializedName("longitude") val longitude: Double? = null
)

data class EnderecoUsuarioResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: Int? = null
)

data class EnderecosUsuarioListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("enderecos") val enderecos: List<EnderecoUsuario>,
    @SerializedName("message") val message: String? = null
)

data class EnderecoUsuarioDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("endereco") val endereco: EnderecoUsuario,
    @SerializedName("message") val message: String? = null
)
