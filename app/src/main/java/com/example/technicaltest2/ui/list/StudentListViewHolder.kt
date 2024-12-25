package com.example.technicaltest2.ui.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R
import com.example.technicaltest2.model.Student
import com.example.technicaltest2.util.glide.ImageLoader
import com.google.android.material.imageview.ShapeableImageView

/**
 * ViewHolder for displaying a single student's data in a RecyclerView item.
 */
class StudentListViewHolder(
    containerView: View,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(containerView) {

    // Profile picture view
    private val profilePictureView: ShapeableImageView by lazy {
        containerView.findViewById(R.id.profile_picture)
    }

    // Student name view
    private val studentNameView: TextView by lazy {
        containerView.findViewById(R.id.student_name)
    }

    // Student address view
    private val studentAddressView: TextView by lazy {
        containerView.findViewById(R.id.student_address)
    }

    /**
     * Binds the student data to the corresponding views in the ViewHolder.
     */
    fun bindData(student: Student) {
        // Load the student's profile picture using the ImageLoader utility
        imageLoader.loadImage(student.profilePicture, profilePictureView)

        // Set the student's name and address to the respective TextViews
        studentNameView.text = student.name
        studentAddressView.text = student.address
    }
}
