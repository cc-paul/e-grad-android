package com.jmr.e_grad.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jmr.e_grad.R
import com.jmr.e_grad.data.Achievement
import com.jmr.e_grad.recycleview.adapter.achievementAdapter
import com.jmr.e_grad.recycleview.data.achievementPassItem

class BottomAchievement(private val achievement: List<Achievement>,private val achievementPassItem: ArrayList<achievementPassItem>) : BottomSheetDialogFragment() {
    private lateinit var viewBottomAchievement: View
    private lateinit var imgGradPic: ImageView
    private lateinit var tvFullName: TextView
    private lateinit var rvAchievement: RecyclerView

    private val achievementList  = ArrayList<String>()

    private var achievementAdapter: achievementAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBottomAchievement = inflater.inflate(R.layout.bottom_achievement, container, false)

        viewBottomAchievement.apply {
            imgGradPic = findViewById(R.id.imgGradPic)
            tvFullName = findViewById(R.id.tvFullName)
            rvAchievement = findViewById(R.id.rvAchievement)
        }

        tvFullName.text = achievement[0].fullName

        Glide.with(requireContext())
            .load(Uri.parse(achievementPassItem[0].imageLink))
            .into(imgGradPic)

        getAchievement()


        return viewBottomAchievement
    }

    private fun getAchievement() {
        achievementList.clear()
        achievement[0].achievement.forEach {
            achievementList.add(it.titleName)
        }

        achievementAdapter = achievementAdapter(achievementList)
        rvAchievement.layoutManager = GridLayoutManager(requireContext(), 2)
        rvAchievement.adapter = achievementAdapter
    }
}