package com.example.infohub_telas.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.navigation.Routes

@Composable
fun FormularioCheckout(
    navController: NavController,
    itensCarrinho: List<com.example.infohub_telas.model.CarrinhoItem> = emptyList(),
    valorTotal: Double = 0.0
) {
    // Estado para armazenar a forma de pagamento selecionada
    var formaPagamentoSelecionada by remember { mutableStateOf("Cartão de Crédito") }

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 24.dp)
    ) {
        // Indicador de progresso
        ProgressIndicatorCheckout(currentStep = 1, totalSteps = 3)

        Spacer(modifier = Modifier.height(24.dp))

        // Dados Pessoais
        DadosPessoaisForm()

        Spacer(modifier = Modifier.height(20.dp))

        // Endereço
        EnderecoForm()

        Spacer(modifier = Modifier.height(20.dp))

        // Formas de Pagamento - captura seleção
        FormasPagamento(
            onPaymentSelected = { formaPagamentoSelecionada = it }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Resumo do Pedido - Com valores reais
        ResumoPedidoCheckout(
            itensCarrinho = itensCarrinho,
            valorTotal = valorTotal
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botão Finalizar - Navega para tela específica baseada na forma de pagamento
        Button(
            onClick = {
                android.util.Log.d("FormularioCheckout", "💳 Forma de pagamento: $formaPagamentoSelecionada")

                // Navegar para tela específica baseada na forma de pagamento
                when (formaPagamentoSelecionada) {
                    "Dinheiro" -> {
                        android.util.Log.d("FormularioCheckout", "💵 Navegando para tela de pagamento em dinheiro")
                        navController.navigate("pagamento_dinheiro")
                    }
                    "PIX" -> {
                        android.util.Log.d("FormularioCheckout", "📱 Navegando para tela de PIX")
                        navController.navigate("pagamento_pix")
                    }
                    "Cartão de Crédito", "Cartão de Débito" -> {
                        android.util.Log.d("FormularioCheckout", "💳 Navegando para cadastro de cartão")
                        navController.navigate(Routes.PAGAMENTO)
                    }
                    else -> {
                        navController.navigate(Routes.PAGAMENTO)
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF25992E)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 6.dp,
                pressedElevation = 2.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Finalizar Pedido",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun ProgressIndicatorCheckout(currentStep: Int, totalSteps: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Dados", "Pagamento", "Confirmação").forEachIndexed { index, step ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    // Círculo do step
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                color = if (index < currentStep) Color(0xFF25992E) else Color(0xFFE0E0E0),
                                shape = androidx.compose.foundation.shape.CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index < currentStep) {
                            Icon(
                                Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        } else {
                            Text(
                                text = "${index + 1}",
                                color = if (index == currentStep) Color.White else Color.Gray,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    // Linha conectora (exceto no último)
                    if (index < totalSteps - 1) {
                        Spacer(modifier = Modifier.width(4.dp))
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(2.dp)
                                .background(
                                    if (index < currentStep - 1) Color(0xFF25992E) else Color(0xFFE0E0E0)
                                )
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Labels dos steps
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("Dados", "Pagamento", "Confirmação").forEachIndexed { index, step ->
                Text(
                    text = step,
                    fontSize = 11.sp,
                    color = if (index == currentStep) Color(0xFF25992E) else Color.Gray,
                    fontWeight = if (index == currentStep) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DadosPessoaisForm() {
    var nome by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Cabeçalho da seção
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color(0xFFF9A01B).copy(alpha = 0.15f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color(0xFFF9A01B),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Dados Pessoais",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                )
            }

            // Campo Nome
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome Completo") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color(0xFFF9A01B)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF9A01B),
                    focusedLabelColor = Color(0xFFF9A01B)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Telefone
            OutlinedTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = { Text("Telefone") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Phone,
                        contentDescription = null,
                        tint = Color(0xFFF9A01B)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF9A01B),
                    focusedLabelColor = Color(0xFFF9A01B)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Email,
                        contentDescription = null,
                        tint = Color(0xFFF9A01B)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFF9A01B),
                    focusedLabelColor = Color(0xFFF9A01B)
                )
            )
        }
    }
}

@Composable
fun EnderecoForm() {
    var cep by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Cabeçalho da seção
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color(0xFF25992E).copy(alpha = 0.15f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = Color(0xFF25992E),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Endereço de Entrega",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                )
            }

            // CEP
            OutlinedTextField(
                value = cep,
                onValueChange = { cep = it },
                label = { Text("CEP") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = null,
                        tint = Color(0xFF25992E)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    focusedLabelColor = Color(0xFF25992E)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Endereço
            OutlinedTextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço") },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Home,
                        contentDescription = null,
                        tint = Color(0xFF25992E)
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    focusedLabelColor = Color(0xFF25992E)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Número e Complemento
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    label = { Text("Número") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF25992E),
                        focusedLabelColor = Color(0xFF25992E)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = complemento,
                    onValueChange = { complemento = it },
                    label = { Text("Complemento") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF25992E),
                        focusedLabelColor = Color(0xFF25992E)
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bairro
            OutlinedTextField(
                value = bairro,
                onValueChange = { bairro = it },
                label = { Text("Bairro") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    focusedLabelColor = Color(0xFF25992E)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Cidade e Estado
            Row(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    label = { Text("Cidade") },
                    modifier = Modifier.weight(2f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF25992E),
                        focusedLabelColor = Color(0xFF25992E)
                    )
                )
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedTextField(
                    value = estado,
                    onValueChange = { estado = it },
                    label = { Text("UF") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFF25992E),
                        focusedLabelColor = Color(0xFF25992E)
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormasPagamento(
    onPaymentSelected: (String) -> Unit = {}
) {
    var selectedPayment by remember { mutableStateOf("Cartão de Crédito") }

    // Notificar quando a seleção mudar
    LaunchedEffect(selectedPayment) {
        onPaymentSelected(selectedPayment)
    }

    val paymentMethods = listOf(
        Triple("Cartão de Crédito", Icons.Default.CreditCard, Color(0xFF1976D2)),
        Triple("Cartão de Débito", Icons.Default.AccountBalance, Color(0xFF7B1FA2)),
        Triple("PIX", Icons.Default.QrCode, Color(0xFF00796B)),
        Triple("Dinheiro", Icons.Default.AttachMoney, Color(0xFFF57C00))
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Cabeçalho da seção
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color(0xFF1976D2).copy(alpha = 0.15f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Payment,
                        contentDescription = null,
                        tint = Color(0xFF1976D2),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Forma de Pagamento",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF333333)
                )
            }

            // Opções de pagamento
            paymentMethods.forEach { (method, icon, iconColor) ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { selectedPayment = method },
                    shape = RoundedCornerShape(12.dp),
                    border = if (selectedPayment == method)
                        BorderStroke(2.dp, Color(0xFFF9A01B))
                    else
                        BorderStroke(1.dp, Color(0xFFE0E0E0)),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedPayment == method)
                            Color(0xFFF9A01B).copy(alpha = 0.08f)
                        else
                            Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = if (selectedPayment == method) 4.dp else 0.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Ícone do método
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        iconColor.copy(alpha = 0.15f),
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    icon,
                                    contentDescription = null,
                                    tint = iconColor,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = method,
                                fontWeight = if (selectedPayment == method)
                                    FontWeight.Bold
                                else
                                    FontWeight.Medium,
                                fontSize = 15.sp,
                                color = if (selectedPayment == method)
                                    Color(0xFF333333)
                                else
                                    Color(0xFF666666)
                            )
                        }

                        // Radio button visual
                        if (selectedPayment == method) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        Color(0xFFF9A01B),
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        Color.Transparent,
                                        shape = androidx.compose.foundation.shape.CircleShape
                                    )
                                    .then(
                                        Modifier.background(
                                            Color(0xFFE0E0E0),
                                            shape = androidx.compose.foundation.shape.CircleShape
                                        )
                                    )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ResumoPedidoCheckout(
    itensCarrinho: List<com.example.infohub_telas.model.CarrinhoItem> = emptyList(),
    valorTotal: Double = 0.0
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = context.getSharedPreferences("InfoHub_Prefs", android.content.Context.MODE_PRIVATE)

    // Se carrinho vazio, tentar ler do SharedPreferences (compra direta)
    val usarDadosSalvos = itensCarrinho.isEmpty()

    // Dados do produto salvo (para compra direta)
    val produtoSalvoId = prefs.getInt("ultimo_produto_id", 0)
    val produtoSalvoNome = prefs.getString("ultimo_produto_nome", null)
    val produtoSalvoPreco = prefs.getFloat("ultimo_produto_preco_final", 0f).toDouble()

    // Log dos itens recebidos
    LaunchedEffect(itensCarrinho, valorTotal, usarDadosSalvos) {
        android.util.Log.d("ResumoPedidoCheckout", "📊 Recebidos ${itensCarrinho.size} itens do carrinho")
        android.util.Log.d("ResumoPedidoCheckout", "💰 Valor Total recebido: $valorTotal")

        if (usarDadosSalvos && produtoSalvoNome != null) {
            android.util.Log.d("ResumoPedidoCheckout", "🔄 Carrinho vazio - Usando dados do SharedPreferences:")
            android.util.Log.d("ResumoPedidoCheckout", "    - ID: $produtoSalvoId")
            android.util.Log.d("ResumoPedidoCheckout", "    - Nome: $produtoSalvoNome")
            android.util.Log.d("ResumoPedidoCheckout", "    - Preço: R$ $produtoSalvoPreco")
        }

        itensCarrinho.forEachIndexed { index, item ->
            android.util.Log.d("ResumoPedidoCheckout", "  Item $index:")
            android.util.Log.d("ResumoPedidoCheckout", "    - Nome: ${item.produto?.nome}")
            android.util.Log.d("ResumoPedidoCheckout", "    - Preço: ${item.produto?.preco}")
            android.util.Log.d("ResumoPedidoCheckout", "    - Quantidade: ${item.quantidade}")
        }
    }

    // Calcular subtotal baseado nos itens do carrinho OU do produto salvo
    val subtotal = if (usarDadosSalvos && produtoSalvoNome != null) {
        // Usar dados do SharedPreferences (compra direta)
        android.util.Log.d("ResumoPedidoCheckout", "💵 Calculando subtotal do produto salvo: R$ $produtoSalvoPreco")
        produtoSalvoPreco
    } else {
        // Usar dados do carrinho
        itensCarrinho.sumOf { item ->
            val preco = item.produto?.preco ?: 0.0
            val quantidade = item.quantidade
            val itemTotal = preco * quantidade
            android.util.Log.d("ResumoPedidoCheckout", "  Cálculo: $preco x $quantidade = $itemTotal")
            itemTotal
        }
    }

    android.util.Log.d("ResumoPedidoCheckout", "💵 Subtotal calculado: $subtotal")

    // Frete fixo (pode ser calculado dinamicamente no futuro)
    val frete = if (subtotal > 0) 5.00 else 0.0

    // Total = subtotal + frete (ou usar valorTotal do carrinho se disponível)
    val total = if (valorTotal > 0.0) valorTotal + frete else subtotal + frete

    android.util.Log.d("ResumoPedidoCheckout", "🚚 Frete: $frete")
    android.util.Log.d("ResumoPedidoCheckout", "💰 Total final: $total")

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Cabeçalho da seção
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            Color(0xFFF9A01B).copy(alpha = 0.15f),
                            shape = androidx.compose.foundation.shape.CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        tint = Color(0xFFF9A01B),
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Resumo do Pedido",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF333333)
                    )
                    if (itensCarrinho.isNotEmpty()) {
                        Text(
                            text = "${itensCarrinho.size} ${if (itensCarrinho.size == 1) "item" else "itens"}",
                            fontSize = 13.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }

            // Lista de itens (resumida)
            if (itensCarrinho.isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    itensCarrinho.take(3).forEach { item ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${item.quantidade}x ${item.produto?.nome ?: "Produto"}",
                                fontSize = 14.sp,
                                color = Color(0xFF666666),
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(
                                    (item.produto?.preco ?: 0.0) * item.quantidade
                                ),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color(0xFF333333)
                            )
                        }
                    }
                    if (itensCarrinho.size > 3) {
                        Text(
                            text = "+ ${itensCarrinho.size - 3} ${if (itensCarrinho.size - 3 == 1) "item" else "itens"}",
                            fontSize = 13.sp,
                            color = Color(0xFF999999),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color(0xFFE0E0E0)
                )
            } else if (usarDadosSalvos && produtoSalvoNome != null) {
                // Exibir produto do SharedPreferences (compra direta)
                Column(
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "1x $produtoSalvoNome",
                            fontSize = 14.sp,
                            color = Color(0xFF666666),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(produtoSalvoPreco),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF333333)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color(0xFFE0E0E0)
                )
            }

            // Subtotal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Subtotal",
                    fontSize = 15.sp,
                    color = Color(0xFF666666)
                )
                Text(
                    text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(subtotal),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333)
                )
            }

            // Frete
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocalShipping,
                        contentDescription = null,
                        tint = Color(0xFF25992E),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Frete",
                        fontSize = 15.sp,
                        color = Color(0xFF666666)
                    )
                }
                Text(
                    text = if (frete > 0) com.example.infohub_telas.utils.AppUtils.formatarMoeda(frete) else "Grátis",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF25992E)
                )
            }

            // Divider
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 1.dp,
                color = Color(0xFFE0E0E0)
            )

            // Total - Destacado
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFF25992E).copy(alpha = 0.08f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Total",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color(0xFF333333)
                    )
                    Text(
                        text = com.example.infohub_telas.utils.AppUtils.formatarMoeda(total),
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color(0xFF25992E)
                    )
                }
            }

            // Info adicional
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Info,
                    contentDescription = null,
                    tint = Color(0xFF666666),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (itensCarrinho.isEmpty())
                        "Adicione produtos ao carrinho"
                    else
                        "Valores calculados com base no seu carrinho",
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }
        }
    }
}