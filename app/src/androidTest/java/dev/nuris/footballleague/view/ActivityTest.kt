package dev.nuris.footballleague.view

import android.view.KeyEvent
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.nuris.footballleague.module.MainActivity
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.rule.ActivityTestRule
import dev.nuris.footballleague.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityTest {

    @Rule
    @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRvBehavior() {
        onView(withId(R.id.mainRv)).check(matches(isDisplayed()))
        onView(withId(R.id.mainRv)).perform( RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.mainRv)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        onView(withId(R.id.itemSearchIcon)).check(matches(isDisplayed()))
        onView(withId(R.id.itemSearchIcon)).perform(click())
        onView(withId(R.id.itemSearch)).check(matches(isDisplayed()))
        onView(withId(R.id.itemSearch)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("liverpool"), pressKey(KeyEvent.KEYCODE_ENTER))

        Thread.sleep(1000)
    }
}