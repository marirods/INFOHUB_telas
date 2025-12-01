package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.model.Produto
import com.example.infohub_telas.utils.AzureBlobUtils
import com.example.infohub_telas.R
import com.example.infohub_telas.viewmodel.ProdutosEstabelecimentoViewModel
import com.example.infohub_telas.viewmodel.ProdutosEstabelecimentoUiState

/**
 * Tela que exibe os produtos de um estabelecimento específico
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaProdutosEstabelecimento(
    navController: NavController,
    idEstabelecimento: Int
) {
    val viewModel: ProdutosEstabelecimentoViewModel = viewModel()
    val uiState by viewModel.produtosState.collectAsState()
    val estabelecimento by viewModel.estabelecimento.collectAsState()

    // Carregar dados ao iniciar
    LaunchedEffect(idEstabelecimento) {
        viewModel.carregarDados(idEstabelecimento)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = estabelecimento?.nome ?: "Produtos",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF9A01B),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is ProdutosEstabelecimentoUiState.Loading -> {
                    LoadingContent()
                }
                is ProdutosEstabelecimentoUiState.Success -> {
                    if (state.produtos.isEmpty()) {
                        EmptyContent()
                    } else {
                        ProdutosGrid(
                            produtos = state.produtos,
                            onProdutoClick = { idProduto ->
                                navController.navigate("produto/$idProduto")
                            }
                        )
                    }
                }
                is ProdutosEstabelecimentoUiState.Error -> {
                    ErrorContent(
                        message = state.message,
                        onRetry = { viewModel.carregarDados(idEstabelecimento) }
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Color(0xFFF9A01B))
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Carregando produtos...",
                color = Color(0xFF666666),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Default.ShoppingBag,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
                tint = Color(0xFFCCCCCC)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nenhum produto encontrado",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF666666)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Este estabelecimento ainda não cadastrou produtos",
                fontSize = 14.sp,
                color = Color(0xFF999999),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Composable
private fun ErrorContent(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFFEBEE)
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Warning,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Erro ao carregar produtos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF9A01B)
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tentar novamente")
                }
            }
        }
    }
}

@Composable
private fun ProdutosGrid(
    produtos: List<Produto>,
    onProdutoClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(produtos) { produto ->
            ProdutoCard(
                produto = produto,
                onClick = { onProdutoClick(produto.id ?: 0) }
            )
        }
    }
}

@Composable
private fun ProdutoCard(
    produto: Produto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagem do produto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(Color(0xFFF5F5F5))
            ) {
                if (!produto.imagem.isNullOrBlank()) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(AzureBlobUtils.getImageUrl(produto.imagem))
                            .crossfade(true)
                            .build(),
                        contentDescription = produto.nome,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = androidx.compose.ui.res.painterResource(R.drawable.jara)
                    )
                } else {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .align(Alignment.Center),
                        tint = Color(0xFFCCCCCC)
                    )
                }

                // Badge de promoção (se houver)
                produto.promocao?.let { promocao ->
                    if (promocao.precoPromocional != null && promocao.precoPromocional < produto.preco) {
                        val desconto = ((produto.preco - promocao.precoPromocional) / produto.preco * 100).toInt()
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFD32F2F)
                        ) {
                            Text(
                                text = "-$desconto%",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            // Informações do produto
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                // Nome
                Text(
                    text = produto.nome,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.height(36.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Preço
                val precoFinal = produto.promocao?.precoPromocional ?: produto.preco
                val temPromocao = produto.promocao?.precoPromocional != null &&
                                  produto.promocao.precoPromocional < produto.preco

                if (temPromocao) {
                    // Preço original riscado
                    Text(
                        text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(produto.preco),
                        fontSize = 12.sp,
                        color = Color(0xFF999999),
                        style = androidx.compose.ui.text.TextStyle(
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    )
                }

                // Preço final
                Text(
                    text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(precoFinal),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (temPromocao) Color(0xFFD32F2F) else Color(0xFF25992E)
                )
            }
        }
    }
}

