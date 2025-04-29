package com.example.diaryapplication

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class RegisterActivity : Activity() {
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerButton: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        usernameInput = findViewById(R.id.registerUsername)
        passwordInput = findViewById(R.id.registerPassword)
        confirmPasswordInput = findViewById(R.id.registerConfirmPassword)
        registerButton = findViewById(R.id.registerUserButton)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = confirmPasswordInput.text.toString().trim()

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val existingUsers = sharedPreferences.getStringSet("usernames", mutableSetOf()) ?: mutableSetOf()
            if (existingUsers.contains(username)) {
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save new user
            existingUsers.add(username)
            val editor = sharedPreferences.edit()
            editor.putStringSet("usernames", existingUsers)
            editor.putString("password_$username", password) // Save password associated with username
            editor.apply()

            Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()

            // Navigate to LoginActivity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}