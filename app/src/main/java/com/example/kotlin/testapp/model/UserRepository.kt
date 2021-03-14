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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class UserRepository(private val retrofitClient: Retrofit?, private val db: UserDatabase) {

    private val mUserLoginResponse = MutableLiveData<UserAccount>()
    private val mStatementListResponse = MutableLiveData<StatementResponse>()

    init {
        Log.e("Constructor", "UserRepository Called")
    }

    /**
     * Perform User Login autentication via API
     **/
    suspend fun loginUser(userName: String?, password: String?) {

        Log.e("loginUser()", "Thread Name ${Thread.currentThread().name}")

        if (db.getUserDao().getUserRecords().isNotEmpty()) {
            Log.e(UserRepository::class.java.name, "db object user received")
            val userAccount = db.getUserDao().getUserRecords().get(0)  // get the data from DB
            withContext(Dispatchers.Main) {
                Log.e("Main Thread", "Thread Name ${Thread.currentThread().name}")
                mUserLoginResponse.value = userAccount  // set the value in the main thread
            }
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                val client = retrofitClient!!.create(RetrofitServices::class.java)
                val response = client.isValidUser(userName, password)
                if (response.isSuccessful) {
                    Log.e(
                        UserRepository::class.java.name,
                        "response for the user details : ${response.body()}"
                    )
                    withContext(Dispatchers.Main) {
                        mUserLoginResponse.value =
                            response.body()!!.userAccount  // setting the value needs to be done in the main Thread
                    }

                    db.getUserDao().insert(response.body()!!.userAccount)
                } else {
                    Log.e(
                        UserRepository::class.java.name,
                        "No response for the user details : ${response.body()}"
                    )
                }
            }
            /*  retrofitClient!!.create(RetrofitServices::class.java).apply {

                  isValidUser(userName, password)?.enqueue(object : Callback<LoginResponse> {
                      override fun onResponse(
                          call: Call<LoginResponse>,
                          loginUserResponse: retrofit2.ResploginAonse<LoginResponse>
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
              }*/
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
    suspend fun fetchStatementList(userAccount: UserAccount?) {

        /* Log.e(UserRepository::class.java.name, "Thread Name ${Thread.currentThread().name}")*/
        if (db.getStatementDao().getUserRecords().isNotEmpty()) {

            val statementResponse =
                StatementResponse(db.getStatementDao().getUserRecords(), Error(200, "Cached"))
            withContext(Dispatchers.Main) {
                /*   Log.e(UserRepository::class.java.name,"Thread Name")*/
                mStatementListResponse.value = statementResponse
            }
        } else {
            GlobalScope.launch {
                val client = retrofitClient!!.create(RetrofitServices::class.java)
                val response =
                    client.doGetUserStatementList("https://bank-app-test.herokuapp.com/api/statements/" + userAccount?.userId)
                if (response.isSuccessful) {
                    Log.e(
                        UserRepository::class.java.name,
                        "response for the Statement list : ${response.body()}"
                    )
                    withContext(Dispatchers.Main) {
                        mStatementListResponse.value =
                            response.body() // setting the value to the mutablelivedata member  field to get notified to its observers
                        //NOTE :  setting the value to the mutablefield on the main Thread
                    }
                    db.getStatementDao().add(response.body()!!.statementList)
                } else {
                    Log.e(
                        UserRepository::class.java.name,
                        "No Response for the statement list : ${response.body()}"
                    )
                }
            }

            /* retrofitClient!!.create(RetrofitServices::class.java).apply {

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
             }*/
        }
    }
}


