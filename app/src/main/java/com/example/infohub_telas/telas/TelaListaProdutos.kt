package com.example.infohub_telas.telas

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.model.PromocaoProduto
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaProdutos(
    navController: NavController,
    produtos: List<PromocaoProduto>
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val searchQuery = remember { mutableStateOf("") }
    val selectedCategoria = remember { mutableStateOf<String?>(null) }
    val categorias = produtos.map { it.categoria }.distinct()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = false,
                    onClick = { navController.navigate("home") }
                )
                NavigationDrawerItem(
                    label = { Text("Chat de Preços") },
                    selected = false,
                    onClick = { navController.navigate("chat_precos") }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                        Text(
                            "Lista de Produtos",
                            style = MaterialTheme.typography.titleLarge
                        )
                        // Espaço vazio para manter o título centralizado
                        Box(modifier = Modifier.size(48.dp))
                    }

                    OutlinedTextField(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeholder = { Text("Buscar produtos...") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                    )

                    LazyRow(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categorias) { categoria ->
                            val isSelected = selectedCategoria.value == categoria
                            SuggestionChip(
                                onClick = {
                                    selectedCategoria.value = if (isSelected) null else categoria
                                },
                                label = { Text(categoria) },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surface
                                )
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("criarPromocao") },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar Produto")
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(
                    produtos.filter {
                        val matchesSearch = it.nomeProduto.contains(searchQuery.value, ignoreCase = true)
                        val matchesCategoria = selectedCategoria.value == null || it.categoria == selectedCategoria.value
                        matchesSearch && matchesCategoria
                    }
                ) { produto ->
                    ProdutoCard(produto = produto)
                }
            }
        }
    }
}

@Composable
fun ProdutoCard(produto: PromocaoProduto) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 2.dp else 4.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { /* TODO: Navegação para detalhes */ },
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(produto.imagemUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = produto.nomeProduto,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = produto.nomeProduto,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "R$ ${produto.precoPromocional}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = produto.categoria,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "Excluir",
                            modifier = Modifier.size(20.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TelaListaProdutosPreview() {
    val sampleProdutos = listOf(
        PromocaoProduto(
            nomeProduto = "Hambúrguer de Picanha",
            categoria = "Alimentação",
            precoPromocional = "29.90",
            dataInicio = Date(),
            dataTermino = Date(System.currentTimeMillis() + 86400000),
            descricao = "",
            imagemUrl = "https://picsum.photos/seed/1/200"
        )
    )

    Surface {
        TelaListaProdutos(
            navController = rememberNavController(),
            produtos = sampleProdutos
        )
    }
}
