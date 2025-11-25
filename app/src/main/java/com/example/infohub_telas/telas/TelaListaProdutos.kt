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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState

import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.utils.AppUtils
import com.example.infohub_telas.network.models.Produto
import com.example.infohub_telas.network.models.Categoria
import com.example.infohub_telas.viewmodel.ListaProdutosViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaProdutos(
    navController: NavController,
    viewModel: ListaProdutosViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val userPerfil = prefs.getString("perfil", null)

    // Estado para controlar rolagem e visibilidade do menu
    val lazyGridState = rememberLazyGridState()
    val isMenuVisible = lazyGridState.rememberMenuVisibility()

    // Estados do ViewModel
    val isLoading by viewModel.isLoading.observeAsState(false)
    val produtos by viewModel.produtos.observeAsState(emptyList())
    val categorias by viewModel.categorias.observeAsState(emptyList())
    val errorMessage by viewModel.errorMessage.observeAsState()
    val searchQuery by viewModel.searchQuery.observeAsState("")
    val selectedCategoria by viewModel.selectedCategoria.observeAsState()



    // Estado do diálogo de erro
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogErrorMessage by remember { mutableStateOf("") }

    // Tratar erros
    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            dialogErrorMessage = error
            showErrorDialog = true
        }
    }

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
                    value = searchQuery,
                    onValueChange = { viewModel.atualizarBusca(it) },
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
                        items(categorias.size) { index ->
                            val categoria = categorias[index]
                            val isSelected = selectedCategoria == categoria.idCategoria
                            FilterChip(
                                onClick = {
                                    viewModel.selecionarCategoria(categoria.idCategoria)
                                },
                                label = { Text(categoria.nome) },
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
            if (isAdmin) {
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
                val produtosFiltrados = viewModel.getProdutosFiltrados()

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
                            text = if (searchQuery.isNotEmpty() || selectedCategoria != null) {
                                "Nenhum produto encontrado"
                            } else {
                                "Sem produtos por enquanto"
                            },
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = if (searchQuery.isNotEmpty() || selectedCategoria != null) {
                                "Tente ajustar os filtros de busca"
                            } else {
                                "Aguarde enquanto os estabelecimentos adicionam produtos ou tente recarregar"
                            },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        if (searchQuery.isEmpty() && selectedCategoria == null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.refresh() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFF9A01B)
                                )
                            ) {
                                Icon(Icons.Default.Refresh, contentDescription = null)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Recarregar")
                            }
                        }
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
                    isAdmin = isAdmin,
                    isVisible = isMenuVisible,
                    userPerfil = userPerfil
                )
            }
        } // Fecha Box principal
    } // Fecha Scaffold

    // Diálogo de erro
    if (showErrorDialog) {
        AppUtils.ErrorDialog(
            message = dialogErrorMessage,
            onDismiss = {
                showErrorDialog = false
                viewModel.clearErrorMessage()
            }
        )
    }
} // Fecha TelaListaProdutos



@Composable
fun ProdutoCard(produto: Produto, navController: NavController) {
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
                navController.navigate(Routes.PRODUTO.replace("{produtoId}", produto.idProduto?.toString() ?: "0"))
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
            // Imagem do produto (placeholder)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Image,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

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
                produto.descricao?.let { desc ->
                    Text(
                        text = desc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Preços
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Preço promocional ou normal
                    produto.precoPromocional?.let { precoPromo ->
                        Text(
                            text = AppUtils.formatarMoeda(precoPromo),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50)
                            )
                        )
                        Text(
                            text = AppUtils.formatarMoeda(produto.preco),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textDecoration = androidx.compose.ui.text.style.TextDecoration.LineThrough
                        )
                    } ?: run {
                        Text(
                            text = AppUtils.formatarMoeda(produto.preco),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                    }
                }

                // Chip da categoria
                produto.categoria?.let { categoria ->
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = categoria,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Botão de comprar
                Button(
                    onClick = {
                        // Ir para página do produto
                        navController.navigate(Routes.PRODUTO.replace("{produtoId}", produto.idProduto?.toString() ?: "0"))
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
