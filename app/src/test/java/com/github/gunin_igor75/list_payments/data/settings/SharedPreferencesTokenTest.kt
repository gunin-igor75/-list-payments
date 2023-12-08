package com.github.gunin_igor75.list_payments.data.settings

import android.content.Context
import android.content.SharedPreferences
import com.github.gunin_igor75.list_payments.util.TOKEN
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit4.MockKRule
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SharedPreferencesTokenTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var appContext: Context

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    @RelaxedMockK
    lateinit var edit: SharedPreferences.Editor

    private lateinit var tokenSettings: SharedPreferencesToken

    private val currentToken = "currentToken"
    @Before
    fun setup() {
        every {
            appContext.getSharedPreferences(any(), any())
        } returns sharedPreferences

        every {
            sharedPreferences.edit()
        } returns edit
        tokenSettings = SharedPreferencesToken(appContext)
    }

    @Test
    fun setCurrentTokenTestPositiveNull() {
        tokenSettings.setCurrentToken(null)

        verifySequence {
            sharedPreferences.edit()
            edit.remove(currentToken)
            edit.apply()
        }
    }

    @Test
    fun setCurrentTokenTestPositiveNotNull() {
        tokenSettings.setCurrentToken(TOKEN)

        verifySequence {
            sharedPreferences.edit()
            edit.putString(currentToken, TOKEN)
            edit.apply()
        }
    }

    @Test
    fun getCurrentTokenTestPositive() {
        every {
            sharedPreferences.getString(any(), any())
        } returns TOKEN

        val token = tokenSettings.getCurrentToken()
        assertThat(token).isEqualTo(TOKEN)
    }
}