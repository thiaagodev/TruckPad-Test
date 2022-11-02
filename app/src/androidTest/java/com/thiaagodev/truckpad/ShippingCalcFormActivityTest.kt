package com.thiaagodev.truckpad

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.NoActivityResumedException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.times
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.thiaagodev.truckpad.view.ShippingCalcFormActivity
import com.thiaagodev.truckpad.view.ShippingDetailActivity
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class ShippingCalcFormActivityTest {

    @get:Rule
    var rule = ActivityTestRule<ShippingCalcFormActivity>(ShippingCalcFormActivity::class.java)

    @Test
    fun testCalculateNewShipping() {
        onView(withId(R.id.edit_origin)).perform(click(), replaceText("Tietê, São Paulo"))
        onView(withId(R.id.edit_destiny)).perform(click(), replaceText("Tietê, São Paulo"))
        onView(withId(R.id.edit_axes)).perform(click(), replaceText("9"))
        onView(withId(R.id.edit_fuel_consumption)).perform(click(), replaceText("12"))
        onView(withId(R.id.edit_fuel_price)).perform(click(), replaceText("5,56"), ViewActions.closeSoftKeyboard())

        Intents.init()
        onView(withId(R.id.button_calc_shipping)).perform(click())
        Intents.intended(IntentMatchers.hasComponent(ShippingDetailActivity::class.java.name), times(0))
        Intents.release()
    }

}