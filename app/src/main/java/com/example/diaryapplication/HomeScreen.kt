package com.example.diaryapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView


class HomeScreen : Activity() {
    private lateinit var homeButton: ImageView
    private lateinit var profileButton: ImageView
    private lateinit var writeDiaryButton: ImageView
    private lateinit var diaryListView: ListView
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ArrayAdapter<String>
    private val diaryEntries = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        // Initialize buttons
        homeButton = findViewById(R.id.homeButton)
        profileButton = findViewById(R.id.profileButton)
        writeDiaryButton = findViewById(R.id.writediary)

        // Initialize ListView
        diaryListView = findViewById(R.id.diaryListView)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("DiaryPrefs", MODE_PRIVATE)

        // Load saved diary entries
        loadDiaryEntries()

        // Set up ListView adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, diaryEntries)
        diaryListView.adapter = adapter

        // Default button colors
        homeButton.setImageResource(R.drawable.blackhome)
        profileButton.setImageResource(R.drawable.blackprofile)

        // HOME button click
        homeButton.setOnClickListener {
            homeButton.setImageResource(R.drawable.whitehome)
            profileButton.setImageResource(R.drawable.blackprofile)

            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        // PROFILE button click
        profileButton.setOnClickListener {
            profileButton.setImageResource(R.drawable.whiteprofile)
            homeButton.setImageResource(R.drawable.blackhome)

            val intent = Intent(this, ProfileScreen::class.java)
            startActivity(intent)
        }

        // WRITE DIARY button click
        writeDiaryButton.setOnClickListener {
            val intent = Intent(this, WriteDiaryScreen::class.java)
            startActivity(intent)
        }
    }

    private fun loadDiaryEntries() {
        val savedEntries = sharedPreferences.getStringSet("diaryList", emptySet()) ?: emptySet()
        diaryEntries.clear()
        diaryEntries.addAll(savedEntries)
    }
}

