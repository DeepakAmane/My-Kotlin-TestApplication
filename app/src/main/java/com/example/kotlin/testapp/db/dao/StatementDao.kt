package com.example.kotlin.testapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlin.testapp.network.response.Statement
import com.example.kotlin.testapp.network.response.UserAccount

@Dao
interface StatementDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(users: List<Statement>)

    @Query("SELECT * FROM statement")
    fun getUserRecords(): List<Statement>
}