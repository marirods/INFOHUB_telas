package com.example.infohub_telas.telas

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import com.example.infohub_telas.model.PromocaoProduto
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.service.RetrofitFactory
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import com.example.infohub_telas.utils.AzureBlobUtils
import com.example.infohub_telas.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaProdutos(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val isJuridico = prefs.getBoolean("isJuridico", false)

    // Estado para controlar rolagem e visibilidade do menu
    val lazyGridState = rememberLazyGridState()
    val isMenuVisible = lazyGridState.rememberMenuVisibility()

    // Estados da API
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val produtos = remember { mutableStateListOf<PromocaoProduto>() }
    val coroutineScope = rememberCoroutineScope()

    // Usar o novo repository com API correta
    val produtoRepository = remember { com.example.infohub_telas.repository.ProdutoApiRepository() }

    // Buscar produtos da API usando o novo repository
    LaunchedEffect(Unit) {
        Log.d("TelaListaProdutos", "🚀 Iniciando busca de produtos da API (novo formato)...")
        isLoading = true

        coroutineScope.launch {
            try {
                // Usar o novo repository com suspend function
                val result = produtoRepository.listarProdutos()

                result.onSuccess { produtosAPI ->
                    Log.d("TelaListaProdutos", "✅ ${produtosAPI.size} produtos recebidos da API")

                    produtos.clear()

                    // Converter Produto para PromocaoProduto
                    produtosAPI.forEach { produto ->
                        // Buscar URL da imagem do Azure ou usar placeholder
                        val imagemUrl = AzureBlobUtils.getImageUrl(produto.imagem)
                            ?: AzureBlobUtils.getPlaceholderImageUrl()

                        // Debug detalhado da URL da imagem
                        AzureBlobUtils.logImageUrlDebug(produto.nome, produto.imagem, imagemUrl)

                        val promocaoProduto = PromocaoProduto(
                            id = produto.id?.toString() ?: "0",
                            nome = produto.nome,
                            categoria = "Categoria ${produto.idCategoria}",
                            precoPromocional = produto.promocao?.precoPromocional?.toString() ?: produto.preco.toString(),
                            dataInicio = Date(),
                            dataTermino = Date(System.currentTimeMillis() + 86400000 * 7),
                            descricao = produto.descricao,
                            imagemUrl = imagemUrl
                        )
                        produtos.add(promocaoProduto)

                        Log.d("TelaListaProdutos", "✅ Produto adicionado: ${produto.nome} (ID: ${produto.id})")
                    }
                }.onFailure { exception ->
                    Log.e("TelaListaProdutos", "❌ Erro ao buscar produtos: ${exception.message}", exception)
                    errorMessage = "Erro ao carregar produtos: ${exception.message}"
                }
            } catch (e: Exception) {
                Log.e("TelaListaProdutos", "💥 Exceção ao buscar produtos: ${e.message}", e)
                errorMessage = "Erro de conexão: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    val searchQuery = remember { mutableStateOf("") }
    val selectedCategoria = remember { mutableStateOf<String?>(null) }
    val categorias = produtos.map { it.categoria }.distinct()

    Scaffold(
        topBar = {
            Column {
                // Header principal
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                    Text(
                        "Promoções",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    // Espaço vazio para manter o título centralizado
                    Box(modifier = Modifier.size(48.dp))
                }

                // Barra de pesquisa
                OutlinedTextField(
                    value = searchQuery.value,
                    onValueChange = { searchQuery.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = { Text("Buscar produtos...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp)
                )

                // Filtros de categoria
                if (categorias.isNotEmpty()) {
                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categorias) { categoria ->
                            val isSelected = selectedCategoria.value == categoria
                            FilterChip(
                                onClick = {
                                    selectedCategoria.value = if (isSelected) null else categoria
                                },
                                label = { Text(categoria) },
                                selected = isSelected,
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            if (isAdmin || isJuridico) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.CADASTRO_PRODUTO) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, "Adicionar Produto")
                }
            }
        },
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            // Indicador de loading
            if (isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Carregando produtos da API...",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                val produtosFiltrados = produtos.filter {
                    val matchesSearch = it.nome.contains(searchQuery.value, ignoreCase = true)
                    val matchesCategoria = selectedCategoria.value == null || it.categoria == selectedCategoria.value
                    matchesSearch && matchesCategoria
                }

                if (produtosFiltrados.isEmpty()) {
                    // Estado vazio
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.ShoppingBag,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "💰 Grandes Ofertas em Breve!",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "🎯 Estamos preparando ofertas incríveis para você economizar ainda mais!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "⏰ Fique atento às próximas promoções e seja o primeiro a aproveitar os melhores preços da região!",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                }
            } else {
                LazyVerticalGrid(
                state = lazyGridState,
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                    items(produtosFiltrados) { produto ->
                        ProdutoCard(produto = produto, navController = navController)
                    }
                }
            }
        } // Fecha else do loading

            // Menu inferior animado
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AnimatedScrollableBottomMenu(
                    navController = navController,
                    isAdmin = isAdmin || isJuridico,
                    isVisible = isMenuVisible
                )
            }
        } // Fecha Box principal
    } // Fecha Scaffold
} // Fecha TelaListaProdutos



@Composable
fun ProdutoCard(produto: PromocaoProduto, navController: NavController) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 2.dp else 6.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { 
                // Navegar para TelaProduto passando o ID do produto
                navController.navigate(Routes.PRODUTO.replace("{produtoId}", produto.id))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagem do produto
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(produto.imagemUrl)
                    .crossfade(true)
                    .placeholder(R.drawable.ic_launcher_foreground) // Imagem temporária
                    .error(R.drawable.ic_launcher_foreground) // Imagem de erro
                    .fallback(R.drawable.ic_launcher_foreground) // Fallback
                    .build(),
                contentDescription = produto.nome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop,
                onError = {
                    Log.e("TelaListaProdutos", "❌ Erro ao carregar imagem: ${produto.imagemUrl}")
                }
            )

            // Conteúdo do card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Nome do produto
                Text(
                    text = produto.nome,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Descrição
                Text(
                    text = produto.descricao,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                // Preço
                Text(
                    text = "R$ ${produto.precoPromocional}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                
                // Chip da categoria
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = produto.categoria,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Botão de comprar
                Button(
                    onClick = {
                        // Ir para página do produto
                        navController.navigate(Routes.PRODUTO.replace("{produtoId}", produto.id))
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF9A01B)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Comprar",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaListaProdutosPreview() {
    MaterialTheme {
        Surface {
            TelaListaProdutos(
                navController = rememberNavController()
            )
        }
    }
}
