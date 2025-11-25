package com.example.infohub_telas.network.models

import com.google.gson.annotations.SerializedName

// ===============================
// REDE SOCIAL - POSTS
// ===============================

data class Post(
    @SerializedName("id_post") val idPost: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("conteudo") val conteudo: String,
    @SerializedName("id_produto") val idProduto: Int? = null,
    @SerializedName("curtidas_count") val curtidasCount: Int? = null,
    @SerializedName("comentarios_count") val comentariosCount: Int? = null,
    @SerializedName("data_criacao") val dataCriacao: String? = null,
    @SerializedName("usuario") val usuario: UsuarioSimples? = null
)

data class UsuarioSimples(
    @SerializedName("nome") val nome: String
)

data class CriarPostRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("conteudo") val conteudo: String,
    @SerializedName("id_produto") val idProduto: Int? = null
)

data class PostsListResponse(
    @SerializedName("posts") val posts: List<Post>
)

data class PostDetailResponse(
    @SerializedName("post") val post: Post
)

data class CurtirPostRequest(
    @SerializedName("id_usuario") val idUsuario: Int
)

data class ComentarPostRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("conteudo") val conteudo: String
)

data class Comentario(
    @SerializedName("id_comentario") val idComentario: Int? = null,
    @SerializedName("id_post") val idPost: Int,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("conteudo") val conteudo: String,
    @SerializedName("data_criacao") val dataCriacao: String? = null,
    @SerializedName("usuario") val usuario: UsuarioSimples? = null
)

data class ComentariosListResponse(
    @SerializedName("comentarios") val comentarios: List<Comentario>
)

// ===============================
// AVALIAÇÕES
// ===============================

data class Avaliacao(
    @SerializedName("id_avaliacao") val idAvaliacao: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("nota") val nota: Int,
    @SerializedName("comentario") val comentario: String? = null,
    @SerializedName("data_avaliacao") val dataAvaliacao: String? = null,
    @SerializedName("usuario") val usuario: UsuarioSimples? = null
)

data class CriarAvaliacaoRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("nota") val nota: Int,
    @SerializedName("comentario") val comentario: String? = null
)

data class AvaliacoesListResponse(
    @SerializedName("avaliacoes") val avaliacoes: List<Avaliacao>
)

data class ProdutosMaisAvaliadosResponse(
    @SerializedName("produtos") val produtos: List<ProdutoComAvaliacao>
)

data class ProdutoComAvaliacao(
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("preco") val preco: Double,
    @SerializedName("nota_media") val notaMedia: Double,
    @SerializedName("total_avaliacoes") val totalAvaliacoes: Int
)

// ===============================
// FAVORITOS
// ===============================

data class Favorito(
    @SerializedName("id_favorito") val idFavorito: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("data_adicao") val dataAdicao: String? = null,
    @SerializedName("produto") val produto: Produto? = null
)

data class AdicionarFavoritoRequest(
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("id_produto") val idProduto: Int,
    @SerializedName("id_estabelecimento") val idEstabelecimento: Int
)

data class FavoritosListResponse(
    @SerializedName("favoritos") val favoritos: List<Favorito>
)

// ===============================
// NOTIFICAÇÕES
// ===============================

data class Notificacao(
    @SerializedName("id_notificacao") val idNotificacao: Int? = null,
    @SerializedName("id_usuario") val idUsuario: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("mensagem") val mensagem: String,
    @SerializedName("tipo") val tipo: String,
    @SerializedName("lida") val lida: Boolean = false,
    @SerializedName("data_criacao") val dataCriacao: String? = null,
    @SerializedName("referencia_id") val referenciaId: Int? = null
)

data class NotificacoesListResponse(
    @SerializedName("notificacoes") val notificacoes: List<Notificacao>
)
