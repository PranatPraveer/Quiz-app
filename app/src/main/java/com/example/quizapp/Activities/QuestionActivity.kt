package com.example.quizapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.quizapp.R
import com.example.quizapp.models.Questions
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.adapters.OptionAdapter
import com.example.quizapp.models.Quiz
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_question.*

class QuestionActivity : AppCompatActivity() {

    var quizzes:MutableList<Quiz>?=null
    var questions:MutableMap<String,Questions>?=null
    var index=1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        setupFirestore()
        setupEventListener()
    }

    private fun setupEventListener() {
        btnPrevious.setOnClickListener{
            index--
            bindviews()
        }
        btnnext.setOnClickListener{
            index++
            bindviews()
        }
        btnSubmit.setOnClickListener{
            Log.d("FINALQUIZ", questions.toString())

            val intent = Intent(this, ResultActivity::class.java)
            val json  = Gson().toJson(quizzes!![0])
            intent.putExtra("QUIZ", json)
            startActivity(intent)
            if (index== questions!!.size){
                finish()
            }
        }
    }

    private fun setupFirestore() {
        val firestore= FirebaseFirestore.getInstance()
        var date= intent.getStringExtra("DATE")
        if (date!=null) {
            firestore.collection("quizzes").whereEqualTo("title", date).get().addOnSuccessListener {
                if (it != null&& !it.isEmpty) {
                    quizzes=it.toObjects(Quiz::class.java)
                    questions=quizzes!![0].questions
                    bindviews()
                }
            }
        }

    }

    private fun bindviews() {
        btnPrevious.visibility = View.GONE
        btnSubmit.visibility = View.GONE
        btnnext.visibility = View.GONE

        if (index == 1) {
            btnnext.visibility = View.VISIBLE
        } else if (index == questions!!.size) {
            btnSubmit.visibility = View.VISIBLE
            btnPrevious.visibility = View.VISIBLE
        } else {
            btnPrevious.visibility = View.VISIBLE
            btnnext.visibility = View.VISIBLE
        }
        val questions = questions!!["question$index"]
        questions?.let {
            description.text = it.description
            val optionAdapter = OptionAdapter(this, it)
            optionList.layoutManager = LinearLayoutManager(this)
            optionList.adapter = optionAdapter
            optionList.setHasFixedSize(true)
        }
    }
}
