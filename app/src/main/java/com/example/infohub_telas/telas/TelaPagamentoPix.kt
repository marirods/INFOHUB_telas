package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.utils.AppUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPagamentoPix(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)

    // Obter dados do último produto
    val produtoNome = prefs.getString("ultimo_produto_nome", "Produto")
    val produtoPreco = prefs.getFloat("ultimo_produto_preco_final", 0f).toDouble()
    val frete = 5.00
    val total = produtoPreco + frete

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pagamento PIX") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00796B),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Valor a pagar
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF00796B)
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Valor a pagar",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = AppUtils.formatarMoeda(total),
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // QR Code placeholder
            Card(
                modifier = Modifier.size(250.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.QrCode,
                            contentDescription = "QR Code",
                            modifier = Modifier.size(180.dp),
                            tint = Color(0xFF333333)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Escaneie para pagar",
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chave PIX
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Chave PIX",
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "pix@infohub.com.br",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                    IconButton(
                        onClick = { /* Copiar chave */ }
                    ) {
                        Icon(
                            Icons.Default.ContentCopy,
                            contentDescription = "Copiar",
                            tint = Color(0xFF00796B)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Instruções
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF00796B),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Como pagar com PIX",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF00796B)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "1. Abra o aplicativo do seu banco\n" +
                                "2. Escolha pagar com PIX\n" +
                                "3. Escaneie o QR Code ou copie a chave\n" +
                                "4. Confirme o pagamento\n" +
                                "5. Aguarde a confirmação automática",
                        fontSize = 14.sp,
                        color = Color(0xFF333333),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Pagamento confirmado em até 2 minutos",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão confirmar
            Button(
                onClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00796B)
                )
            ) {
                Text(
                    text = "Já fiz o pagamento",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { navController.navigateUp() }
            ) {
                Text(
                    text = "Cancelar",
                    color = Color(0xFF666666)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

