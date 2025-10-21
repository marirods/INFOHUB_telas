package com.example.infohub_telas.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FormPessoaFisica(
    nome: String, onNomeChange: (String) -> Unit,
    cpf: String, onCpfChange: (String) -> Unit,
    telefone: String, onTelefoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    senha: String, onSenhaChange: (String) -> Unit,
    confirmarSenha: String, onConfirmarSenhaChange: (String) -> Unit,
    mostrarSenha: Boolean, onMostrarSenhaChange: (Boolean) -> Unit,
    mostrarConfirmarSenha: Boolean, onMostrarConfirmarSenhaChange: (Boolean) -> Unit
) {
    Column {
        CustomTextFieldCadastro(
            value = nome,
            onValueChange = onNomeChange,
            placeholder = "Nome Completo*"
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = cpf,
            onValueChange = onCpfChange,
            placeholder = "CPF*",
            keyboardType = KeyboardType.Number
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = telefone,
            onValueChange = onTelefoneChange,
            placeholder = "Telefone*",
            keyboardType = KeyboardType.Phone
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextFieldCadastro(
            value = email,
            onValueChange = onEmailChange,
            placeholder = "E-mail*",
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(8.dp))

        SenhaTextFieldCadastro(
            value = senha,
            onValueChange = onSenhaChange,
            mostrarSenha = mostrarSenha,
            onMostrarSenhaChange = onMostrarSenhaChange,
            label = "Senha*"
        )

        Spacer(modifier = Modifier.height(8.dp))

        SenhaTextFieldCadastro(
            value = confirmarSenha,
            onValueChange = onConfirmarSenhaChange,
            mostrarSenha = mostrarConfirmarSenha,
            onMostrarSenhaChange = onMostrarConfirmarSenhaChange,
            label = "Confirme a senha*"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormPessoaFisicaPreview() {
    FormPessoaFisica(
        nome = "",
        onNomeChange = {},
        cpf = "",
        onCpfChange = {},
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
