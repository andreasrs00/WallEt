package com.example.wallet

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.adapter.FaqAdapter
import com.example.wallet.model.DataFaq

class FAQ : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_faq)

        // Menyesuaikan padding untuk sistem bar
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backarrorw: ImageView = findViewById(R.id.backArrow)

        backarrorw.setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }


        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FaqAdapter(
            listOf(
                DataFaq("Apa itu WallEt?", "WallEt adalah aplikasi dompet digital yang memudahkan transaksi keuangan secara online."),
                DataFaq("Bagaimana cara mendaftar?", "Anda dapat mendaftar melalui aplikasi dengan memasukkan data pribadi Anda."),
                DataFaq("Apakah WallEt aman?", "Ya, WallEt menggunakan enkripsi tinggi untuk melindungi data pengguna."),
                DataFaq("Bisakah saya menggunakan WallEt di luar negeri?", "Ya, WallEt dapat digunakan di banyak negara yang mendukung."),
                DataFaq("Apa keuntungan menggunakan WallEt?", "WallEt memudahkan pembayaran, transfer uang, dan mengelola keuangan Anda."),
                DataFaq("Bagaimana cara menambahkan saldo ke WallEt?", "Anda bisa menambahkan saldo melalui transfer bank atau menggunakan kartu kredit/debit."),
                DataFaq("Apa saja jenis pembayaran yang bisa dilakukan dengan WallEt?", "WallEt mendukung pembayaran untuk berbagai layanan seperti belanja online, tagihan, dan pembelian pulsa."),
                DataFaq("Apakah WallEt mengenakan biaya transaksi?", "WallEt mungkin mengenakan biaya kecil untuk transaksi tertentu seperti transfer antar bank atau penarikan tunai."),
                DataFaq("Bagaimana cara menarik uang dari WallEt?", "Anda dapat menarik uang ke rekening bank yang terdaftar di aplikasi WallEt."),
                DataFaq("Bagaimana cara mengganti nomor telepon yang terdaftar di WallEt?", "Anda dapat mengganti nomor telepon di pengaturan aplikasi setelah melalui verifikasi keamanan."),
                DataFaq("Apakah WallEt mendukung berbagai mata uang?", "Ya, WallEt mendukung beberapa mata uang tergantung pada negara tempat Anda mendaftar."),
                DataFaq("Bagaimana cara menghapus akun WallEt?", "Anda bisa menghapus akun WallEt melalui pengaturan aplikasi, namun pastikan untuk menarik saldo terlebih dahulu."),
                DataFaq("Apakah WallEt mendukung transaksi internasional?", "Ya, WallEt memungkinkan transaksi internasional, tergantung pada negara dan mata uang yang didukung."),
                DataFaq("Bagaimana cara menghubungi customer service WallEt?", "Anda dapat menghubungi customer service melalui fitur chat dalam aplikasi atau melalui email."),
                DataFaq("Apa yang harus saya lakukan jika saya lupa PIN WallEt?", "Anda dapat mereset PIN melalui aplikasi dengan mengikuti prosedur pemulihan yang aman."),
                DataFaq("Bagaimana cara melindungi akun WallEt saya?", "Pastikan Anda mengaktifkan autentikasi dua faktor dan tidak membagikan informasi akun dengan orang lain.")
            )

        )
    }
}
