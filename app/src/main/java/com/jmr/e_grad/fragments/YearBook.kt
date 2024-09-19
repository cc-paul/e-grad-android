package com.jmr.e_grad.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.jmr.e_grad.Constants
import com.jmr.e_grad.R
import com.jmr.e_grad.helper.linkHelper
import com.jmr.e_grad.helper.sharedHelper.getInt


class YearBook : Fragment() {
    lateinit var imgYearBookCover: ImageView
    lateinit var lnProceed: LinearLayout

    private var link :String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val yearBookView: View = inflater.inflate(R.layout.fragment_year_book, container, false)

        yearBookView.apply {
            imgYearBookCover = findViewById(R.id.imgYearBookCover)
            lnProceed = findViewById(R.id.lnProceed)
        }

        loadCover()

        lnProceed.setOnClickListener {
            val yearBookFrag = YearBookDetails()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frMain, yearBookFrag)
                .commit()
        }

        return yearBookView
    }

    fun loadCover() {
        link = linkHelper.coverPhotoLink + getInt("yearGraduated").toString() + ".png"

        Glide.with(requireActivity())
            .load(Uri.parse(link))
            .into(imgYearBookCover)
    }
}