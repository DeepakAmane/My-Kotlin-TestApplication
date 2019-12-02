package com.example.kotlin.testapp.network.response


data class StatementResponse(val statementList : List<Statement>, val error : Error) {
}