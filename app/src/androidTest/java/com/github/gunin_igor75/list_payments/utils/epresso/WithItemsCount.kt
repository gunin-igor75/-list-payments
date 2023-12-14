package com.github.gunin_igor75.list_payments.utils.epresso

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun withItemsCount(count: Int): Matcher<View> = WithItemsCount(count)

private class WithItemsCount(
    private val count: Int
): TypeSafeMatcher<View>() {
    override fun describeTo(description: Description?) {
        description?.appendText("recyclerView with item $count")
    }

    override fun matchesSafely(item: View?): Boolean {
        if (item !is RecyclerView) return false
        val adapter = item.adapter?: return false
        return adapter.itemCount == count
    }
}