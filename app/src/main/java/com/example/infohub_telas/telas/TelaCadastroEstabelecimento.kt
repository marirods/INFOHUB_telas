package com.example.infohub_telas.telas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEstabelecimento(navController: NavController, id: Int?, categoria: String?) {
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    val categorias = listOf("Alimentação", "Varejo", "Serviços", "Saúde", "Educação", "Outros")
    var categoriaSelecionada by remember { mutableStateOf(categoria ?: categorias[0]) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val estabelecimentoApi = RetrofitFactory().getInfoHub_EstabelecimentoService()

    // Se um ID for fornecido, carregue os dados do estabelecimento
    LaunchedEffect(id) {
        if (id != null && id != 0) {
            // TODO: Implementar a chamada à API para buscar os dados do estabelecimento pelo ID
            // Exemplo de como preencher os campos:
            // nomeEstabelecimento = "Nome do Estabelecimento"
            // cnpj = "12345678000199"
            // ...
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = if (id != null) "Editar Estabelecimento" else "Cadastro de Estabelecimento",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // ... (campos de texto) ...
            ExposedDropdownMenuBox(
                expanded = categoriaExpandida,
                onExpandedChange = { categoriaExpandida = !categoriaExpandida },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = categoriaSelecionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Categoria do Negócio*") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoriaExpandida) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(28.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = textFieldBackground,
                        unfocusedContainerColor = textFieldBackground
                    )
                )
                ExposedDropdownMenu(
                    expanded = categoriaExpandida,
                    onDismissRequest = { categoriaExpandida = false }
                ) {
                    categorias.forEach { cat ->
                        DropdownMenuItem(
                            text = { Text(cat) },
                            onClick = {
                                categoriaSelecionada = cat
                                categoriaExpandida = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    // ... (lógica de validação e salvamento) ...
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = if (id != null) "Salvar Alterações" else "Cadastrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // ... (Diálogos de sucesso e erro) ...
    }
}
// ... (Restante do código: CustomTextField, validações, preview, etc) ...
