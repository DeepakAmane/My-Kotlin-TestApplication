package com.example.kotlin.testapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.testapp.network.RetrofitServices
import com.example.kotlin.testapp.network.response.LoginResponse
import com.example.kotlin.testapp.network.response.StatementResponse
import com.example.kotlin.testapp.network.response.UserAccount
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(private val retrofitClient: Retrofit?) {

    private val mUserLoginResponse = MutableLiveData<LoginResponse>()
    private val mStatementListResponse = MutableLiveData<StatementResponse>()

    init {
        Log.e("Constructor", "UserRepository Called")
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(retrofit: Retrofit?) =
            this.instance ?: synchronized(this) {
                this.instance ?: UserRepository(retrofit).also { this.instance = it }
            }
    }

    /**
     * Perform User Login autentication via API
     **/
    fun loginUser(userName: String?, password: String?) {
        retrofitClient!!.create(RetrofitServices::class.java).apply {

            isValidUser(userName, password)?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    loginUserResponse: retrofit2.Response<LoginResponse>
                ) {
                    Log.e("GOT", "RESPONSE")
                    loginUserResponse.body()?.let {
                        mUserLoginResponse.value = loginUserResponse.body()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    fun observeLoginUser(): LiveData<LoginResponse> {
        return mUserLoginResponse
    }

    fun observeStatementList(): LiveData<StatementResponse> {
        return mStatementListResponse
    }

    /***
     * API call for fethcing the Statements List for the Respective USer ID
     * @param userAccount
     */
    fun fetchStatementList(userAccount: UserAccount?) {

        retrofitClient!!.create(RetrofitServices::class.java).apply {

            doGetUserStatementList("https://bank-app-test.herokuapp.com/api/statements/" + userAccount?.userId).enqueue(
                object : Callback<StatementResponse> {
                    override fun onResponse(
                        call: Call<StatementResponse>,
                        statementListResponse: Response<StatementResponse>
                    ) {
                        statementListResponse.body()?.let {
                            Log.e("Succcess", "Success")
                            mStatementListResponse.value = statementListResponse.body()
                        }
                    }

                    override fun onFailure(call: Call<StatementResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
        }
    }
}


