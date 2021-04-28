package com.example.quizapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import com.example.quizapp.models.Questions

class OptionAdapter(val context: Context,val questions: Questions):
    RecyclerView.Adapter<OptionAdapter.OptionViewHolder>() {
    private  var options: List<String> = listOf(questions.option1,questions.option2,questions.option3,questions.option4)

    inner class OptionViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var optionView=itemView.findViewById<TextView>(R.id.Quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val view =LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return OptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        holder.optionView.text= options[position]
        holder.itemView.setOnClickListener{
           // Toast.makeText(context,options[position],Toast.LENGTH_SHORT).show()
            questions.UserAnswer=options[position]
            notifyDataSetChanged()
        }
        if (questions.UserAnswer==options[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
            //Toast.makeText(context,"Show Red Border",Toast.LENGTH_SHORT).show()
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}