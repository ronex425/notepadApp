package com.example.notepad.data.prefs

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

private val Context.dataStore by preferencesDataStore("auth_prefs")
private val KEY_USERNAME = stringPreferencesKey("username")

class AuthPrefs(private val context: Context) {
    val usernameFlow: Flow<String?> =
        context.dataStore.data.map { it[KEY_USERNAME] }

    suspend fun signIn(username: String) {
        context.dataStore.edit { it[KEY_USERNAME] = username }
    }

    suspend fun signOut() {
        context.dataStore.edit { it.remove(KEY_USERNAME) }
    }
}