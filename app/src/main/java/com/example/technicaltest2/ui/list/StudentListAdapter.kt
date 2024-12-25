package com.example.technicaltest2.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.util.glide.ImageLoader

/**
 * Adapter for displaying a list of students in a RecyclerView.
 * Handles the binding of student data to each item view.
 */
class StudentListAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<StudentListViewHolder>() {

    // List of students to be displayed in the RecyclerView
    private val students = mutableListOf<Student>()

    /**
     * Updates the list of students in the adapter and notifies the RecyclerView.
     */
    fun setData(newStudents: List<Student>) {
        // Clear existing list
        students.clear()
        // Add new students
        students.addAll(newStudents)
        // Notify RecyclerView to update the view
        notifyDataSetChanged()
    }

    /**
     * Creates a new ViewHolder for each item in the RecyclerView.
     *
     * @return A new instance of StudentListViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListViewHolder {
        // Inflate the item layout for a student and create a ViewHolder
        val view = layoutInflater.inflate(R.layout.item_student, parent, false)
        return StudentListViewHolder(view, imageLoader)  // Return the ViewHolder
    }

    /**
     * Returns the total number of students in the list.
     *
     * @return The number of students in the list.
     */
    override fun getItemCount() = students.size

    /**
     * Binds data for a specific student to the corresponding ViewHolder.
     */
    override fun onBindViewHolder(holder: StudentListViewHolder, position: Int) {
        // Bind the student data to the ViewHolder
        holder.bindData(students[position])
    }
}
