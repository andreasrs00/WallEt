package com.example.wallet

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        // Ambil UID pengguna yang sedang login
        val userId = auth.currentUser?.uid

        // Ambil data pengguna dari Firestore
        if (userId != null) {
            firestore.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        // Ambil data firstname dan lastname dari Firestore
                        val firstName = document.getString("name")
                        val lastName = document.getString("fullName")

                        // Tampilkan data pada TextView
                        val tvFirstName: TextView = findViewById(R.id.tvfirstname)
                        val tvLastName: TextView = findViewById(R.id.tvlastname)

                        tvFirstName.text = firstName ?: "Nama Depan Tidak Tersedia"
                        tvLastName.text = lastName ?: "Nama Belakang Tidak Tersedia"
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Gagal mengambil data pengguna: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }

        // Atur padding untuk insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            val defaultPaddingLeft = view.paddingLeft
            val defaultPaddingTop = view.paddingTop
            val defaultPaddingRight = view.paddingRight
            val defaultPaddingBottom = view.paddingBottom

            view.setPadding(
                defaultPaddingLeft + systemBarsInsets.left,
                defaultPaddingTop + systemBarsInsets.top,
                defaultPaddingRight + systemBarsInsets.right,
                defaultPaddingBottom + systemBarsInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        // Data untuk tabel
        val data = listOf(
            listOf("Tanggal", "Keterangan", "Pemasukan", "Pengeluaran"),
            listOf("2 Maret 2024", "Gaji", "5,000,000", "-"),
            listOf("3 Maret 2024", "Belanja", "-", "1,000,000"),
            listOf("5 Maret 2024", "Investasi", "2,000,000", "-"),
            listOf("10 Maret 2024", "Makan Siang", "-", "50,000"),
            listOf("15 Maret 2024", "Bonus", "1,000,000", "-"),
            listOf("20 Maret 2024", "Transportasi", "-", "100,000")
        )

        val tableLayout: TableLayout = findViewById(R.id.tabel_layout)

        // Iterasi melalui data dan buat tabel
        for ((index, row) in data.withIndex()) {
            val tableRow = TableRow(this)

            // Tambahkan latar belakang untuk header
            if (index == 0) {
                tableRow.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
            }

            for (cell in row) {
                val textView = TextView(this)
                textView.text = cell
                textView.setPadding(16, 16, 16, 16)
                textView.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                if (index == 0) {
                    textView.setTypeface(null, Typeface.BOLD)
                    textView.gravity = Gravity.CENTER
                } else {
                    textView.gravity = Gravity.START
                }

                tableRow.addView(textView)
            }

            // Tambahkan pembatas
            val divider = View(this)
            divider.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                1
            )
            divider.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))

            tableLayout.addView(tableRow)
            if (index != data.size - 1) {
                tableLayout.addView(divider)
            }
        }

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)

        // Tombol lain
        val editprofil: ImageButton = findViewById(R.id.ib_editprofile)
        val notification: ImageButton = findViewById(R.id.ib_notif)
        val termsAndCondition: ImageButton = findViewById(R.id.ib_terms)
        val faq: ImageButton = findViewById(R.id.ib_faq)
        val theme: ImageButton = findViewById(R.id.ib_theme)
        val logoutButton: ImageButton = findViewById(R.id.ib_logout)

        faq.setOnClickListener {
            val intent = Intent(this, FAQ::class.java)
            startActivity(intent)
        }

        theme.setOnClickListener {
            val intent = Intent(this, Theme::class.java)
            startActivity(intent)
        }

        termsAndCondition.setOnClickListener {
            val intent = Intent(this, TermsAndCondition::class.java)
            startActivity(intent)
        }

        editprofil.setOnClickListener {
            val intent = Intent(this, editprofilePage::class.java)
            startActivity(intent)
        }

        notification.setOnClickListener {
            val intent = Intent(this, notificationPage::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val sharedPreferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("IS_LOGGED_IN", false)
            editor.apply()

            Toast.makeText(this, "Logout berhasil!", Toast.LENGTH_SHORT).show()


            val intent = Intent(this, landingPage::class.java)
            startActivity(intent)
            finish()
        }
    }
}
