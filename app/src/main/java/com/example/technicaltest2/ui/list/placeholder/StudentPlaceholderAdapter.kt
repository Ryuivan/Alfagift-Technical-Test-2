package com.example.technicaltest2.ui.list.placeholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.technicaltest2.R

/**
 * Adapter for displaying placeholder items in the RecyclerView.
 * This is used for showing loading indicators before actual data is fetched.
 */
class StudentPlaceholderAdapter(private val itemCount: Int) :
    RecyclerView.Adapter<StudentPlaceholderAdapter.PlaceholderViewHolder>() {

    /**
     * Creates a new ViewHolder for the placeholder item.
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlaceholderViewHolder {
        // Inflate the placeholder layout for each item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student_placeholder, parent, false)
        return PlaceholderViewHolder(view)
    }

    /**
     * No actual data binding is needed since this is just a placeholder view.
     */
    override fun onBindViewHolder(holder: PlaceholderViewHolder, position: Int) {
        // No data binding needed for placeholder
    }

    /**
     * Returns the total number of placeholder items to be displayed.
     */
    override fun getItemCount(): Int = itemCount

    /**
     * A simple ViewHolder without data binding.
     */
    class PlaceholderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
