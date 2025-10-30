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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoProdutosScreen(
    onNavigateBack: () -> Unit,
    onProdutoClick: (String) -> Unit,
    empresaId: String? = null,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedCategoria by remember { mutableStateOf<String?>(null) }
    var selectedStatus by remember { mutableStateOf<String?>(null) }

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

    val filteredProdutos = produtos.filter { produto ->
        val matchesSearch = produto.nome.contains(searchQuery, ignoreCase = true)
        val matchesCategoria = selectedCategoria == null || produto.categoria == selectedCategoria
        val matchesStatus = selectedStatus == null || produto.status == selectedStatus
        matchesSearch && matchesCategoria && matchesStatus
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Produtos Cadastrados",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = onNavigateBack,
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            Icons.Default.FilterList,
                            contentDescription = "Filtrar",
                            tint = Color.White
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
            // Search bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar produtos...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )

            // Products list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredProdutos) { produto ->
                    ProdutoCard(
                        produto = produto,
                        onClick = { onProdutoClick(produto.id) }
                    )
                }
            }
        }

        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtros") },
                text = {
                    Column {
                        Text("Categoria", fontWeight = FontWeight.Bold)
                        RadioButton(
                            selected = selectedCategoria == null,
                            onClick = { selectedCategoria = null }
                        )
                        Text("Todas")
                        RadioButton(
                            selected = selectedCategoria == "Jurídico",
                            onClick = { selectedCategoria = "Jurídico" }
                        )
                        Text("Jurídico")
                        RadioButton(
                            selected = selectedCategoria == "Administrativo",
                            onClick = { selectedCategoria = "Administrativo" }
                        )
                        Text("Administrativo")
                        RadioButton(
                            selected = selectedCategoria == "Financeiro",
                            onClick = { selectedCategoria = "Financeiro" }
                        )
                        Text("Financeiro")

                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Status", fontWeight = FontWeight.Bold)
                        RadioButton(
                            selected = selectedStatus == null,
                            onClick = { selectedStatus = null }
                        )
                        Text("Todos")
                        RadioButton(
                            selected = selectedStatus == "Ativo",
                            onClick = { selectedStatus = "Ativo" }
                        )
                        Text("Ativo")
                        RadioButton(
                            selected = selectedStatus == "Inativo",
                            onClick = { selectedStatus = "Inativo" }
                        )
                        Text("Inativo")
                        RadioButton(
                            selected = selectedStatus == "Pendente",
                            onClick = { selectedStatus = "Pendente" }
                        )
                        Text("Pendente")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Aplicar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        selectedCategoria = null
                        selectedStatus = null
                        showFilterDialog = false
                    }) {
                        Text("Limpar Filtros")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProdutoCard(
    produto: ProdutoJuridico,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = produto.nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                StatusChip(status = produto.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Categoria: ${produto.categoria}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Preço: R$ ${String.format("%.2f", produto.preco)}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Empresa: ${produto.empresa}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StatusChip(status: String) {
    val (backgroundColor, textColor) = when (status) {
        "Ativo" -> Color(0xFF4CAF50) to Color.White
        "Pendente" -> Color(0xFFFFC107) to Color.Black
        "Inativo" -> Color(0xFFE91E63) to Color.White
        else -> Color.Gray to Color.White
    }

    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = status,
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

data class ProdutoJuridico(
    val id: String,
    val nome: String,
    val categoria: String,
    val preco: Double,
    val status: String,
    val empresa: String
)

@Preview(showBackground = true)
@Composable
fun JuridicoProdutosScreenPreview() {
    InfoHub_telasTheme {
        JuridicoProdutosScreen(
            onNavigateBack = {},
            onProdutoClick = {},
            empresaId = null
        )
    }
}
