package com.example.technicaltest2.ui.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.util.glide.ImageLoader
import com.google.android.material.imageview.ShapeableImageView

class StudentListViewHolder(
    containerView: View,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(containerView) {
    private val profilePictureView: ShapeableImageView by lazy {
        containerView.findViewById(R.id.profile_picture)
    }

    private val studentNameView: TextView by lazy {
        containerView.findViewById(R.id.student_name)
    }

    private val studentAddressView: TextView by lazy {
        containerView.findViewById(R.id.student_address)
    }

    fun bindData(student: Student) {
        imageLoader.loadImage(student.profilePicture, profilePictureView)
        studentNameView.text = student.name
        studentAddressView.text = student.address
    }
}