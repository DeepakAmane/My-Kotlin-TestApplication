package com.example.kotlin.testapp.network.response

import java.io.Serializable

data class LoginResponse(
     val userAccount: UserAccount,
     var error: Error) : Serializable {
}