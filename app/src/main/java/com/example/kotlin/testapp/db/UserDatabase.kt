package com.example.kotlin.testapp.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlin.testapp.db.dao.StatementDao
import com.example.kotlin.testapp.db.dao.UserDao
import com.example.kotlin.testapp.network.response.Statement
import com.example.kotlin.testapp.network.response.UserAccount

@Database(
    entities = [UserAccount::class, Statement::class],
    version = 2
)
abstract class UserDatabase : RoomDatabase() {

    init {
        Log.e(UserDatabase::class.java.name, "User Data base")
    }

    abstract fun getUserDao(): UserDao
    abstract fun getStatementDao(): StatementDao


    companion object {
        @Volatile
        private var instance: UserDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance
            ?: synchronized(LOCK) {
                instance
                    ?: createDatabase(
                        context
                    ).also { instance = it }
            }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "UserDB.db"
            ).build()// .allowMainThreadQueries().build()
    }
}