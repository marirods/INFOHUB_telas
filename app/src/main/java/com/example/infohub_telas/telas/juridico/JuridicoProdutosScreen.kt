package com.example.infohub_telas.telas.juridico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

data class ProdutoJuridico(
    val id: String,
    val nome: String,
    val categoria: String,
    val preco: Double,
    val status: String,
    val empresa: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoProdutosScreen(
    navController: NavController,
    empresaId: String? = null,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedCategoria by remember { mutableStateOf<String?>(null) }
    var selectedStatus by remember { mutableStateOf<String?>(null) }

    // Sample data
    val produtos = remember {
        listOf(
            ProdutoJuridico(
                id = "1",
                nome = "Produto Legal 1",
                categoria = "Jurídico",
                preco = 199.99,
                status = "Ativo",
                empresa = "Empresa ABC"
            ),
            ProdutoJuridico(
                id = "2",
                nome = "Produto Legal 2",
                categoria = "Administrativo",
                preco = 299.99,
                status = "Inativo",
                empresa = "Empresa XYZ"
            ),
            ProdutoJuridico(
                id = "3",
                nome = "Produto Legal 3",
                categoria = "Financeiro",
                preco = 399.99,
                status = "Pendente",
                empresa = "Empresa 123"
            )
        )
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Produtos Cadastrados",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filtrar",
                            tint = androidx.compose.ui.graphics.Color.White
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Search Bar
            SearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onSearch = { },
                active = false,
                onActiveChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar produtos...") },
                leadingIcon = { Icon(Icons.Default.Search, "Buscar") }
            ) { }

            // Estatísticas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatisticCard(
                    title = "Total",
                    value = "${produtos.size}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatisticCard(
                    title = "Ativos",
                    value = "${produtos.count { it.status == "Ativo" }}",
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                StatisticCard(
                    title = "Valor Total",
                    value = "R$ ${produtos.sumOf { it.preco }.format(2)}",
                    modifier = Modifier.weight(1f)
                )
            }

            // Lista de Produtos
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(produtos.filter {
                    it.nome.contains(searchQuery, ignoreCase = true) &&
                    (selectedCategoria == null || it.categoria == selectedCategoria) &&
                    (selectedStatus == null || it.status == selectedStatus)
                }) { produto ->
                    ProdutoCard(produto = produto)
                }
            }
        }

        // Dialog de Filtros
        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtrar Produtos") },
                text = {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        Text("Categoria")
                        produtos.map { it.categoria }.distinct().forEach { categoria ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RadioButton(
                                    selected = selectedCategoria == categoria,
                                    onClick = { selectedCategoria = categoria }
                                )
                                Text(categoria, modifier = Modifier.padding(start = 8.dp))
                            }
                        }

                        Divider(modifier = Modifier.padding(vertical = 8.dp))

                        Text("Status")
                        produtos.map { it.status }.distinct().forEach { status ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                RadioButton(
                                    selected = selectedStatus == status,
                                    onClick = { selectedStatus = status }
                                )
                                Text(status, modifier = Modifier.padding(start = 8.dp))
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Aplicar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedCategoria = null
                            selectedStatus = null
                            showFilterDialog = false
                        }
                    ) {
                        Text("Limpar")
                    }
                }
            )
        }
    }
}

@Composable
private fun StatisticCard(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProdutoCard(
    produto: ProdutoJuridico,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        onClick = { /* Navegar para detalhes */ }
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = produto.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = produto.empresa,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = when (produto.status) {
                        "Ativo" -> PrimaryOrange.copy(alpha = 0.1f)
                        "Inativo" -> MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
                        else -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
                    }
                ) {
                    Text(
                        text = produto.status,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = when (produto.status) {
                            "Ativo" -> PrimaryOrange
                            "Inativo" -> MaterialTheme.colorScheme.error
                            else -> MaterialTheme.colorScheme.secondary
                        }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = produto.categoria,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "R$ ${produto.preco.format(2)}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryOrange
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = { /* Editar */ }) {
                    Icon(Icons.Default.Edit, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }
            }
        }
    }
}

private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun JuridicoProdutosScreenPreview() {
    InfoHub_telasTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            JuridicoProdutosScreen(
                navController = rememberNavController()
            )
        }
    }
}
