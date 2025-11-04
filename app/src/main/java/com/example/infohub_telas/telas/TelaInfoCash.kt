package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import com.example.infohub_telas.components.BottomMenuWithCart

// Cores do tema
private val OrangeColor = Color(0xFFF9A01B)
private val LightOrangeColor = Color(0xFFFFF3E0)
private val LightGrayColor = Color(0xFFF7F7F7)
private val TextGrayColor = Color(0xFF888888)
private val DarkGrayColor = Color(0xFF424242)
private val GreenColor = Color(0xFF4CAF50)
private val RedColor = Color(0xFFE53935)
private val BlueColor = Color(0xFF2196F3)
private val CardBackgroundColor = Color(0xFFFAFAFA)

@Composable
fun TelaInfoCash(navController: NavController?) {
    Scaffold(
        topBar = { InfoCashTopBar() },
        bottomBar = {
            if (navController != null) {
                BottomMenuWithCart(navController = navController)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { 
                    // TODO: Implementar ação de comentar
                    // Aqui pode abrir um dialog ou navegar para tela de comentário
                },
                containerColor = OrangeColor,
                contentColor = Color.White,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Escrever Comentário",
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        containerColor = LightGrayColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { SearchSection() }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { InfoCashCard() }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { ConquistasSection() }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { ComoGanharHubCoinsSection() }
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item { ComunidadeSection() }
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoCashTopBar() {
    TopAppBar(
        title = { },
        navigationIcon = {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(50.dp)
                    .background(
                        Color.White,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(30.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OrangeColor
        )
    )
}

@Composable
fun SearchSection() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text("Buscar...", color = TextGrayColor) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.lupa_loc),
                contentDescription = "Buscar",
                tint = OrangeColor,
                modifier = Modifier.size(24.dp)
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.microfone_loc),
                contentDescription = "Busca por voz",
                tint = OrangeColor,
                modifier = Modifier.size(24.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        )
    )
}

@Composable
fun InfoCashCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            // Título InfoCash
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(
                    "InfoCash",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = OrangeColor
                )
            }
            
            // Card interno com saldo
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = CardBackgroundColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 8.dp)
                    ) {
                        Icon(
                            Icons.Default.MonetizationOn,
                            contentDescription = "HubCoin",
                            tint = OrangeColor,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            "HubCoin",
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = DarkGrayColor
                        )
                        Spacer(Modifier.weight(1f))
                        Text(
                            "R$ 0,00",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = DarkGrayColor
                        )
                    }
                    
                    Text(
                        "1.285 HC",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = DarkGrayColor,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                    
                    LinearProgressIndicator(
                        progress = { 0.7f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        color = OrangeColor,
                        trackColor = OrangeColor.copy(alpha = 0.2f)
                    )
                }
            }
        }
    }
}

@Composable
fun ConquistasSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Conquistas",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DarkGrayColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Grid de conquistas 2x2
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConquistaItem(
                        title = "CONQUISTADO",
                        subtitle = "Caçador de Ofertas",
                        icon = Icons.Default.Search,
                        iconColor = BlueColor,
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                    ConquistaItem(
                        title = "CONQUISTADO",
                        subtitle = "Top Colaborador",
                        icon = Icons.Default.EmojiEvents,
                        iconColor = OrangeColor,
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ConquistaItem(
                        title = "CONQUISTADO",
                        subtitle = "Embaixador Expert",
                        icon = Icons.Default.Star,
                        iconColor = Color(0xFFFFD700), // Dourado
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                    ConquistaItem(
                        title = "CONQUISTADO",
                        subtitle = "Economizador Pro",
                        icon = Icons.Default.MonetizationOn,
                        iconColor = GreenColor,
                        isCompleted = true,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun ConquistaItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    isCompleted: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Círculo com ícone
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(
                    if (isCompleted) iconColor.copy(alpha = 0.15f) else CardBackgroundColor,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = subtitle,
                tint = if (isCompleted) iconColor else TextGrayColor,
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Badge "CONQUISTADO"
        if (isCompleted) {
            Box(
                modifier = Modifier
                    .background(GreenColor, RoundedCornerShape(8.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    title,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Título da conquista
        Text(
            subtitle,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = DarkGrayColor,
            textAlign = TextAlign.Center,
            maxLines = 2,
            lineHeight = 12.sp
        )
    }
}

@Composable
fun ComoGanharHubCoinsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Como ganhar HubCoins",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DarkGrayColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                ComoGanharItem(
                    icon = Icons.Default.ShoppingCart,
                    label = "Comprar",
                    iconColor = GreenColor
                )
                ComoGanharItem(
                    icon = Icons.Default.Share,
                    label = "Compartilhar",
                    iconColor = BlueColor
                )
                ComoGanharItem(
                    icon = Icons.Default.Star,
                    label = "Avaliar",
                    iconColor = OrangeColor
                )
            }
            
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeColor),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    "Começar a ganhar",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ComoGanharItem(
    icon: ImageVector,
    label: String,
    iconColor: Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(iconColor.copy(alpha = 0.1f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            label,
            fontSize = 12.sp,
            color = DarkGrayColor,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ComunidadeSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                "Comunidade",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = DarkGrayColor,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            
            // Comentários da comunidade
            ComentarioItem(
                nomeUsuario = "Maria Silva",
                tempo = "2h",
                comentario = "Consegui economizar R$ 150 esse mês usando o InfoCash! 🎉",
                iniciais = "MS",
                corAvatar = Color(0xFF9C27B0)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            ComentarioItem(
                nomeUsuario = "João Santos",
                tempo = "5h",
                comentario = "As promoções do app são incríveis, sempre encontro bons preços!",
                iniciais = "JS",
                corAvatar = Color(0xFF2196F3)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            ComentarioItem(
                nomeUsuario = "Ana Costa",
                tempo = "1d",
                comentario = "Recomendo demais! Já indiquei para toda minha família 👨‍👩‍👧‍👦",
                iniciais = "AC",
                corAvatar = Color(0xFF4CAF50)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botão para ver mais comentários
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    "Ver mais comentários",
                    color = OrangeColor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun ComentarioItem(
    nomeUsuario: String,
    tempo: String,
    comentario: String,
    iniciais: String,
    corAvatar: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Avatar do usuário
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(corAvatar, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                iniciais,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Conteúdo do comentário
        Column(
            modifier = Modifier.weight(1f)
        ) {
            // Nome e tempo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    nomeUsuario,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = DarkGrayColor
                )
                Text(
                    "• $tempo",
                    fontSize = 12.sp,
                    color = TextGrayColor
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Comentário
            Text(
                comentario,
                fontSize = 13.sp,
                color = DarkGrayColor,
                lineHeight = 18.sp
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Ações (curtir, responder)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.FavoriteBorder,
                        contentDescription = "Curtir",
                        tint = TextGrayColor,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "${(12..45).random()}",
                        fontSize = 12.sp,
                        color = TextGrayColor
                    )
                }
                
                Text(
                    "Responder",
                    fontSize = 12.sp,
                    color = TextGrayColor,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Tela InfoCash Completa")
@Composable
fun TelaInfoCashPreview() {
    MaterialTheme {
        TelaInfoCash(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "InfoCash Card")
@Composable
fun InfoCashCardPreview() {
    MaterialTheme {
        InfoCashCard()
    }
}

@Preview(showBackground = true, name = "Conquistas Section")
@Composable
fun ConquistasSectionPreview() {
    MaterialTheme {
        ConquistasSection()
    }
}

@Preview(showBackground = true, name = "Como Ganhar HubCoins")
@Composable
fun ComoGanharHubCoinsSectionPreview() {
    MaterialTheme {
        ComoGanharHubCoinsSection()
    }
}
