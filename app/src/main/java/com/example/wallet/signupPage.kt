package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class signupPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup_page)

        // Handle btn_daftar click
        findViewById<View>(R.id.btn_daftar)?.setOnClickListener {
            // Navigate to afterSignUp
            startActivity(Intent(this, afterSignUp::class.java))

            // Use a Handler to navigate to LoginPage after 1 second
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, loginPage::class.java)

                // Clear previous activities from the stack to prevent afterSignUp from being called again
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish() // Close SignupPage
            }, 1000) // Delay of 1 second
        }

        // Handle tv_masuk click
        findViewById<View>(R.id.tv_masuk)?.setOnClickListener {
            // Navigate directly to LoginPage
            val intent = Intent(this, loginPage::class.java)
            startActivity(intent)
            finish() // Close SignupPage
        }
    }
}
