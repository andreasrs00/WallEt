package com.example.wallet

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val myLayout = findViewById<View>(R.id.main)

        myLayout?.setOnApplyWindowInsetsListener { view, insets ->
            view.onApplyWindowInsets(insets)


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


    }
}