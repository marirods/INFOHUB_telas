package com.example.infohub_telas.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infohub_telas.R

// Componente principal do formulário
@Composable
fun FormPessoaJuridica(
    nome: String,
    onNomeChange: (String) -> Unit,
    cnpj: String,
    onCnpjChange: (String) -> Unit,
    telefone: String,
    onTelefoneChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    senha: String,
    onSenhaChange: (String) -> Unit,
    confirmarSenha: String,
    onConfirmarSenhaChange: (String) -> Unit,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: (Boolean) -> Unit,
    mostrarConfirmarSenha: Boolean,
    onMostrarConfirmarSenhaChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        // Campo para nome da empresa
        CustomTextFieldCadastro(
            value = nome,
            onValueChange = onNomeChange,
            placeholder = "Nome da Empresa*"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para CNPJ - Usando Text para permitir formatação (ex.: máscara)
        CustomTextFieldCadastro(
            value = cnpj,
            onValueChange = onCnpjChange,
            placeholder = "CNPJ*",
            keyboardType = KeyboardType.Text
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para telefone
        CustomTextFieldCadastro(
            value = telefone,
            onValueChange = onTelefoneChange,
            placeholder = "Telefone*",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para e-mail
        CustomTextFieldCadastro(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "E-mail*",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para senha
        SenhaTextFieldCadastro(
            value = senha,
            onValueChange = onSenhaChange,
            mostrarSenha = mostrarSenha,
            onMostrarSenhaChange = onMostrarSenhaChange,
            placeholder = "Senha*"
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Campo para confirmação de senha
        SenhaTextFieldCadastro(
            value = confirmarSenha,
            onValueChange = onConfirmarSenhaChange,
            mostrarSenha = mostrarConfirmarSenha,
            onMostrarSenhaChange = onMostrarConfirmarSenhaChange,
            placeholder = "Confirme a senha*"
        )
    }
}

// Definição do componente customizado para campos de texto normais
@Composable
fun CustomTextFieldCadastro(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    val shape = RoundedCornerShape(12.dp)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = androidx.compose.ui.graphics.Color.Gray) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = shape)
            .clip(shape)
            .background(androidx.compose.ui.graphics.Color.White),
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = androidx.compose.ui.graphics.Color.White,
            unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun SenhaTextFieldCadastro(
    value: String,
    onValueChange: (String) -> Unit,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: (Boolean) -> Unit,
    placeholder: String
) {
    val shape = RoundedCornerShape(12.dp)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = androidx.compose.ui.graphics.Color.Gray) },
        visualTransformation = if (mostrarSenha) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = { onMostrarSenhaChange(!mostrarSenha) }) {
                Icon(
                    painter = painterResource(
                        id = if (mostrarSenha) R.drawable.olho_fechado else R.drawable.olho_aberto
                    ),
                    contentDescription = if (mostrarSenha) "Ocultar senha" else "Mostrar senha"
                )
            }
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = shape)
            .clip(shape)
            .background(androidx.compose.ui.graphics.Color.White),
        shape = shape,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedContainerColor = androidx.compose.ui.graphics.Color.White,
            unfocusedContainerColor = androidx.compose.ui.graphics.Color.White,
            cursorColor = MaterialTheme.colorScheme.primary
        )
    )
}

// Preview do componente
@Preview(showBackground = true)
@Composable
fun FormPessoaJuridicaPreview() {
    FormPessoaJuridica(
        nome = "",
        onNomeChange = {},
        cnpj = "",
        onCnpjChange = {},
        telefone = "",
        onTelefoneChange = {},
        email = "",
        onEmailChange = {},
        senha = "",
        onSenhaChange = {},
        confirmarSenha = "",
        onConfirmarSenhaChange = {},
        mostrarSenha = false,
        onMostrarSenhaChange = {},
        mostrarConfirmarSenha = false,
        onMostrarConfirmarSenhaChange = {}
    )
}
