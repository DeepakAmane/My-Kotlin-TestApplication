package com.example.kotlin.testapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin.testapp.viewmodel.LoginViewModel
import com.example.kotlin.testapp.viewmodel.StatementsListViewModel

class UserViewModelFactory(private val application: Application, private val userRepository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {


    init {
        Log.e("Constructor", "UserViewModelFactory Called")
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(application, userRepository) as T
        } else if (modelClass.isAssignableFrom(StatementsListViewModel::class.java)) {
            return StatementsListViewModel(application, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}