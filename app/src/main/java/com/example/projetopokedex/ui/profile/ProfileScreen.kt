package com.example.projetopokedex.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetopokedex.R

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onAvatarChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onToggleEdit: () -> Unit,
    onSaveChanges: () -> Unit,
    onCancelEdit: () -> Unit,
    onToggleThemeIcon: () -> Unit,
    onDeleteClick: () -> Unit,
    onDismissDeleteDialog: () -> Unit,
    onConfirmDelete: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 12.dp)
        ) {
            // HEADER
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DexGo",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Image(
                    painter = painterResource(
                        if (uiState.isDarkIcon)
                            R.drawable.do_not_disturb_ios
                        else
                            R.drawable.sun
                    ),
                    contentDescription = "Trocar tema",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onToggleThemeIcon() }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // AVATAR + NOME
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(R.drawable.icon_avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier.size(140.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = uiState.name.ifBlank { "Usuário" },
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // CAMPOS
            ProfileTextField(
                label = "Senha",
                value = uiState.password,
                onValueChange = onPasswordChange,
                enabled = uiState.isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileTextField(
                label = "Nome",
                value = uiState.name,
                onValueChange = onNameChange,
                enabled = uiState.isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileTextField(
                label = "Avatar",
                value = uiState.avatar,
                onValueChange = onAvatarChange,
                enabled = uiState.isEditing
            )

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÃO EDITAR / CONFIRMAR / CANCELAR
            if (uiState.isEditing) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ProfileButton(
                        text = "Confirmar",
                        modifier = Modifier.weight(1f),
                        onClick = onSaveChanges
                    )
                    ProfileButton(
                        text = "Cancelar",
                        modifier = Modifier.weight(1f),
                        onClick = onCancelEdit
                    )
                }
            } else {
                ProfileButton(
                    text = "Editar",
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = onToggleEdit
                )
            }

            if (uiState.showSuccessMessage) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dados atualizados com sucesso.",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            uiState.error?.let { errorMsg ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = errorMsg,
                    fontSize = 12.sp,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            ProfileButton(
                text = "Excluir Conta",
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun ProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {
    val labelColor = if (enabled) {
        Color(0xFF777777)
    } else {
        Color(0xFFBBBBBB)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            color = labelColor
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true
        )
    }
}

@Composable
private fun ProfileButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        border = ButtonDefaults.outlinedButtonBorder
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
fun DeleteAccountDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(280.dp)
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Excluir conta?",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "x",
                    modifier = Modifier
                        .clickable { onDismiss() },
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tem certeza que deseja excluir sua conta? Essa ação não pode ser desfeita.",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            ProfileButton(
                text = "Confirmar",
                onClick = onConfirm
            )
        }
    }
}