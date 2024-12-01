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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Gunakan padding XML sebagai dasar
            val defaultPaddingLeft = view.paddingLeft
            val defaultPaddingTop = view.paddingTop
            val defaultPaddingRight = view.paddingRight
            val defaultPaddingBottom = view.paddingBottom

            // Tambahkan padding dari WindowInsets
            view.setPadding(
                defaultPaddingLeft + systemBarsInsets.left,
                defaultPaddingTop + systemBarsInsets.top,
                defaultPaddingRight + systemBarsInsets.right,
                defaultPaddingBottom + systemBarsInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

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

// Iterasi melalui setiap baris data
        for ((index, row) in data.withIndex()) {
            val tableRow = TableRow(this)

            // Atur latar belakang untuk header
            if (index == 0) {
                tableRow.setBackgroundColor(ContextCompat.getColor(this, R.color.light_gray))
            }

            for (cell in row) {
                val textView = TextView(this)
                textView.text = cell
                textView.setPadding(16, 16, 16, 16) // Padding dalam dp
                textView.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT
                )

                // Atur gaya teks untuk header
                if (index == 0) {
                    textView.setTypeface(null, Typeface.BOLD) // Teks tebal untuk header
                    textView.gravity = Gravity.CENTER
                } else {
                    textView.gravity = Gravity.START // Rata kiri untuk data
                }

                tableRow.addView(textView)
            }

            // Tambahkan garis pembatas di bawah setiap baris
            val divider = View(this)
            divider.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                1
            )
            divider.setBackgroundColor(ContextCompat.getColor(this, R.color.primary))

            tableLayout.addView(tableRow)
            if (index != data.size - 1) {
                tableLayout.addView(divider) // Jangan tambahkan garis pembatas di baris terakhir
            }
        }
        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)

        val editprofil: ImageButton = findViewById(R.id.ib_editprofile)
        val notification: ImageButton = findViewById(R.id.ib_notif)
        val termsAndCondition: ImageButton = findViewById(R.id.ib_terms)
        val faq: ImageButton = findViewById(R.id.ib_faq)
        val theme: ImageButton = findViewById(R.id.ib_theme)


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


    }
}