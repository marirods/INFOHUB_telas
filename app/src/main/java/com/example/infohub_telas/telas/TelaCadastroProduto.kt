package com.example.infohub_telas.telas

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.model.Produto
import com.example.infohub_telas.model.PromocaoProdutoRequest
import com.example.infohub_telas.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroProduto(navController: NavController) {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val token = prefs.getString("token", "") ?: ""

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var idCategoria by remember { mutableStateOf("") }
    var idEstabelecimento by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var precoPromocional by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }
    var temPromocao by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val produtoApi = RetrofitFactory().getInfoHub_ProdutoService()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Cadastrar Produto",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Card de Informações do Produto
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingBag,
                            contentDescription = null,
                            tint = Color(0xFF25992E),
                            modifier = Modifier.size(28.dp)
                        )
                        Text(
                            "Informações do Produto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    OutlinedTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = { Text("Nome do Produto") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )

                    OutlinedTextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text("Descrição") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = idCategoria,
                            onValueChange = { idCategoria = it },
                            label = { Text("ID Categoria") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Category,
                                    contentDescription = null
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E)
                            )
                        )

                        OutlinedTextField(
                            value = idEstabelecimento,
                            onValueChange = { idEstabelecimento = it },
                            label = { Text("ID Estabelecimento") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Store,
                                    contentDescription = null
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E)
                            )
                        )
                    }

                    OutlinedTextField(
                        value = preco,
                        onValueChange = { preco = it },
                        label = { Text("Preço") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = null
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        prefix = { Text("R$ ") },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF25992E),
                            focusedLabelColor = Color(0xFF25992E)
                        )
                    )
                }
            }

            // Card de Promoção
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (temPromocao) 
                        Color(0xFF25992E).copy(alpha = 0.1f)
                    else MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalOffer,
                                contentDescription = null,
                                tint = if (temPromocao) Color(0xFF25992E) else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(28.dp)
                            )
                            Column {
                                Text(
                                    "Promoção",
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                if (temPromocao) {
                                    Text(
                                        "Ativa",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFF25992E),
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }
                            }
                        }
                        Switch(
                            checked = temPromocao,
                            onCheckedChange = { temPromocao = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF25992E),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = MaterialTheme.colorScheme.outline
                            )
                        )
                    }

                    if (temPromocao) {
                        Spacer(modifier = Modifier.height(16.dp))

                        HorizontalDivider(
                            thickness = 1.dp,
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = precoPromocional,
                            onValueChange = { precoPromocional = it },
                            label = { Text("Preço Promocional") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Discount,
                                    contentDescription = null
                                )
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            prefix = { Text("R$ ") },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataInicio,
                            onValueChange = { dataInicio = it },
                            label = { Text("Data Início") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("2025-10-01") },
                            supportingText = { Text("Formato: YYYY-MM-DD", fontSize = 12.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = dataFim,
                            onValueChange = { dataFim = it },
                            label = { Text("Data Fim") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Event,
                                    contentDescription = null
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("2025-10-31") },
                            supportingText = { Text("Formato: YYYY-MM-DD", fontSize = 12.sp) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF25992E),
                                focusedLabelColor = Color(0xFF25992E),
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    when {
                        nome.isBlank() -> errorMessage = "Nome é obrigatório"
                        descricao.isBlank() -> errorMessage = "Descrição é obrigatória"
                        idCategoria.isBlank() -> errorMessage = "ID Categoria é obrigatório"
                        idEstabelecimento.isBlank() -> errorMessage = "ID Estabelecimento é obrigatório"
                        preco.isBlank() -> errorMessage = "Preço é obrigatório"
                        temPromocao && precoPromocional.isBlank() -> errorMessage = "Preço promocional é obrigatório"
                        temPromocao && dataInicio.isBlank() -> errorMessage = "Data início é obrigatória"
                        temPromocao && dataFim.isBlank() -> errorMessage = "Data fim é obrigatória"
                        else -> {
                            isLoading = true

                            val promocao = if (temPromocao) {
                                PromocaoProdutoRequest(
                                    precoPromocional = precoPromocional.toDoubleOrNull() ?: 0.0,
                                    dataInicio = dataInicio,
                                    dataFim = dataFim
                                )
                            } else null

                            val produto = Produto(
                                nome = nome,
                                descricao = descricao,
                                idCategoria = idCategoria.toIntOrNull() ?: 0,
                                idEstabelecimento = idEstabelecimento.toIntOrNull() ?: 0,
                                preco = preco.toDoubleOrNull() ?: 0.0,
                                promocao = promocao
                            )

                            Log.d("PRODUTO", "Cadastrando: $produto")

                            val authToken = "Bearer $token"
                            produtoApi.cadastrarProduto(authToken, produto).enqueue(
                                object : Callback<Produto> {
                                    override fun onResponse(
                                        call: Call<Produto>,
                                        response: Response<Produto>
                                    ) {
                                        isLoading = false
                                        if (response.isSuccessful) {
                                            Log.d("PRODUTO", "Sucesso: ${response.body()}")
                                            showSuccessDialog = true
                                        } else {
                                            Log.e("PRODUTO", "Erro: ${response.code()} - ${response.errorBody()?.string()}")
                                            errorMessage = "Erro ao cadastrar: ${response.message()}"
                                            showErrorDialog = true
                                        }
                                    }

                                    override fun onFailure(call: Call<Produto>, t: Throwable) {
                                        isLoading = false
                                        Log.e("PRODUTO", "Falha: ${t.message}")
                                        errorMessage = "Erro de conexão: ${t.message}"
                                        showErrorDialog = true
                                    }
                                }
                            )
                        }
                    }

                    if (errorMessage.isNotEmpty() && !isLoading) {
                        showErrorDialog = true
                    }
                },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp
                )
            ) {
                if (isLoading) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.5.dp
                        )
                        Text(
                            "Cadastrando...",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                } else {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White
                        )
                        Text(
                            "Cadastrar Produto",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Diálogo de sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                Button(
                    onClick = {
                        showSuccessDialog = false
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color(0xFF25992E),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { 
                Text(
                    "Sucesso!", 
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                ) 
            },
            text = { 
                Text(
                    "Produto cadastrado com sucesso!",
                    style = MaterialTheme.typography.bodyLarge
                ) 
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp)
        )
    }

    // Diálogo de erro
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                Button(
                    onClick = {
                        showErrorDialog = false
                        errorMessage = ""
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("OK", fontWeight = FontWeight.Bold)
                }
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = null,
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(48.dp)
                )
            },
            title = { 
                Text(
                    "Erro", 
                    fontWeight = FontWeight.Bold, 
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.titleLarge
                ) 
            },
            text = { 
                Text(
                    errorMessage,
                    style = MaterialTheme.typography.bodyLarge
                ) 
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaCadastroProdutoPreview() {
    MaterialTheme {
        TelaCadastroProduto(navController = rememberNavController())
    }
}
