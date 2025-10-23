package com.example.infohub_telas.telas

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
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
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.model.Estabelecimento
import com.example.infohub_telas.service.RetrofitFactory
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEstabelecimento(navController: NavController?) {
    var nomeEstabelecimento by remember { mutableStateOf("") }
    var cnpj by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var categoriaExpandida by remember { mutableStateOf(false) }
    val categorias = listOf("Alimentação", "Varejo", "Serviços", "Saúde", "Educação", "Outros")
    var categoriaSelecionada by remember { mutableStateOf(categorias[0]) }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val primaryOrange = Color(0xFFFF8C00)
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val estabelecimentoApi = RetrofitFactory().getInfoHub_EstabelecimentoService()

    Scaffold(
        topBar = { Header(title = "Cadastro de Estabelecimento") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(lightGray)
                .padding(paddingValues)
                .padding(horizontal = 32.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Preencha os dados do seu estabelecimento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            CustomTextField(
                value = nomeEstabelecimento,
                onValueChange = { nomeEstabelecimento = it },
                placeholder = "Nome do Estabelecimento*",
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            CustomTextField(
                value = cnpj,
                onValueChange = { cnpj = it.filter { c -> c.isDigit() }.take(14) },
                placeholder = "CNPJ*",
                keyboardType = KeyboardType.Number,
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            CustomTextField(
                value = endereco,
                onValueChange = { endereco = it },
                placeholder = "Endereço*",
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            CustomTextField(
                value = telefone,
                onValueChange = { telefone = it.filter { c -> c.isDigit() }.take(11) },
                placeholder = "Telefone*",
                keyboardType = KeyboardType.Phone,
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                ),
                visualTransformation = telefoneVisualTransformation
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "E-mail de Contato*",
                keyboardType = KeyboardType.Email,
                textFieldColors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = textFieldBackground,
                    unfocusedContainerColor = textFieldBackground
                )
            )

            // Dropdown para Categoria
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
                    categorias.forEach { categoria ->
                        DropdownMenuItem(
                            text = { Text(categoria) },
                            onClick = {
                                categoriaSelecionada = categoria
                                categoriaExpandida = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    // Validações
                    if (nomeEstabelecimento.isBlank() || cnpj.isBlank() || endereco.isBlank() ||
                        telefone.isBlank() || email.isBlank() || categoriaSelecionada.isBlank()
                    ) {
                        errorMessage = "Preencha todos os campos obrigatórios."
                        showErrorDialog = true
                        return@Button
                    }

                    val (telValido, telMsg) = validarTelefone(telefone)
                    if (!telValido) {
                        errorMessage = telMsg
                        showErrorDialog = true
                        return@Button
                    }

                    if (!validarEmail(email)) {
                        errorMessage = "E-mail inválido. Use apenas Gmail, Hotmail ou Yahoo e não deixe vazio."
                        showErrorDialog = true
                        return@Button
                    }

                    if (!validarCNPJ(cnpj)) {
                        errorMessage = "CNPJ inválido. Deve conter 14 dígitos."
                        showErrorDialog = true
                        return@Button
                    }

                    val estabelecimento = Estabelecimento(
                        nome = nomeEstabelecimento,
                        cnpj = cnpj,
                        endereco = endereco,
                        telefone = telefone,
                        email = email,
                        categoria = categoriaSelecionada
                    )

                    Log.d("DEBUG", "Enviando estabelecimento -> $estabelecimento")

                    estabelecimentoApi.cadastrarEstabelecimento(estabelecimento).enqueue(object : Callback<Estabelecimento> {
                        override fun onResponse(call: Call<Estabelecimento>, response: Response<Estabelecimento>) {
                            if (response.isSuccessful) {
                                Log.d("API", "Estabelecimento cadastrado: ${response.body()}")
                                showSuccessDialog = true
                            } else {
                                Log.e("API", "Erro: ${response.code()} - ${response.message()}")
                                errorMessage = "Erro ao cadastrar estabelecimento. Tente novamente."
                                showErrorDialog = true
                            }
                        }

                        override fun onFailure(call: Call<Estabelecimento>, t: Throwable) {
                            Log.e("API", "Falha na requisição: ${t.message}")
                            errorMessage = "Falha na conexão. Verifique sua internet."
                            showErrorDialog = true
                        }
                    })
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Cadastrar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Diálogo de sucesso
        if (showSuccessDialog) {
            AlertDialog(
                onDismissRequest = { showSuccessDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        showSuccessDialog = false
                        // Navegação corrigida - certifique-se que a rota existe no NavHost
                        navController?.navigate("homeJuridico") {
                            popUpTo("cadastroEstabelecimento") { inclusive = true }
                        }
                    }) {
                        Text("OK")
                    }
                },
                title = { Text("Sucesso") },
                text = { Text("Estabelecimento cadastrado com sucesso!") }
            )
        }

        // Diálogo de erro
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { showErrorDialog = false },
                confirmButton = {
                    TextButton(onClick = { showErrorDialog = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Erro") },
                text = { Text(errorMessage) }
            )
        }
    }
}

// ==================== FUNÇÕES AUXILIARES ====================

/**
 * TextField customizado reutilizável
 * Facilita a criação de campos de texto com estilo consistente
 */
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors()
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(placeholder) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        colors = textFieldColors,
        shape = RoundedCornerShape(28.dp),
        singleLine = true
    )
}

/**
 * Valida o telefone
 * Retorna um Pair com (isValido, mensagemDeErro)
 */
fun validarTelefone(telefone: String): Pair<Boolean, String> {
    return when {
        telefone.length < 10 -> Pair(false, "Telefone deve ter no mínimo 10 dígitos (com DDD)")
        telefone.length > 11 -> Pair(false, "Telefone deve ter no máximo 11 dígitos")
        else -> Pair(true, "")
    }
}

/**
 * Valida o email
 * Aceita apenas Gmail, Hotmail e Yahoo
 */
fun validarEmail(email: String): Boolean {
    val dominiosValidos = listOf("gmail.com", "hotmail.com", "yahoo.com", "outlook.com")

    // Verifica se o email está no formato correto
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()

    if (!email.matches(emailRegex)) return false

    // Verifica se termina com um dos domínios válidos
    return dominiosValidos.any { email.endsWith(it, ignoreCase = true) }
}

/**
 * Valida o CNPJ
 * Verifica se tem exatamente 14 dígitos
 */
fun validarCNPJ(cnpj: String): Boolean {
    return cnpj.length == 14 && cnpj.all { it.isDigit() }
}

/**
 * Máscara visual para telefone
 * Formata automaticamente: (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
 */
val telefoneVisualTransformation = VisualTransformation { text ->
    val numbers = text.text.filter { it.isDigit() }
    val masked = buildString {
        numbers.forEachIndexed { i, c ->
            when (i) {
                0 -> append("(").append(c)
                1 -> append(c).append(") ")
                6 -> if (numbers.length == 11) append(c) else append("-").append(c)
                7 -> if (numbers.length == 11) append("-").append(c) else append(c)
                else -> append(c)
            }
        }
    }

    val offsetMapping = object : androidx.compose.ui.text.input.OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            var transformed = offset
            if (offset > 1) transformed += 1 // para o ') '
            if (numbers.length == 11) {
                if (offset > 6) transformed += 1 // para o '-' no celular
            } else {
                if (offset > 5) transformed += 1 // para o '-' no fixo
            }
            return transformed.coerceAtMost(masked.length)
        }

        override fun transformedToOriginal(offset: Int): Int {
            var original = offset
            if (offset > 2) original -= 1
            if (numbers.length == 11) {
                if (offset > 8) original -= 1
            } else {
                if (offset > 7) original -= 1
            }
            return original.coerceAtMost(numbers.length)
        }
    }

    androidx.compose.ui.text.input.TransformedText(AnnotatedString(masked), offsetMapping)
}

@Preview(showSystemUi = true)
@Composable
fun TelaCadastroEstabelecimentoPreview() {
    InfoHub_telasTheme {
        TelaCadastroEstabelecimento(null)
    }
}