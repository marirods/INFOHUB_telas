package com.example.infohub_telas.telas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.infohub_telas.components.BottomMenu
import com.example.infohub_telas.components.Header
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaTermosDeUso(navController: NavController) {
    var aceito by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { Header(title = "Termos de Uso") },
        bottomBar = { BottomMenu(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    TermoSecao(
                        titulo = "1. Aceitação dos Termos",
                        conteudo = "Ao acessar e usar este aplicativo, você aceita e concorda em estar vinculado aos termos e condições aqui estabelecidos. Se você não concordar com alguma parte destes termos, não deve usar o aplicativo."
                    )

                    TermoSecao(
                        titulo = "2. Uso do Aplicativo",
                        conteudo = "O InfoHub é um aplicativo destinado a fornecer informações e serviços diversos. Você concorda em usar o aplicativo apenas para fins legais e de acordo com estes termos."
                    )

                    TermoSecao(
                        titulo = "3. Privacidade e Dados",
                        conteudo = "Respeitamos sua privacidade. As informações coletadas serão utilizadas apenas para melhorar sua experiência no aplicativo. Seus dados de localização serão usados apenas quando você solicitar funcionalidades específicas."
                    )

                    TermoSecao(
                        titulo = "4. Responsabilidades do Usuário",
                        conteudo = "Você é responsável por manter a confidencialidade de suas informações de conta e por todas as atividades que ocorram sob sua conta. Você concorda em notificar-nos imediatamente sobre qualquer uso não autorizado."
                    )

                    TermoSecao(
                        titulo = "5. Propriedade Intelectual",
                        conteudo = "Todo o conteúdo presente no aplicativo, incluindo textos, gráficos, logos e software, é propriedade do InfoHub e está protegido por leis de direitos autorais."
                    )

                    TermoSecao(
                        titulo = "6. Limitação de Responsabilidade",
                        conteudo = "O aplicativo é fornecido 'como está'. Não garantimos que o serviço será ininterrupto ou livre de erros. Não nos responsabilizamos por danos diretos ou indiretos resultantes do uso do aplicativo."
                    )

                    TermoSecao(
                        titulo = "7. Modificações nos Termos",
                        conteudo = "Reservamo-nos o direito de modificar estes termos a qualquer momento. As alterações entrarão em vigor imediatamente após a publicação. O uso continuado do aplicativo constitui aceitação dos termos modificados."
                    )

                    TermoSecao(
                        titulo = "8. Contato",
                        conteudo = "Para questões sobre estes termos, entre em contato conosco através do email: contato@infohub.com.br"
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Última atualização: Outubro de 2024",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
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
                        checkedColor = Color(0xFF4CAF50)
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
                    containerColor = Color(0xFF4CAF50),
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
fun TermoSecao(titulo: String, conteudo: String) {
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