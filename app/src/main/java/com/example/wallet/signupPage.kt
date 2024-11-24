package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log


class signupPage : AppCompatActivity() {

    // Firebase instance
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Form fields
        val nameField = findViewById<EditText>(R.id.edt_nama)
        val emailField = findViewById<EditText>(R.id.edt_email_signup)
        val passwordField = findViewById<EditText>(R.id.edt_password_signup)
        val genderGroup = findViewById<RadioGroup>(R.id.edt_gender)
        val btnDaftar = findViewById<Button>(R.id.btn_daftar)

        // Handle signup button click
        btnDaftar.setOnClickListener {
            val name = nameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()
            val gender = when (genderGroup.checkedRadioButtonId) {
                R.id.gender_male -> "Laki-Laki"
                R.id.gender_female -> "Perempuan"
                else -> ""
            }

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create user with Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Get current user ID
                        val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener

                        // Save user data to Firestore
                        val userData = mapOf(
                            "name" to name,
                            "email" to email,
                            "gender" to gender
                        )

                        firestore.collection("users").document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                // Log success
                                Toast.makeText(
                                    this,
                                    "Data berhasil disimpan ke Firestore!",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Navigate to afterSignUp
                                startActivity(Intent(this, afterSignUp::class.java))
                                val intent = Intent(this, loginPage::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                // Log failure
                                Toast.makeText(
                                    this,
                                    "Gagal menyimpan data: ${e.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e("TAG", "Gagal menyimpan data: ${e.message}")

                            }
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