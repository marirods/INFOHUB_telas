package com.example.infohub_telas.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R

@Composable
fun CarrinhoVazio(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Carrinho Vazio",
            modifier = Modifier.size(100.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Seu carrinho está vazio", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("inicio") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B)),
            modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "Continuar Comprando", color = Color.White)
        }
    }
}

@Composable
fun CarrinhoCheio(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(3) { // Exemplo com 3 itens
                ProdutoItem()
            }
        }
        ResumoPedido(navController)
    }
}

@Composable
fun ProdutoItem() {
    var quantidade by remember { mutableStateOf(1) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Placeholder
                contentDescription = "Imagem do Produto",
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Nome do Produto", fontWeight = FontWeight.Bold)
                Text(text = "R$ 99,99", color = Color.Gray)
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { if (quantidade > 1) quantidade-- }) {
                        Icon(Icons.Filled.Remove, contentDescription = "Diminuir")
                    }
                    Text(text = quantidade.toString())
                    IconButton(onClick = { quantidade++ }) {
                        Icon(Icons.Filled.Add, contentDescription = "Aumentar")
                    }
                }
                IconButton(onClick = { /* Lógica de remover item */ }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Remover", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun ResumoPedido(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 2.dp, shape = RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Subtotal")
                Text(text = "R$ 299,97")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Frete")
                Text(text = "R$ 5,00")
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Total", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "R$ 304,97", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("checkout") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            ) {
                Text(text = "Finalizar Compra", color = Color.White)
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(
                onClick = { navController.navigate("inicio") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Continuar Comprando", color = Color(0xFFF9A01B))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarrinhoVazioPreview() {
    MaterialTheme {
        CarrinhoVazio(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun CarrinhoCheioPreview() {
    MaterialTheme {
        CarrinhoCheio(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun ProdutoItemPreview() {
    MaterialTheme {
        ProdutoItem()
    }
}

@Preview(showBackground = true)
@Composable
fun ResumoPedidoPreview() {
    MaterialTheme {
        ResumoPedido(navController = rememberNavController())
    }
}
