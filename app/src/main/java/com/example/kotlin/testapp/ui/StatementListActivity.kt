package com.example.kotlin.testapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.testapp.R
import com.example.kotlin.testapp.adapter.StatementListAdapter
import com.example.kotlin.testapp.model.UserViewModelFactory
import com.example.kotlin.testapp.network.response.Statement
import com.example.kotlin.testapp.network.response.StatementResponse
import com.example.kotlin.testapp.network.response.UserAccount
import com.example.kotlin.testapp.utils.InjectorUtils
import com.example.kotlin.testapp.utils.Utils
import com.example.kotlin.testapp.viewmodel.StatementsListViewModel

import kotlinx.android.synthetic.main.activity_statement_list.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit

/**
 * Statement details List is displayed for authenticated User
 **/
class StatementListActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factoryInstance: UserViewModelFactory by instance()

    private var adapter: StatementListAdapter? = null
    private lateinit var viewModel: StatementsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statement_list)
        initRecyclerView()
        bindValuesToUI()
        initializeViewModel()
        observeToFetchStatementsList()
        pullStatementListOnLoad()
    }


    /**
     * Renders RecyclerView with statement list
     */
    private fun initRecyclerView() {
        val mStatementListRecyclerView = findViewById<RecyclerView>(R.id.statementListRecylerview)
        mStatementListRecyclerView.layoutManager = LinearLayoutManager(this)
        mStatementListRecyclerView.setHasFixedSize(true)
        adapter = StatementListAdapter()
        mStatementListRecyclerView.adapter = adapter
    }

    /**
     * Logged in User details received from preivous screen are binded to UI Widgets
     * */
    private fun bindValuesToUI() {

        intent.getSerializableExtra("MyClass")?.let {

            val account = intent.getSerializableExtra("MyClass") as UserAccount
            name_textview.text = account.name
            account_value_textView.text = account.bankAccount
            balance_value_textView.text = "${account.balance}"

        }
    }

    private fun initializeViewModel() {
        //  val factory = InjectorUtils.provideUserViewModelFactory(this.application)
        viewModel =
            ViewModelProviders.of(this, factoryInstance).get(StatementsListViewModel::class.java)
    }

    /*
     * Observe the Statement List and set to the adapter
     * ***/
    private fun observeToFetchStatementsList() {
        viewModel.observeStatementList()
            .observe(this, Observer<StatementResponse> { statementResponse ->
                if (statementResponse.statementList.isEmpty()) {
                    Toast.makeText(
                        this@StatementListActivity,
                        "Unbale to get Statement List",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    adapter?.setStatementList(statementResponse.statementList)
                    progressBar.visibility = View.GONE
                }
            })
    }

    /**
     * Call the StatementList API on launch of the Screen
     */
    private fun pullStatementListOnLoad() {
        if (Utils.isNetworkConnected(this@StatementListActivity)) {
            intent.getSerializableExtra("MyClass")?.let {
                progressBar.visibility = View.VISIBLE
                viewModel.onLaunchPullStatementList(intent.getSerializableExtra("MyClass") as UserAccount)
            }
        }
    }
}
