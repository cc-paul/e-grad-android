package com.jmr.e_grad.recycleview.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.fragments.CourseList
import com.jmr.e_grad.helper.linkHelper
import com.jmr.e_grad.helper.sharedHelper
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.data.achievementPassItem
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.recycleview.data.getPicItem

class gradsPicAdapter(
        private var mainActivity: MainActivity,
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

            val popupMenu = PopupMenu(
                itemView.context,
                crdMore
            )


            if (sharedHelper.getString("studentNumber") == item.studentNumber) {
                popupMenu.menu.add(Menu.NONE, 0, 0, "Download Picture")
            }

            if (item.totalAchievement != 0) {
                popupMenu.menu.add(Menu.NONE, 1, 1, "View Achievement")
            }

            popupMenu.setOnMenuItemClickListener { menuItem ->
                val id = menuItem.itemId

                if (id == 0){
                    val filename = "${System.currentTimeMillis()}_my_grad_pic.jpg"
                    mainActivity.downloadImageNew(
                        filename,
                        link
                    )
                } else if (id == 1) {
                    val achievementPassItem = ArrayList<achievementPassItem>()

                    achievementPassItem.add(achievementPassItem(
                        studentNumber = item.studentNumber,
                        imageLink = link
                    ))

                    mainActivity.getAchievement(achievementPassItem)
                }

                false

            }

            crdMore.visibility = if (popupMenu.menu.size() != 0) {
                View.VISIBLE
            } else {
                View.GONE
            }

            crdMore.setOnClickListener {
                popupMenu.show()
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgGradPic : ImageView = itemView.findViewById(R.id.imgGradPic)
        val tvFullName : TextView = itemView.findViewById(R.id.tvFullName)
        val crdMore : CardView = itemView.findViewById(R.id.crdMore)
    }
}