package com.joao.garageapp.data.user

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.material.internal.NavigationMenu
import com.joao.garageapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

const val DATASTORE_NAME = "USER"
val Context.datastore : DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)
class DataSourceImpl @Inject constructor(
    private val context: Context
    ): DataStoreSource {

    companion object {
        val ID = stringPreferencesKey("ID")
        val NAME = stringPreferencesKey("NAME")
        val IMAGE = stringPreferencesKey("IMAGE")
        val EMAIL = stringPreferencesKey("EMAIL")
    }

    override suspend fun saveCurrentUser(u: User): User {
        context.datastore.edit { user ->
            user[ID] = u.userId
            user[NAME] = u.name
            user[IMAGE] = u.image
            user[EMAIL] = u.email
        }
        return u
    }

    override suspend fun getCurrentUser() = context.datastore.data.map { user ->
        User(
            userId = user[ID]?:"",
            name = user[NAME]?:"",
            image = user[IMAGE]?:"",
            email = user[EMAIL]?:""
        )
    }

    override suspend fun clearDataStore() {
        context.datastore.edit { user ->
            user.clear()
        }
    }
}