package com.example.diaryapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WriteDiaryScreen : Activity() {
    private lateinit var titleInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var entryInput: EditText
    private lateinit var postButton: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_diary_screen)

        titleInput = findViewById(R.id.titleInput)
        dateInput = findViewById(R.id.dateInput)
        entryInput = findViewById(R.id.entryInput)
        postButton = findViewById(R.id.postButton)

        sharedPreferences = getSharedPreferences("DiaryPrefs", MODE_PRIVATE)

        // Set default date to today's date but allow manual change
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        dateInput.setText(currentDate) // Pre-fills the date field

        postButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val date = dateInput.text.toString().trim()
            val entry = entryInput.text.toString().trim()

            if (title.isNotEmpty() && date.isNotEmpty() && entry.isNotEmpty()) {
                val existingEntries = sharedPreferences.getStringSet("diaryList", mutableSetOf()) ?: mutableSetOf()
                existingEntries.add("$title\n$date\n$entry")

                val editor = sharedPreferences.edit()
                editor.putStringSet("diaryList", existingEntries)
                editor.apply()

                startActivity(Intent(this, HomeScreen::class.java))
                finish() // Close WriteDiaryScreen
            }
        }
    }
}