package dev.nuris.footballleague

import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.nuris.footballleague.ui.MainActivity
import androidx.test.rule.ActivityTestRule
import dev.nuris.footballleague.ui.adapter.MatchRvAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchTest {
    @Rule
    @JvmField val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(R.id.searchItemHome)).perform(click())
        onView(withId(R.id.itemSearch)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("united"), pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        for (i in 1.."united".length) {
            onView(isAssignableFrom(EditText::class.java)).perform(pressKey(KeyEvent.KEYCODE_DEL))
        }
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("united"), pressKey(KeyEvent.KEYCODE_ENTER))
        Thread.sleep(1000)
        onView(withId(R.id.searchEventRv)).perform(actionOnItemAtPosition<MatchRvAdapter.ViewHolder>(1, click()))
    }
}