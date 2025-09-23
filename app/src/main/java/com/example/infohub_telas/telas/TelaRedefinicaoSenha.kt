package com.example.infohub_telas.telas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infohub_telas.R
import com.example.infohub_telas.ui.theme.InfoHub_telasTheme
import androidx.compose.material3.OutlinedTextField

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaRedefinicaoSenha() {
    var telefone by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column {

            // Fundo amarelo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(420.dp)
                    .background(Color(0xFFFFD966))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.elipse_vermelha),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                        .offset(x = (-41).dp, y = 80.dp)
                        .align(Alignment.TopStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.amarela_elipse),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .offset(x = (6).dp, y = (-10).dp)
                        .align(Alignment.TopEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.elipse_branca),
                    contentDescription = null,
                    modifier = Modifier
                        .size(260.dp)
                        .offset(x = (6).dp, y = (-10).dp)
                        .align(Alignment.TopEnd)
                )
                Image(
                    painter = painterResource(id = R.drawable.mulher_lendo),
                    contentDescription = "",
                    modifier = Modifier
                        .size(230.dp)
                        .align(Alignment.TopCenter)
                        .offset(y = 70.dp)
                )

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .offset(y = (0).dp)
                        .padding(horizontal = 24.dp)
                        .padding(top = 140.dp)
                ) {
                    Text(
                        text = "Recuperar Senha",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 23.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Preencha o campo abaixo com o seu telefone",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(3.dp))

                    OutlinedTextField(
                        value = telefone,
                        onValueChange = { novoValor -> telefone = novoValor },
                        placeholder = { Text("Telefone") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White
                        ),
                        shape = RoundedCornerShape(28.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* ação */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(56.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25992E))
            ) {
                Text(
                    text = "Recuperar",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TelaRedefinicaoSenhaPreview() {
    InfoHub_telasTheme {
        TelaRedefinicaoSenha()
    }
}
