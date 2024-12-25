package com.example.technicaltest2.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

/**
 * FirebaseInstance is a singleton object that provides access to Firebase Authentication and Firestore.
 * It initializes the FirebaseAuth and FirebaseFirestore instances lazily to ensure they are created only when needed.
 */
object FirebaseInstance {

    /**
     * FirebaseAuth instance used for Firebase Authentication operations.
     */
    val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    /**
     * FirebaseFirestore instance used for Firestore database operations.
     */
    val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}
