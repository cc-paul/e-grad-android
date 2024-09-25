package com.jmr.e_grad.recycleview.adapter

import android.content.Intent
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
import com.jmr.e_grad.VideoPlayerActivity
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.helper.linkHelper
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.awardeeItem
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.mediaItem
import de.hdodenhof.circleimageview.CircleImageView

class mediaAdapter(
    private var items: ArrayList<mediaItem>
    ) : RecyclerView.Adapter<mediaAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pics, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.apply {
            val link = linkHelper.eventPhotoLink + "${item.schoolYear}/${item.fileName}"

            Glide.with(itemView.context)
                .load(Uri.parse(link))
                .into(imgEventPic)

            tvDescription.text = item.description

            lnDescription.visibility = if (item.description.trim().isEmpty()) {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }

            imgPlayVideo.visibility = if (item.type == "pic") {
                View.INVISIBLE
            } else {
                View.VISIBLE
            }

            imgPlayVideo.setOnClickListener {
                val intent = Intent(itemView.context, VideoPlayerActivity::class.java)
                intent.putExtra("videoLink", link)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPlayVideo : ImageView = itemView.findViewById(R.id.imgPlayVideo)
        val imgEventPic : ImageView = itemView.findViewById(R.id.imgEventPic)
        val tvDescription : TextView = itemView.findViewById(R.id.tvDescription)
        val lnDescription : LinearLayout = itemView.findViewById(R.id.lnDescription)
    }

}