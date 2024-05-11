package com.jmr.e_grad.recycleview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jmr.e_grad.R
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.courseItem

class courseAdapter(
    private val courseList: CourseList,
    private val dataTransfer: dataTransfer?,
    private var items: ArrayList<courseItem>
    ) : RecyclerView.Adapter<courseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_courses, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            tvCourse.text = item.course

            lnRowCourse.setOnClickListener {
                dataTransfer?.passCourseDataToRegistration(item.id,item.course)
                courseList.goBackToRegistration()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lnRowCourse : LinearLayout = itemView.findViewById(R.id.lnRowCourse)
        val tvCourse : TextView = itemView.findViewById(R.id.tvCourse)
    }

    fun filterList(filteredList: ArrayList<courseItem>) {
        items = filteredList
        notifyDataSetChanged()
    }

}