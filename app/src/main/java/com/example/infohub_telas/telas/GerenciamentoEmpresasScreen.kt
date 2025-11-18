package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.PrimaryOrange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GerenciamentoEmpresasScreen(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = prefs.getString("token", "") ?: ""

    var searchQuery by remember { mutableStateOf("") }
    var estabelecimentos by remember { mutableStateOf<List<Estabelecimento>>(emptyList()) }
    var filteredEstabelecimentos by remember { mutableStateOf<List<Estabelecimento>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var selectedEstabelecimento by remember { mutableStateOf<Estabelecimento?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var estabelecimentoToDelete by remember { mutableStateOf<Estabelecimento?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val estabelecimentoApi = remember { RetrofitFactory().getInfoHub_EstabelecimentoService() }

    // Função para buscar todos os estabelecimentos
    fun buscarEstabelecimentos() {
        isLoading = true
        errorMessage = null

        coroutineScope.launch {
            try {
                Log.d("GerenciamentoEmpresas", "🚀 Buscando estabelecimentos da API...")

                val response = withContext(Dispatchers.IO) {
                    estabelecimentoApi.listarEstabelecimentos().execute()
                }

                if (response.isSuccessful) {
                    val lista = response.body() ?: emptyList()
                    estabelecimentos = lista
                    filteredEstabelecimentos = lista
                    Log.d("GerenciamentoEmpresas", "✅ ${lista.size} estabelecimentos carregados")

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "✅ ${lista.size} estabelecimentos carregados", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    errorMessage = "Erro ao carregar estabelecimentos: ${response.code()}"
                    Log.e("GerenciamentoEmpresas", "❌ Erro: ${response.code()} - ${response.errorBody()?.string()}")

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                errorMessage = "Erro de conexão: ${e.message}"
                Log.e("GerenciamentoEmpresas", "💥 Exceção: ${e.message}", e)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            } finally {
                isLoading = false
            }
        }
    }

    // Função para buscar estabelecimento por ID
    fun buscarEstabelecimentoPorId(id: Int) {
        coroutineScope.launch {
            try {
                Log.d("GerenciamentoEmpresas", "🔍 Buscando estabelecimento ID: $id")

                val response = withContext(Dispatchers.IO) {
                    estabelecimentoApi.buscarEstabelecimentoPorId(id).execute()
                }

                if (response.isSuccessful) {
                    selectedEstabelecimento = response.body()
                    Log.d("GerenciamentoEmpresas", "✅ Estabelecimento encontrado: ${selectedEstabelecimento?.nome}")
                } else {
                    Log.e("GerenciamentoEmpresas", "❌ Erro ao buscar: ${response.code()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Erro ao buscar estabelecimento", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("GerenciamentoEmpresas", "💥 Erro: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Buscar estabelecimentos ao carregar a tela
    LaunchedEffect(Unit) {
        buscarEstabelecimentos()
    }

    // Filtrar estabelecimentos baseado na busca
    LaunchedEffect(searchQuery, estabelecimentos) {
        filteredEstabelecimentos = if (searchQuery.isEmpty()) {
            estabelecimentos
        } else {
            estabelecimentos.filter {
                it.nome.contains(searchQuery, ignoreCase = true) ||
                it.cnpj.contains(searchQuery, ignoreCase = true)
            }
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

            // Loading State
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            color = PrimaryOrange,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = "Carregando estabelecimentos...",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }
            // Empty State
            else if (filteredEstabelecimentos.isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.BusinessCenter,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = if (searchQuery.isNotEmpty())
                                "Nenhum estabelecimento encontrado"
                            else
                                "Nenhum estabelecimento cadastrado",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Clique no botão + para adicionar",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            // List State
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredEstabelecimentos) { estabelecimento ->
                        EstabelecimentoCard(
                            estabelecimento = estabelecimento,
                            onEditClick = {
                                estabelecimento.id?.let { id ->
                                    buscarEstabelecimentoPorId(id)
                                    navController.navigate("cadastroEmpresa?id=$id")
                                }
                            },
                            onDeleteClick = {
                                estabelecimentoToDelete = estabelecimento
                                showDeleteDialog = true
                            },
                            onCardClick = {
                                estabelecimento.id?.let { id ->
                                    buscarEstabelecimentoPorId(id)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EstabelecimentoCard(
    estabelecimento: Estabelecimento,
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Store,
                            contentDescription = null,
                            tint = PrimaryOrange,
                            modifier = Modifier.size(24.dp)
                        )
                        Text(
                            text = estabelecimento.nome,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Badge,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = estabelecimento.cnpj,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    estabelecimento.telefone?.let { telefone ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Phone,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = telefone,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                    }
                }

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF4CAF50),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = "ATIVO",
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color.LightGray.copy(alpha = 0.5f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "ID: ${estabelecimento.id ?: "N/A"}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedButton(
                        onClick = onEditClick,
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = PrimaryOrange
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Editar", style = MaterialTheme.typography.labelSmall)
                    }

                    OutlinedButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.height(36.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFFD32F2F)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GerenciamentoEmpresasScreenPreview() {
    MaterialTheme {
        GerenciamentoEmpresasScreen(rememberNavController())
    }
}
