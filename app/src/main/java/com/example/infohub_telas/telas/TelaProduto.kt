package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.R

@Composable
fun TelaProduto() {
    Scaffold(
        topBar = { TopBarPromocao() },
        bottomBar = { BottomBarPromocao() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SearchBarPromocao()
            Spacer(modifier = Modifier.height(16.dp))
            FilterChipsPromocao()
            Spacer(modifier = Modifier.height(16.dp))
            ProductCardPromocao()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarPromocao() {
    TopAppBar(
        title = { },
        actions = {
            IconButton(onClick = { /* TODO: Ação do carrinho */ }) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Carrinho de compras",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun SearchBarPromocao() {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text("Pesquisar") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(onClick = { /* TODO: Ação de busca por voz */ }) {
                // TODO: Substituir pelo ícone de microfone da imagem
                Icon(painter = painterResource(id = R.drawable.microfone_loc), contentDescription = "Busca por voz")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(30.dp)),
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun FilterChipsPromocao() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AssistChip(
            onClick = { /* TODO */ },
            label = { Text("Promoções") },
            colors = AssistChipDefaults.assistChipColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        )
        AssistChip(onClick = { /* TODO */ }, label = { Text("Hortifrúti") })
        AssistChip(onClick = { /* TODO */ }, label = { Text("Carne") })
        AssistChip(onClick = { /* TODO */ }, label = { Text("Bebidas") })
    }
}

@Composable
fun ProductCardPromocao() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .background(Color(0xFF1F9E4A), RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("Oferta", color = Color.White, fontSize = 12.sp)
                }
                IconButton(
                    onClick = { /* TODO: Ação de favoritar */ },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favoritar")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { /* TODO: Imagem anterior */ }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Anterior")
                }
                // TODO: Substituir pelo placeholder da imagem do produto
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(Color.Gray.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ){
                    Text("IMAGEM")
                }
                IconButton(onClick = { /* TODO: Próxima imagem */ }) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "Próximo")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "-33%",
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "R$ 11,98",
                    color = Color.Gray,
                    textDecoration = TextDecoration.LineThrough
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text("R$ 7,99", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1F9E4A))
            Text("Garrafa de leite laranja C/1UN", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // TODO: Adicionar os ícones corretos
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Icon(painter = painterResource(id = R.drawable.lista), contentDescription = null, tint = Color.White)
                }
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                     Icon(painter = painterResource(id = R.drawable.iabranca), contentDescription = null, tint = Color.White)
                }
                Button(onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)) {
                     Icon(painter = painterResource(id = R.drawable.sacola), contentDescription = null, tint = Color.White)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Ação de comprar */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("COMPRAR AGORA", modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}


@Composable
fun BottomBarPromocao() {
    Column {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Ver carrinho", color = Color.White)
                Text("R$ 0,00", color = Color.White)
            }
        }
        BottomAppBar(
            containerColor = Color.White
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // TODO: Substituir pelos ícones corretos
                BottomNavigationItem(label = "Início", icon = R.drawable.inicio, selected = false, onClick = {})
                BottomNavigationItem(label = "Promoções", icon = R.drawable.promocoes, selected = true, onClick = {})
                BottomNavigationItem(label = "Localização", icon = R.drawable.local, selected = false, onClick = {})
                BottomNavigationItem(label = "InfoCash", icon = R.drawable.dinheiro, selected = false, onClick = {})
                BottomNavigationItem(label = "Meu Perfil", icon = R.drawable.perfil_icon, selected = false, onClick = {})
            }
        }
    }
}

@Composable
fun BottomNavigationItem(label: String, icon: Int, selected: Boolean, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        // TODO: Substituir pelo ícone correto
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.primary else Color.Gray
        )
        Text(
            text = label,
            color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray,
            fontSize = 12.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InfoHub_telasTheme {
        TelaProduto()
    }
}
