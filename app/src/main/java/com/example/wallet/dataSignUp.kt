package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class dataSignUp : AppCompatActivity() {

    // Firebase instance
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_sign_up)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Form fields
        val fullNameField = findViewById<EditText>(R.id.edt_nama_lengkap)
        val nameField = findViewById<EditText>(R.id.edt_nama_panggilan)
        val emailField = findViewById<EditText>(R.id.edt_email_signup)
        val passwordField = findViewById<EditText>(R.id.edt_password_signup)  // Field untuk password
        val genderGroup = findViewById<RadioGroup>(R.id.edt_gender)
        val btnDaftar = findViewById<Button>(R.id.btn_daftar)

        // Handle signup button click
        btnDaftar.setOnClickListener {
            val name = nameField.text.toString().trim()
            val fullName = fullNameField.text.toString().trim()
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()  // Ambil password
            val gender = when (genderGroup.checkedRadioButtonId) {
                R.id.gender_male -> "Laki-Laki"
                R.id.gender_female -> "Perempuan"
                else -> ""
            }

            if (name.isEmpty() || fullName.isEmpty() || email.isEmpty() || password.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validasi password (misalnya minimal 6 karakter)
            if (password.length < 6) {
                Toast.makeText(this, "Password harus memiliki minimal 6 karakter!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Coba login dengan email dan password
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Login berhasil, simpan data ke Firestore
                        val userId = firebaseAuth.currentUser?.uid ?: return@addOnCompleteListener

                        val userData = mapOf(
                            "name" to name,
                            "fullName" to fullName,
                            "email" to email,
                            "gender" to gender
                        )

                        firestore.collection("users").document(userId)
                            .set(userData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Data berhasil disimpan ke Firestore!", Toast.LENGTH_SHORT).show()

                                // Navigasi ke halaman setelah registrasi (afterSignUp)
                                Handler().postDelayed({
                                    val intent = Intent(this, afterSignUp::class.java)
                                    startActivity(intent)
                                }, 1000)  // Delay 1 detik sebelum pindah halaman

                                // Tambahkan delay lagi sebelum masuk ke loginPage
                                Handler().postDelayed({
                                    val loginIntent = Intent(this, loginPage::class.java)
                                    loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(loginIntent)
                                    finish()  // Tutup aktivitas saat ini (dataSignUp)
                                }, 2000)  // Delay 1 detik setelah afterSignUp sebelum loginPage
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                                Log.e("TAG", "Gagal menyimpan data: ${e.message}")
                            }
                    } else {
                        // Jika login gagal, berarti email belum terdaftar
                        Toast.makeText(this, "Email belum terdaftar, silakan daftar terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
