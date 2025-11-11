package com.example.infohub_telas.telas

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaProduto(navController: NavController, id: String) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", android.content.Context.MODE_PRIVATE)
    val isAdmin = prefs.getBoolean("isAdmin", false)
    
    Scaffold(
        topBar = { TopBarPromocao() },
        bottomBar = { BottomMenu(navController = navController, isAdmin = isAdmin) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarPromocao()
            Spacer(modifier = Modifier.height(12.dp))
            FilterChipsPromocao()
            Spacer(modifier = Modifier.height(12.dp))
            ProductCardPromocao()
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

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
fun ProductCardPromocao() {
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { /* TODO: Imagem anterior */ }) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Anterior",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(28.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text("IMAGEM", color = Color.LightGray, fontSize = 14.sp)
                }
                IconButton(onClick = { /* TODO: Próxima imagem */ }) {
                    Icon(
                        Icons.Default.ArrowForward,
                        contentDescription = "Próximo",
                        tint = Color(0xFFFFA726),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Surface(
                    color = Color(0xFFFFE0B2),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "-33%",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = Color(0xFFFFA726),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "R$ 11,98",
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                "R$ 7,99",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4CAF50)
            )

            Text(
                "Garrafa de leite laranja C/1UN",
                color = Color.Gray,
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.lista),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.iabranca),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.size(70.dp, 48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sacola),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Ação de comprar */ },
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