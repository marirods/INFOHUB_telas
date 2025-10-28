package com.example.infohub_telas.telas

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.components.CustomTextField
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.model.Empresa
import com.example.infohub_telas.ui.theme.BackgroundGray
import com.example.infohub_telas.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfiguracaoPerfil(
    navController: NavController,
    empresa: Empresa,
    onSaveChanges: (Empresa) -> Unit
) {
    var nome by remember { mutableStateOf(empresa.nome) }
    var email by remember { mutableStateOf(empresa.email) }
    var telefone by remember { mutableStateOf(empresa.telefone) }
    var endereco by remember { mutableStateOf(empresa.endereco) }
    var descricao by remember { mutableStateOf(empresa.descricao) }
    var logoUri by remember { mutableStateOf<Uri?>(null) }

    // Validação de campos
    var nomeError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var telefoneError by remember { mutableStateOf(false) }
    var enderecoError by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    // Image picker launcher
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { logoUri = it }
    }

    fun validateFields(): Boolean {
        nomeError = nome.isBlank()
        emailError = email.isBlank() || !email.contains("@")
        telefoneError = telefone.isBlank()
        enderecoError = endereco.isBlank()

        return !(nomeError || emailError || telefoneError || enderecoError)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Header(title = "Configuração do Perfil")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(BackgroundGray)
        ) {
            // Logo section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(PrimaryOrange),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Business,
                            contentDescription = "Logo da empresa",
                            tint = Color.White,
                            modifier = Modifier.size(64.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { imagePicker.launch("image/*") },
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Icon(
                            Icons.Default.PhotoCamera,
                            contentDescription = null,
                            tint = PrimaryOrange,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text("Alterar Logo")
                    }
                }
            }

            // Form fields
            CustomTextField(
                value = nome,
                onValueChange = { nome = it; nomeError = false },
                label = "Nome da Empresa",
                icon = Icons.Default.Business,
                isError = nomeError,
                errorMessage = "Nome é obrigatório"
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it; emailError = false },
                label = "E-mail",
                icon = Icons.Default.Email,
                isError = emailError,
                errorMessage = "E-mail inválido",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            CustomTextField(
                value = telefone,
                onValueChange = { telefone = it; telefoneError = false },
                label = "Telefone",
                icon = Icons.Default.Phone,
                isError = telefoneError,
                errorMessage = "Telefone é obrigatório",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            CustomTextField(
                value = endereco,
                onValueChange = { endereco = it; enderecoError = false },
                label = "Endereço",
                icon = Icons.Default.LocationOn,
                isError = enderecoError,
                errorMessage = "Endereço é obrigatório"
            )

            CustomTextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = "Descrição do Negócio",
                icon = Icons.Default.Description,
                maxLines = 3
            )

            // Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }

                Button(
                    onClick = {
                        if (validateFields()) {
                            val updatedEmpresa = empresa.copy(
                                nome = nome,
                                email = email,
                                telefone = telefone,
                                endereco = endereco,
                                descricao = descricao,
                                logoUrl = logoUri?.toString() ?: empresa.logoUrl
                            )
                            onSaveChanges(updatedEmpresa)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryOrange
                    )
                ) {
                    Text("Salvar")
                }
            }

            // Bottom spacing
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ConfiguracaoPerfilPreview() {
    val empresaSample = Empresa(
        nome = "Tech Solutions Ltda",
        cnpj = "12.345.678/0001-99",
        email = "contato@techsolutions.com",
        telefone = "(11) 99999-9999",
        endereco = "Rua Exemplo, 123, São Paulo",
        setor = "Tecnologia",
        descricao = "Empresa de soluções tecnológicas.",
        logoUrl = ""
    )
    Surface(modifier = Modifier.fillMaxSize()) {
        ConfiguracaoPerfil(
            navController = rememberNavController(),
            empresa = empresaSample,
            onSaveChanges = {}
        )
    }
}
