package com.example.infohub_telas.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de resposta da API de Produtos
 * Corresponde exatamente ao formato retornado pelo backend
 */
data class ProdutoApiResponse(
    @SerializedName("status")
    val status: Boolean,

    @SerializedName("status_code")
    val statusCode: Int,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("produto")
    val produto: ProdutoApi? = null,

    @SerializedName("produtos")
    val produtos: List<ProdutoApi>? = null,

    @SerializedName("id")
    val id: Int? = null
)

/**
 * Modelo de Produto retornado pela API
 * Corresponde exatamente aos campos do banco de dados
 */
data class ProdutoApi(
    @SerializedName("id_produto")
    val idProduto: Int? = null,

    @SerializedName("nome")
    val nome: String,

    @SerializedName("descricao")
    val descricao: String? = null,

    @SerializedName("id_categoria")
    val idCategoria: Int,

    @SerializedName("id_estabelecimento")
    val idEstabelecimento: Int,

    @SerializedName("imagem")
    val imagem: String? = null,

    @SerializedName("categoria")
    val categoria: String? = null,

    @SerializedName("preco")
    val preco: Double,

    @SerializedName("preco_promocional")
    val precoPromocional: Double? = null,

    @SerializedName("data_inicio")
    val dataInicio: String? = null,

    @SerializedName("data_fim")
    val dataFim: String? = null
)

/**
 * Função de extensão para converter ProdutoApi para o modelo interno Produto
 */
fun ProdutoApi.toInternalModel(): com.example.infohub_telas.model.Produto {
    return com.example.infohub_telas.model.Produto(
        id = this.idProduto,
        nome = this.nome,
        descricao = this.descricao ?: "",
        idCategoria = this.idCategoria,
        idEstabelecimento = this.idEstabelecimento,
        preco = this.preco,
        imagem = this.imagem,
        promocao = if (this.precoPromocional != null && this.dataInicio != null && this.dataFim != null) {
            PromocaoProdutoRequest(
                precoPromocional = this.precoPromocional,
                dataInicio = this.dataInicio,
                dataFim = this.dataFim
            )
        } else null
    )
}

