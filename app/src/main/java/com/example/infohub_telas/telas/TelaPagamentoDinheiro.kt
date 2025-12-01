package com.example.infohub_telas.telas

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.navigation.Routes
import com.example.infohub_telas.utils.AppUtils
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPagamentoDinheiro(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("InfoHub_Prefs", Context.MODE_PRIVATE)

    // Estado para controlar animação de loading
    var isLoading by androidx.compose.runtime.remember { androidx.compose.runtime.mutableStateOf(true) }

    // Obter dados do último produto
    val produtoNome = prefs.getString("ultimo_produto_nome", "Produto")
    val produtoPreco = prefs.getFloat("ultimo_produto_preco_final", 0f).toDouble()
    val frete = 5.00
    val total = produtoPreco + frete

    // Animação de loading por 2 segundos
    androidx.compose.runtime.LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        isLoading = false
    }

    // Mostrar animação de loading primeiro
    if (isLoading) {
        AnimacaoCompraRealizada()
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pedido Confirmado") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }}) {
                        Icon(Icons.Default.Close, contentDescription = "Fechar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF25992E),
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

            // Ícone de sucesso
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        Color(0xFF25992E).copy(alpha = 0.15f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Sucesso",
                    tint = Color(0xFF25992E),
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Título
            Text(
                text = "Pedido Realizado!",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Pagamento em Dinheiro",
                fontSize = 18.sp,
                color = Color(0xFF666666)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Card com recibo
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    // Título do recibo
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 20.dp)
                    ) {
                        Icon(
                            Icons.Default.Receipt,
                            contentDescription = null,
                            tint = Color(0xFFF9A01B),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Recibo do Pedido",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                    }

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFE0E0E0)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Itens do pedido
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "1x $produtoNome",
                            fontSize = 16.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = AppUtils.formatarMoeda(produtoPreco),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Subtotal
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Subtotal:",
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                        Text(
                            text = AppUtils.formatarMoeda(produtoPreco),
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Frete
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Taxa de serviço:",
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                        Text(
                            text = AppUtils.formatarMoeda(frete),
                            fontSize = 14.sp,
                            color = Color(0xFF666666)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color(0xFFE0E0E0)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Total a pagar:",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = AppUtils.formatarMoeda(total),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF25992E)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Card com instruções
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF3E0)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Store,
                            contentDescription = null,
                            tint = Color(0xFFF57C00),
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Instruções de Retirada",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFF57C00)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Instruções
                    InstrucaoItem(
                        numero = "1",
                        texto = "Dirija-se à loja com este recibo"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InstrucaoItem(
                        numero = "2",
                        texto = "Apresente o valor exato de ${AppUtils.formatarMoeda(total)} no caixa"
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    InstrucaoItem(
                        numero = "3",
                        texto = "Retire seu produto após o pagamento"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Informação adicional
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = null,
                            tint = Color(0xFF666666),
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "O produto ficará reservado por 24 horas",
                            fontSize = 12.sp,
                            color = Color(0xFF666666),
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botão voltar para home
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
                    containerColor = Color(0xFF25992E)
                )
            ) {
                Icon(
                    Icons.Default.Home,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Voltar para Início",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun InstrucaoItem(numero: String, texto: String) {
    Row(
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .background(
                    Color(0xFFF57C00),
                    shape = androidx.compose.foundation.shape.CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = numero,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = texto,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            lineHeight = 20.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun AnimacaoCompraRealizada() {
    // Animação de escala
    val scale = androidx.compose.animation.core.animateFloatAsState(
        targetValue = 1f,
        animationSpec = androidx.compose.animation.core.spring(
            dampingRatio = androidx.compose.animation.core.Spring.DampingRatioMediumBouncy,
            stiffness = androidx.compose.animation.core.Spring.StiffnessLow
        ),
        label = "scale"
    )

    // Animação de rotação para o check
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "rotation")
    val rotation = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween(2000, easing = androidx.compose.animation.core.LinearEasing),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Círculo verde com check animado
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .graphicsLayer {
                        scaleX = scale.value
                        scaleY = scale.value
                    }
                    .background(
                        Color(0xFF25992E).copy(alpha = 0.15f),
                        shape = androidx.compose.foundation.shape.CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(
                            Color(0xFF25992E),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Compra Realizada",
                        tint = Color.White,
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Texto animado
            Text(
                text = "Processando Pedido...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Loading circular
            CircularProgressIndicator(
                modifier = Modifier.size(40.dp),
                color = Color(0xFF25992E),
                strokeWidth = 4.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Aguarde um momento...",
                fontSize = 16.sp,
                color = Color(0xFF666666)
            )
        }
    }
}

