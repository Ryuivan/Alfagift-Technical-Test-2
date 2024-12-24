package com.example.technicaltest2.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.util.glide.ImageLoader

class StudentListAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<StudentListViewHolder>() {
    private val students = mutableListOf<Student>()

    fun setData(newStudents: List<Student>) {
        students.clear()
        students.addAll(newStudents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentListViewHolder {
        val view = layoutInflater.inflate(R.layout.item_student, parent, false)
        return StudentListViewHolder(view, imageLoader)
    }

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: StudentListViewHolder, position: Int) {
        holder.bindData(students[position])
    }
}