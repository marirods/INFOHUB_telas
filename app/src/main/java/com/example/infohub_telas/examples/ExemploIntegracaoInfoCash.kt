package com.example.infohub_telas.examples

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infohub_telas.model.TransacaoInfoCash
import com.example.infohub_telas.utils.ApiUtils
import com.example.infohub_telas.viewmodel.*

/**
 * Exemplo prático de como integrar as APIs do InfoCash
 * Esta tela demonstra o uso completo do InfoCashViewModel com dados reais da API
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExemploIntegracaoInfoCash(
    userId: Int,
    viewModel: InfoCashViewModel = viewModel()
) {
    val saldoState by viewModel.uiState.collectAsState()
    val historicoState by viewModel.historicoState.collectAsState()
    val rankingState by viewModel.rankingState.collectAsState()
    val resumoState by viewModel.resumoState.collectAsState()

    // Carregar dados quando a tela for criada
    LaunchedEffect(userId) {
        viewModel.recarregarTodosDados(userId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header com ação de refresh
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "InfoCash Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(
                onClick = { viewModel.recarregarTodosDados(userId) }
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Atualizar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card do Saldo
        SaldoInfoCashCard(saldoState)

        Spacer(modifier = Modifier.height(16.dp))

        // Resumo por tipo de ação
        if (resumoState.isNotEmpty()) {
            ResumoInfoCashSection(resumoState)
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Histórico de transações
        HistoricoInfoCashSection(historicoState)
    }
}

@Composable
private fun SaldoInfoCashCard(saldoState: InfoCashUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Saldo InfoCash",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (saldoState) {
                is InfoCashUiState.Loading -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Carregando saldo...")
                    }
                }

                is InfoCashUiState.Success -> {
                    val saldo = saldoState.saldoInfoCash
                    Text(
                        text = ApiUtils.formatInfoCashPoints(saldo.saldoTotal),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Text(
                        text = "Nível ${ApiUtils.calculateLevel(saldo.saldoTotal)}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LinearProgressIndicator(
                        progress = { ApiUtils.calculateProgress(saldo.saldoTotal) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "${ApiUtils.pointsToNextLevel(saldo.saldoTotal)} pontos para o próximo nível",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                is InfoCashUiState.Error -> {
                    Text(
                        text = "Erro: ${ApiUtils.formatErrorMessage(saldoState.message)}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun ResumoInfoCashSection(resumo: List<com.example.infohub_telas.model.ResumoAcaoInfoCash>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Resumo por Atividade",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            resumo.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = formatTipoAcao(item.tipoAcao),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "${item.totalTransacoes} transações",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Text(
                        text = "+${item.totalPontos} HC",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoricoInfoCashSection(historicoState: HistoricoUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Histórico Recente",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            when (historicoState) {
                is HistoricoUiState.Loading -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Carregando histórico...")
                    }
                }

                is HistoricoUiState.Success -> {
                    if (historicoState.transacoes.isEmpty()) {
                        Text(
                            text = "Nenhuma transação encontrada",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 200.dp)
                        ) {
                            items(historicoState.transacoes) { transacao ->
                                TransacaoInfoCashItem(transacao)
                            }
                        }
                    }
                }

                is HistoricoUiState.Error -> {
                    Text(
                        text = "Erro: ${ApiUtils.formatErrorMessage(historicoState.message)}",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun TransacaoInfoCashItem(transacao: TransacaoInfoCash) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = transacao.descricao,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = formatTipoAcao(transacao.tipoAcao),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            text = "+${transacao.pontos} HC",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun formatTipoAcao(tipoAcao: String): String {
    return when (tipoAcao) {
        "avaliacao_promocao" -> "Avaliação de Promoção"
        "avaliacao_empresa" -> "Avaliação de Empresa"
        "cadastro_produto" -> "Cadastro de Produto"
        "manual" -> "Concessão Manual"
        else -> tipoAcao.replace("_", " ").replaceFirstChar { it.uppercase() }
    }
}
