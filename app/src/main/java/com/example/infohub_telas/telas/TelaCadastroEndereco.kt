package com.example.infohub_telas.telas

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infohub_telas.R
import com.example.infohub_telas.model.EnderecoViaCep
import com.example.infohub_telas.service.RetroFitFactoryVIACEP
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaCadastroEndereco(navController: NavController?) {
    var cep by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var complemento by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    var isLoadingCep by remember { mutableStateOf(false) }

    val primaryOrange = Color(0xFFFF8C00)
    val lightGray = Color(0xFFF0F0F0)
    val textFieldBackground = Color.White

    val viaCepApi = RetroFitFactoryVIACEP().getViaCepService()

    // 🔹 Efeito que dispara a busca no ViaCEP quando o CEP tiver 8 dígitos
    LaunchedEffect(cep) {
        if (cep.length == 8) {
            isLoadingCep = true
            try {
                val response = viaCepApi.buscarCep(cep)
                if (response.isSuccessful) {
                    val endereco = response.body()
                    if (endereco != null) {
                        rua = endereco.logradouro ?: ""
                        bairro = endereco.bairro ?: ""
                        cidade = endereco.localidade ?: ""
                        estado = endereco.uf ?: ""
                    } else {
                        Log.e("ViaCEP", "CEP não retornou dados")
                    }
                } else {
                    Log.e("ViaCEP", "Erro HTTP: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("ViaCEP", "Falha ao buscar CEP: ${e.message}")
            } finally {
                isLoadingCep = false
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(lightGray)
    ) {
        // Decoração
        Image(
            painter = painterResource(id = R.drawable.bola_cadastro_vermelho),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = (-40).dp, y = 30.dp)
                .size(70.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.bola_laranja_cadastro),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 40.dp, y = 0.dp)
                .size(130.dp)
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.cadastro),
                contentDescription = "Cadastro",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Cadastro de Endereço",
                        fontSize = 18.sp,
                    )
                    Box(
                        modifier = Modifier
                            .width(90.dp)
                            .height(3.dp)
                            .background(primaryOrange, RoundedCornerShape(2.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Formulário
            Column(modifier = Modifier.padding(horizontal = 32.dp)) {
                // 🔸 Campo CEP com loading
                Box {
                    CustomTextField(
                        value = cep,
                        onValueChange = { cep = it.filter { c -> c.isDigit() }.take(8) },
                        placeholder = "CEP*",
                        keyboardType = KeyboardType.Number
                    )
                    if (isLoadingCep) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = 16.dp)
                                .size(24.dp),
                            color = primaryOrange,
                            strokeWidth = 2.dp
                        )
                    }
                }

                // 🔸 Campos preenchidos automaticamente
                CustomTextField(
                    value = rua,
                    onValueChange = { rua = it },
                    placeholder = "Rua*"
                )
                CustomTextField(
                    value = numero,
                    onValueChange = { numero = it },
                    placeholder = "Número*"
                )
                CustomTextField(
                    value = complemento,
                    onValueChange = { complemento = it },
                    placeholder = "Complemento"
                )
                CustomTextField(
                    value = bairro,
                    onValueChange = { bairro = it },
                    placeholder = "Bairro*"
                )
                CustomTextField(
                    value = cidade,
                    onValueChange = { cidade = it },
                    placeholder = "Cidade*"
                )
                CustomTextField(
                    value = estado,
                    onValueChange = { estado = it },
                    placeholder = "Estado*"
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (cep.isBlank() || rua.isBlank() || numero.isBlank() ||
                        bairro.isBlank() || cidade.isBlank() || estado.isBlank()) {
                        errorMessage = "Preencha todos os campos obrigatórios."
                        showErrorDialog = true
                        return@Button
                    }

                    showSuccessDialog = true
                },
                modifier = Modifier
                    .width(220.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E)),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "Cadastrar Endereço",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(text = "Já tem uma conta? ", fontSize = 14.sp, color = Color.Black)
                Text(
                    text = "Faça login",
                    fontSize = 14.sp,
                    color = primaryOrange,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable { navController?.navigate("login") }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(20.dp)
                .background(primaryOrange, RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
        )
    }

    // Diálogo de sucesso
    if (showSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    showSuccessDialog = false
                    navController?.navigate("login")
                }) {
                    Text("OK")
                }
            },
            title = { Text("Sucesso") },
            text = { Text("Endereço cadastrado com sucesso!") }
        )
    }

    // Diálogo de erro
    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) { Text("OK") }
            },
            title = { Text("Erro") },
            text = { Text(errorMessage) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textFieldColors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    validator: ((String) -> Pair<Boolean, String>)? = null,
    showError: Boolean = true
) {
    var errorText by remember { mutableStateOf("") }
    val shape = RoundedCornerShape(12.dp)

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                validator?.let { validate ->
                    val (valid, message) = validate(it)
                    errorText = if (!valid && showError) message else ""
                }
            },
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
                .shadow(elevation = 4.dp, shape = shape)
                .clip(shape)
                .background(Color.White),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            visualTransformation = visualTransformation,
            isError = errorText.isNotEmpty(),
            shape = shape
        )
        if (errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 12.dp, top = 2.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaCadastroEnderecoPreview() {
    InfoHub_telasTheme {
        TelaCadastroEndereco(null)
    }
}