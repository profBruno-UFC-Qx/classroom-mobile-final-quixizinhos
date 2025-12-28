package com.example.projetopokedex.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.projetopokedex.data.model.UserLocal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

private const val USER_PREFS_NAME = "user_prefs"

private val Context.userDataStore by preferencesDataStore(
    name = USER_PREFS_NAME
)

object UserPrefsKeys {
    val AVATAR = stringPreferencesKey("avatar")
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
    val PASSWORD = stringPreferencesKey("password")
    val TOKEN = stringPreferencesKey("token")
}

class UserLocalDataSource(private val context: Context) {

    // USER
    suspend fun saveUser(user: UserLocal) {
        context.userDataStore.edit { prefs ->
            prefs[UserPrefsKeys.AVATAR] = user.avatar
            prefs[UserPrefsKeys.NAME] = user.name
            prefs[UserPrefsKeys.EMAIL] = user.email
            prefs[UserPrefsKeys.PASSWORD] = user.password
        }
    }

    suspend fun getUserByEmail(email: String): UserLocal? {
        val prefs = context.userDataStore.data.firstOrNull() ?: return null
        val storedEmail = prefs[UserPrefsKeys.EMAIL]
        if (storedEmail == null || storedEmail != email) return null

        val avatar = prefs[UserPrefsKeys.AVATAR] ?: ""
        val name = prefs[UserPrefsKeys.NAME] ?: ""
        val password = prefs[UserPrefsKeys.PASSWORD] ?: ""
        return UserLocal(
            avatar = avatar,
            name = name,
            email = storedEmail,
            password = password
        )
    }

    suspend fun getUserName(): String? {
        val prefs = context.userDataStore.data.firstOrNull() ?: return null
        return prefs[UserPrefsKeys.NAME]
    }

    suspend fun updateUser(updated: UserLocal) {
        context.userDataStore.edit { prefs ->
            prefs[UserPrefsKeys.AVATAR] = updated.avatar
            prefs[UserPrefsKeys.NAME] = updated.name
            prefs[UserPrefsKeys.EMAIL] = updated.email
            prefs[UserPrefsKeys.PASSWORD] = updated.password
        }
    }

    suspend fun deleteUser() {
        context.userDataStore.edit { prefs ->
            prefs[UserPrefsKeys.AVATAR] = ""
            prefs[UserPrefsKeys.NAME] = ""
            prefs[UserPrefsKeys.EMAIL] = ""
            prefs[UserPrefsKeys.PASSWORD] = ""
            prefs[UserPrefsKeys.TOKEN] = ""
        }
    }

    suspend fun getCurrentUser(): UserLocal? {
        val prefs = context.userDataStore.data.firstOrNull() ?: return null
        val email = prefs[UserPrefsKeys.EMAIL] ?: return null
        val avatar = prefs[UserPrefsKeys.AVATAR] ?: ""
        val name = prefs[UserPrefsKeys.NAME] ?: ""
        val password = prefs[UserPrefsKeys.PASSWORD] ?: ""
        return UserLocal(avatar = avatar, name = name, email = email, password = password)
    }

    val userNameFlow: Flow<String?> =
        context.userDataStore.data.map { prefs -> prefs[UserPrefsKeys.NAME] }

    // TOKEN
    val tokenFlow: Flow<String?> =
        context.userDataStore.data.map { prefs -> prefs[UserPrefsKeys.TOKEN] }

    suspend fun saveToken(token: String) {
        context.userDataStore.edit { prefs ->
            prefs[UserPrefsKeys.TOKEN] = token
        }
    }

    suspend fun clearToken() {
        context.userDataStore.edit { prefs ->
            prefs[UserPrefsKeys.TOKEN] = ""
        }
    }
}