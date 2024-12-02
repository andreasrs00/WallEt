package com.example.wallet

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.ParseException
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

        // Set padding for window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val navbar = findViewById<View>(R.id.navbar)
        NavBarComponent(this, navbar)

        // **Date Input Feature**
        val dateInput: EditText = findViewById(R.id.dateInput)
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        dateInput.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Show DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(selectedYear, selectedMonth, selectedDay)
                    val formattedDate = dateFormat.format(selectedDate.time)
                    dateInput.setText(formattedDate) // Set the date to EditText
                },
                year, month, day
            )
            datePickerDialog.show()
        }

        // **Amount Input with Rupiah Format**
        val amountInput: EditText = findViewById(R.id.amountInput)
        amountInput.addTextChangedListener(object : TextWatcher {
            private var isEditing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true
                try {
                    val input = s.toString().replace("Rp. ", "").replace(".", "")
                    if (input.isNotEmpty()) {
                        val parsed = input.toLong()
                        val formatted = NumberFormat.getInstance(Locale("in", "ID")).format(parsed)
                        amountInput.setText("Rp. $formatted")
                        amountInput.setSelection(amountInput.text.length)
                    } else {
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
            val date = dateInput.text.toString()
            val typeSpinner: Spinner = findViewById(R.id.TypeSpinner)
            val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
            val amountText = amountInput.text.toString().replace("Rp. ", "").replace(".", "")
            val amount = if (amountText.isNotEmpty()) amountText.toLong() else 0L
            val expenseTitleInput: EditText = findViewById(R.id.expenseTitleInput)
            val title = expenseTitleInput.text.toString()

            // Validation
            if (date.isEmpty() || title.isEmpty() || amount == 0L) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert date string to Timestamp
            val dateParsed = try {
                dateFormat.parse(date)
            } catch (e: ParseException) {
                null
            }
            if (dateParsed == null) {
                Toast.makeText(this, "Invalid date format", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val timestamp = Timestamp(dateParsed)

            // Get user ID
            val userId = auth.currentUser?.uid
            if (userId == null) {
                Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("FirestoreDebug", "User ID: $userId")

            // Determine whether it's income or expense
            val transactionType = typeSpinner.selectedItem.toString()

            // Create data map
            val transactionData = hashMapOf(
                "Amount" to amount, // Number
                "Category" to categorySpinner.selectedItem.toString(), // String
                "Date" to timestamp, // Timestamp
                "Expense Title" to title, // String
                "Type" to transactionType // String
            )

            // Log data for debugging
            Log.d("FirestoreData", "Data to save: $transactionData")

            // Save to Firestore under the user's UID in 'transactions'
            firestore.collection("Transaction")
                .document(userId) // Main document is the user's UID
                .collection("categories") // Sub-collection for categories
                .document(categorySpinner.selectedItem.toString()) // Document for specific category
                .collection(transactionType.lowercase()) // Sub-collection for income or expense
                .add(transactionData) // Add the transaction
                .addOnSuccessListener {
                    Toast.makeText(this, "Transaction saved successfully", Toast.LENGTH_SHORT).show()
                    navigateToCategories()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error saving transaction: ${e.message}", Toast.LENGTH_SHORT).show()
                    navigateToCategories()
                }
        }
    }

    private fun navigateToCategories() {
        val intent = Intent(this, categories::class.java) // Change to your categories activity
        startActivity(intent)
        finish()
    }
}
