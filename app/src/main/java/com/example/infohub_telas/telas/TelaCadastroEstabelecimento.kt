package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
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
import com.example.infohub_telas.viewmodel.EstabelecimentoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEstabelecimento(
    navController: NavController,
    estabelecimentoId: String? = null,
    viewModel: EstabelecimentoViewModel = viewModel()
) {
    // Estados do formulário
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }

    // Estados dos diálogos
    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    // Estados do ViewModel
    val isLoading by viewModel.isLoading.observeAsState(false)
    val createResult by viewModel.createResult.observeAsState()
    val updateResult by viewModel.updateResult.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Verificar se é edição
    val isEditing = estabelecimentoId != null

    // Cores da UI
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    // Tratar resultados do ViewModel
    LaunchedEffect(createResult) {
        createResult?.onSuccess {
            dialogMessage = "Estabelecimento criado com sucesso!"
            showSuccessDialog = true
            viewModel.clearResults()
        }?.onFailure {
            // Erro já tratado pelo errorMessage
        }
    }

    LaunchedEffect(updateResult) {
        updateResult?.onSuccess {
            dialogMessage = "Estabelecimento atualizado com sucesso!"
            showSuccessDialog = true
            viewModel.clearResults()
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

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = if (isEditing) "Editar Estabelecimento" else "Cadastro de Estabelecimento",
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
                text = "Dados do Estabelecimento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25992E),
                modifier = Modifier.padding(vertical = 8.dp)
            )

            OutlinedTextField(
                value = nomeEstabelecimento,
                onValueChange = { nomeEstabelecimento = it },
                label = { Text("Nome do Estabelecimento*") },
                placeholder = { Text("Ex: Supermercado Central") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = cnpj,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.length <= 14) {
                        cnpj = when {
                            digitsOnly.length <= 2 -> digitsOnly
                            digitsOnly.length <= 5 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2)}"
                            digitsOnly.length <= 8 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5)}"
                            digitsOnly.length <= 12 -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5, 8)}/${digitsOnly.substring(8)}"
                            else -> "${digitsOnly.substring(0, 2)}.${digitsOnly.substring(2, 5)}.${digitsOnly.substring(5, 8)}/${digitsOnly.substring(8, 12)}-${digitsOnly.substring(12)}"
                        }
                    }
                },
                label = { Text("CNPJ*") },
                placeholder = { Text("12.345.678/0001-90") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )
            
            OutlinedTextField(
                value = telefone,
                onValueChange = { newValue ->
                    val digitsOnly = newValue.filter { it.isDigit() }
                    if (digitsOnly.length <= 11) {
                        telefone = when {
                            digitsOnly.length <= 2 -> "($digitsOnly"
                            digitsOnly.length <= 6 -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2)}"
                            else -> "(${digitsOnly.substring(0, 2)}) ${digitsOnly.substring(2, 7)}-${digitsOnly.substring(7)}"
                        }
                    }
                },
                label = { Text("Telefone (opcional)") },
                placeholder = { Text("(11) 3333-4444") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF25992E),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (isEditing) {
                        val id = estabelecimentoId?.toIntOrNull()
                        if (id != null) {
                            viewModel.atualizarEstabelecimento(
                                id = id,
                                nome = nomeEstabelecimento,
                                cnpj = cnpj,
                                telefone = telefone.ifBlank { null }
                            )
                        }
                    } else {
                        viewModel.criarEstabelecimento(
                            nome = nomeEstabelecimento,
                            cnpj = cnpj,
                            telefone = telefone.ifBlank { null }
                        )
                    }
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = if (isEditing) "Atualizar" else "Cadastrar",
                        color = Color.White,
                        fontSize = 18.sp,
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
                viewModel.clearErrorMessage()
            }
        )
    }
}
