package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infohub_telas.viewmodel.CarrinhoViewModel
import com.example.infohub_telas.viewmodel.OperationUiState
import android.widget.Toast
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.utils.AzureBlobUtils
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.model.Produto
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaProduto(navController: NavController, id: String) {
    val context = LocalContext.current
    // CORRIGIDO: Usar "InfoHub_Prefs" onde o login realmente salva os dados
    val prefs = context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)
    val token = prefs.getString("jwt_token", "") ?: ""  // CORRIGIDO: jwt_token, não token
    val userId = prefs.getInt("user_id", 0)

    // Logs de debug para verificar autenticação
    Log.d("TelaProduto", "═══════════════════════════════════════════")
    Log.d("TelaProduto", "🔐 VERIFICAÇÃO DE AUTENTICAÇÃO")
    Log.d("TelaProduto", "═══════════════════════════════════════════")
    Log.d("TelaProduto", "📋 Dados do SharedPreferences:")
    Log.d("TelaProduto", "   - user_id: $userId")
    Log.d("TelaProduto", "   - token existe? ${token.isNotBlank()}")
    Log.d("TelaProduto", "   - token: ${if (token.isNotBlank()) token.take(30) + "..." else "VAZIO"}")

    // Verificar todas as chaves salvas
    val allEntries = prefs.all
    Log.d("TelaProduto", "   ")
    Log.d("TelaProduto", "📦 Todas as chaves no SharedPreferences 'InfoHub_Prefs':")
    allEntries.forEach { (key, value) ->
        val displayValue = if (key == "jwt_token" && value is String) value.take(30) + "..." else value
        Log.d("TelaProduto", "      - $key = $displayValue")
    }
    Log.d("TelaProduto", "═══════════════════════════════════════════")

    val carrinhoViewModel: CarrinhoViewModel = viewModel()
    val operationState by carrinhoViewModel.operationState.collectAsState()

    // Estado para controlar rolagem e visibilidade do menu
    val scrollState = rememberScrollState()
    val isMenuVisible = scrollState.rememberMenuVisibility()

    // Estados para carregar produto da API
    var produto by remember { mutableStateOf<Produto?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // Usar o novo repository com API correta
    val produtoRepository = remember { com.example.infohub_telas.repository.ProdutoApiRepository() }

    // Controlar navegação após adicionar ao carrinho
    var navegarParaCheckout by remember { mutableStateOf(false) }

    LaunchedEffect(operationState) {
        when (val op = operationState) {
            is OperationUiState.Success -> {
                Log.d("TelaProduto", "✅ Operação bem-sucedida: ${op.message}")

                // Se estava aguardando para navegar, navega agora
                if (navegarParaCheckout) {
                    Log.d("TelaProduto", "🚀 Navegando para checkout...")
                    navController.navigate(Routes.CHECKOUT)
                    navegarParaCheckout = false
                } else {
                    // Só mostra toast se não for navegação (ex: adicionar sem comprar)
                    Toast.makeText(context, op.message, Toast.LENGTH_SHORT).show()
                }

                carrinhoViewModel.resetOperationState()
            }
            is OperationUiState.Error -> {
                Log.e("TelaProduto", "❌ Erro na operação: ${op.message}")
                Toast.makeText(context, "Erro ao adicionar produto: ${op.message}", Toast.LENGTH_LONG).show()
                navegarParaCheckout = false
                carrinhoViewModel.resetOperationState()
            }
            else -> {}
        }
    }

    // Buscar produto da API usando o novo repository
    LaunchedEffect(id) {
        val idProduto = id.toIntOrNull()
        if (idProduto == null) {
            errorMessage = "ID de produto inválido"
            isLoading = false
            return@LaunchedEffect
        }

        Log.d("TelaProduto", "🔍 Buscando produto com ID: $idProduto (novo formato)")
        isLoading = true

        coroutineScope.launch {
            try {
                // Usar o novo repository com suspend function
                val result = produtoRepository.buscarProdutoPorId(idProduto)

                result.onSuccess { produtoRecebido ->
                    produto = produtoRecebido
                    Log.d("TelaProduto", "✅ Produto encontrado: ${produto?.nome}")

                    // Debug detalhado da imagem do produto
                    val imagemUrl = AzureBlobUtils.getImageUrl(produto?.imagem)
                    AzureBlobUtils.logImageUrlDebug(produto?.nome, produto?.imagem, imagemUrl)
                }.onFailure { exception ->
                    errorMessage = "Erro ao carregar produto: ${exception.message}"
                    Log.e("TelaProduto", "❌ Erro ao buscar produto: ${exception.message}", exception)
                }
            } catch (e: Exception) {
                errorMessage = "Erro de conexão: ${e.message}"
                Log.e("TelaProduto", "💥 Exceção ao buscar produto: ${e.message}", e)
            } finally {
                isLoading = false
            }
        }
    }

    // ID do produto vindo do parâmetro 'id' (converter para Int se possível)
    val idProduto = id.toIntOrNull() ?: -1
    // TODO: recuperar idEstabelecimento real associado ao produto
    val idEstabelecimento = 1 // placeholder

    fun adicionarAoCarrinho(quantidade: Int = 1) {
        if (token.isBlank() || userId == 0) {
            Toast.makeText(context, "Faça login para adicionar ao carrinho", Toast.LENGTH_LONG).show()
            return
        }
        if (idProduto == -1) {
            Toast.makeText(context, "Produto inválido", Toast.LENGTH_LONG).show()
            return
        }
        carrinhoViewModel.adicionarItem(
            token = token,
            idUsuario = userId,
            idProduto = idProduto,
            idEstabelecimento = idEstabelecimento,
            quantidade = quantidade
        )
    }

    Scaffold(
        topBar = { TopBarPromocao() }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarPromocao()
            Spacer(modifier = Modifier.height(12.dp))
            FilterChipsPromocao()
            Spacer(modifier = Modifier.height(12.dp))
            if (isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Carregando produto...")
                }
            } else if (errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingBag,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "🎁 Produto em Destaque!",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Este produto está sendo preparado especialmente para você com os melhores preços da região!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            } else if (produto != null) {
                ProductCardPromocao(
                    produto = produto!!,
                    adicionarAoCarrinho = { adicionarAoCarrinho() },
                    navController = navController,
                    context = context,
                    token = token,
                    userId = userId,
                    idProduto = idProduto,
                    idEstabelecimento = idEstabelecimento,
                    carrinhoViewModel = carrinhoViewModel,
                    onComprarAgora = {
                        Log.d("TelaProduto", "🛒 Iniciando processo de compra rápida...")
                        Log.d("TelaProduto", "📦 Adicionando produto ao carrinho - ID: $idProduto")

                        // Salvar dados do produto no SharedPreferences para exibir no checkout
                        produto?.let { prod ->
                            Log.d("TelaProduto", "💾 Salvando dados do produto no SharedPreferences")
                            with(prefs.edit()) {
                                putInt("ultimo_produto_id", prod.id ?: idProduto)
                                putString("ultimo_produto_nome", prod.nome)
                                putFloat("ultimo_produto_preco", prod.preco.toFloat())
                                putString("ultimo_produto_imagem", prod.imagem)

                                // Se tiver promoção, salvar preço promocional
                                val precoFinal = prod.promocao?.precoPromocional ?: prod.preco
                                putFloat("ultimo_produto_preco_final", precoFinal.toFloat())

                                apply()
                            }
                            Log.d("TelaProduto", "✅ Dados salvos: ${prod.nome} - R$ ${prod.preco}")
                        }

                        // Ativa flag para navegar após sucesso
                        navegarParaCheckout = true

                        // Adiciona ao carrinho automaticamente
                        carrinhoViewModel.adicionarItem(
                            token = token,
                            idUsuario = userId,
                            idProduto = idProduto,
                            idEstabelecimento = idEstabelecimento,
                            quantidade = 1
                        )
                    }
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        } // Fecha a Column

        // Menu inferior animado
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            AnimatedScrollableBottomMenu(
                navController = navController,
                isAdmin = false,
                isVisible = isMenuVisible
            )
        }
        } // Fecha o Box principal
    } // Fecha o Scaffold
} // Fecha a função TelaProduto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPromocao() {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(onClick = { /* TODO: Voltar */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.sacola),
                    contentDescription = "Voltar",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFA726)
        )
    )
}

@Composable
fun SearchBarPromocao() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Pesquisar", color = Color.Gray) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.Gray) },
        trailingIcon = {
            IconButton(onClick = { /* TODO: Ação de busca por voz */ }) {
                Icon(painter = painterResource(id = R.drawable.microfone_loc), contentDescription = "Busca por voz", tint = Color.Gray)
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(30.dp)
    )
}

@Composable
fun FilterChipsPromocao() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AssistChip(
            onClick = { /* TODO */ },
            border = BorderStroke(1.dp, color = Color(0xFFFFA726)),
            label = { Text("Promoções", color = Color.White, fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color(0xFFFFA726)
            ),
            modifier = Modifier.height(36.dp)
        )
        AssistChip(
            onClick = { /* TODO */ },
            border = BorderStroke(1.dp, color = Color(0xFFFFA726)),
            label = { Text("Hortifrúti", color = Color(0xFFFFA726), fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color.White
            ),
            modifier = Modifier.height(36.dp)
        )
        AssistChip(
            onClick = { /* TODO */ },
            border = BorderStroke(1.dp, color = Color(0xFFFFA726)),
            label = { Text("Carne", color = Color(0xFFFFA726), fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color.White
            ),
            modifier = Modifier.height(36.dp)
        )
        AssistChip(
            onClick = { /* TODO */ },
            border = BorderStroke(1.dp, color = Color(0xFFFFA726)),
            label = { Text("Bebidas", color = Color(0xFFFFA726), fontWeight = FontWeight.Medium, fontSize = 12.sp, maxLines = 1) },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = Color.White
            ),
            modifier = Modifier.height(36.dp)
        )
    }
}

@Composable
fun ProductCardPromocao(
    produto: Produto,
    adicionarAoCarrinho: () -> Unit,
    navController: NavController,
    context: android.content.Context,
    token: String,
    userId: Int,
    idProduto: Int,
    idEstabelecimento: Int,
    carrinhoViewModel: CarrinhoViewModel,
    onComprarAgora: () -> Unit
) {
    // Obter URL da imagem do Azure
    val imagemUrl = AzureBlobUtils.getImageUrl(produto.imagem)
        ?: AzureBlobUtils.getPlaceholderImageUrl()

    // Calcular desconto se houver promoção
    val precoOriginal = produto.preco
    val precoPromocional = produto.promocao?.precoPromocional ?: precoOriginal
    val desconto = if (precoPromocional < precoOriginal) {
        ((precoOriginal - precoPromocional) / precoOriginal * 100).toInt()
    } else {
        0
    }
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Surface(
                    color = Color(0xFF4CAF50),
                    shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                ) {
                    Text(
                        "Oferta",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
                IconButton(
                    onClick = { /* TODO: Ação de favoritar */ },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Favoritar",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Imagem do produto
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(imagemUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_launcher_foreground) // Imagem temporária
                        .error(R.drawable.ic_launcher_foreground) // Imagem de erro
                        .fallback(R.drawable.ic_launcher_foreground) // Fallback
                        .build(),
                    contentDescription = produto.nome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop,
                    onError = {
                        Log.e("TelaProduto", "❌ Erro ao carregar imagem: $imagemUrl")
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mostrar desconto e preço original apenas se houver promoção
            if (desconto > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        color = Color(0xFFFFE0B2),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "-$desconto%",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = Color(0xFFFFA726),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "R$ %.2f".format(precoOriginal),
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Preço atual
            Text(
                "R$ %.2f".format(precoPromocional),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = if (desconto > 0) Color(0xFF4CAF50) else Color(0xFFFFA726)
            )

            // Nome do produto
            Text(
                produto.nome,
                color = Color.Gray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            // Descrição do produto
            if (!produto.descricao.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    produto.descricao,
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão Lista de Compras
                Button(
                    onClick = {
                        // TODO: Implementar funcionalidade de Lista de Compras
                        Toast.makeText(context, "Lista de Compras - Em breve!", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lista),
                        contentDescription = "Adicionar à lista",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Botão Chat IA
                Button(
                    onClick = {
                        navController.navigate(Routes.CHAT_PRECOS)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iabranca),
                        contentDescription = "Consultar IA",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                // Botão Adicionar ao Carrinho
                Button(
                    onClick = { adicionarAoCarrinho() },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sacola),
                        contentDescription = "Adicionar ao carrinho",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Comprar Agora - Adiciona ao carrinho e vai direto para checkout
            Button(
                onClick = {
                    if (token.isBlank() || userId == 0) {
                        Toast.makeText(context, "Faça login para comprar", Toast.LENGTH_LONG).show()
                        return@Button
                    }
                    if (idProduto == -1) {
                        Toast.makeText(context, "Produto inválido", Toast.LENGTH_LONG).show()
                        return@Button
                    }

                    // Chama a função callback que vai ativar a flag e adicionar ao carrinho
                    onComprarAgora()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "COMPRAR AGORA",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun BottomBarPromocao() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Barra "Ver carrinho"
            Surface(
                color = Color(0xFFFFA726),
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.sacola),
                            contentDescription = "Carrinho",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Ver carrinho",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Text(
                        text = "R$ 0,00",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Barra de navegação
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                var selectedIndex by remember { mutableStateOf(1) }
                val items = listOf(
                    "Início" to R.drawable.inicio,
                    "Promoções" to R.drawable.promocoes,
                    "Localização" to R.drawable.local,
                    "InfoCash" to R.drawable.dinheiro,
                    "Meu Perfil" to R.drawable.perfil_icon
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items.forEachIndexed { index, (label, icon) ->
                        BottomNavigationItemAnimated(
                            label = label,
                            icon = icon,
                            selected = index == selectedIndex,
                            onClick = { selectedIndex = index }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationItemAnimated(
    label: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    val transition = updateTransition(targetState = selected, label = "navTransition")
    val circleScale by transition.animateFloat(label = "circleScale") { if (it) 1f else 0f }
    val iconColor by transition.animateColor(label = "iconColor") { isSelected ->
        if (isSelected) Color.White else Color(0xFFFFA726)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(48.dp)
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer {
                        scaleX = circleScale
                        scaleY = circleScale
                    }
                    .background(
                        color = Color(0xFFFFA726),
                        shape = CircleShape
                    )
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InfoHub_telasTheme {
        TelaProduto(
            navController = rememberNavController(),
            id = "1"
        )
    }
}

