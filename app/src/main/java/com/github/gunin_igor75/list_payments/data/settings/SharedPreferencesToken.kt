package com.github.gunin_igor75.list_payments.data.settings

import android.content.Context
import com.github.gunin_igor75.list_payments.domain.settings.TokenSettings
import javax.inject.Inject

class SharedPreferencesToken @Inject constructor(
    appContext: Context
) : TokenSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences(NAME_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    override fun setCurrentToken(token: String?) {
        val edit = sharedPreferences.edit()
        if (token == null) {
            edit.remove(CURRENT_TOKEN)
        } else {
            edit.putString(CURRENT_TOKEN, token)
        }
        edit.apply()
    }

    override fun getCurrentToken(): String? {
        return sharedPreferences.getString(CURRENT_TOKEN, null)
    }

    companion object {
        private const val NAME_SHARED_PREFERENCES = "tokenSettings"
        private const val CURRENT_TOKEN = "currentToken"
    }

}