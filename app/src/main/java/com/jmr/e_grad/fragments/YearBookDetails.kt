package com.jmr.e_grad.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jmr.e_grad.Constants.Companion.REGISTRATION_FRAGMENT
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.CourseResponseData
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.adapter.courseAdapter
import com.jmr.e_grad.recycleview.adapter.courseHorizontalAdapter
import com.jmr.e_grad.recycleview.adapter.gradsAdapter
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils

class YearBookDetails(private val mainActivity: MainActivity) : Fragment() {
    lateinit var etSearch: EditText
    lateinit var rvCourses: RecyclerView
    lateinit var rvGraduatePics: RecyclerView
    lateinit var yearBookDetailsView:View

    private val courseList  = ArrayList<courseItem>()
    private val gradList = ArrayList<getGradItem>()
    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    var courseId: Int = 0
    private var courseHorizontalAdapter: courseHorizontalAdapter? = null
    private var gradsAdapter: gradsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        yearBookDetailsView = inflater.inflate(R.layout.fragment_year_book_details, container, false)

        yearBookDetailsView.apply {
            etSearch = findViewById(R.id.etSearch)
            rvCourses = findViewById(R.id.rvCourses)
            rvGraduatePics = findViewById(R.id.rvGraduatePics)
        }

        getCourses()

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                loadAllGrads()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        return yearBookDetailsView
    }

    private fun getCourses() {
        if (utils.hasInternet(requireContext())) {
            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_all_courses_with_grad",
                schoolYear = getInt("yearGraduated")
            )

            mainActivity.allowSwitching = false

            apiServices.getCourseGrad(yearBookRelatedData) {
                if (it!!.success) {
                    val courseListResponse = it.data

                    courseList.clear()
                    courseListResponse?.course?.let { courseDetails ->
                        courseList.add(courseItem(0,"All Courses",""))

                        courseDetails.forEach { currentCourse ->
                            currentCourse.apply {
                                courseList.add(courseItem(id,course,description))
                            }
                        }
                    }

                    courseHorizontalAdapter = courseHorizontalAdapter(this,courseList)
                    rvCourses.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                    rvCourses.adapter = courseHorizontalAdapter

                    loadAllGrads()
                }
            }
        }
    }

    fun loadAllGrads() {
        if (utils.hasInternet(requireContext())) {
            val yearBookRelatedData = YearBookRelatedData(
                mode = "get_grad_pics",
                schoolYear = getInt("yearGraduated"),
                courseId = courseId,
                search = etSearch.text.toString()
            )

            apiServices.getAllGrads(yearBookRelatedData) {
                if (it!!.success) {
                    val gradListResponse = it.data

                    gradList.clear()
                    gradListResponse?.graduates?.let { gradDetails ->

                        gradDetails.forEach { currentGrad ->
                            currentGrad.apply {
                                gradList.add(getGradItem(letter,graduates))
                            }
                        }
                    }

                    gradsAdapter = gradsAdapter(mainActivity,gradList)
                    rvGraduatePics.layoutManager = LinearLayoutManager(requireContext())
                    rvGraduatePics.adapter = gradsAdapter
                }

                mainActivity.allowSwitching = true
            }
        }
    }
}