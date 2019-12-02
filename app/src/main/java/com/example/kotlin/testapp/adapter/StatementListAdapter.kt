package com.example.kotlin.testapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin.testapp.R
import com.example.kotlin.testapp.network.response.Statement

import java.util.ArrayList

class StatementListAdapter : RecyclerView.Adapter<StatementListAdapter.StatementHolder>() {

    private var statementList: List<Statement> = ArrayList<Statement>()

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_statement_item, parent, false)
        return StatementHolder(itemView)
    }


    override fun onBindViewHolder(holder: StatementHolder, position: Int) {

        val statement = statementList[position]
        holder.title.text = statement.title
        holder.description.text = statement.desc
        holder.date.text = statement.date
        holder.value.text = "${statement.value}"
    }

    override fun getItemCount(): Int {
        return statementList.size
    }


    fun setStatementList(statementList: List<Statement>) {
        this.statementList = statementList
        notifyDataSetChanged()
    }

    class StatementHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val title: TextView = itemView.findViewById(R.id.title_textView)
        internal val description: TextView = itemView.findViewById(R.id.description_textView)
        internal val date: TextView = itemView.findViewById(R.id.date_textView)
        internal val value: TextView = itemView.findViewById(R.id.value_textView)
    }

}
