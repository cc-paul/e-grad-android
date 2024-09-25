package com.jmr.e_grad.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.recycleview.adapter.awardeeAdapter
import com.jmr.e_grad.recycleview.adapter.courseAdapter
import com.jmr.e_grad.recycleview.adapter.courseHorizontalAdapter
import com.jmr.e_grad.recycleview.adapter.gradsAdapter
import com.jmr.e_grad.recycleview.data.awardeeItem
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils


class Awardee(private val mainActivity: MainActivity) : Fragment() {
    private lateinit var rvAwardee:RecyclerView

    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    private val awardeeList  = ArrayList<awardeeItem>()

    private var awardeeAdapter: awardeeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val awardeeView:View = inflater.inflate(R.layout.fragment_awardee, container, false)

        awardeeView.apply {
            rvAwardee = findViewById(R.id.rvAwardee)
        }

        loadAwardee()

        return awardeeView
    }

    private fun loadAwardee() {
        if (utils.hasInternet(requireContext())) {
            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_awardee",
                schoolYear = getInt("yearGraduated")
            )

            mainActivity.allowSwitching = false

            apiServices.getAwardee(yearBookRelatedData) {
                if (it!!.success) {
                    val awardeeListResponse = it.data

                    awardeeList.clear()
                    awardeeListResponse?.awardee?.let { awardDetails ->

                        awardDetails.forEach { currentAwardee ->
                            currentAwardee.apply {
                                awardeeList.add(
                                    awardeeItem(
                                        id,
                                        studentId,
                                        folderName,
                                        fileName,
                                        fullName,
                                        titleName,
                                        course
                                    )
                                )
                            }
                        }
                    }

                    awardeeAdapter = awardeeAdapter(awardeeList)
                    rvAwardee.layoutManager = GridLayoutManager(context, 2)
                    rvAwardee.adapter = awardeeAdapter
                }

                mainActivity.allowSwitching = true
            }
        }
    }
}