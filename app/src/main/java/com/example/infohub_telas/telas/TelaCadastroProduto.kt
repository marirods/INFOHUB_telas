package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Produto
import com.example.infohub_telas.model.PromocaoProdutoRequest
import com.example.infohub_telas.service.RetrofitFactory
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Função para validar formato de data YYYY-MM-DD
private fun isValidDateFormat(date: String): Boolean {
    return try {
        val regex = Regex("""^\d{4}-\d{2}-\d{2}$""")
        if (!regex.matches(date)) return false

        val parts = date.split("-")
        val year = parts[0].toInt()
        val month = parts[1].toInt()
        val day = parts[2].toInt()

        // Validações básicas
        when {
            year < 2020 || year > 2030 -> false
            month < 1 || month > 12 -> false
            day < 1 || day > 31 -> false
            else -> true
        }
        } catch (_: Exception) {
            false
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroProduto(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = prefs.getString("token", "") ?: ""

    // Log para verificar se o token está disponível
    LaunchedEffect(Unit) {
        Log.d("TelaCadastroProduto", "🔑 Token disponível: ${if (token.isNotEmpty()) "Sim (${token.take(20)}...)" else "NÃO - USUÁRIO NÃO LOGADO"}")
        if (token.isEmpty()) {
            Log.e("TelaCadastroProduto", "❌ ATENÇÃO: Token vazio! Usuário precisa fazer login.")
        }
    }

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var idCategoria by remember { mutableStateOf("") }
    var idEstabelecimento by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var precoPromocional by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }
    var temPromocao by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Estados para categorias e estabelecimentos
    var categorias by remember { mutableStateOf<List<com.example.infohub_telas.model.Categoria>>(emptyList()) }
    var estabelecimentos by remember { mutableStateOf<List<com.example.infohub_telas.model.Estabelecimento>>(emptyList()) }
    var isLoadingData by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    val retrofitFactory = remember { RetrofitFactory() }
    val produtoApi = remember { retrofitFactory.getInfoHub_ProdutoService() }
    val categoriaApi = remember { retrofitFactory.getCategoriaApiService() }
    val estabelecimentoApi = remember { retrofitFactory.getInfoHub_EstabelecimentoService() }

    // Buscar categorias e estabelecimentos ao carregar a tela
    LaunchedEffect(Unit) {
        Log.d("TelaCadastroProduto", "🚀 Buscando categorias e estabelecimentos...")
        isLoadingData = true

        coroutineScope.launch {
            try {
                // Buscar categorias
                val categoriasResponse = withContext(Dispatchers.IO) {
                    categoriaApi.listarCategorias().execute()
                }

                if (categoriasResponse.isSuccessful) {
                    categorias = categoriasResponse.body() ?: emptyList()
                    Log.d("TelaCadastroProduto", "✅ ${categorias.size} categorias carregadas")
                } else {
                    Log.e("TelaCadastroProduto", "❌ Erro ao buscar categorias: ${categoriasResponse.code()}")
                }

                // Buscar estabelecimentos
                val estabelecimentosResponse = withContext(Dispatchers.IO) {
                    estabelecimentoApi.listarEstabelecimentos().execute()
                }

                if (estabelecimentosResponse.isSuccessful) {
                    estabelecimentos = estabelecimentosResponse.body() ?: emptyList()
                    Log.d("TelaCadastroProduto", "✅ ${estabelecimentos.size} estabelecimentos carregados")
                } else {
                    Log.e("TelaCadastroProduto", "❌ Erro ao buscar estabelecimentos: ${estabelecimentosResponse.code()}")
                }

                if (categorias.isEmpty() || estabelecimentos.isEmpty()) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "⚠️ Cadastre categorias e estabelecimentos primeiro",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                Log.e("TelaCadastroProduto", "💥 Erro ao buscar dados: ${e.message}", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Erro ao carregar dados: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                isLoadingData = false
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Cadastrar Produto",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            // Card de Informações do Produto
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = Color(0xFF25992E),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Informações do Produto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome do Produto") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text("Descrição") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )

                    // Dropdown de Categorias
                    var categoriaExpandida by remember { mutableStateOf(false) }
                    var categoriaSelecionada by remember { mutableStateOf<com.example.infohub_telas.model.Categoria?>(null) }

                    ExposedDropdownMenuBox(
                        expanded = categoriaExpandida,
                        onExpandedChange = { categoriaExpandida = !categoriaExpandida }
                    ) {
                        OutlinedTextField(
                            value = categoriaSelecionada?.nome ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Categoria*") },
                            placeholder = { Text("Selecione a categoria") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpandida) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E)
                            ),
                            enabled = !isLoadingData
                        )

                        ExposedDropdownMenu(
                            expanded = categoriaExpandida,
                            onDismissRequest = { categoriaExpandida = false }
                        ) {
                            if (isLoadingData) {
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                strokeWidth = 2.dp
                                            )
                                            Text("Carregando categorias...")
                                        }
                                    },
                                    onClick = {},
                                    enabled = false
                                )
                            } else if (categorias.isEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("❌ Nenhuma categoria disponível") },
                                    onClick = {},
                                    enabled = false
                                )
                            } else {
                                categorias.forEach { categoria ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(categoria.nome, fontWeight = FontWeight.Bold)
                                                categoria.descricao?.let {
                                                    Text(it, fontSize = 12.sp, color = Color.Gray)
                                                }
                                            }
                                        },
                                        onClick = {
                                            categoriaSelecionada = categoria
                                            idCategoria = categoria.id?.toString() ?: ""
                                            categoriaExpandida = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    // Dropdown de Estabelecimentos
                    var estabelecimentoExpandido by remember { mutableStateOf(false) }
                    var estabelecimentoSelecionado by remember { mutableStateOf<com.example.infohub_telas.model.Estabelecimento?>(null) }

                    ExposedDropdownMenuBox(
                        expanded = estabelecimentoExpandido,
                        onExpandedChange = { estabelecimentoExpandido = !estabelecimentoExpandido }
                    ) {
                        OutlinedTextField(
                            value = estabelecimentoSelecionado?.nome ?: "",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Estabelecimento*") },
                            placeholder = { Text("Selecione o estabelecimento") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Store,
                                    contentDescription = null
                                )
                            },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = estabelecimentoExpandido) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E)
                            ),
                            enabled = !isLoadingData
                        )

                        ExposedDropdownMenu(
                            expanded = estabelecimentoExpandido,
                            onDismissRequest = { estabelecimentoExpandido = false }
                        ) {
                            if (isLoadingData) {
                                DropdownMenuItem(
                                    text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.size(16.dp),
                                                strokeWidth = 2.dp
                                            )
                                            Text("Carregando estabelecimentos...")
                                        }
                                    },
                                    onClick = {},
                                    enabled = false
                                )
                            } else if (estabelecimentos.isEmpty()) {
                                DropdownMenuItem(
                                    text = { Text("❌ Nenhum estabelecimento disponível") },
                                    onClick = {},
                                    enabled = false
                                )
                            } else {
                                estabelecimentos.forEach { estab ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(estab.nome, fontWeight = FontWeight.Bold)
                                                Text(estab.cnpj, fontSize = 12.sp, color = Color.Gray)
                                            }
                                        },
                                        onClick = {
                                            estabelecimentoSelecionado = estab
                                            idEstabelecimento = estab.id?.toString() ?: ""
                                            estabelecimentoExpandido = false
                                        }
                                    )
                                }
                            }
                        }
                    }

                    OutlinedTextField(
                        value = preco,
                        onValueChange = { preco = it },
                        label = { Text("Preço (R$)*") },
                        placeholder = { Text("Ex: 19.90") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        prefix = { Text("R$ ") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )
                }
            }

            // Card de Promoção
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (temPromocao) 
                        Color(0xFF25992E).copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalOffer,
                                contentDescription = null,
                                tint = if (temPromocao) Color(0xFF25992E) else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    "Promoção",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (temPromocao) {
                                    Text(
                                        "Ativa",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF25992E),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                        Switch(
                            checked = temPromocao,
                            onCheckedChange = { temPromocao = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF25992E),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }

                    if (temPromocao) {
                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = precoPromocional,
                            onValueChange = { precoPromocional = it },
                            label = { Text("Preço Promocional") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Discount,
                                    contentDescription = null
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            prefix = { Text("R$ ") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataInicio,
                            onValueChange = { dataInicio = it },
                            label = { Text("Data Início") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("2025-10-01") },
                            supportingText = { Text("Formato: YYYY-MM-DD", fontSize = 12.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataFim,
                            onValueChange = { dataFim = it },
                            label = { Text("Data Fim") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Event,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("2025-10-31") },
                            supportingText = { Text("Formato: YYYY-MM-DD", fontSize = 12.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Validações mais robustas
                    when {
                        nome.isBlank() -> errorMessage = "📝 Nome do produto é obrigatório"
                        nome.length < 2 -> errorMessage = "📝 Nome deve ter pelo menos 2 caracteres"
                        descricao.isBlank() -> errorMessage = "📄 Descrição é obrigatória"
                        descricao.length < 10 -> errorMessage = "📄 Descrição deve ter pelo menos 10 caracteres"
                        idCategoria.isBlank() -> errorMessage = "🏷️ Selecione uma categoria"
                        idEstabelecimento.isBlank() -> errorMessage = "🏪 Selecione um estabelecimento"
                        preco.isBlank() -> errorMessage = "💰 Preço é obrigatório"
                        preco.toDoubleOrNull() == null -> errorMessage = "💰 Preço deve ser um número válido"
                        (preco.toDoubleOrNull() ?: 0.0) <= 0 -> errorMessage = "💰 Preço deve ser maior que zero"
                        temPromocao && precoPromocional.isBlank() -> errorMessage = "🎯 Preço promocional é obrigatório"
                        temPromocao && precoPromocional.toDoubleOrNull() == null -> errorMessage = "🎯 Preço promocional deve ser um número válido"
                        temPromocao && (precoPromocional.toDoubleOrNull() ?: 0.0) <= 0 -> errorMessage = "🎯 Preço promocional deve ser maior que zero"
                        temPromocao && (precoPromocional.toDoubleOrNull() ?: 0.0) >= (preco.toDoubleOrNull() ?: 0.0) -> errorMessage = "🎯 Preço promocional deve ser menor que o preço normal"
                        temPromocao && dataInicio.isBlank() -> errorMessage = "📅 Data de início da promoção é obrigatória"
                        temPromocao && dataFim.isBlank() -> errorMessage = "📅 Data de fim da promoção é obrigatória"
                        temPromocao && !isValidDateFormat(dataInicio) -> errorMessage = "📅 Formato de data início inválido (use YYYY-MM-DD)"
                        temPromocao && !isValidDateFormat(dataFim) -> errorMessage = "📅 Formato de data fim inválido (use YYYY-MM-DD)"
                        temPromocao && isValidDateFormat(dataInicio) && isValidDateFormat(dataFim) && dataInicio >= dataFim -> errorMessage = "📅 Data de início deve ser anterior à data de fim"
                        else -> {
                            // Limpar mensagem de erro anterior
                            errorMessage = ""
                            isLoading = true

                            // Criar objeto promocao se necessário
                            val promocao = if (temPromocao) {
                                PromocaoProdutoRequest(
                                    precoPromocional = precoPromocional.toDoubleOrNull() ?: 0.0,
                                    dataInicio = dataInicio,
                                    dataFim = dataFim
                                )
                            } else null

                            // Criar objeto produto
                            val produto = Produto(
                                nome = nome.trim(),
                                descricao = descricao.trim(),
                                idCategoria = idCategoria.toIntOrNull() ?: 0,
                                idEstabelecimento = idEstabelecimento.toIntOrNull() ?: 0,
                                preco = preco.toDoubleOrNull() ?: 0.0,
                                promocao = promocao
                            )

                            Log.d("TelaCadastroProduto", "🚀 Cadastrando produto: $produto")
                            Log.d("TelaCadastroProduto", "🔑 Token: Bearer ${token.take(20)}...")

                            // Fazer requisição usando corrotinas
                            coroutineScope.launch {
                                try {
                                    val authToken = "Bearer $token"
                                    val response = withContext(Dispatchers.IO) {
                                        produtoApi.cadastrarProduto(authToken, produto).execute()
                                    }

                                    withContext(Dispatchers.Main) {
                                        isLoading = false

                                        if (response.isSuccessful) {
                                            val produtoCadastrado = response.body()
                                            Log.d("TelaCadastroProduto", "✅ Produto cadastrado com sucesso: $produtoCadastrado")

                                            Toast.makeText(
                                                context,
                                                "✅ Produto '${produto.nome}' cadastrado com sucesso!",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            showSuccessDialog = true
                                        } else {
                                            val errorBody = response.errorBody()?.string()
                                            Log.e("TelaCadastroProduto", "❌ Erro HTTP ${response.code()}: $errorBody")

                                            errorMessage = when (response.code()) {
                                                400 -> "Dados inválidos. Verifique os campos preenchidos."
                                                401 -> "Não autorizado. Faça login novamente."
                                                403 -> "Sem permissão para cadastrar produtos."
                                                409 -> "Produto já existe com esse nome."
                                                422 -> "Erro de validação. Verifique os dados."
                                                500 -> "Erro interno do servidor. Tente novamente."
                                                else -> "Erro ao cadastrar produto (${response.code()})"
                                            }

                                            showErrorDialog = true
                                        }
                                    }
                                } catch (e: Exception) {
                                    Log.e("TelaCadastroProduto", "💥 Exceção ao cadastrar produto: ${e.message}", e)

                                    withContext(Dispatchers.Main) {
                                        isLoading = false
                                        errorMessage = when {
                                            e.message?.contains("timeout", ignoreCase = true) == true ->
                                                "Timeout na conexão. Verifique sua internet."
                                            e.message?.contains("connection", ignoreCase = true) == true ->
                                                "Erro de conexão. Verifique sua internet."
                                            else -> "Erro inesperado: ${e.message}"
                                        }
                                        showErrorDialog = true
                                    }
                                }
                            }
                        }
                    }

                    if (errorMessage.isNotEmpty() && !isLoading) {
                        showErrorDialog = true
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                if (isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.5.dp
                        )
                        Text(
                            "Cadastrando...",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            "Cadastrar Produto",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Overlay de loading quando carregando dados iniciais
        if (isLoadingData) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier.padding(32.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(40.dp),
                            color = Color(0xFF25992E),
                            strokeWidth = 4.dp
                        )
                        Text(
                            "Carregando dados...",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Buscando categorias e estabelecimentos",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        }
    }

    // Diálogo de sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF25992E),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { 
                Text(
                    "Sucesso!", 
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                ) 
            },
            text = { 
                Text(
                    "Produto cadastrado com sucesso!",
                    style = MaterialTheme.typography.bodyLarge
                ) 
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp)
        )
    }

    // Diálogo de erro
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                        errorMessage = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { 
                Text(
                    "Erro", 
                    fontWeight = FontWeight.Bold, 
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.titleLarge
                ) 
            },
            text = { 
                Text(
                    errorMessage,
                    style = MaterialTheme.typography.bodyLarge
                ) 
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaCadastroProdutoPreview() {
    MaterialTheme {
        TelaCadastroProduto(navController = rememberNavController())
    }
}
