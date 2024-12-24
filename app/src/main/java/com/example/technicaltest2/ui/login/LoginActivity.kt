package com.example.technicaltest2.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.technicaltest2.ui.list.StudentListActivity
import com.example.technicaltest2.R
import com.example.technicaltest2.util.FirebaseInstance

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameForm: EditText
    private lateinit var passwordForm: EditText
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        loginBtn = findViewById(R.id.button_login)
        loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        usernameForm = findViewById(R.id.username_form)
        passwordForm = findViewById(R.id.password_form)

        val username: String = usernameForm.text.toString().trim()
        var password: String = passwordForm.text.toString().trim()
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

    private fun redirectToMain() {
        val mainIntent = Intent(this, StudentListActivity::class.java)
        startActivity(mainIntent)
        finish()
    }
}