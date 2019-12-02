package com.example.kotlin.testapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.kotlin.testapp.ui.LoginActivity
import junit.framework.Assert.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // before test case execution
    }


    @Test
    @Throws(InterruptedException::class)
    fun loginWithoutUserShouldDisplayError() {
        Thread.sleep(5000)
        onView(withId(R.id.username_editText)).perform(typeText(""))
        onView(withId(R.id.password_editText)).perform(typeText("John123@#"), closeSoftKeyboard())
        Thread.sleep(5000)
        onView(withId(R.id.login_button)).perform(click())
        val activity = activityTestRule.activity
        Assert.assertEquals(
            activity.resources.getString(R.string.enter_email),
            activity.getmViewModel()?.getToastMessage()?.value
        )
    }

    @Test
    @Throws(InterruptedException::class)
    fun loginWithoutPasswordShouldDisplayError() {
        Thread.sleep(5000)
        onView(withId(R.id.username_editText)).perform(typeText("john@gmail.com"))
        onView(withId(R.id.password_editText)).perform(typeText(""), closeSoftKeyboard())
        Thread.sleep(5000)
        onView(withId(R.id.login_button)).perform(click())
        val activity = activityTestRule.activity
        Assert.assertEquals(
            activity.resources.getString(R.string.enter_password),
            activity.getmViewModel()?.getToastMessage()?.value
        )
    }


    @Test
    @Throws(InterruptedException::class)
    fun successfulLoginShouldOpenStatementListScreen() {
        onView(withId(R.id.username_editText)).perform(typeText("john@gmail.com"))
        onView(withId(R.id.password_editText)).perform(typeText("John123@#"), closeSoftKeyboard())
        onView(withId(R.id.login_button)).perform(click())
        Thread.sleep(5000)
        val activity = activityTestRule.activity
        assertNotNull(activity.getmViewModel()?.let {
             activity.getmViewModel()!!.observeLoginUser().value
        })
    }
}