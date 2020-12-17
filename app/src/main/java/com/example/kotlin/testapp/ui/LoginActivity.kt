package com.example.kotlin.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.testapp.R
import com.example.kotlin.testapp.db.UserDatabase
import com.example.kotlin.testapp.model.UserRepository
import com.example.kotlin.testapp.model.UserViewModelFactory
import com.example.kotlin.testapp.network.response.UserAccount
import com.example.kotlin.testapp.utils.InjectorUtils
import com.example.kotlin.testapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import retrofit2.Retrofit


class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()


    private val factoryInstance: UserViewModelFactory by instance()

    private lateinit var viewModel: LoginViewModel
    private var activityLoginBinding: com.example.kotlin.testapp.databinding.ActivityLoginBinding? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_login)
        activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        initializeUi()
        obeserveLoginAuthentication()
        handleLoginSubmit()
        observeToastMessages()
    }

    fun getmViewModel(): LoginViewModel? {
        return if (viewModel != null) {
            viewModel
        } else
            null
    }

    /***
     * On Successful authentication navigate  the Statement List Screen via Intent
     */
    private fun obeserveLoginAuthentication() {
        viewModel.observeLoginUser().observe(this, Observer { userAccount ->

            // safe call with let
            userAccount?.let {
                Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, StatementListActivity::class.java).apply {
                    userAccount.let {
                        putExtra("MyClass", userAccount)
                    }
                }
                startActivity(intent)
                finish()
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun initializeUi() {

        // val factory = InjectorUtils.provideUserViewModelFactory(this.application)
        viewModel = ViewModelProviders.of(this, factoryInstance).get(LoginViewModel::class.java)

        activityLoginBinding?.viewModel = viewModel
        activityLoginBinding?.lifecycleOwner = this
    }

    /***
     * Handle the Login button listener via LoginActivity.java
     */
    fun handleLoginSubmit() {
        activityLoginBinding?.loginButton?.setOnClickListener { view: View -> viewModel.onBtnLoginClick() }
    }

    /**
     * Show the Toast Messages as per the user invalid inputs
     */
    private fun observeToastMessages() {
        viewModel.getToastMessage().observe(this, Observer { toastMessgae ->
            if (!toastMessgae.isEmpty()) {
                Toast.makeText(this@LoginActivity, toastMessgae, Toast.LENGTH_LONG).show()
            }
        })
    }
}



