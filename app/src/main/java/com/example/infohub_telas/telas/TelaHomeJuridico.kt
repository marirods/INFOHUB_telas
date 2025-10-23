package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu // Ícone de menu lateral
import androidx.compose.material.icons.filled.Notifications // Ícone de notificação
import androidx.compose.material.icons.filled.Person // Ícone de perfil
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import kotlinx.coroutines.delay

// Mock de dados para categorias
val listaCategoriasMock = listOf(
    "Eletrônicos",
    "Moda",
    "Casa e Jardim",
    "Esportes",
    "Livros",
    "Brinquedos"
)

// Mock de dados para produtos em destaque
data class ProdutoDestaque(
    val id: Int,
    val status: String, // "ATIVO", "PROMOÇÃO"
    val imagem: Int,
    val preco: String,
    val descricao: String,
    val categoria: String,
    val estabelecimento: String
)

val produtosDestaqueMock = listOf(
    ProdutoDestaque(
        1,
        "PROMOÇÃO",
        R.drawable.jara,
        "R$ 7,99",
        "Jarra de Vidro Elegante",
        "Casa e Jardim",
        "Casa & Cia"
    ),
    ProdutoDestaque(
        2,
        "ATIVO",
        R.drawable.jara,
        "R$ 150,00",
        "Smartphone X Pro",
        "Eletrônicos",
        "Tech Store"
    ),
    ProdutoDestaque(
        3,
        "PROMOÇÃO",
        R.drawable.jara,
        "R$ 35,90",
        "Camiseta Estampada",
        "Moda",
        "Fashion Wear"
    ),
    ProdutoDestaque(
        4,
        "ATIVO",
        R.drawable.jara,
        "R$ 25,50",
        "Bola de Futebol Oficial",
        "Esportes",
        "Esporte Total"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHomeJuridico(navController: NavController) {
    val scrollState = rememberScrollState()
    var isLoadingCategories by remember { mutableStateOf(true) } // Simula carregamento de categorias
    var selectedCategoryIndex by remember { mutableIntStateOf(0) } // Para destacar a categoria selecionada

    // Simula o carregamento das categorias
    LaunchedEffect(Unit) {
        delay(1000) // Simula um atraso de rede
        isLoadingCategories = false
    }

    Scaffold(
        containerColor = Color(0xFFF8F8F8), // CinzaFundo
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Ícone de menu lateral
                        IconButton(onClick = { /* TODO: Abrir Drawer ou NavigationRail */ }) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu",
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Painel Jurídico",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                actions = {
                    // Ícone de notificação
                    IconButton(onClick = { /* TODO: Abrir tela de notificações */ }) {
                        Icon(
                            Icons.Default.Notifications,
                            contentDescription = "Notificações",
                            tint = Color.White
                        )
                    }

                    // Perfil do usuário
                    var showDropdownMenu by remember { mutableStateOf(false) }
                    Box {
                        IconButton(onClick = { showDropdownMenu = true }) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = Color.White
                            )
                        }
                        DropdownMenu(
                            expanded = showDropdownMenu,
                            onDismissRequest = { showDropdownMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Perfil") },
                                onClick = { /* TODO: Navegar para perfil */ }
                            )
                            DropdownMenuItem(
                                text = { Text("Sair") },
                                onClick = { /* TODO: Realizar logout */ }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF9A01B)) // Laranja
            )
        },
        bottomBar = { BottomMenu(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 2. Banner principal
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent), // Usaremos gradiente
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFF9A01B), Color(0xFFF7C045)) // Laranja, AmareloGradiente
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Bem-vindo ao seu Painel",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* TODO: Navegar para criar nova promoção */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFFF9A01B) // Laranja
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.adicionar), // Ícone genérico de adicionar
                                contentDescription = "Nova Promoção",
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("➕ Nova Promoção", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Bolinhas indicativas do carrossel
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                repeat(3) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                if (index == 0) Color(0xFFF9A01B) else Color(0xFFE0E0E0), // Laranja, CinzaClaro
                                CircleShape
                            )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Barra de pesquisa
            OutlinedTextField(
                value = "",
                onValueChange = { /* TODO: Implement search functionality */ },
                placeholder = {
                    Text(
                        "Buscar produtos, empresas, usuários...",
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.lupa_loc),
                        contentDescription = "Buscar",
                        tint = Color(0xFFF9A01B), // Laranja
                        modifier = Modifier.size(24.dp)
                    )
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.microfone_loc),
                        contentDescription = "Busca por voz",
                        tint = Color(0xFFF9A01B), // Laranja
                        modifier = Modifier.size(24.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Categorias (rolagem horizontal)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categorias",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    // Botão "Todas"
                    Button(
                        onClick = { selectedCategoryIndex = -1 }, // -1 para indicar "Todas"
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedCategoryIndex == -1) Color(0xFFF9A01B) else Color.White, // Laranja
                            contentColor = if (selectedCategoryIndex == -1) Color.White else Color(0xFFF9A01B) // Laranja
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Todas", fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (isLoadingCategories) {
                    Text("Carregando categorias...", color = Color.Gray)
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listaCategoriasMock.size) { index ->
                            val categoria = listaCategoriasMock[index]
                            Button(
                                onClick = { selectedCategoryIndex = index },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedCategoryIndex == index) Color(0xFFF9A01B) else Color.White, // Laranja
                                    contentColor = if (selectedCategoryIndex == index) Color.White else Color(0xFFF9A01B) // Laranja
                                ),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier.height(32.dp)
                            ) {
                                Text(categoria, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 5. Seção de produtos em destaque
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "📦 Produtos em Destaque",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Botão "➕ Adicionar"
                        Button(
                            onClick = { /* TODO: Navegar para adicionar produto */ },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFF9A01B), // Laranja
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.height(32.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.adicionar),
                                contentDescription = "Adicionar Produto",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Adicionar", fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        // Botão "Ver Todos →"
                        Text(
                            text = "Ver Todos →",
                            fontSize = 14.sp,
                            color = Color(0xFFF9A01B), // Laranja
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(start = 4.dp) // Pequeno espaçamento
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Grid 2x2 (ou adaptativo) de cards de produtos
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Duas colunas
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.heightIn(max = 600.dp) // Limita a altura para evitar scroll infinito dentro do scroll principal
                ) {
                    items(produtosDestaqueMock) { produto ->
                        CardProdutoPainel(produto = produto)
                    }
                }
            }

            Spacer(modifier = Modifier.height(100.dp)) // Espaço para o FAB não cobrir conteúdo
        }
    }
}

@Composable
fun CardProdutoPainel(produto: ProdutoDestaque) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Status ATIVO / PROMOÇÃO
            Box(
                modifier = Modifier
                    .align(Alignment.Start)
                    .background(
                        if (produto.status == "PROMOÇÃO") Color(0xFF25992E) else Color(0xFFF9A01B), // Verde, Laranja
                        RoundedCornerShape(4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(
                    text = produto.status,
                    color = Color.White,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Ícone ⚙️ para editar
            Icon(
                painter = painterResource(id = R.drawable.adicionar), // Placeholder para Ícone de configurações
                contentDescription = "Editar Produto",
                tint = Color(0xFFE0E0E0), // CinzaClaro
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Imagem do produto
            Image(
                painter = painterResource(id = produto.imagem),
                contentDescription = produto.descricao,
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Preço
            Text(
                text = produto.preco,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25992E) // Verde
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descrição
            Text(
                text = produto.descricao,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                maxLines = 1
            )

            // Categoria + Estabelecimento
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Categoria: ${produto.categoria}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
                Text(
                    text = "Estabelecimento: ${produto.estabelecimento}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

            // Botão ✏️ de editar
            Button(
                onClick = { /* TODO: Navegar para editar produto */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF9A01B), // Laranja
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.adicionar), // Placeholder para Ícone de lápis para editar
                    contentDescription = "Editar",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Editar", fontSize = 12.sp)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun TelaHomeJuridicoPreview() {
    InfoHub_telasTheme {
        TelaHomeJuridico(rememberNavController())
    }
}
