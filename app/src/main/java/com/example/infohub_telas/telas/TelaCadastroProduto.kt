package com.example.infohub_telas.telas

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
import androidx.compose.material3.MenuAnchorType
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.utils.AppUtils
import com.example.infohub_telas.viewmodel.ProdutoJuridicoViewModel
import com.example.infohub_telas.viewmodel.EstabelecimentoViewModel
import com.example.infohub_telas.network.models.PromocaoRequest
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroProduto(
    navController: NavController,
    produtoId: String? = null,
    produtoViewModel: ProdutoJuridicoViewModel = viewModel(),
    estabelecimentoViewModel: EstabelecimentoViewModel = viewModel()
) {
    // Estados do formulário
    var nomeProduto by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var categoriaSelecionada by remember { mutableStateOf<Int?>(null) }
    var estabelecimentoSelecionado by remember { mutableStateOf<Int?>(null) }

    // Estados da promoção (opcional)
    var temPromocao by remember { mutableStateOf(false) }
    var precoPromocional by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf("") }
    var dataFim by remember { mutableStateOf("") }

    // Estados dos diálogos
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Estados dos ViewModels
    val isLoading by produtoViewModel.isLoading.observeAsState(false)
    val createResult by produtoViewModel.createResult.observeAsState()
    val errorMessage by produtoViewModel.errorMessage.observeAsState()
    val categorias by produtoViewModel.categorias.observeAsState(emptyList())
    val estabelecimentos by estabelecimentoViewModel.estabelecimentos.observeAsState(emptyList())

    // Verificar se é edição
    val isEditing = produtoId != null

    // Cores da UI
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White
    val primaryGreen = Color(0xFF25992E)

    // Tratar resultados
    LaunchedEffect(createResult) {
        createResult?.onSuccess {
            dialogMessage = "Produto criado com sucesso!"
            showSuccessDialog = true
            produtoViewModel.clearResults()
        }?.onFailure {
            // Erro já tratado pelo errorMessage
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { error ->
            dialogMessage = error
            showErrorDialog = true
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(
            title = if (isEditing) "Editar Produto" else "Cadastro de Produto",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Informações do Produto",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = primaryGreen,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Nome do produto
            OutlinedTextField(
                value = nomeProduto,
                onValueChange = { nomeProduto = it },
                label = { Text("Nome do Produto*") },
                placeholder = { Text("Ex: iPhone 15 Pro Max") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            // Descrição
            OutlinedTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição (opcional)") },
                placeholder = { Text("Descreva as características do produto...") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                maxLines = 5,
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            // Preço
            OutlinedTextField(
                value = preco,
                onValueChange = { newValue ->
                    // Permitir apenas números e ponto decimal
                    val filtered = newValue.filter { it.isDigit() || it == '.' }
                    if (filtered.count { it == '.' } <= 1) {
                        preco = filtered
                    }
                },
                label = { Text("Preço*") },
                placeholder = { Text("99.90") },
                leadingIcon = { Text("R$", color = primaryGreen, fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            // Seletor de categoria
            var expandedCategoria by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedCategoria,
                onExpandedChange = { expandedCategoria = it }
            ) {
                OutlinedTextField(
                    value = categorias.find { it.idCategoria == categoriaSelecionada }?.nome ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Categoria*") },
                    placeholder = { Text("Selecione uma categoria") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCategoria) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )

                ExposedDropdownMenu(
                    expanded = expandedCategoria,
                    onDismissRequest = { expandedCategoria = false }
                ) {
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria.nome) },
                            onClick = {
                                categoriaSelecionada = categoria.idCategoria
                                expandedCategoria = false
                            }
                        )
                    }
                }
            }

            // Seletor de estabelecimento
            var expandedEst by remember { mutableStateOf(false) }

            ExposedDropdownMenuBox(
                expanded = expandedEst,
                onExpandedChange = { expandedEst = it }
            ) {
                OutlinedTextField(
                    value = estabelecimentos.find { it.idEstabelecimento == estabelecimentoSelecionado }?.nome ?: "",
                    onValueChange = { },
                    readOnly = true,
                    label = { Text("Estabelecimento*") },
                    placeholder = { Text("Selecione um estabelecimento") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEst) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = primaryGreen,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )


                ExposedDropdownMenu(
                    expanded = expandedEst,
                    onDismissRequest = { expandedEst = false }
                ) {
                    estabelecimentos.forEach { estabelecimento ->
                        DropdownMenuItem(
                            text = { Text(estabelecimento.nome) },
                            onClick = {
                                estabelecimentoSelecionado = estabelecimento.idEstabelecimento
                                expandedEst = false
                            }
                        )
                    }
                }
            }

            // Seção de promoção (opcional)
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = textFieldBackground),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Switch(
                            checked = temPromocao,
                            onCheckedChange = { temPromocao = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = primaryGreen)
                        )
                        Text(
                            text = "Produto em promoção",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    if (temPromocao) {
                        Spacer(modifier = Modifier.height(12.dp))

                        // Preço promocional
                        OutlinedTextField(
                            value = precoPromocional,
                            onValueChange = { newValue ->
                                val filtered = newValue.filter { it.isDigit() || it == '.' }
                                if (filtered.count { it == '.' } <= 1) {
                                    precoPromocional = filtered
                                }
                            },
                            label = { Text("Preço Promocional*") },
                            placeholder = { Text("79.90") },
                            leadingIcon = { Text("R$", color = primaryGreen, fontWeight = FontWeight.Bold) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Data de início
                        OutlinedTextField(
                            value = dataInicio,
                            onValueChange = { dataInicio = it },
                            label = { Text("Data de Início") },
                            placeholder = { Text("2024-01-01") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Data de fim
                        OutlinedTextField(
                            value = dataFim,
                            onValueChange = { dataFim = it },
                            label = { Text("Data de Fim") },
                            placeholder = { Text("2024-12-31") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de cadastrar
            Button(
                onClick = {
                    val promocao = if (temPromocao && precoPromocional.isNotBlank()) {
                        PromocaoRequest(
                            precoPromocional = precoPromocional.toDoubleOrNull() ?: 0.0,
                            dataInicio = dataInicio.ifBlank { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) },
                            dataFim = dataFim.ifBlank { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) }
                        )
                    } else null

                    produtoViewModel.criarProduto(
                        nome = nomeProduto,
                        descricao = descricao.ifBlank { null },
                        categoriaId = categoriaSelecionada ?: 0,
                        estabelecimentoId = estabelecimentoSelecionado ?: 0,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        promocao = promocao
                    )
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = primaryGreen),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = if (isEditing) "Atualizar Produto" else "Cadastrar Produto",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }

    // Diálogos
    if (showSuccessDialog) {
        AppUtils.SuccessDialog(
            message = dialogMessage,
            onDismiss = {
                showSuccessDialog = false
                navController.popBackStack()
            }
        )
    }

    if (showErrorDialog) {
        AppUtils.ErrorDialog(
            message = dialogMessage,
            onDismiss = {
                showErrorDialog = false
                produtoViewModel.clearErrorMessage()
            }
        )
    }
}
