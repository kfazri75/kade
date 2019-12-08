package dev.nuris.footballleague.view

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.nuris.footballleague.ui.MainActivity
import androidx.test.rule.ActivityTestRule
import dev.nuris.footballleague.R
import dev.nuris.footballleague.ui.adapter.MatchRvAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ActivityTest {
    @Rule
    @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        Thread.sleep(1000)

        //menu search click
        onView(withId(R.id.itemSearch)).perform(click())

        //enter text
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("english"), pressKey(KeyEvent.KEYCODE_ENTER))

        //delete text
        for (i in 1.."english".length) {
            onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KeyEvent.KEYCODE_DEL))
        }

        //enter text
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("UEFA"), pressKey(KeyEvent.KEYCODE_ENTER))

        //delete text
        for (i in 1.."UEFA".length) {
            onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KeyEvent.KEYCODE_DEL))
        }

        //search navigation bar click
        onView(withId(R.id.searchItemHome)).perform(click())

        //menu search click
        onView(withId(R.id.itemSearch)).perform(click())

        // enter text
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("united"), pressKey(KeyEvent.KEYCODE_ENTER))

        // sleep 1000
        Thread.sleep(1000)

        //delete text
        for (i in 1.."english".length) {
            onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KeyEvent.KEYCODE_DEL))
        }

        // enter text
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("liverpool"), pressKey(KeyEvent.KEYCODE_ENTER))

        // sleep 1000
        Thread.sleep(1000)

        //click rv position 1
        onView(withId(R.id.searchEventRv)).perform(actionOnItemAtPosition<MatchRvAdapter.ViewHolder>(1, click()))
    }
}