package com.example.quizapp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.quizapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.BtnSignup
import kotlinx.android.synthetic.main.activity_sign_up.EtEmailaddress
import kotlinx.android.synthetic.main.activity_sign_up.EtPassword

class SignUp : AppCompatActivity() {

    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        firebaseAuth= FirebaseAuth.getInstance()
        BtnSignup.setOnClickListener{
            signupUser()
        }
        BtnSignin.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun signupUser(){
        val email=EtEmailaddress.text.toString()
        val password=EtPassword.text.toString()
        val confirmpassword= Etconfirmpassword.text.toString()
        if (email.isBlank()||password.isBlank()||confirmpassword.isBlank()){
            Toast.makeText(this,"Email and Password can't be blank",Toast.LENGTH_SHORT).show()
            return
        }
        if (password!= confirmpassword){
            Toast.makeText(this,"Password and Confirm Password is not the same",Toast.LENGTH_SHORT).show()
            return
        }
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"created User.",Toast.LENGTH_SHORT).show()
                    val intent= Intent(this, MainActivity::class.java)
                    startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Error creating User.",Toast.LENGTH_SHORT).show()
            }
        }

    }
}