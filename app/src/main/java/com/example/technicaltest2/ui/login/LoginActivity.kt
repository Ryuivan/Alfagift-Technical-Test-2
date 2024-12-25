package com.example.technicaltest2.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.technicaltest2.ui.list.StudentListActivity
import com.example.technicaltest2.R
import com.example.technicaltest2.util.FirebaseInstance

class LoginActivity : AppCompatActivity() {
    // Variable declarations for UI elements
    private lateinit var usernameForm: EditText
    private lateinit var passwordForm: EditText
    private lateinit var passwordToggle: ImageButton
    private lateinit var loginBtn: Button

    // Variable to track the visibility of the password
    private var passwordVisibility = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Assign UI elements to variables
        usernameForm = findViewById(R.id.username_form)
        passwordForm = findViewById(R.id.password_form)
        passwordToggle = findViewById(R.id.password_toggle)
        loginBtn = findViewById(R.id.button_login)

        // Handle click on password visibility toggle
        passwordToggle.setOnClickListener {
            passwordVisibilityToggle()
        }

        // Handle click on login button
        loginBtn.setOnClickListener {
            login()
        }
    }

    /**
     * Toggles the visibility of the password by changing the input type
     * of the password field and updating the password toggle icon.
     */
    private fun passwordVisibilityToggle() {
        passwordVisibility = !passwordVisibility
        passwordForm.inputType = if (passwordVisibility) {
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        passwordToggle.setImageResource(
            if (passwordVisibility) R.drawable.visibility else R.drawable.visibility_off
        )

        passwordForm.letterSpacing = if (passwordVisibility) 0.01f else 0.175f
        passwordForm.setSelection(passwordForm.text.length)
    }

    /**
     * Handles the login process by taking the username and password input,
     * and authenticating the user with Firebase.
     * The password is concatenated with "__" to meet Firebase's password requirements.
     */
    private fun login() {
        val username: String = usernameForm.text.toString().trim()
        var password: String = passwordForm.text.toString().trim()

        // Appends "__" to the password
        password += "__"

        if (username.isNotEmpty() && password.isNotEmpty()) {
            val email = "$username@example.com"

            FirebaseInstance.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(this.toString(), "signInWithEmail:success")
                        redirectToMain()
                    } else {
                        Log.e(this.toString(), "signInWithEmail:failed, username: $username and password: $password")
                        Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Username and password are required!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Redirects the user to the main activity after a successful login.
     */
    private fun redirectToMain() {
        val mainIntent = Intent(this, StudentListActivity::class.java)
        startActivity(mainIntent)

        // Finishes the current activity to prevent returning to the login screen
        finish()
    }
}
