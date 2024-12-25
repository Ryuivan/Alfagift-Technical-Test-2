package com.example.technicaltest2.ui.list

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.ui.list.placeholder.StudentPlaceholderAdapter
import com.example.technicaltest2.ui.login.BaseAuthActivity
import com.example.technicaltest2.util.FirebaseInstance
import com.example.technicaltest2.util.glide.GlideImageLoader
import com.facebook.shimmer.ShimmerFrameLayout
import com.github.javafaker.Faker

class StudentListActivity : BaseAuthActivity() {
    // UI Elements for displaying student list and shimmer layout
    private val studentRecyclerView: RecyclerView by lazy {
        findViewById(R.id.student_recycler_view)
    }

    private val shimmerLayout: ShimmerFrameLayout by lazy {
        findViewById(R.id.shimmer_layout)
    }

    private val studentListAdapter: StudentListAdapter by lazy {
        StudentListAdapter(layoutInflater, GlideImageLoader(this))
    }

    // SharedPreferences for storing seed status and student data
    private lateinit var sharedPreferences: SharedPreferences

    // Lists to hold all students and filtered students based on search query
    private var studentList = mutableListOf<Student>()
    private var filteredList = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        // Initialize SharedPreferences to check if the seeding process is done
        sharedPreferences = getSharedPreferences("StudentSeeder", MODE_PRIVATE)

        // Set up RecyclerView with a GridLayoutManager for displaying students
        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        studentRecyclerView.layoutManager = gridLayoutManager
        studentRecyclerView.adapter = studentListAdapter

        // Set up SearchView to filter students by name
        val searchView: SearchView = findViewById(R.id.search_student)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterStudents(newText)
                return true
            }
        })

        // Check if the seeding process has already been done
        val isSeeded = sharedPreferences.getBoolean("isSeeded", false)

        // Run seeding if not already done
        if (!isSeeded) {
            studentSeeder()
        }

        // Fetch students from Firestore
        fetchStudentFromFirestore()
    }

    /**
     * Seed students with random data using Faker library.
     * Adds the generated students to Firestore.
     */
    private fun studentSeeder() {
        val faker = Faker()
        val students = mutableListOf<Student>()

        // Generate 10 fake students
        for (i in 1..10) {
            val student = Student(
                id = i.toString(),
                name = faker.name().fullName(),
                address = faker.address().fullAddress(),
                profilePicture = "https://robohash.org/${i + 1}"
            )
            students.add(student)
        }

        // Add generated students to Firestore
        addStudentToFirestore(students)

        // Mark that the seeder has been run
        sharedPreferences.edit().putBoolean("isSeeded", true).apply()
    }

    /**
     * Adds the generated list of students to Firestore.
     * Logs success or failure for each student.
     */
    private fun addStudentToFirestore(students: List<Student>) {
        for (student in students) {
            FirebaseInstance.db
                .collection("student")
                .document(student.id)
                .set(student)
                .addOnSuccessListener {
                    Log.d("Firestore", "Student {$student.id}, {$student.name} added successfully")
                }
                .addOnFailureListener { e ->
                    Log.e("StudentSeeder", "Error adding student ${student.name}", e)
                }
        }
    }

    /**
     * Fetches student data from Firestore and updates the UI.
     * Displays a skeleton effect while data is loading.
     */
    private fun fetchStudentFromFirestore() {
        // Show skeleton layout while data is loading
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE
        studentRecyclerView.visibility = View.VISIBLE

        val placeholderAdapter = StudentPlaceholderAdapter(10)
        studentRecyclerView.adapter = placeholderAdapter

        // Simulate delay and fetch real data from Firestore
        Handler(mainLooper).postDelayed({
            FirebaseInstance.db
                .collection("student")
                .orderBy("id")
                .limit(10)
                .get()
                .addOnSuccessListener {
                    // Map Firestore data to Student objects
                    studentList = it.toObjects(Student::class.java)
                    filteredList.clear()
                    filteredList.addAll(studentList)
                    studentListAdapter.setData(filteredList)

                    // Stop skeleton effect and display the real data
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                    studentRecyclerView.adapter = studentListAdapter
                }
                .addOnFailureListener { e ->
                    Log.e("StudentSeeder", "Error fetching students", e)
                    shimmerLayout.stopShimmer()
                    shimmerLayout.visibility = View.GONE
                }
        }, 1000)  // Simulate network delay
    }

    /**
     * Filters the list of students based on the search query.
     * Updates the RecyclerView with the filtered list.
     */
    private fun filterStudents(query: String?) {
        val filtered = if (query.isNullOrEmpty()) {
            studentList
        } else {
            studentList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        filteredList.clear()
        filteredList.addAll(filtered)
        studentListAdapter.setData(filteredList)
    }
}