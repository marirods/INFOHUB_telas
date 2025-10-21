package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.DetalhesDoPedido
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.components.MensagemSucesso
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPagamentoSucesso(navController: NavController) {
    Scaffold(
        topBar = { Header(title = "Pagamento Aprovado") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFFF9A01B).copy(alpha = 0.2f))
                    )
                )
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MensagemSucesso()
            Spacer(modifier = Modifier.height(24.dp))
            DetalhesDoPedido(navController)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaPagamentoSucessoPreview() {
    InfoHub_telasTheme {
        TelaPagamentoSucesso(rememberNavController())
    }
}
