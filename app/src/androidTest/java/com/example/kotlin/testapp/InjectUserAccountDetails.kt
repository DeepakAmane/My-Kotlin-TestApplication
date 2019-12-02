package com.example.kotlin.testapp

import com.example.kotlin.testapp.network.response.UserAccount

object InjectUserAccountDetails {
    fun injectUserAccount(): UserAccount {
        return UserAccount(1, "Jose da Silva Teste", "2050", "012314564", 3.3445)
    }
}