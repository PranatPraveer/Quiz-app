package com.example.quizapp.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R

class splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(1200)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    val intent = Intent(this@splash, LoginIntro::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        } ;thread.start ()
    }
}