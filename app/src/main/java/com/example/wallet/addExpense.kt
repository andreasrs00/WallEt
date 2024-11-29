package com.example.wallet

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Timestamp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class addExpense : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_expense)

        // Initialize Firebase components
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        // Atur padding untuk insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)

        // **Fitur Date Input**
        val dateInput: EditText = findViewById(R.id.dateInput)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Tampilkan DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    val formattedDate = dateFormat.format(selectedDate.time)
                    dateInput.setText(formattedDate) // Set tanggal ke EditText
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        // **Fitur Amount Input dengan Format Rupiah**
        val amountInput: EditText = findViewById(R.id.amountInput)
        amountInput.addTextChangedListener(object : TextWatcher {
            private var isEditing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                try {
                    // Hapus format lama
                    val input = s.toString().replace("Rp. ", "").replace(".", "")
                    if (input.isNotEmpty()) {
                        // Parse angka ke Long untuk memformat ribuan
                        val parsed = input.toLong()

                        // Format angka dengan pemisah ribuan
                        val formatted = NumberFormat.getInstance(Locale("in", "ID")).format(parsed)

                        // Tambahkan prefix "Rp."
                        amountInput.setText("Rp. $formatted")
                        amountInput.setSelection(amountInput.text.length) // Posisikan kursor di akhir teks
                    } else {
                        // Jika input kosong, kosongkan EditText
                        amountInput.setText("")
                    }
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

                isEditing = false
            }
        })

        // **Save Button**
        val saveButton: View = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            // Get the data from input fields
            val date = dateInput.text.toString()
            val typeSpinner: Spinner = findViewById(R.id.TypeSpinner)
            val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
            val amountText = amountInput.text.toString().replace("Rp. ", "").replace(".", "") // Clean the currency format
            val amount = if (amountText.isNotEmpty()) amountText.toLong() else 0L
            val expenseTitleInput: EditText = findViewById(R.id.expenseTitleInput)
            val title = expenseTitleInput.text.toString()

            if (date.isEmpty() || title.isEmpty() || amount == 0L) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert the date string to a Timestamp
            val dateFormatForTimestamp = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val dateParsed = dateFormatForTimestamp.parse(date)
            val timestamp = Timestamp(dateParsed!!)

            // Get the user ID (assuming the user is logged in)
            val userId = auth.currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a map to save to Firestore
            val transactionData = hashMapOf(
                "Amount" to amount, // number type
                "Category" to categorySpinner.selectedItem.toString(), // string type
                "Date" to timestamp, // timestamp type
                "Type" to typeSpinner.selectedItem.toString(), // string type
                "Title" to title // string type
            )

            // Save the data to Firestore under the collection 'Transaction' and document userId
            firestore.collection("Transaction")
                .document(userId)
                .collection("Expenses")
                .add(transactionData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Expense saved successfully", Toast.LENGTH_SHORT).show()
                    finish() // Optionally, finish the activity or clear inputs
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving expense: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
