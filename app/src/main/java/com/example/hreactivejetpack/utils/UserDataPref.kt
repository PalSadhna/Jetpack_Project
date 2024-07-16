package com.example.hreactivejetpack.utils

import android.content.Context
import android.preference.PreferenceDataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserDataPref @Inject constructor (@ApplicationContext private val context: Context) {
    companion object{
        private val Context.dataStore by preferencesDataStore(name = "HreactiveDatabase")
        val FirstNAME = stringPreferencesKey("firstName")
        val LASTNAME = stringPreferencesKey("lastName")
        val USERID = intPreferencesKey("EmployeeId")
        val ORG_ID = intPreferencesKey("orgId")
        val TOKEN = stringPreferencesKey("token")
        val IS_USER_LOGGED_IN = booleanPreferencesKey("isLoggedIn")
        val PROFILE_PICTURE = stringPreferencesKey("picture")
        val DESIGNATION = stringPreferencesKey("designation")

    }

    suspend fun setInt(PREF_NAME: Preferences.Key<Int>, Id: Int){
        context.dataStore.edit {preference ->
            preference[PREF_NAME] = Id

        }
    }

    suspend fun getInt(id: Preferences.Key<Int>): Int {
        val preference = context.dataStore.data.first()
        return preference[id] ?: 0
    }



    suspend fun setString(PREF_NAME: Preferences.Key<String>, name: String){
        context.dataStore.edit {preference ->
            preference[PREF_NAME] = name
        }
    }

    suspend fun setStringData(PREF_NAME: Preferences.Key<String>,value: String){
        context.dataStore.edit {preference ->
            // val prefName = stringPreferencesKey(PREF_NAME)
            preference[PREF_NAME] = value

        }
    }

    fun getStringData(PREF_NAME : Preferences.Key<String>): kotlinx.coroutines.flow.Flow<String> = context.dataStore.data
        .catch {
            if (this is Exception){
                emit(emptyPreferences())
            }
        }.map {preference->
            // val data = stringPreferencesKey(PREFNAME)
            val name = preference[PREF_NAME]?: ""
            name
        }

    suspend fun getString(name: Preferences.Key<String>): String{
        val preferences = context.dataStore.data.first()
        return preferences[name] ?: ""
    }

    suspend fun setUserLoggedInStatus(isLoggedIn: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_USER_LOGGED_IN] = isLoggedIn
        }
    }

    suspend fun isUserLoggedIn(): Boolean {
        val preferences = context.dataStore.data.first()
        return preferences[IS_USER_LOGGED_IN] ?: false // Default to false if the key doesn't exist
    }

    suspend fun logout(){
        context.dataStore.edit {
            it.clear()
        }
    }


}