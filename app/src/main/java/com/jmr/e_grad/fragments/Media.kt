package com.jmr.e_grad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.recycleview.adapter.awardeeAdapter
import com.jmr.e_grad.recycleview.adapter.mediaAdapter
import com.jmr.e_grad.recycleview.data.awardeeItem
import com.jmr.e_grad.recycleview.data.mediaItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils


class Media(private var mainActivity: MainActivity) : Fragment() {
    private lateinit var rvEventPics: RecyclerView
    private lateinit var lnGallery: LinearLayout
    private lateinit var lnVideos: LinearLayout
    private lateinit var lnGallerySelector: LinearLayout
    private lateinit var lnVideosSelector: LinearLayout

    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    private var eventList  = ArrayList<mediaItem>()
    private var eventPicList = ArrayList<mediaItem>()
    private var eventVideoList = ArrayList<mediaItem>()
    private var mediaAdapter: mediaAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mediaView : View = inflater.inflate(R.layout.fragment_media, container, false)

        mediaView.apply {
            rvEventPics = findViewById(R.id.rvEventPics)
            lnVideos = findViewById(R.id.lnVideos)
            lnGallery = findViewById(R.id.lnGallery)
            lnVideosSelector = findViewById(R.id.lnVideosSelector)
            lnGallerySelector = findViewById(R.id.lnGallerySelector)
        }

        loadMedia()

        lnVideos.setOnClickListener {
            if (mainActivity.allowSwitching) {
                loadVideo()
                lnGallerySelector.visibility = View.INVISIBLE
                lnVideosSelector.visibility = View.VISIBLE
            }
        }

        lnGallery.setOnClickListener {
            if (mainActivity.allowSwitching) {
                loadPic()
                lnGallerySelector.visibility = View.VISIBLE
                lnVideosSelector.visibility = View.INVISIBLE
            }
        }

        return mediaView
    }

    private fun loadMedia() {
        if (utils.hasInternet(requireContext())) {
            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_media",
                schoolYear = getInt("yearGraduated")
            )

            mainActivity.allowSwitching = false

            apiServices.getMedia(yearBookRelatedData) {
                if (it!!.success) {
                    val mediaResponse = it.data

                    eventList.clear()
                    eventPicList.clear()
                    eventVideoList.clear()
                    mediaResponse?.media?.let { mediaDetails ->

                        mediaDetails.forEach { currentMedia ->
                            currentMedia.apply {
                                if (type == "pic") {
                                    eventPicList.add(
                                        mediaItem(
                                            schoolYear,
                                            fileName,
                                            description,
                                            type
                                        )
                                    )
                                } else {
                                    eventVideoList.add(
                                        mediaItem(
                                            schoolYear,
                                            fileName,
                                            description,
                                            type
                                        )
                                    )
                                }
                            }
                        }
                    }


                }

                loadPic()
                mainActivity.allowSwitching = true
            }
        }
    }

    private fun loadPic() {
        eventList.clear()
        eventList.addAll(eventPicList)
        mediaAdapter = mediaAdapter(eventList)
        rvEventPics.layoutManager = LinearLayoutManager(requireContext())
        rvEventPics.adapter = mediaAdapter
    }

    private fun loadVideo() {
        eventList.clear()
        eventList.addAll(eventVideoList)
        mediaAdapter = mediaAdapter(eventList)
        rvEventPics.layoutManager = LinearLayoutManager(requireContext())
        rvEventPics.adapter = mediaAdapter
    }
}