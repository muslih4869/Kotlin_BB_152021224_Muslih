package com.example.midterm_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.midterm_app.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    lateinit var  auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.logbackBtn.setOnClickListener{
            val bck = Intent(this, Login::class.java)
            startActivity(bck)
        }

        binding.btnRegister.setOnClickListener{
            val fullname = binding.etFullName.text.toString()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (fullname.isEmpty()){
                binding.etFullName.error = "Fill Your Name"
                binding.etFullName.requestFocus()
                return@setOnClickListener
            }
            if (username.isEmpty()){
                binding.etUsername.error = "Fill Username"
                binding.etUsername.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.etPassword.error = "Fill Password "
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            RegisterFirebase(fullname, username, password)

        }

    }

    private fun RegisterFirebase(fullname: String, username: String, password: String) {
        auth.createUserWithEmailAndPassword(username,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                    val reg = Intent(this, Login::class.java)
                    startActivity(reg)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}