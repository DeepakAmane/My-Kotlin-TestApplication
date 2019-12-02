package com.example.kotlin.testapp.network.response

import java.io.Serializable

data class Statement(
    val title: String, val desc: String, val date: String,
    val value: Double
) : Serializable {
}