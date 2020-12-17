package com.example.kotlin.testapp.app

import android.app.Application
import android.util.Log
import com.example.kotlin.testapp.db.UserDatabase
import com.example.kotlin.testapp.model.UserRepository
import com.example.kotlin.testapp.model.UserViewModelFactory
import com.example.kotlin.testapp.network.RetrofitServices
import okhttp3.OkHttpClient
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@App))

        Log.e(App::class.java.name, "App class | Retrfoit instance creation about to start")
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl("https://bank-app-test.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }

        bind() from singleton { UserDatabase(instance()) }


        bind() from singleton {
            UserRepository(instance(), instance())
        }


        bind() from provider {
            UserViewModelFactory(instance(), instance())
        }
    }
}