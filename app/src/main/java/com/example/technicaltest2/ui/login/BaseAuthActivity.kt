package com.example.technicaltest2.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.technicaltest2.util.FirebaseInstance

open class BaseAuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            // Check if there is a current authenticated user
            val currentUser = FirebaseInstance.auth.currentUser

            // If no user is authenticated or if the user is blocked, redirect to the login screen
            if (currentUser == null) {
                redirectToLogin()
            }
        } catch (e: Exception) {
            Log.e("BaseAuthActivity", "Error during Firebase auth initialization: ${e.message}")
        }
    }

    /**
     * Redirects the user to the login screen.
     */
    private fun redirectToLogin() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)

        // Finish the current activity to prevent returning to it
        finish()
    }
}
