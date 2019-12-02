package com.example.kotlin.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlin.testapp.R
import com.example.kotlin.testapp.utils.InjectorUtils
import com.example.kotlin.testapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    private lateinit var viewModel: LoginViewModel
    private var activityLoginBinding: com.example.kotlin.testapp.databinding.ActivityLoginBinding? = null

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
        viewModel.observeLoginUser().observe(this, Observer { userResponse ->

            // safe call with let
            userResponse?.let {
                Toast.makeText(this@LoginActivity, "Login success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, StatementListActivity::class.java).apply {
                    userResponse.userAccount.let {
                        putExtra("MyClass", userResponse.userAccount)
                    }
                }
                startActivity(intent)
                finish()
                progressBar.visibility = View.GONE
            }
        })
    }

    private fun initializeUi() {

        val factory = InjectorUtils.provideUserViewModelFactory(this.application)
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

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



