package com.example.infohub_telas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MensagemSucesso() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = "Sucesso",
                tint = Color(0xFF25992E),
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pedido Realizado!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Seu pedido foi confirmado com sucesso",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DetalhesDoPedido(onVoltarClick: () -> Unit, onPedidosClick: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        InfoCard(icon = Icons.Filled.Receipt, title = "Número do Pedido", subtitle = "#123456")
        InfoCard(icon = Icons.Filled.DateRange, title = "Data e Hora", subtitle = "28/05/2024 às 10:30")
        InfoCard(icon = Icons.Filled.LocalShipping, title = "Previsão de Entrega", subtitle = "01/06/2024")
        InfoCard(icon = Icons.Filled.LocationOn, title = "Endereço de Entrega", subtitle = "Rua das Flores, 123")
        InfoCard(icon = Icons.Filled.Payment, title = "Forma de Pagamento", subtitle = "Cartão de Crédito")
        InfoCard(icon = Icons.Filled.AttachMoney, title = "Total do Pedido", subtitle = "R$ 304,97")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onVoltarClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF9A01B)),
            modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
        ) {
            Text(text = "Voltar ao Início", color = Color.White)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedButton(
            onClick = onPedidosClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver Meus Pedidos", color = Color(0xFFF9A01B))
        }
    }
}

@Composable
fun InfoCard(icon: ImageVector, title: String, subtitle: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9A01B).copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = Color(0xFFF9A01B))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontWeight = FontWeight.Bold)
                Text(text = subtitle, color = Color.Gray)
            }
        }
    }
}
