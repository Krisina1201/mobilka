package com.example.pypypy.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.pypypy.data.model.SignInModel.UserResponce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class DataStore(private val context: Context) {
    val TOKEN_KEY = stringPreferencesKey("token_key")

    val USER_ID = intPreferencesKey("user_id")

    val userFlow: Flow<Int> = context.dataStore.data.map { pref ->
        pref[USER_ID] ?: 0
    }
    val tokenFlow: Flow<String> = context.dataStore.data.map{ pref ->
        pref[TOKEN_KEY] ?: ""
    }

    suspend fun setUserId(userId: Int) {
        context.dataStore.edit { pref ->
            pref[USER_ID] = userId
        }
    }

    suspend fun setToken(token: String) {
        context.dataStore.edit { pref ->
            pref[TOKEN_KEY] = token
        }
    }


}