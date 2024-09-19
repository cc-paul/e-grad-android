package com.jmr.e_grad.recycleview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jmr.e_grad.R
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.fragments.YearBookDetails
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.courseItem

class courseHorizontalAdapter(
    private val yearBookDetails: YearBookDetails,
    private var items: ArrayList<courseItem>
    ) : RecyclerView.Adapter<courseHorizontalAdapter.ViewHolder>() {
    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses_horizontal_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val item = items[position]

        holder.apply {
            tvCourseNameSelected.text = item.course
            tvCourseNameUnselected.text = item.course

            if (position == 0) {
                spSpace.visibility = View.VISIBLE
            }

            if (position == selectedPosition) {
                lnCourseSelected.visibility = View.VISIBLE
                lnCourseUnselected.visibility = View.GONE
            } else {
                lnCourseSelected.visibility = View.GONE
                lnCourseUnselected.visibility = View.VISIBLE
            }

            lnCourseSelected.setOnClickListener {
                changeCourse(item.id)
            }

            lnCourseUnselected.setOnClickListener {
                val previousSelectedPosition = selectedPosition
                selectedPosition = position

                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)

                changeCourse(item.id)
            }
        }
    }

    fun changeCourse(currentCourseId: Int) {
        yearBookDetails.apply {
            courseId = currentCourseId
            loadAllGrads()
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lnCourseSelected : LinearLayout = itemView.findViewById(R.id.lnCourseSelected)
        val lnCourseUnselected : LinearLayout = itemView.findViewById(R.id.lnCourseUnselected)
        val tvCourseNameSelected : TextView = itemView.findViewById(R.id.tvCourseNameSelected)
        val tvCourseNameUnselected : TextView = itemView.findViewById(R.id.tvCourseNameUnselected)
        val spSpace : Space = itemView.findViewById(R.id.spSpace)
    }
}