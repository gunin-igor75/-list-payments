package com.github.gunin_igor75.list_payments.utils.epresso

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun isToast(): Matcher<Root> = IsToast()

private class IsToast : TypeSafeMatcher<Root>() {
    override fun describeTo(description: Description?) {
        description?.appendText("is Toast")
    }

    override fun matchesSafely(root: Root?): Boolean {
        if (root == null)return false
        val type = root.windowLayoutParams.get().type
        if (type == WindowManager.LayoutParams.FIRST_APPLICATION_WINDOW) {
            val windowToken = root.decorView.windowToken
            val appToken = root.decorView.applicationWindowToken
            if (windowToken === appToken) {
                return true
            }
        }
        return false
    }
}