package com.example.diaryapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class ProfileScreen : Activity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var updateButton: ImageView
    private lateinit var logoutButton: ImageView
    private lateinit var homeButton: ImageView
    private lateinit var profileButton: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)

        usernameInput = findViewById(R.id.profileusername)
        passwordInput = findViewById(R.id.profilepassword)
        updateButton = findViewById(R.id.updateButton)
        logoutButton = findViewById(R.id.logoutButton)
        homeButton = findViewById(R.id.homeButton)
        profileButton = findViewById(R.id.profileButton)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Load current user credentials
        val currentUsername = sharedPreferences.getString("current_user", "")
        usernameInput.setText(currentUsername)
        passwordInput.setText(sharedPreferences.getString("password_$currentUsername", ""))

        // Update user information
        updateButton.setOnClickListener {
            val newUsername = usernameInput.text.toString().trim()
            val newPassword = passwordInput.text.toString().trim()

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Username and Password cannot be empty!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val storedUsers = sharedPreferences.getStringSet("usernames", mutableSetOf()) ?: mutableSetOf()
            val currentUsername = sharedPreferences.getString("current_user", "")

            if (storedUsers.contains(newUsername) && newUsername != currentUsername) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update credentials
            storedUsers.remove(currentUsername)
            storedUsers.add(newUsername)

            val editor = sharedPreferences.edit()
            editor.putStringSet("usernames", storedUsers)
            editor.putString("password_$newUsername", newPassword)
            editor.putString("current_user", newUsername)
            editor.apply()

            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
        }

        // Log out and return to LoginActivity
        logoutButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.remove("current_user") // Remove active user
            editor.apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish() // Close ProfileScreen
        }

        // Navigation Buttons
        homeButton.setImageResource(R.drawable.blackhome)
        profileButton.setImageResource(R.drawable.blackprofile)

        homeButton.setOnClickListener {
            homeButton.setImageResource(R.drawable.whitehome)
            profileButton.setImageResource(R.drawable.blackprofile)

            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }

        profileButton.setOnClickListener {
            profileButton.setImageResource(R.drawable.whiteprofile)
            homeButton.setImageResource(R.drawable.blackhome)

            val intent = Intent(this, ProfileScreen::class.java)
            startActivity(intent)
        }
    }
}
