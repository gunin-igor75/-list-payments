package com.github.gunin_igor75.list_payments.extension

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import org.robolectric.android.controller.ActivityController

fun <T : Activity> ActivityScenario<T>.with(block: T.() -> Unit) {
    onActivity(block)
}