package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// ===============================
// CATEGORIA
// ===============================

data class Categoria(
    @SerializedName("id_categoria") val idCategoria: Int? = null,
    @SerializedName("nome") val nome: String,
    @SerializedName("total_produtos") val totalProdutos: Int? = null
)

data class CategoriaRequest(
    @SerializedName("nome") val nome: String
)

data class CategoriaResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: Int? = null
)

data class CategoriasListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("categorias") val categorias: List<Categoria>,
    @SerializedName("message") val message: String? = null
)

data class CategoriaDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("categoria") val categoria: Categoria,
    @SerializedName("message") val message: String? = null
)

data class CategoriaComProdutos(
    @SerializedName("id_categoria") val idCategoria: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("total_produtos") val totalProdutos: Int,
    @SerializedName("produtos") val produtos: List<ProdutoSimples>
)

data class ProdutoSimples(
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("preco") val preco: Double,
    @SerializedName("preco_promocional") val precoPromocional: Double? = null,
    @SerializedName("data_inicio_promocao") val dataInicioPromocao: String? = null,
    @SerializedName("data_fim_promocao") val dataFimPromocao: String? = null
)

data class CategoriasComProdutosResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("categorias") val categorias: List<CategoriaComProdutos>,
    @SerializedName("message") val message: String? = null
)

data class CategoriaComProdutosDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("categoria") val categoria: CategoriaComProdutos,
    @SerializedName("message") val message: String? = null
)

// ===============================
// PRODUTO
// ===============================

data class Produto(
    @SerializedName("id_produto") val idProduto: Int? = null,
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("id_categoria") val idCategoria: Int,
    @SerializedName("categoria") val categoria: String? = null,
    @SerializedName("preco") val preco: Double,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("preco_promocional") val precoPromocional: Double? = null,
    @SerializedName("data_inicio") val dataInicio: String? = null,
    @SerializedName("data_fim") val dataFim: String? = null
)

data class ProdutoRequest(
    @SerializedName("nome") val nome: String,
    @SerializedName("descricao") val descricao: String? = null,
    @SerializedName("id_categoria") val idCategoria: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("preco") val preco: Double,
    @SerializedName("promocao") val promocao: PromocaoRequest? = null
)

data class PromocaoRequest(
    @SerializedName("preco_promocional") val precoPromocional: Double,
    @SerializedName("data_inicio") val dataInicio: String,
    @SerializedName("data_fim") val dataFim: String
)

data class ProdutoResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("message") val message: String,
    @SerializedName("id") val id: Int? = null
)

data class ProdutosListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("produtos") val produtos: List<Produto>,
    @SerializedName("message") val message: String? = null
)

data class ProdutoDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("produto") val produto: Produto,
    @SerializedName("message") val message: String? = null
)
