package com.github.gunin_igor75.list_payments.utils.epresso

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue


private val device: UiDevice
    get() = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())


fun assertPopupIsNotDisplayed(text: String) {
    device.waitForIdle()
    assertFalse(device.hasObject(By.text(text)))
}

fun assertPopupIsDisplayed(text: String) {
    device.pressHome()
    assertTrue(device.hasObject(By.text(text)))
}


//private fun isToastDisplayed(text: String, action: Runnable): Boolean {
//    var toastDisplayed = false
//    val startTimeMs = System.currentTimeMillis()
//    Log.d(TAG, "isToastDisplayed: start waiting")
//    InstrumentationRegistry.getInstrumentation().uiAutomation
//        .setOnAccessibilityEventListener { event: AccessibilityEvent ->
//            Log.d(TAG, "isToastDisplayed: event")
//            if (event.eventType == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED && PACKAGE_NAME == event.packageName
//                    .toString() && event.className.toString().contains(
//                    Toast::class.java.name
//                ) && "[$text]" == event.text.toString()
//            ) {
//                Log.d(TAG, "isToastDisplayed: TOAST")
//                toastDisplayed = true
//            }
//            event.recycle()
//        }
//    action.run()
//    while (!toastDisplayed && System.currentTimeMillis() - startTimeMs < 10000) {
//        Log.d(TAG, "isToastDisplayed: sleep")
//        SystemClock.sleep(500)
//    }
//    InstrumentationRegistry.getInstrumentation().uiAutomation.setOnAccessibilityEventListener(null)
//    return toastDisplayed
//}