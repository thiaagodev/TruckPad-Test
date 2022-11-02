package com.thiaagodev.truckpad

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.thiaagodev.truckpad.view.MainActivity
import com.thiaagodev.truckpad.view.ShippingCalcFormActivity
import com.thiaagodev.truckpad.view.ShippingDetailActivity
import com.thiaagodev.truckpad.view.viewholder.ShippingViewHolder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var rule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testShippingFormOpenOnButtonClick() {
        Intents.init()
        onView(withId(R.id.button_calc_new_shipping)).perform(click())
        intended(hasComponent(ShippingCalcFormActivity::class.java.name))
        Intents.release()
    }

    @Test
    fun testShippingDetailsOpenOnClickHistoryItem() {
        Intents.init()
        onView(withId(R.id.recycler_shipping))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<ShippingViewHolder>(
                    0,
                    click()
                )
            )
        intended(hasComponent(ShippingDetailActivity::class.java.name))
        Intents.release()
    }

}