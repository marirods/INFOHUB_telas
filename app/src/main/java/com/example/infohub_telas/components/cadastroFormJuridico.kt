package com.example.infohub_telas.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CustomTextFieldCadastro(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {

}

@Composable
fun SenhaTextFieldCadastro(
    value: String,
    onValueChange: (String) -> Unit,
    mostrarSenha: Boolean,
    onMostrarSenhaChange: (Boolean) -> Unit,
    label: String
) {
    // implementação do TextField de senha
}

@Composable
fun FormPessoaJuridica(
    nome: String, onNomeChange: (String) -> Unit,
    cnpj: String, onCnpjChange: (String) -> Unit,
    telefone: String, onTelefoneChange: (String) -> Unit,
    email: String, onEmailChange: (String) -> Unit,
    senha: String, onSenhaChange: (String) -> Unit,
    confirmarSenha: String, onConfirmarSenhaChange: (String) -> Unit,
    mostrarSenha: Boolean, onMostrarSenhaChange: (Boolean) -> Unit,
    mostrarConfirmarSenha: Boolean, onMostrarConfirmarSenhaChange: (Boolean) -> Unit
) {
    Column {
        CustomTextFieldCadastro(value = nome, onValueChange = onNomeChange, placeholder = "Nome Da Empresa*")
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextFieldCadastro(value = cnpj, onValueChange = onCnpjChange, placeholder = "CNPJ*", keyboardType = KeyboardType.Number)
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextFieldCadastro(value = telefone, onValueChange = onTelefoneChange, placeholder = "Telefone*", keyboardType = KeyboardType.Phone)
        Spacer(modifier = Modifier.height(8.dp))
        CustomTextFieldCadastro(value = email, onValueChange = onEmailChange, placeholder = "E-mail*", keyboardType = KeyboardType.Email)
        Spacer(modifier = Modifier.height(8.dp))
        SenhaTextFieldCadastro(value = senha, onValueChange = onSenhaChange, mostrarSenha = mostrarSenha, onMostrarSenhaChange = onMostrarSenhaChange, label = "Senha*")
        Spacer(modifier = Modifier.height(8.dp))
        SenhaTextFieldCadastro(value = confirmarSenha, onValueChange = onConfirmarSenhaChange, mostrarSenha = mostrarConfirmarSenha, onMostrarSenhaChange = onMostrarConfirmarSenhaChange, label = "Confirme a senha*")
    }
}


@Preview(showBackground = true)
@Composable
fun FormPessoaJuridicaPreview() {
    FormPessoaJuridica(
        nome = "",
        onNomeChange = {},
        cnpj = "",
        onCnpjChange  = {},
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
