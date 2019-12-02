package com.example.kotlin.testapp.network.response

import java.io.Serializable

data class Error(val code: Int, var message: String) : Serializable {

}