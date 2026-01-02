package com.example.projetopokedex.ui.components.darktheme

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
import com.example.projetopokedex.ui.profile.ProfileUiState

@Composable
fun ProfileScreenDark(
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
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Image(
                    painter = painterResource(
                        if (uiState.isDarkIcon)
                            R.drawable.sun_02
                        else
                            R.drawable.do_not_disturb_ios
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
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // CAMPOS
            ProfileTextFieldDark(
                label = "Senha",
                value = uiState.password,
                onValueChange = onPasswordChange,
                enabled = uiState.isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileTextFieldDark(
                label = "Nome",
                value = uiState.name,
                onValueChange = onNameChange,
                enabled = uiState.isEditing
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileTextFieldDark(
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
                    ProfileButtonDark(
                        text = "Confirmar",
                        modifier = Modifier.weight(1f),
                        onClick = onSaveChanges
                    )
                    ProfileButtonDark(
                        text = "Cancelar",
                        modifier = Modifier.weight(1f),
                        onClick = onCancelEdit
                    )
                }
            } else {
                ProfileButtonDark(
                    text = "Editar",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onToggleEdit
                )
            }

            if (uiState.showSuccessMessage) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Dados atualizados com sucesso.",
                    fontSize = 12.sp,
                    color = Color.White,
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

            ProfileButtonDark(
                text = "Excluir Conta",
                modifier = Modifier.fillMaxWidth(),
                onClick = onDeleteClick
            )
        }
    }
}

@Composable
private fun ProfileTextFieldDark(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean
) {
    val labelColor = if (enabled) {
        Color(0xFFBBBBBB)
    } else {
        Color(0xFF777777)
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
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                disabledTextColor = Color(0xFF777777),
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color(0xFF777777),
                disabledBorderColor = Color(0xFF555555)
            )
        )
    }
}

@Composable
private fun ProfileButtonDark(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        ),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White)
    ) {
        Text(text = text, fontSize = 16.sp)
    }
}

