package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.components.AnimatedScrollableBottomMenu
import com.example.infohub_telas.components.rememberMenuVisibility
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.viewmodel.HomeViewModel
import com.example.infohub_telas.network.models.Promocao
import com.example.infohub_telas.utils.AppUtils
import com.example.infohub_telas.utils.AzureBlobUtils

// Paleta de cores
val Laranja = Color(0xFFF9A01B)
val Verde = Color(0xFF25992E)
val Vermelho = Color(0xFFF06339)
val CinzaFundo = Color(0xFFF8F8F8)
val CinzaClaro = Color(0xFFE0E0E0)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaHome(
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    val userPerfil = prefs.getString("perfil", null)
    val scrollState = rememberScrollState()
    val isMenuVisible = scrollState.rememberMenuVisibility()

    // Estados do ViewModel
    val promocoes by homeViewModel.promocoes.observeAsState(emptyList())
    val isLoading by homeViewModel.isLoading.observeAsState(false)
    val errorMessage by homeViewModel.errorMessage.observeAsState()

    fun navigateToProfile() {
        navController.navigate(Routes.PERFIL)
    }

    fun navigateToCart() {
        navController.navigate(Routes.CARRINHO)
    }

    fun navigateToChat() {
        navController.navigate(Routes.CHAT_PRECOS)
    }

    fun navigateToLocation() {
        navController.navigate(Routes.LOCALIZACAO)
    }

    fun navigateToProductList() {
        navController.navigate(Routes.LISTA_PRODUTOS)
    }

    Scaffold(
        containerColor = CinzaFundo,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.jara),
                            contentDescription = "Logo InfoHub",
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "INFOHUB",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Laranja)
            )
        },

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Banner promocional com imagem
                // Tamanho recomendado da imagem: 1080x540px (ratio 2:1) ou 1200x600px
                // Formato: PNG ou JPG
                // Nome do arquivo: banner_home.png (ou .jpg)
                // Local: app/src/main/res/drawable/
                // IMPORTANTE: Use apenas letras minúsculas, números e underscores no nome!
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Imagem do banner
                        // INSTRUÇÕES: Adicione sua imagem em app/src/main/res/drawable/
                        // e nomeie como banner_home.png (SEM LETRAS MAIÚSCULAS!)
                        Image(
                            painter = painterResource(id = R.drawable.imgbanner), // <- Nome correto do arquivo
                            contentDescription = "Banner Promocional",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop // A imagem preencherá todo o espaço mantendo proporção
                        )

                        // Gradiente opcional para melhorar legibilidade de texto sobre a imagem
                        // Box(
                        //     modifier = Modifier
                        //         .fillMaxSize()
                        //         .background(
                        //             Brush.verticalGradient(
                        //                 colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.3f))
                        //             )
                        //         )
                        // )
                    }
                }

                // Indicador de banner (bolinhas)
                Row(
                    modifier = Modifier.padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    if (index == 0) Laranja else CinzaClaro,
                                    CircleShape
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Barra de busca
                OutlinedTextField(
                    value = "",
                    onValueChange = { /* TODO: Implement search functionality */ },
                    placeholder = { Text("Buscar produtos...", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.lupa_loc),
                            contentDescription = "Buscar",
                            tint = Laranja,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.microfone_loc),
                            contentDescription = "Busca por voz",
                            tint = Laranja,
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

                // Seção de Promoções
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Promoções",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Ver mais",
                                    fontSize = 14.sp,
                                    color = Laranja,
                                    fontWeight = FontWeight.Medium
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.seta_direita),
                                    contentDescription = "Ver mais",
                                    tint = Laranja,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (isLoading) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(color = Laranja)
                            }
                        } else {
                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(promocoes) { promocao ->
                                    CardProduto(
                                        promocao = promocao,
                                        onClick = {
                                            // Navegar para a página do produto
                                            navController.navigate(
                                                Routes.PRODUTO.replace("{produtoId}", promocao.idProduto.toString())
                                            )
                                        }
                                    )
                                }
                            }
                        }

                        // Indicador de scroll
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(12) { index ->
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .padding(horizontal = 2.dp)
                                        .background(
                                            if (index < 3) Laranja else CinzaClaro,
                                            CircleShape
                                        )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Seção de Ranking Semanal
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Ranking Semanal",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            text = "Top usuários da semana",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        // Lista de usuários no ranking - mostra apenas o primeiro
                        ItemRanking(
                            posicao = 1,
                            nome = "Israel Magalhães Dos Santos",
                            pontos = "248 IC",
                            corMedalha = Laranja,
                            isLast = true
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
            }

            // Botão flutuante de chat posicionado no canto inferior direito
            FloatingActionButton(
                onClick = { navigateToChat() },
                containerColor = Laranja,
                shape = CircleShape,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 96.dp)
                    .size(64.dp)
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.robo),
                    contentDescription = "Chat de Preços",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }

            // Menu inferior animado
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                AnimatedScrollableBottomMenu(
                    navController = navController,
                    isAdmin = isAdmin,
                    isVisible = isMenuVisible,
                    userPerfil = userPerfil
                )
            }
        }
    }
}

@Composable
fun CardProduto(promocao: Promocao, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(230.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Container da imagem com fundo suave e gradiente
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(
                            androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFFFF9F0),
                                    Color(0xFFFFF5E6)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    // Imagem do produto
                    if (promocao.produto?.imagem != null) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(AzureBlobUtils.getImageUrl(promocao.produto.imagem))
                                .crossfade(true)
                                .placeholder(R.drawable.jara)
                                .error(R.drawable.jara)
                                .build(),
                            contentDescription = promocao.produto.nome ?: "Produto",
                            modifier = Modifier
                                .size(110.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.jara),
                            contentDescription = "Produto",
                            modifier = Modifier
                                .size(110.dp)
                                .padding(8.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }

                // Linha divisória sutil
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFEEEEEE)
                )

                // Informações do produto
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Nome do produto
                    Text(
                        text = promocao.produto?.nome ?: "Produto",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF2C2C2C),
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Container de preço com fundo suave
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Preço em destaque
                        Text(
                            text = AppUtils.formatarMoeda(promocao.precoPromocional ?: 0.0),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Verde
                        )

                        // Ícone de seta indicando clique
                        Icon(
                            painter = painterResource(id = R.drawable.seta_direita),
                            contentDescription = "Ver produto",
                            tint = Laranja,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Badge de desconto no canto superior direito - mais destacado
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Vermelho,
                    shadowElevation = 4.dp
                ) {
                    Text(
                        text = "-${promocao.valorDesconto.toInt()}%",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            // Tag "OFERTA" no canto superior esquerdo - redesenhada
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(10.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Verde,
                    shadowElevation = 3.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "⚡",
                            fontSize = 10.sp
                        )
                        Text(
                            text = "OFERTA",
                            color = Color.White,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRanking(
    posicao: Int,
    nome: String,
    pontos: String,
    corMedalha: Color,
    isLast: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "#$posicao",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Laranja,
                    modifier = Modifier.width(40.dp)
                )

                // Medalha
                if (posicao <= 3) {
                    Text(
                        text = "🏆",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                } else {
                    Text(
                        text = "👤",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }

                // Nome
                Text(
                    text = nome,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )
            }

            // Pontos
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pontos,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Laranja,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Icon(
                    painter = painterResource(id = R.drawable.cash_menu),
                    contentDescription = "InfoCash",
                    tint = Laranja,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        if (!isLast) {
            HorizontalDivider(
                color = CinzaClaro,
                thickness = 1.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaHomePreview() {
    InfoHub_telasTheme {
        TelaHome(rememberNavController())
    }
}