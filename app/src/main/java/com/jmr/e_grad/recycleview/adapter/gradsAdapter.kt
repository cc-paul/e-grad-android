package com.jmr.e_grad.recycleview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.fragments.YearBookDetails
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.recycleview.data.getPicItem

class gradsAdapter(
    private var yearBookDetails: YearBookDetails,
    private var mainActivity: MainActivity,
    private var items: ArrayList<getGradItem>
    ) : RecyclerView.Adapter<gradsAdapter.ViewHolder>() {
    private var gradsPicAdapter: gradsPicAdapter? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group_grad_pics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            tvLetter.text = item.letter

            val gradList = ArrayList<getPicItem>()

            item.graduates.forEach {
                it.apply {
                    gradList.add(getPicItem(
                        studentId,
                        fullName,
                        gradPicFileName,
                        folderName,
                        studentNumber,
                        totalAchievement
                    ))
                }
            }

            gradsPicAdapter = gradsPicAdapter(yearBookDetails,mainActivity,gradList)
            rvGraduatePics.layoutManager = GridLayoutManager(itemView.context, 3)
            rvGraduatePics.adapter = gradsPicAdapter
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLetter : TextView = itemView.findViewById(R.id.tvLetter)
        val rvGraduatePics : RecyclerView = itemView.findViewById(R.id.rvGraduatePics)
    }
}