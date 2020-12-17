package com.example.kotlin.testapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.kotlin.testapp.model.UserRepository
import com.example.kotlin.testapp.network.response.UserAccount
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * StatementList UI holder for
 * StatementListActivity
 **/
class StatementsListViewModel(
    application: Application,
    private val userRepository: UserRepository
) :
    AndroidViewModel(application) {

    init {
        Log.e("StatementsListViewModel", "Constructor called")
    }

    fun observeStatementList() = userRepository.observeStatementList();

    fun onLaunchPullStatementList(userAccount: UserAccount?) = userRepository.fetchStatementList(userAccount)


}