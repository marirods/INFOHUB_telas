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
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.model.PromocaoProduto
import com.example.infohub_telas.navigation.Routes
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaProdutos(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    
    // Produtos mockados
    val produtos = remember { 
        mutableStateListOf<PromocaoProduto>().apply {
            addAll(listOf(
                PromocaoProduto(
                    id = "1",
                    nome = "Arroz Branco 5kg",
                    categoria = "Grãos e Cereais",
                    precoPromocional = "18.90",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 7),
                    descricao = "Arroz branco tipo 1, grãos longos e soltos",
                    imagemUrl = "https://picsum.photos/seed/rice/300/200"
                ),
                PromocaoProduto(
                    id = "2",
                    nome = "Feijão Preto 1kg",
                    categoria = "Grãos e Cereais", 
                    precoPromocional = "7.50",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 5),
                    descricao = "Feijão preto selecionado, rico em proteínas",
                    imagemUrl = "https://picsum.photos/seed/beans/300/200"
                ),
                PromocaoProduto(
                    id = "3",
                    nome = "Leite Integral 1L",
                    categoria = "Laticínios",
                    precoPromocional = "4.25",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 3),
                    descricao = "Leite integral pasteurizado, fonte de cálcio",
                    imagemUrl = "https://picsum.photos/seed/milk/300/200"
                ),
                PromocaoProduto(
                    id = "4",
                    nome = "Banana Prata 1kg",
                    categoria = "Frutas",
                    precoPromocional = "5.90",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 2),
                    descricao = "Banana prata fresca, rica em potássio",
                    imagemUrl = "https://picsum.photos/seed/banana/300/200"
                ),
                PromocaoProduto(
                    id = "5",
                    nome = "Carne Moída 500g",
                    categoria = "Carnes",
                    precoPromocional = "16.90",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 1),
                    descricao = "Carne bovina moída primeira qualidade",
                    imagemUrl = "https://picsum.photos/seed/meat/300/200"
                ),
                PromocaoProduto(
                    id = "6",
                    nome = "Pão de Forma Integral",
                    categoria = "Padaria",
                    precoPromocional = "6.80",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 4),
                    descricao = "Pão de forma integral com fibras, 500g",
                    imagemUrl = "https://picsum.photos/seed/bread/300/200"
                ),
                PromocaoProduto(
                    id = "7",
                    nome = "Ovos Brancos 12 unid",
                    categoria = "Laticínios",
                    precoPromocional = "8.90",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 6),
                    descricao = "Ovos frescos de granja, cartela com 12 unidades",
                    imagemUrl = "https://picsum.photos/seed/eggs/300/200"
                ),
                PromocaoProduto(
                    id = "8",
                    nome = "Tomate 1kg",
                    categoria = "Verduras e Legumes",
                    precoPromocional = "7.20",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 3),
                    descricao = "Tomate maduro selecionado, ideal para saladas",
                    imagemUrl = "https://picsum.photos/seed/tomato/300/200"
                ),
                PromocaoProduto(
                    id = "9",
                    nome = "Refrigerante Cola 2L",
                    categoria = "Bebidas",
                    precoPromocional = "5.50",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 10),
                    descricao = "Refrigerante sabor cola, garrafa 2 litros",
                    imagemUrl = "https://picsum.photos/seed/soda/300/200"
                ),
                PromocaoProduto(
                    id = "10",
                    nome = "Sabão em Pó 1kg",
                    categoria = "Limpeza",
                    precoPromocional = "12.90",
                    dataInicio = Date(),
                    dataTermino = Date(System.currentTimeMillis() + 86400000 * 15),
                    descricao = "Sabão em pó concentrado, remove manchas difíceis",
                    imagemUrl = "https://picsum.photos/seed/soap/300/200"
                )
            ))
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
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
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
            if (isAdmin) {
                FloatingActionButton(
                    onClick = { navController.navigate(Routes.CADASTRO_PRODUTO) },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, "Adicionar Produto")
                }
            }
        },
        bottomBar = { BottomMenu(navController = navController, isAdmin = isAdmin) }
    ) { paddingValues ->
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
                    text = if (searchQuery.value.isNotEmpty() || selectedCategoria.value != null) {
                        "Nenhum produto encontrado"
                    } else {
                        "Nenhuma promoção disponível"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = if (searchQuery.value.isNotEmpty() || selectedCategoria.value != null) {
                        "Tente ajustar os filtros de busca"
                    } else {
                        "Adicione produtos para começar"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyVerticalGrid(
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
    }
}

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
                navController.navigate(Routes.PRODUTO.replace("{produtoId}", produto.id.toString()))
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
                    .build(),
                contentDescription = produto.nome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
                contentScale = ContentScale.Crop
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
                        containerColor = Laranja
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
