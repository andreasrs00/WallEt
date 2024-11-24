package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class loginPage : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth // Firebase Authentication instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Find views
        val emailField = findViewById<EditText>(R.id.edt_email_login)
        val passwordField = findViewById<EditText>(R.id.edt_password_login)
        val btnLogin = findViewById<View>(R.id.btn_login)
        val tvDaftar = findViewById<View>(R.id.tv_daftar)

        // Handle login button click
        btnLogin.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Login with Firebase
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Navigate to HomePage
                        val intent = Intent(this, HomePage::class.java)
                        startActivity(intent)
                        finish() // Close LoginPage
                    } else {
                        // Show error message
                        Toast.makeText(this, "Login gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Handle "Daftar" text click
        tvDaftar.setOnClickListener {
            val intent = Intent(this, signupPage::class.java)
            startActivity(intent)
        }
    }
}
