package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de Estabelecimento com endereço completo
 * Conforme documentação da API de Estabelecimentos
 *
 * Nota: A classe base Estabelecimento já existe em model/Estabelecimento.kt
 * Este modelo estende com informações de endereço para a API de localização
 */
data class EstabelecimentoComEndereco(
    @SerializedName("id_estabelecimento")
    val id_estabelecimento: Int,

    @SerializedName("id_usuario")
    val id_usuario: Int,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("cnpj")
    val cnpj: String? = null,

    @SerializedName("telefone")
    val telefone: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("descricao")
    val descricao: String? = null,

    @SerializedName("foto_url")
    val foto_url: String? = null,

    @SerializedName("data_criacao")
    val data_criacao: String? = null,

    @SerializedName("ativo")
    val ativo: Boolean = true,

    // Dados do endereço
    @SerializedName("cep")
    val cep: String? = null,

    @SerializedName("logradouro")
    val logradouro: String? = null,

    @SerializedName("numero")
    val numero: String? = null,

    @SerializedName("complemento")
    val complemento: String? = null,

    @SerializedName("bairro")
    val bairro: String? = null,

    @SerializedName("cidade")
    val cidade: String? = null,

    @SerializedName("estado")
    val estado: String? = null,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null
)

/**
 * Response da API de Estabelecimentos
 * Aceita 'data' como List<T> já que a API retorna array
 */
data class EstabelecimentoApiResponse<T>(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val status_code: Int,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("estabelecimento")
    val estabelecimento: T? = null,

    @SerializedName("estabelecimentos")
    val estabelecimentos: List<T>? = null,

    @SerializedName("data")
    val data: List<T>? = null,  // CORRIGIDO: de T? para List<T>?

    @SerializedName("quantidade")
    val quantidade: Int? = null,

    @SerializedName("raio_km")
    val raio_km: Double? = null
)

