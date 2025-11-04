package com.example.infohub_telas.components

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.model.*
import com.example.infohub_telas.ui.theme.PrimaryOrange
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelatorioCard(
    relatorio: Relatorio,
    onVisualizarClick: () -> Unit,
    onExportarClick: () -> Unit,
    onCompartilharClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))

    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Cabeçalho
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = relatorio.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = dateFormat.format(relatorio.dataGeracao),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                RelatorioTipoChip(tipo = relatorio.tipo.name)
            }

            // Descrição
            Text(
                text = relatorio.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Métricas
            if (relatorio.metricas.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    relatorio.metricas.entries.take(3).forEach { (key, value) ->
                        MetricaItem(
                            label = key,
                            value = value,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Tags
            if (relatorio.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    relatorio.tags.forEach { tag ->
                        TagChip(tag = tag)
                    }
                }
            }

            // Ações
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onExportarClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Exportar")
                }
                OutlinedButton(
                    onClick = onCompartilharClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Compartilhar")
                }
                Button(
                    onClick = onVisualizarClick,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryOrange)
                ) {
                    Text("Visualizar")
                }
            }
        }
    }
}

@Composable
fun MetricaItem(
    label: String,
    value: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = String.format("%.1f%%", value),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = if (value >= 0) MaterialTheme.colorScheme.primary else Color.Red
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TagChip(tag: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
    ) {
        Text(
            text = tag,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Composable
fun RelatorioTipoChip(tipo: String) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = PrimaryOrange.copy(alpha = 0.2f)
    ) {
        Text(
            text = tipo,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = PrimaryOrange
        )
    }
}

@Composable
fun CircularProgressIndicator(
    percentage: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Background circle
            drawArc(
                color = color.copy(alpha = 0.2f),
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )

            // Progress arc
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = percentage * 360f,
                useCenter = false,
                style = Stroke(width = 8f, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${(percentage * 100).toInt()}%",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RelatorioCardPreview() {
    val sampleRelatorio = Relatorio(
        id = "1",
        nome = "Relatório de Vendas Mensal",
        descricao = "Análise detalhada das vendas do último mês",
        dataGeracao = Date(),
        tipo = TipoRelatorio.VENDAS,
        periodo = PeriodoRelatorio.MENSAL,
        autor = "Sistema",
        metricas = mapOf(
            "Crescimento" to 15.7,
            "Satisfação" to 4.5,
            "Conversão" to 68.3
        ),
        tags = listOf("Vendas", "Mensal", "KPIs")
    )

    MaterialTheme {
        Surface {
            RelatorioCard(
                relatorio = sampleRelatorio,
                onVisualizarClick = {},
                onExportarClick = {},
                onCompartilharClick = {}
            )
        }
    }
}
