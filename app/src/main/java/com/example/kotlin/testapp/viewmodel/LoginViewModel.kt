package com.example.kotlin.testapp.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.testapp.model.UserRepository
import com.example.kotlin.testapp.utils.Utils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginViewModel(application: Application, private val userRepository: UserRepository) :
    AndroidViewModel(application) {

    private var context: Context? = null
    private val mToastMessage = MutableLiveData<String>("")
    private var visibility = MutableLiveData(8)

    val email = ObservableField<String>()
    val password = ObservableField<String>()

    init {
        Log.e("LoginViewmodel", "Constructor called")
        context = application.applicationContext
    }

    fun getVisibility(): MutableLiveData<Int> {
        if (visibility == null) {
            visibility = MutableLiveData()
            visibility.value = 8
        }
        return visibility
    }


    fun observeLoginUser() = userRepository.observeLoginUser();

    fun onBtnLoginClick() {
        if (validateInputs()) {
            if (Utils.isNetworkConnected(context)) {
                getVisibility().value = 0 // show Progress Dialog
                GlobalScope.launch {
                    userRepository.loginUser(email.get(), password.get())
                }
            } else {
                mToastMessage.setValue("No Network Connection")
            }
        }
    }

    fun validateInputs(): Boolean {
        var isValid = true

        if (email.get() == null) {
            isValid = false
            mToastMessage.setValue("Enter Email id")

        } else if (!Utils.isEmailValid(email.get())) {
            isValid = false
            mToastMessage.setValue("Invalid Email")

        } else if (password.get() == null) {
            isValid = false
            mToastMessage.setValue("Enter Password")

        } else if (!Utils.isValidPassword(password.get())) {
            isValid = false
            mToastMessage.setValue("Password does not Match")
        }
        return isValid
    }

    fun getToastMessage(): LiveData<String> {
        return mToastMessage
    }
}