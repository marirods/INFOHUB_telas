package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.ui.theme.PrimaryOrange

data class EmpresaItem(
    val id: Int,
    val nome: String,
    val cnpj: String,
    val status: StatusEmpresa
)

enum class StatusEmpresa {
    ATIVA,
    INATIVA
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerenciamentoEmpresasScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val empresas = remember { generateSampleEmpresas() }
    var filteredEmpresas by remember { mutableStateOf(empresas) }

    LaunchedEffect(searchQuery) {
        filteredEmpresas = if (searchQuery.isEmpty()) {
            empresas
        } else {
            empresas.filter { it.nome.contains(searchQuery, ignoreCase = true) }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerenciamento de Empresas",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() },
            ) {
                IconButton(onClick = { /* Implementar filtros */ }) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filtrar",
                        tint = Color.White
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("cadastroEmpresa") },
                containerColor = PrimaryOrange,
                content = {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Adicionar Empresa",
                        tint = Color.White
                    )
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Buscar empresas...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredEmpresas) { empresa ->
                    EmpresaCard(
                        empresa = empresa,
                        onEditClick = { navController.navigate("cadastroEmpresa?id=${empresa.id}") },
                        onDeleteClick = { /* Implementar exclusão */ },
                        onCardClick = { navController.navigate("cadastroEmpresa?id=${empresa.id}") }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmpresaCard(
    empresa: EmpresaItem,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
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
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = empresa.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = empresa.cnpj,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                StatusChip(status = empresa.status)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        tint = PrimaryOrange
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Excluir",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusChip(status: StatusEmpresa) {
    val (backgroundColor, textColor) = when (status) {
        StatusEmpresa.ATIVA -> Color(0xFF4CAF50) to Color.White
        StatusEmpresa.INATIVA -> Color(0xFFE57373) to Color.White
    }

    Surface(
        shape = RoundedCornerShape(16.dp),
        color = backgroundColor,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(
            text = status.name,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = textColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}

private fun generateSampleEmpresas(): List<EmpresaItem> {
    return listOf(
        EmpresaItem(1, "Tech Solutions LTDA", "12.345.678/0001-90", StatusEmpresa.ATIVA),
        EmpresaItem(2, "Mercado Central", "98.765.432/0001-10", StatusEmpresa.ATIVA),
        EmpresaItem(3, "Padaria do João", "45.678.901/0001-23", StatusEmpresa.INATIVA),
        EmpresaItem(4, "Farmácia Saúde", "32.109.876/0001-45", StatusEmpresa.ATIVA),
        EmpresaItem(5, "Auto Peças Silva", "65.432.109/0001-78", StatusEmpresa.INATIVA)
    )
}

@Preview(showBackground = true)
@Composable
private fun GerenciamentoEmpresasScreenPreview() {
    MaterialTheme {
        GerenciamentoEmpresasScreen(rememberNavController())
    }
}
