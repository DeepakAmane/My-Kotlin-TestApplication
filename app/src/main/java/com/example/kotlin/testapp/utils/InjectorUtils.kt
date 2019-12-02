package com.example.kotlin.testapp.utils

import android.app.Application
import android.util.Log
import com.example.kotlin.testapp.model.UserRepository
import com.example.kotlin.testapp.model.UserViewModelFactory
import com.example.kotlin.testapp.network.RetrofitClientInstance

object InjectorUtils {

    fun provideUserViewModelFactory(application: Application): UserViewModelFactory {
        Log.e("InjectorUtils", "provideUserViewModelFactory  Called")
        return UserViewModelFactory(application, UserRepository.getInstance(RetrofitClientInstance.getRetrofitClient()))
    }
}