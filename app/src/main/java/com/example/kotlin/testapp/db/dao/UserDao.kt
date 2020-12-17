package com.example.kotlin.testapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlin.testapp.network.response.UserAccount

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: UserAccount)

    @Query("SELECT * FROM user")
    fun getUserRecords(): List<UserAccount>
}