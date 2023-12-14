package com.github.gunin_igor75.list_payments.utils.epresso

import android.view.View
import com.google.android.material.textfield.TextInputLayout
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun hasTextILHint(
    hintText: String
): Matcher<View> = object : TypeSafeMatcher<View>() {

    override fun describeTo(description: Description?) {
        description?.appendText("TextInputLayout check hint")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is TextInputLayout) return false
        val hint = item.hint ?: return false
        return hintText == hint.toString()
    }
}