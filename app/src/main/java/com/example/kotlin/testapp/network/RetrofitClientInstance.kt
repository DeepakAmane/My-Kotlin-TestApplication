package com.example.kotlin.testapp.network

import android.util.Log
import com.example.kotlin.testapp.model.UserRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null

    init {
        Log.e("Constructor", "UserRepository Called")
    }

    fun getRetrofitClient(): Retrofit? {
        Log.e("retrofit", "NULL")
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl("https://bank-app-test.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            Log.e("retrofit", "CREATED")
        }
        return retrofit
    }
}