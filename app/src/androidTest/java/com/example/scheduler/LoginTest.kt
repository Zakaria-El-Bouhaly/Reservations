package com.example.scheduler


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginTest {

    @get:Rule
    val myActScenarioTest = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun login() {
        onView(withId(R.id.emailfield)).perform(typeText("admin@demo.com"))
        onView(withId(R.id.pass)).perform(typeText("demo123"))
        onView(ViewMatchers.isRoot()).perform(closeSoftKeyboard())
        onView(withId(R.id.signin)).perform(click())
    }

}