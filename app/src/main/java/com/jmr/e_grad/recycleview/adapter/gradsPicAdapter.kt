package com.jmr.e_grad.recycleview.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmr.e_grad.R
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.helper.linkHelper
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.recycleview.data.getPicItem

class gradsPicAdapter(
    private var items: ArrayList<getPicItem>
    ) : RecyclerView.Adapter<gradsPicAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grad_pics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            tvFullName.text = item.fullName

            val link = linkHelper.photoLink + "${item.folderName}/${item.gradPicFileName}"

            Glide.with(itemView.context)
                .load(Uri.parse(link))
                .into(imgGradPic)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGradPic : ImageView = itemView.findViewById(R.id.imgGradPic)
        val tvFullName : TextView = itemView.findViewById(R.id.tvFullName)
    }
}