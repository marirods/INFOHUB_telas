package com.example.infohub_telas.telas

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.infohub_telas.components.MyTopAppBar
import com.example.infohub_telas.model.Promocao
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaListaProdutos(navController: NavController, produtos: List<Promocao>) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todos") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text("Home Jurídico") },
                    selected = false,
                    onClick = { navController.navigate("homeJuridico") }
                )
                NavigationDrawerItem(
                    label = { Text("Meu Estabelecimento") },
                    selected = false,
                    onClick = { navController.navigate("meuEstabelecimento") }
                )
                NavigationDrawerItem(
                    label = { Text("Lista de Produtos") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(
                    title = "Produtos Cadastrados",
                    navigationIcon = Icons.Default.Menu,
                    onNavigationIconClick = { scope.launch { drawerState.open() } }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate("criarPromocao") },
                    shape = CircleShape,
                    containerColor = Color(0xFFF2811D),
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar Produto", tint = Color.White)
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp)
            ) {
                // Barra de busca
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar produtos...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ícone de busca") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                // Filtro de categoria
                val categories = listOf("Todos") + produtos.map { it.categoria }.distinct()
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    items(categories) { category ->
                        SuggestionChip(
                            onClick = { selectedCategory = category },
                            label = { Text(category) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = if (selectedCategory == category) Color(0xFFF9A01B) else Color.LightGray.copy(alpha = 0.5f),
                                labelColor = if (selectedCategory == category) Color.White else Color.Black
                            )
                        )
                    }
                }

                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    val filteredProdutos = produtos.filter { produto ->
                        val categoryMatch = selectedCategory == "Todos" || produto.categoria.equals(selectedCategory, ignoreCase = true)
                        val searchMatch = produto.nomeProduto.contains(searchQuery, ignoreCase = true)
                        categoryMatch && (searchQuery.isBlank() || searchMatch)
                    }
                    items(filteredProdutos) { produto ->
                        ProdutoCard(produto = produto)
                    }
                }
            }
        }
    }
}

@Composable
fun ProdutoCard(produto: Promocao) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 12.dp else 4.dp)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation, RoundedCornerShape(12.dp))
            .clickable(interactionSource = interactionSource, indication = null) {},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Imagem do produto
            AsyncImage(
                model = produto.imagemUrl,
                contentDescription = "Imagem do produto",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(produto.nomeProduto, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(produto.categoria, color = Color.Gray, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                val formattedPrice = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(produto.precoPromocional.toDouble())
                Text(formattedPrice, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color(0xFF4CAF50))

                if (produto.dataInicio.before(Date()) && produto.dataTermino.after(Date())) {
                    Box(
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFFFFE0B2))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text("EM PROMOÇÃO", color = Color(0xFFF57C00), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* TODO: Editar */ }) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color(0xFF03A9F4))
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = { /* TODO: Excluir */ }) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color.Red)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaListaProdutosPreview() {
    val sampleProdutos = listOf(
        Promocao(nomeProduto = "Hambúrguer de Picanha", categoria = "Alimentação", precoPromocional = "29.90", dataInicio = Date(), dataTermino = Date(System.currentTimeMillis() + 86400000), descricao = "", imagemUrl = "https://picsum.photos/seed/1/200"),
        Promocao(nomeProduto = "Tênis de Corrida", categoria = "Varejo", precoPromocional = "249.99", dataInicio = Date(System.currentTimeMillis() - 86400000), dataTermino = Date(System.currentTimeMillis() - 1000), descricao = "", imagemUrl = "https://picsum.photos/seed/2/200"),
        Promocao(nomeProduto = "Corte de Cabelo", categoria = "Serviços", precoPromocional = "45.00", dataInicio = Date(), dataTermino = Date(System.currentTimeMillis() + 5 * 86400000), descricao = "", imagemUrl = "https://picsum.photos/seed/3/200")
    )
    InfoHub_telasTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            TelaListaProdutos(navController = rememberNavController(), produtos = sampleProdutos)
        }
    }
}
