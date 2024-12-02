package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class signupPage : AppCompatActivity() {

    // Firebase instance
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()

        // Form fields
        val emailField = findViewById<EditText>(R.id.edt_email_signup)
        val passwordField = findViewById<EditText>(R.id.edt_password_signup)
        val btnDaftar = findViewById<Button>(R.id.btn_daftar)
        val btnMasuk = findViewById<TextView>(R.id.tv_masuk)

        btnMasuk.setOnClickListener {
            val intent = Intent(this, loginPage::class.java)
            startActivity(intent)
        }

        // Handle signup button click
        btnDaftar.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user with Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Navigate to loginPage after successful signup
                        val intent = Intent(this, dataSignUp::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Signup gagal: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
