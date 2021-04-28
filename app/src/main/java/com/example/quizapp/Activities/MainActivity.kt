package com.example.quizapp.Activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.quizapp.R
import com.example.quizapp.adapters.QuizAdapter
import com.example.quizapp.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    lateinit var firestore: FirebaseFirestore
    private var QuizList= mutableListOf<Quiz>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupviews()
        setupRecyclerView()
        setupDatePicker()
    }

    private fun setupDatePicker() {
        btnDatePicker.setOnClickListener{
            val datePicker:MaterialDatePicker<Long> = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager,"DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("Datepicker",datePicker.headerText)
                val dateformatter = SimpleDateFormat("mm-dd-yyyy")
                val date= dateformatter.format(Date(it))
                val intent=Intent(this,QuestionActivity::class.java)
                intent.putExtra("DATE",date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener{
                Log.d("Datepicker",datePicker.headerText)

            }
            datePicker.addOnCancelListener{
                Log.d("Datepicker","Date Picker is cancelled")
            }
        }
    }


    private fun setupRecyclerView() {
        adapter= QuizAdapter(this,QuizList)
        QuizRecyclerView.layoutManager=GridLayoutManager(this,2)
        QuizRecyclerView.adapter=adapter
    }

    private fun setupviews() {
        setupDrawersLayout()
        setupFireStore()
    }

    private fun setupFireStore() {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference=firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value==null|| error!=null){
                Toast.makeText(this,"problem in fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA",value.toObjects(Quiz::class.java).toString())
            QuizList.clear()
            QuizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupDrawersLayout() {
        setSupportActionBar(AppBar)
        actionBarDrawerToggle = ActionBarDrawerToggle(this,mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        Navigationview.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.BtnProfile -> {
                    val intent=Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }
                R.id.BtnFollowus -> {
                    val url:String="https://github.com/PranatPraveer"
                    val intent=Intent(Intent.ACTION_VIEW)
                    intent.setData(Uri.parse(url))
                    startActivity(intent)
                    true
                }
            }
           false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}