package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelos para o sistema de avaliações baseados na documentação da API
 */

// Modelo de Avaliação
data class Avaliacao(
    @SerializedName("id_avaliacao")
    val idAvaliacao: Int,
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("id_produto")
    val idProduto: Int?,
    @SerializedName("nota")
    val nota: Int,
    @SerializedName("comentario")
    val comentario: String,
    @SerializedName("data_avaliacao")
    val dataAvaliacao: String,
    @SerializedName("nome_usuario")
    val nomeUsuario: String? = null,
    @SerializedName("curtidas")
    val curtidas: Int = 0,
    @SerializedName("curtido_por_usuario")
    val curtidoPorUsuario: Boolean = false
)

// Request para criar avaliação
data class CriarAvaliacaoRequest(
    @SerializedName("id_usuario")
    val idUsuario: Int,
    @SerializedName("id_produto")
    val idProduto: Int? = null,
    @SerializedName("nota")
    val nota: Int,
    @SerializedName("comentario")
    val comentario: String
)

// Response de lista de avaliações
data class AvaliacoesResponse(
    @SerializedName("avaliacoes")
    val avaliacoes: List<Avaliacao>
)

// Response de criação de avaliação
data class CriarAvaliacaoResponse(
    @SerializedName("sucesso")
    val sucesso: Boolean,
    @SerializedName("mensagem")
    val mensagem: String,
    @SerializedName("avaliacao")
    val avaliacao: Avaliacao?
)

