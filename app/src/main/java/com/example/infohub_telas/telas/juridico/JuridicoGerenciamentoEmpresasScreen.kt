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
import com.example.infohub_telas.model.CompanyStatus
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.StatusActive
import com.example.infohub_telas.ui.theme.StatusInactive
import com.example.infohub_telas.ui.theme.StatusPending

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuridicoGerenciamentoEmpresasScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCadastroEmpresa: () -> Unit,
    onEmpresaClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }
    var selectedStatus by remember { mutableStateOf<CompanyStatus?>(null) }

    val empresas = remember {
        listOf(
            JuridicoEmpresa(
                "1",
                "ABC Comércio Ltda",
                "12.345.678/0001-90",
                CompanyStatus.ACTIVE,
                "01/01/2025",
                "João Silva"
            ),
            JuridicoEmpresa(
                "2",
                "XYZ Serviços ME",
                "98.765.432/0001-10",
                CompanyStatus.PENDING,
                "15/03/2025",
                "Maria Santos"
            ),
            JuridicoEmpresa(
                "3",
                "Tech Solutions S.A.",
                "45.678.901/0001-23",
                CompanyStatus.INACTIVE,
                "10/06/2025",
                "Pedro Oliveira"
            )
        )
    }

    val filteredEmpresas = empresas.filter { empresa ->
        val matchesSearch = empresa.nome.contains(searchQuery, ignoreCase = true) ||
                empresa.cnpj.contains(searchQuery)
        val matchesStatus = selectedStatus == null || empresa.status == selectedStatus
        matchesSearch && matchesStatus
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Gerenciamento Jurídico",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = onNavigateBack,
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filtrar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCadastroEmpresa,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, "Adicionar Empresa")
            }
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
                placeholder = { Text("Buscar empresas...") },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar")
                },
                singleLine = true
            )

            // List of companies
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredEmpresas) { empresa ->
                    EmpresaCard(
                        empresa = empresa,
                        onClick = { onEmpresaClick(empresa.id) }
                    )
                }
            }
        }

        if (showFilterDialog) {
            AlertDialog(
                onDismissRequest = { showFilterDialog = false },
                title = { Text("Filtrar por Status") },
                text = {
                    Column {
                        RadioButton(
                            selected = selectedStatus == null,
                            onClick = { selectedStatus = null }
                        )
                        Text("Todos")

                        RadioButton(
                            selected = selectedStatus == CompanyStatus.ACTIVE,
                            onClick = { selectedStatus = CompanyStatus.ACTIVE }
                        )
                        Text("Ativo")

                        RadioButton(
                            selected = selectedStatus == CompanyStatus.PENDING,
                            onClick = { selectedStatus = CompanyStatus.PENDING }
                        )
                        Text("Pendente")

                        RadioButton(
                            selected = selectedStatus == CompanyStatus.INACTIVE,
                            onClick = { selectedStatus = CompanyStatus.INACTIVE }
                        )
                        Text("Inativo")
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showFilterDialog = false }) {
                        Text("Confirmar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
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
private fun EmpresaCard(
    empresa: JuridicoEmpresa,
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
                    text = empresa.nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                StatusChip(status = empresa.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "CNPJ: ${empresa.cnpj}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Data de Abertura: ${empresa.dataAbertura}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Responsável: ${empresa.responsavel}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun StatusChip(status: CompanyStatus) {
    val (backgroundColor, textColor) = when (status) {
        CompanyStatus.ACTIVE -> StatusActive to Color.White
        CompanyStatus.PENDING -> StatusPending to Color.Black
        CompanyStatus.INACTIVE -> StatusInactive to Color.White
    }

    Surface(
        color = backgroundColor,
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = when (status) {
                CompanyStatus.ACTIVE -> "Ativo"
                CompanyStatus.PENDING -> "Pendente"
                CompanyStatus.INACTIVE -> "Inativo"
            },
            color = textColor,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

data class JuridicoEmpresa(
    val id: String,
    val nome: String,
    val cnpj: String,
    val status: CompanyStatus,
    val dataAbertura: String,
    val responsavel: String
)

@Preview(showBackground = true)
@Composable
fun JuridicoGerenciamentoEmpresasScreenPreview() {
    InfoHub_telasTheme {
        JuridicoGerenciamentoEmpresasScreen(
            onNavigateBack = {},
            onNavigateToCadastroEmpresa = {},
            onEmpresaClick = {}
        )
    }
}
