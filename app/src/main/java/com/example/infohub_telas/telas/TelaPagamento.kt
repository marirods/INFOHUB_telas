package com.example.infohub_telas.telas

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.viewmodel.PagamentoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPagamento(navController: NavController, pagamentoViewModel: PagamentoViewModel = viewModel()) {
    val uiState by pagamentoViewModel.uiState.collectAsState()

    fun navigateToPaymentSuccess() {
        navController.navigate(Routes.PAGAMENTO_SUCESSO) {
            popUpTo(Routes.HOME) { inclusive = false }
        }
    }

    fun navigateBack() {
        navController.navigateUp()
    }

    Scaffold(
        topBar = { Header(title = "Pagamento", onBackClick = { navigateBack() }) }
    ) { paddingValues ->
        Crossfade(targetState = uiState.etapa, modifier = Modifier.padding(paddingValues)) { etapa ->
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                when (etapa) {
                    1 -> EtapaDadosCartao(pagamentoViewModel)
                    2 -> EtapaEnderecoCobranca(pagamentoViewModel)
                    3 -> EtapaConfirmacao(pagamentoViewModel) {
                        // Callback para navegação após confirmação
                        navigateToPaymentSuccess()
                    }
                }
            }
        }
    }
}

@Composable
fun EtapaDadosCartao(viewModel: PagamentoViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.9f)) // Fundo levemente acinzentado
    ) {
        // Coluna com os campos do formulário e o botão
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Espaço para o cartão flutuante não sobrepor o título do card de baixo
            Spacer(modifier = Modifier.height(140.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Espaço para o cartão não sobrepor o título
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(text = "Dados do Cartão", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = uiState.nomeCompleto,
                        onValueChange = viewModel::onNomeChange,
                        label = { Text("Nome Completo") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = uiState.numeroCartao,
                        onValueChange = viewModel::onNumeroCartaoChange,
                        label = { Text("Número do Cartão") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = uiState.validade,
                            onValueChange = viewModel::onValidadeChange,
                            label = { Text("Validade (MM/AA)") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = uiState.cvv,
                            onValueChange = viewModel::onCvvChange,
                            label = { Text("CVV") },
                            visualTransformation = PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = viewModel::proximaEtapa,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            ) {
                Text("Continuar", color = Color.White)
            }
        }

        // Cartão Flutuante
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 24.dp)
                .offset(y = 30.dp) // Deslocamento para baixo
                .shadow(elevation = 12.dp, shape = RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(Color(0xFFF9A01B), Color(0xFFFF8C00))
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "**** **** **** ${uiState.numeroCartao.takeLast(4)}", color = Color.White, fontSize = 22.sp, letterSpacing = 1.5.sp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = uiState.nomeCompleto.ifEmpty { "NOME COMPLETO" }.uppercase(), color = Color.White, fontSize = 16.sp, maxLines = 1)
                        Text(text = uiState.validade.ifEmpty { "MM/AA" }, color = Color.White, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun EtapaEnderecoCobranca(viewModel: PagamentoViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Endereço de Cobrança", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = uiState.cep,
                    onValueChange = viewModel::onCepChange,
                    label = { Text("CEP") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.rua,
                    onValueChange = viewModel::onRuaChange,
                    label = { Text("Rua / Número") },
                    modifier = Modifier.fillMaxWidth()
                )
                 Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.bairro,
                    onValueChange = viewModel::onBairroChange,
                    label = { Text("Bairro") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = uiState.cidade,
                    onValueChange = viewModel::onCidadeChange,
                    label = { Text("Cidade / Estado") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedButton(
                onClick = viewModel::etapaAnterior,
                 modifier = Modifier.weight(1f)
            ) {
                Text("Voltar", color = Color(0xFFF9A01B))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = viewModel::proximaEtapa,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                 modifier = Modifier
                    .weight(1f)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
            ) {
                Text("Continuar", color = Color.White)
            }
        }
    }
}

@Composable
fun EtapaConfirmacao(viewModel: PagamentoViewModel, onConfirm: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.pagamentoRealizado) {
        LaunchedEffect(Unit) {
            onConfirm()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
         Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Confirme seus dados:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Nome: ${uiState.nomeCompleto}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Cartão: **** **** **** ${uiState.numeroCartao.takeLast(4)}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Valor Total: R$ 123,45", fontWeight = FontWeight.Bold) // Valor de exemplo
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = viewModel::confirmarPagamento,
            modifier = Modifier.fillMaxWidth().shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
            enabled = !uiState.pagamentoRealizado
        ) {
            Text("Confirmar e Pagar", color = Color.White)
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPagamentoPreview() {
    InfoHub_telasTheme {
        TelaPagamento(navController = rememberNavController())
    }
}
