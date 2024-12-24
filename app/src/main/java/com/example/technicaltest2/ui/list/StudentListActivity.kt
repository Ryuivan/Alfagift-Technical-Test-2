package com.example.technicaltest2.ui.list

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.ui.login.BaseAuthActivity
import com.example.technicaltest2.util.FirebaseInstance
import com.example.technicaltest2.util.glide.GlideImageLoader
import com.github.javafaker.Faker

class StudentListActivity : BaseAuthActivity() {
    private val studentRecyclerView: RecyclerView by lazy {
        findViewById(R.id.student_recycler_view)
    }

    private val studentListAdapter: StudentListAdapter by lazy {
        StudentListAdapter(layoutInflater, GlideImageLoader(this))
    }

    private lateinit var sharedPreferences: SharedPreferences
    private var studentList = mutableListOf<Student>()  // Initialize studentList
    private var filteredList = mutableListOf<Student>()  // Initialize filteredList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_list)

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("StudentSeeder", MODE_PRIVATE)

        val gridLayoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        studentRecyclerView.layoutManager = gridLayoutManager
        studentRecyclerView.adapter = studentListAdapter

        // Set up SearchView
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

        // Periksa apakah seeder sudah dijalankan
        val isSeeded = sharedPreferences.getBoolean("isSeeded", false)
        if (!isSeeded) {
            studentSeeder()
        }

        fetchStudentFromFirestore()
    }

    private fun studentSeeder() {
        val faker = Faker()
        val students = mutableListOf<Student>()

        for (i in 1..10) {
            val student = Student(
                id = i.toString(),
                name = faker.name().fullName(),
                address = faker.address().fullAddress(),
                profilePicture = "https://robohash.org/${i + 1}"
            )
            students.add(student)
        }

        addStudentToFirestore(students)

        // Tandai bahwa seeder sudah dijalankan
        sharedPreferences.edit().putBoolean("isSeeded", true).apply()
    }

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

    private fun fetchStudentFromFirestore() {
        FirebaseInstance.db
            .collection("student")
            .orderBy("id")
            .limit(10)
            .get()
            .addOnSuccessListener {
                studentList = it.toObjects(Student::class.java) // Populate studentList
                filteredList.clear()
                filteredList.addAll(studentList) // Initialize filteredList with all students
                studentListAdapter.setData(filteredList)
            }
            .addOnFailureListener { e ->
                Log.e("StudentSeeder", "Error fetching students", e)
            }
    }

    // Function to filter students by name
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

