package com.example.infohub_telas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infohub_telas.model.Empresa

@Composable
fun EmpresaCard(empresa: Empresa, onEditarClick: () -> Unit) {
    Box(contentAlignment = Alignment.TopCenter) {
        Card(
            modifier = Modifier.padding(top = 50.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 70.dp, start = 24.dp, end = 24.dp, bottom = 24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = empresa.nome,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
                Text(
                    text = "CNPJ: ${empresa.cnpj}",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Setor de atuação em destaque
                Text(
                    text = "Setor de Atuação",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
                Text(
                    text = empresa.setor,
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                // Informações de contato
                InfoSection(
                    title = "Informações de Contato",
                    items = listOf(
                        IconTextPair(Icons.Default.Email, empresa.email),
                        IconTextPair(Icons.Default.Phone, empresa.telefone)
                    )
                )

                // Endereço
                InfoSection(
                    title = "Endereço",
                    items = listOf(
                        IconTextPair(Icons.Default.LocationOn, empresa.endereco)
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onEditarClick,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        "Editar Perfil",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Ícone da empresa
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(Color(0xFF1976D2))
                .padding(4.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Business,
                contentDescription = "Ícone da empresa",
                tint = Color(0xFF1976D2),
                modifier = Modifier.size(60.dp)
            )
        }
    }
}

@Composable
private fun InfoSection(title: String, items: List<IconTextPair>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        items.forEach { item ->
            InfoRow(icon = item.icon, text = item.text)
        }
    }
}

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF1976D2),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            color = Color.DarkGray
        )
    }
}

private data class IconTextPair(val icon: ImageVector, val text: String)

@Preview(showBackground = true)
@Composable
fun EmpresaCardPreview() {
    val empresaExemplo = Empresa(
        nome = "Tech Solutions Ltda",
        cnpj = "12.345.678/0001-99",
        email = "contato@techsolutions.com",
        telefone = "(11) 99999-9999",
        endereco = "Av. Paulista, 1000, São Paulo",
        setor = "Tecnologia",
        descricao = "Empresa líder em soluções tecnológicas",
        logoUrl = ""
    )

    Box(
        modifier = Modifier
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        EmpresaCard(
            empresa = empresaExemplo,
            onEditarClick = {}
        )
    }
}
