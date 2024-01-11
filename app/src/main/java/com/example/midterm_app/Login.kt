package com.example.midterm_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midterm_app.databinding.ActivityLoginBinding
import com.example.midterm_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.regBtn.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener{
            val user = binding.etUsr.text.toString()
            val password = binding.etPass.text.toString()
            if (user.isEmpty()){
                binding.etUsr.error = "Fill Username"
                binding.etUsr.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPass.error = "Fill Password "
                binding.etPass.requestFocus()
                return@setOnClickListener
            }

            LoginFirebase(user, password)
            }

        }

    private fun LoginFirebase(user: String, password: String) {
        auth.signInWithEmailAndPassword(user,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val log = Intent(this, fHome::class.java)
                    startActivity(log)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
    }
