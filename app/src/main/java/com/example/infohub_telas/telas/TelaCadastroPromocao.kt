package com.example.infohub_telas.telas

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.CategoryDropdown
import com.example.infohub_telas.components.ImageUpload
import com.example.infohub_telas.components.MyTopAppBar
import com.example.infohub_telas.components.StyledCard
import com.example.infohub_telas.components.StyledDatePicker
import com.example.infohub_telas.components.StyledTextField
import com.example.infohub_telas.model.PromocaoProduto
import com.example.infohub_telas.model.StatusPromocao
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroPromocao(navController: NavController) {
    var nome by remember { mutableStateOf("") }
    var precoPromocional by remember { mutableStateOf("") }
    var dataInicio by remember { mutableStateOf<Long?>(null) }
    var dataTermino by remember { mutableStateOf<Long?>(null) }
    var descricao by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var categoriaExpandida by remember { mutableStateOf(false) }
    val categorias = listOf("Alimentação", "Varejo", "Serviços", "Saúde", "Educação", "Outros")
    var categoriaSelecionada by remember { mutableStateOf(categorias[0]) }
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? -> imageUri = uri }
    )

    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Cadastro de Promoção",
                navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
                onNavigationIconClick = { navController.popBackStack() }
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nome do Produto
            StyledTextField(value = nome, onValueChange = { nome = it }, placeholder = "Nome do Produto", icon = Icons.Default.ShoppingBag)

            // Card para Categoria e Foto
            StyledCard {
                Text("Categoria", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(12.dp))
                CategoryDropdown(
                    expanded = categoriaExpandida,
                    onExpandedChange = { categoriaExpandida = it },
                    selectedCategory = categoriaSelecionada,
                    onCategorySelected = { categoriaSelecionada = it },
                    categories = categorias
                )
                Spacer(modifier = Modifier.height(16.dp))
                ImageUpload(imageUri = imageUri, onClick = { imagePickerLauncher.launch("image/*") })
            }

            // Preço Promocional
            StyledTextField(
                value = precoPromocional,
                onValueChange = { value ->
                    if (value.isEmpty() || value.matches(Regex("^\\d*[,.]?\\d*$"))) {
                        precoPromocional = value
                    }
                },
                placeholder = "Preço Promocional (ex: 29.90)",
                icon = Icons.Default.LocalOffer,
                keyboardType = KeyboardType.Decimal
            )

            // Datas
            StyledDatePicker(label = "Data de Início", selectedDate = dataInicio, onDateSelected = { dataInicio = it })
            StyledDatePicker(label = "Data de Término", selectedDate = dataTermino, onDateSelected = { dataTermino = it })

            // Descrição
            StyledCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, tint = Color.Gray)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Descrição da Promoção", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                Text("Destaque os principais benefícios e diferenciais da promoção", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    placeholder = { Text("Insira a descrição aqui...") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent
                    )
                )
            }

            Spacer(modifier = Modifier.weight(1f, fill = false))

            // Botão Salvar
            Button(
                onClick = {
                    // Validação dos campos
                    when {
                        nome.isBlank() -> {
                            Toast.makeText(context, "Preencha o nome do produto", Toast.LENGTH_SHORT).show()
                        }
                        precoPromocional.isBlank() -> {
                            Toast.makeText(context, "Preencha o preço promocional", Toast.LENGTH_SHORT).show()
                        }
                        precoPromocional.replace(",", ".").toDoubleOrNull() == null -> {
                            Toast.makeText(context, "Preço inválido", Toast.LENGTH_SHORT).show()
                        }
                        dataInicio == null -> {
                            Toast.makeText(context, "Selecione a data de início", Toast.LENGTH_SHORT).show()
                        }
                        dataTermino == null -> {
                            Toast.makeText(context, "Selecione a data de término", Toast.LENGTH_SHORT).show()
                        }
                        dataTermino!! < dataInicio!! -> {
                            Toast.makeText(context, "Data de término deve ser após a data de início", Toast.LENGTH_SHORT).show()
                        }
                        descricao.isBlank() -> {
                            Toast.makeText(context, "Preencha a descrição", Toast.LENGTH_SHORT).show()
                        }
                        imageUri == null -> {
                            Toast.makeText(context, "Adicione uma imagem do produto", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            val novaPromocao = PromocaoProduto(
                                nome = nome,
                                categoria = categoriaSelecionada,
                                precoPromocional = precoPromocional.replace(",", "."),
                                dataInicio = Date(dataInicio!!),
                                dataTermino = Date(dataTermino!!),
                                descricao = descricao,
                                imagemUrl = imageUri.toString(),
                                status = StatusPromocao.ATIVA
                            )
                            Log.d("CadastroPromocao", "Promoção Criada: $novaPromocao")
                            Toast.makeText(context, "Promoção salva com sucesso!", Toast.LENGTH_SHORT).show()

                            navController.popBackStack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF2811D))
            ) {
                Text("Salvar Promoção", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Preview(showSystemUi = true, backgroundColor = 0xFFF5F5F5)
@Composable
fun TelaCadastroPromocaoFinalPreview() {
    InfoHub_telasTheme {
        TelaCadastroPromocao(rememberNavController())
    }
}
