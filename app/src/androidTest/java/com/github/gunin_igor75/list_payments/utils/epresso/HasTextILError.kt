package com.github.gunin_igor75.list_payments.utils.epresso

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun hasTextILError(
    errorText: String
): Matcher<View> = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("TextInputLayout check error")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is TextInputLayout) return false
        val error = item.error ?: return false
        return errorText == error.toString()
    }
}