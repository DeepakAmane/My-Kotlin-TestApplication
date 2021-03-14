package com.example.kotlin.testapp.network

import com.example.kotlin.testapp.network.response.LoginResponse
import com.example.kotlin.testapp.network.response.StatementResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface RetrofitServices {

    @FormUrlEncoded
    @POST("/api/login")
    suspend fun isValidUser(
        @Field("user") user: String?,
        @Field("password") password: String?
    ): Response<LoginResponse>


    @GET
    suspend fun doGetUserStatementList(@Url url: String): Response<StatementResponse>

}