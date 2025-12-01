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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.utils.AzureBlobUtils

@Composable
fun CarrinhoVazio(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Filled.ShoppingCart,
            contentDescription = "Momento de Economizar",
            modifier = Modifier.size(100.dp),
            tint = Color(0xFFF9A01B)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "💰 Hora de Economizar!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFF9A01B)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "🛍️ Explore nossas ofertas exclusivas e encontre os melhores preços para você!",
            fontSize = 14.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate(Routes.HOME) },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B)),
            modifier = Modifier.shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "🔍 Descobrir Ofertas", color = Color.White)
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
fun ItemCarrinhoCard(
    item: com.example.infohub_telas.model.CarrinhoItem,
    onRemove: () -> Unit,
    onUpdateQuantidade: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagem do produto
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.produto?.imagem != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(AzureBlobUtils.getImageUrl(item.produto.imagem))
                            .crossfade(true)
                            .placeholder(R.drawable.jara)
                            .error(R.drawable.jara)
                            .build(),
                        contentDescription = item.produto.nome,
                        modifier = Modifier.size(70.dp),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.jara),
                        contentDescription = "Produto",
                        modifier = Modifier.size(70.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Informações do produto
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Nome do produto
                Text(
                    text = item.produto?.nome ?: "Produto",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                    maxLines = 2
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Preço unitário
                Text(
                    text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(item.produto?.preco ?: 0.0),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF25992E)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Controles de quantidade
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Botão diminuir
                    IconButton(
                        onClick = {
                            if (item.quantidade > 1) {
                                onUpdateQuantidade(item.quantidade - 1)
                            }
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Remove,
                            contentDescription = "Diminuir",
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Quantidade
                    Text(
                        text = "${item.quantidade}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    // Botão aumentar
                    IconButton(
                        onClick = { onUpdateQuantidade(item.quantidade + 1) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Aumentar",
                            tint = Color(0xFF25992E),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Botão remover
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = Color(0xFFF06339),
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Total do item
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF5F5F5))
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Subtotal:",
                fontSize = 14.sp,
                color = Color(0xFF666666)
            )
            Text(
                text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(
                    (item.produto?.preco ?: 0.0) * item.quantidade
                ),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25992E)
            )
        }
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
