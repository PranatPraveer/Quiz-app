package com.example.quizapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.Activities.QuestionActivity
import com.example.quizapp.R
import com.example.quizapp.models.Quiz
import com.example.quizapp.utils.ColourPicker
import com.example.quizapp.utils.IconPicker
import kotlinx.android.synthetic.main.quiz_item.view.*

class QuizAdapter(val context: Context,val quizzes:List<Quiz>):RecyclerView.Adapter<QuizAdapter.QuizviewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizviewHolder {
             val view = LayoutInflater.from(context).inflate(R.layout.quiz_item,parent,false)
             return QuizviewHolder(view)

    }

    override fun onBindViewHolder(holder: QuizviewHolder, position: Int) {
        holder.textViewTitle.text=quizzes[position].title
        holder.cardContainer.setCardBackgroundColor(Color.parseColor(ColourPicker.getcolor()))
        holder.iconView.setImageResource(IconPicker.getIcon())
        holder.itemView.setOnClickListener{
            Toast.makeText(context,quizzes[position].title,Toast.LENGTH_SHORT).show()
            val intent= Intent(context,QuestionActivity::class.java)
            intent.putExtra("DATE",quizzes[position].title)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
           return quizzes.size
    }
    inner class QuizviewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var textViewTitle:TextView= itemView.findViewById(R.id.QuizTitle)
        var iconView: ImageView= itemView.findViewById(R.id.QuizIcon)
        var cardContainer: CardView=itemView.findViewById(R.id.CardContainer)
    }
}