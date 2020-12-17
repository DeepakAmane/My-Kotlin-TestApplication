package com.example.kotlin.testapp.network.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "User")
data class UserAccount(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userId")
    val userId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "bankAccount")
    val bankAccount: String,

    @ColumnInfo(name = "agency")
    val agency: String,

    @ColumnInfo(name = "balance")
    val balance: Double) : Serializable {
}