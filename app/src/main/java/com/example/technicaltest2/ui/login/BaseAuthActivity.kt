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
            val currentUser = FirebaseInstance.auth.currentUser
            if (currentUser == null || isUserBlocked()) {
                redirectToLogin()
            }
        } catch (e: Exception) {
            Log.e("BaseAuthActivity", "Error during Firebase auth initialization: ${e.message}")
        }
    }

    private fun isUserBlocked(): Boolean {
        return false
    }

    private fun redirectToLogin() {
        val loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
        finish()
    }
}