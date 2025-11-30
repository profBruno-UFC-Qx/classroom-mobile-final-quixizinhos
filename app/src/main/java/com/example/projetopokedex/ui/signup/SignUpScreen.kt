package com.example.projetopokedex.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopokedex.ui.components.HoverButton

@Composable
fun SignUpScreen(
    onBackToLogin: () -> Unit
) {
    var avatar by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = "Voltar",
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onBackToLogin() }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Cadastro",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Text(text = "Avatar", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = avatar,
            onValueChange = { avatar = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            placeholder = { Text("avatar_user") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )
        Text(
            text = "Seu avatar ficará visível para outros jogadores",
            fontSize = 11.sp,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
        )

        Text(text = "Nome", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp),
            placeholder = { Text("nome_user") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Text(text = "E-mail", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp),
            placeholder = { Text("test12@gmail.com") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp)
        )

        Text(text = "Senha", fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 32.dp),
            placeholder = { Text("sua_senha") },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        HoverButton(
            text = "Confirmar",
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
    }
}
