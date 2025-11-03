package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.infohub_telas.components.AppTopBar
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import com.example.infohub_telas.ui.theme.PrimaryOrange

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaTermosDeUso(navController: NavController) {
    var aceito by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = "Termos de Uso",
            navigationIcon = Icons.AutoMirrored.Filled.ArrowBack,
            onNavigationIconClick = { navController.popBackStack() }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Card com os termos
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                        .verticalScroll(scrollState)
                ) {
                    Text(
                        text = "Termos e Condições de Uso",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryOrange,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TermoSecao(
                        titulo = "1. Aceitação dos Termos",
                        conteudo = "Ao acessar e usar este aplicativo, você aceita e concorda em estar vinculado aos termos e condições aqui estabelecidos. Se você não concordar com alguma parte destes termos, não deve usar o aplicativo."
                    )

                    // ...existing sections...
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Checkbox de aceitação
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = aceito,
                    onCheckedChange = { aceito = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryOrange
                    )
                )
                Text(
                    text = "Li e aceito os Termos de Uso",
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botão de confirmação
            Button(
                onClick = {
                    if (aceito) {
                        navController.navigate("home") {
                            popUpTo("termosDeUso") { inclusive = true }
                        }
                    }
                },
                enabled = aceito,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryOrange,
                    disabledContainerColor = Color.LightGray
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Aceitar e Continuar",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun TermoSecao(titulo: String, conteudo: String) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = titulo,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = conteudo,
            fontSize = 14.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaTermosDeUsoPreview() {
    InfoHub_telasTheme {
        TelaTermosDeUso(rememberNavController())
    }
}
