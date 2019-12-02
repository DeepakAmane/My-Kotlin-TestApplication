package com.example.kotlin.testapp

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.kotlin.testapp.adapter.StatementListAdapter
import com.example.kotlin.testapp.ui.LoginActivity
import com.example.kotlin.testapp.ui.StatementListActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNull.notNullValue
import org.hamcrest.Matchers.instanceOf
import org.junit.Test

@RunWith(AndroidJUnit4::class)
@LargeTest
class StatementListActivityTest {


    @Rule
    @JvmField
    var intentsTestRule = IntentsTestRule(LoginActivity::class.java)

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<StatementListActivity> =
        object : ActivityTestRule<StatementListActivity>(StatementListActivity::class.java) {
            override fun getActivityIntent(): Intent {
                val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
                val result = Intent(targetContext, StatementListActivity::class.java)
                result.putExtra("MyClass", InjectUserAccountDetails.injectUserAccount())
                return result
            }
        }

    @Before
    @Throws(Exception::class)
    fun setUp() {
        // before test case execution
    }


    @Test
    @Throws(Exception::class)
    fun ensureRecyclerViewIsPresentWithStatementList() {
        Thread.sleep(5000)
        val activity = activityTestRule.activity
        val viewById = activity.findViewById(R.id.statementListRecylerview) as RecyclerView
        assertThat<View>(viewById, notNullValue())
        assertThat<View>(viewById, instanceOf<Any>(RecyclerView::class.java))
        val recyclerView = viewById as RecyclerView
        val adapter = recyclerView.adapter as StatementListAdapter?
        assertThat(adapter, instanceOf<Any>(StatementListAdapter::class.java))
        assert(adapter != null)
        assertThat(adapter!!.itemCount, greaterThan(0))
    }

}