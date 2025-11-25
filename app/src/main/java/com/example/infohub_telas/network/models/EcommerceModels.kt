package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// ===============================
// CARRINHO
// ===============================

data class CarrinhoItem(
    @SerializedName("id_carrinho") val idCarrinho: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("quantidade") val quantidade: Int,
    @SerializedName("data_adicao") val dataAdicao: String? = null,
    @SerializedName("produto") val produto: Produto? = null,
    @SerializedName("valor_total") val valorTotal: Double? = null
)

data class AdicionarCarrinhoRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("quantidade") val quantidade: Int
)

data class CarrinhoResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("carrinho") val carrinho: List<CarrinhoItem>,
    @SerializedName("valor_total") val valorTotal: Double
)

data class AtualizarQuantidadeRequest(
    @SerializedName("quantidade") val quantidade: Int
)

// ===============================
// PEDIDOS
// ===============================

data class Pedido(
    @SerializedName("id_pedido") val idPedido: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("status") val status: StatusPedido,
    @SerializedName("valor_total") val valorTotal: Double,
    @SerializedName("data_pedido") val dataPedido: String? = null,
    @SerializedName("metodo_pagamento") val metodoPagamento: String,
    @SerializedName("itens") val itens: List<ItemPedido>? = null
)

enum class StatusPedido(val value: String) {
    @SerializedName("pendente") PENDENTE("pendente"),
    @SerializedName("confirmado") CONFIRMADO("confirmado"),
    @SerializedName("em_preparacao") EM_PREPARACAO("em_preparacao"),
    @SerializedName("enviado") ENVIADO("enviado"),
    @SerializedName("entregue") ENTREGUE("entregue"),
    @SerializedName("cancelado") CANCELADO("cancelado")
}

enum class MetodoPagamento(val value: String) {
    @SerializedName("cartao_credito") CARTAO_CREDITO("cartao_credito"),
    @SerializedName("cartao_debito") CARTAO_DEBITO("cartao_debito"),
    @SerializedName("pix") PIX("pix"),
    @SerializedName("boleto") BOLETO("boleto")
}

data class ItemPedido(
    @SerializedName("id_item") val idItem: Int? = null,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("quantidade") val quantidade: Int,
    @SerializedName("preco_unitario") val precoUnitario: Double,
    @SerializedName("valor_total") val valorTotal: Double,
    @SerializedName("produto") val produto: Produto? = null
)

data class CriarPedidoRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_endereco_entrega") val idEnderecoEntrega: Int,
    @SerializedName("metodo_pagamento") val metodoPagamento: String
)

data class PedidoResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("pedido") val pedido: Pedido
)

data class PedidosListResponse(
    @SerializedName("pedidos") val pedidos: List<Pedido>
)

// ===============================
// PROMOÇÕES
// ===============================

data class Promocao(
    @SerializedName("id_promocao") val idPromocao: Int? = null,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("tipo_desconto") val tipoDesconto: TipoDesconto,
    @SerializedName("valor_desconto") val valorDesconto: Double,
    @SerializedName("data_inicio") val dataInicio: String,
    @SerializedName("data_fim") val dataFim: String,
    @SerializedName("ativa") val ativa: Boolean? = null,
    @SerializedName("produto") val produto: Produto? = null,
    @SerializedName("preco_promocional") val precoPromocional: Double? = null
)

enum class TipoDesconto(val value: String) {
    @SerializedName("percentual") PERCENTUAL("percentual"),
    @SerializedName("valor_fixo") VALOR_FIXO("valor_fixo")
}

data class CriarPromocaoRequest(
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int,
    @SerializedName("tipo_desconto") val tipoDesconto: String,
    @SerializedName("valor_desconto") val valorDesconto: Double,
    @SerializedName("data_inicio") val dataInicio: String,
    @SerializedName("data_fim") val dataFim: String
)

data class PromocoesListResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("promocoes") val promocoes: List<Promocao>
)

data class PromocaoDetailResponse(
    @SerializedName("status") val status: Boolean,
    @SerializedName("promocao") val promocao: Promocao
)
