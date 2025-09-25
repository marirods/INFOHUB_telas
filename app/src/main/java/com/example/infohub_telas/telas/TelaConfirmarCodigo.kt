package com.example.infohub_telas.telas

import android.content.Context
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TelaConfirmarCodigo() {
    var codigo by remember { mutableStateOf("") }
    // mari, aqui vou puxar o email da outra tela para voce usar no seu componente
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    val emailSalvo = prefs.getString("email", "") ?: ""
    // calmaa, tem aqla interrogacao para se nao tiver nada vai vim nulo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                painter = painterResource(id = R.drawable.elipse_laranja),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp)
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
        Text(
            text = "Confirme seu e-mail",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 23.sp,
            color = Color.Black,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Código enviado para:$emailSalvo",
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black,
            lineHeight = 18.sp
        )
        Spacer(modifier = Modifier.height(3.dp))



//
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

//            Button(
//                onClick = { /* ação */ },
//                modifier = Modifier.fillMaxWidth(),
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
//            ) {
//                Text(text = "Confirmar", color = Color.White)
//            }
        }
    }


@Preview(showSystemUi = true)
@Composable
fun TelaConfirmarCodigoPreview() {
    InfoHub_telasTheme {
        TelaConfirmarCodigo()
    }
}
