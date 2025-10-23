package com.example.infohub_telas.components



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Card que exibe todas as informações do estabelecimento
 * Utilizado na tela "Meu Estabelecimento"
 */
@Composable
fun EstabelecimentoInfoCard(
    nomeEstabelecimento: String,
    cnpj: String,
    endereco: String,
    telefone: String,
    email: String,
    categoria: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título do Card
            Text(
                text = nomeEstabelecimento,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF25992E),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Divider(color = Color.LightGray, thickness = 1.dp)

            // CNPJ
            InfoRow(
                icon = Icons.Default.Badge,
                label = "CNPJ",
                value = formatarCNPJ(cnpj)
            )

            // Categoria
            InfoRow(
                icon = Icons.Default.Category,
                label = "Categoria",
                value = categoria
            )

            // Endereço
            InfoRow(
                icon = Icons.Default.LocationOn,
                label = "Endereço",
                value = endereco
            )

            // Telefone
            InfoRow(
                icon = Icons.Default.Phone,
                label = "Telefone",
                value = formatarTelefone(telefone)
            )

            // E-mail
            InfoRow(
                icon = Icons.Default.Email,
                label = "E-mail",
                value = email
            )
        }
    }
}

/**
 * Linha de informação com ícone, label e valor
 * Componente reutilizável para exibir dados formatados
 */
@Composable
fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = Color(0xFF25992E),
            modifier = Modifier
                .size(24.dp)
                .padding(end = 4.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

/**
 * Botão estilizado para ações principais
 * Reutilizável em várias telas
 */
@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF25992E),
    modifier: Modifier = Modifier,
    icon: ImageVector? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(28.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
        }
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

/**
 * Formata o CNPJ para o padrão XX.XXX.XXX/XXXX-XX
 */
fun formatarCNPJ(cnpj: String): String {
    if (cnpj.length != 14) return cnpj

    return buildString {
        append(cnpj.substring(0, 2))
        append(".")
        append(cnpj.substring(2, 5))
        append(".")
        append(cnpj.substring(5, 8))
        append("/")
        append(cnpj.substring(8, 12))
        append("-")
        append(cnpj.substring(12, 14))
    }
}

/**
 * Formata o telefone para o padrão (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
 */
fun formatarTelefone(telefone: String): String {
    if (telefone.length < 10) return telefone

    return if (telefone.length == 11) {
        "(${telefone.substring(0, 2)}) ${telefone.substring(2, 7)}-${telefone.substring(7)}"
    } else {
        "(${telefone.substring(0, 2)}) ${telefone.substring(2, 6)}-${telefone.substring(6)}"
    }
}

/**
 * Card de loading para quando os dados estão sendo carregados
 */
@Composable
fun LoadingCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = Color(0xFF25992E),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Carregando dados...",
                fontSize = 16.sp,
                color = Color.Gray
            )
        }
    }
}

/**
 * Card de erro para quando não foi possível carregar os dados
 */
@Composable
fun ErrorCard(
    mensagem: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Erro",
                tint = Color.Red,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = mensagem,
                fontSize = 16.sp,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E))
            ) {
                Text("Tentar Novamente")
            }
        }
    }
}