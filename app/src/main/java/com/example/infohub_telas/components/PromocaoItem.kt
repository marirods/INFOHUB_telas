package com.example.infohub_telas.components

// Imports necessários para Compose e Material 3
import androidx.compose.foundation.layout.*  // Para Row, Column, Spacer, etc.
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*  // Para Card, Text, etc.
import androidx.compose.runtime.Composable  // Para @Composable
import androidx.compose.ui.Alignment  // Para alinhamento vertical
import androidx.compose.ui.Modifier  // Para modificadores
import androidx.compose.ui.draw.shadow  // Para sombra
import androidx.compose.ui.graphics.Color  // Para cores
import androidx.compose.ui.text.font.FontWeight  // Para peso da fonte (Bold)
import androidx.compose.ui.unit.dp  // Para unidades de medida (dp)
import androidx.compose.ui.unit.sp  // Para unidades de fonte (sp)

// Imports para o modelo e datas
import com.example.infohub_telas.model.Promocao  // Modelo Promocao
import java.text.SimpleDateFormat  // Para formatar Date
import java.util.*  // Para Date e Locale

@Composable
fun PromocaoItem(promocao: Promocao) {
    Card(
        modifier = Modifier
            .fillMaxWidth()  // Ocupa toda a largura
            .padding(8.dp)  // Espaçamento externo
            .shadow(4.dp, RoundedCornerShape(12.dp)),  // Sombra suave
        shape = RoundedCornerShape(12.dp),  // Cantos arredondados
        colors = CardDefaults.cardColors(containerColor = Color.White)  // Fundo branco
    ) {
        Row(
            modifier = Modifier.padding(16.dp),  // Espaçamento interno
            verticalAlignment = Alignment.CenterVertically  // Alinhamento vertical
        ) {
            Column(modifier = Modifier.weight(1f)) {  // Coluna ocupa o espaço restante
                Text(
                    promocao.nomeProduto,
                    fontWeight = FontWeight.Bold,  // Negrito
                    fontSize = 16.sp  // Tamanho da fonte
                )
                // Correção: Use SimpleDateFormat para formatar Date
                Text(
                    "Data: ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(promocao.dataInicio)}",
                    color = Color.Gray  // Cor cinza
                )
                // Correção: Use Date().after() para comparar com a data atual
                Text(
                    "Status: ${if (promocao.dataTermino.after(Date())) "Ativa" else "Expirada"}",
                    color = if (promocao.dataTermino.after(Date())) Color(0xFF4CAF50) else Color.Red  // Verde para ativa, vermelho para expirada
                )
            }
        }
    }
}
