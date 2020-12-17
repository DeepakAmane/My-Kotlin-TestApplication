package com.example.kotlin.testapp.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin.testapp.db.UserDatabase
import com.example.kotlin.testapp.network.RetrofitServices
import com.example.kotlin.testapp.network.response.Error
import com.example.kotlin.testapp.network.response.LoginResponse
import com.example.kotlin.testapp.network.response.StatementResponse
import com.example.kotlin.testapp.network.response.UserAccount
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(private val retrofitClient: Retrofit?, private val db: UserDatabase) {

    private val mUserLoginResponse = MutableLiveData<UserAccount>()
    private val mStatementListResponse = MutableLiveData<StatementResponse>()
    private lateinit var localResponse: StatementResponse

    init {
        Log.e("Constructor", "UserRepository Called")
    }

    /**
     * Perform User Login autentication via API
     **/
    fun loginUser(userName: String?, password: String?) {

        if (db.getUserDao().getUserRecords().isNotEmpty()) {
            Log.e(UserRepository::class.java.name, "db object user received")
            mUserLoginResponse.value = db.getUserDao().getUserRecords().get(0)
        } else {
            retrofitClient!!.create(RetrofitServices::class.java).apply {

                isValidUser(userName, password)?.enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(
                        call: Call<LoginResponse>,
                        loginUserResponse: retrofit2.Response<LoginResponse>
                    ) {
                        Log.e("GOT", "RESPONSE")
                        loginUserResponse.body()?.let {
                            mUserLoginResponse.value = loginUserResponse.body()!!.userAccount
                            db.getUserDao().insert(loginUserResponse.body()!!.userAccount)
                        }
                    }
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        t.printStackTrace()
                    }
                })
            }
        }
    }

    fun observeLoginUser(): LiveData<UserAccount> {
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

        if (db.getStatementDao().getUserRecords().isNotEmpty()) {

            val statementResponse =
                StatementResponse(db.getStatementDao().getUserRecords(), Error(200, "Cached"))
            mStatementListResponse.value = statementResponse

        } else {
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
                                db.getStatementDao()
                                    .add(statementListResponse.body()!!.statementList)
                            }
                        }

                        override fun onFailure(call: Call<StatementResponse>, t: Throwable) {
                            t.printStackTrace()
                        }
                    })
            }
        }
    }
}


