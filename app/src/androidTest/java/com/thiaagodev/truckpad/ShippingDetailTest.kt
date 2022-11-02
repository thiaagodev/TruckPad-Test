package com.thiaagodev.truckpad

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.thiaagodev.truckpad.view.ShippingDetailActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ShippingDetailTest {

    @get:Rule
    var rule = ActivityTestRule<ShippingDetailActivity>(ShippingDetailActivity::class.java)

    @Test
    fun testCloseShippingDetailsActivityOnPressImageClose() {
        try {
            onView(withId(R.id.image_close)).perform(click())
        } catch (e: Exception) {
            assertEquals(NoActivityResumedException::class, e::class)
        }
    }

}