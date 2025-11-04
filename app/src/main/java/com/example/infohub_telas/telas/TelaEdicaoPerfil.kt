package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

// Cores do tema
private val OrangeColor = Color(0xFFF9A01B)
private val LightGrayColor = Color(0xFFF7F7F7)
private val TextGrayColor = Color(0xFF888888)
private val DarkGrayColor = Color(0xFF424242)
private val GreenColor = Color(0xFF4CAF50)
private val RedColor = Color(0xFFE53935)

// Data class para os dados do perfil
data class PerfilData(
    val nome: String = "ISRAEL JUNIOR",
    val email: String = "raraeldev@gmail.com",
    val telefone: String = "(11) 99999-9999",
    val cidade: String = "São Paulo",
    val estado: String = "SP",
    val iniciais: String = "iJ"
)

@Composable
fun TelaEdicaoPerfil(
    navController: NavController?,
    perfilData: PerfilData = PerfilData()
) {
    var nome by remember { mutableStateOf(TextFieldValue(perfilData.nome)) }
    var email by remember { mutableStateOf(TextFieldValue(perfilData.email)) }
    var telefone by remember { mutableStateOf(TextFieldValue(perfilData.telefone)) }
    var cidade by remember { mutableStateOf(TextFieldValue(perfilData.cidade)) }
    var estado by remember { mutableStateOf(TextFieldValue(perfilData.estado)) }
    var isLoading by remember { mutableStateOf(false) }
    var showSuccessMessage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { EdicaoPerfilTopBar(navController) },
        containerColor = LightGrayColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Seção do Avatar
            AvatarEditSection(perfilData.iniciais)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Formulário de edição
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        "Informações Pessoais",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = DarkGrayColor
                    )
                    
                    // Campo Nome
                    EditTextField(
                        value = nome,
                        onValueChange = { nome = it },
                        label = "Nome Completo",
                        leadingIcon = Icons.Default.Person
                    )
                    
                    // Campo Email
                    EditTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "E-mail",
                        leadingIcon = Icons.Default.Email
                    )
                    
                    // Campo Telefone
                    EditTextField(
                        value = telefone,
                        onValueChange = { telefone = it },
                        label = "Telefone",
                        leadingIcon = Icons.Default.Phone
                    )
                    
                    // Campos de Localização
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        EditTextField(
                            value = cidade,
                            onValueChange = { cidade = it },
                            label = "Cidade",
                            leadingIcon = Icons.Default.LocationOn,
                            modifier = Modifier.weight(2f)
                        )
                        
                        EditTextField(
                            value = estado,
                            onValueChange = { estado = it },
                            label = "Estado",
                            leadingIcon = null,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Botão Cancelar
                OutlinedButton(
                    onClick = { navController?.popBackStack() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextGrayColor
                    )
                ) {
                    Text("Cancelar")
                }
                
                // Botão Salvar
                Button(
                    onClick = {
                        isLoading = true
                        // TODO: Implementar salvamento via API
                        // Simular delay da API
                        // Em produção, fazer chamada real para API
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(1500)
                            isLoading = false
                            showSuccessMessage = true
                            delay(2000)
                            navController?.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = OrangeColor),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Salvar", color = Color.White)
                    }
                }
            }
            
            // Mensagem de sucesso
            if (showSuccessMessage) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = GreenColor.copy(alpha = 0.1f)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = "Sucesso",
                            tint = GreenColor
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Perfil atualizado com sucesso!",
                            color = GreenColor,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EdicaoPerfilTopBar(navController: NavController?) {
    TopAppBar(
        title = {
            Text(
                "Editar Perfil",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController?.popBackStack() }) {
                Icon(
                    ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = OrangeColor
        )
    )
}

@Composable
fun AvatarEditSection(iniciais: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.size(120.dp)
        ) {
            // Avatar principal
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(OrangeColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = iniciais,
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Botão de editar foto
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color.White)
                    .clickable {
                        // TODO: Implementar seleção de foto
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.CameraAlt,
                    contentDescription = "Alterar Foto",
                    tint = OrangeColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            "Toque para alterar a foto",
            color = TextGrayColor,
            fontSize = 12.sp
        )
    }
}

@Composable
fun EditTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector?,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    leadingIcon,
                    contentDescription = label,
                    tint = OrangeColor
                )
            }
        } else null,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = OrangeColor,
            focusedLabelColor = OrangeColor,
            cursorColor = OrangeColor
        ),
        singleLine = true
    )
}

@Preview(showBackground = true, name = "Tela Edição Perfil")
@Composable
fun TelaEdicaoPerfilPreview() {
    MaterialTheme {
        TelaEdicaoPerfil(navController = rememberNavController())
    }
}
