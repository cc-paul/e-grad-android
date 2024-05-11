package com.jmr.e_grad.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.jmr.e_grad.Constants.Companion.REGISTRATION_FRAGMENT
import com.jmr.e_grad.LoginActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.CourseResponseData
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.adapter.courseAdapter
import com.jmr.e_grad.recycleview.data.courseItem
import java.util.Locale

class CourseList : Fragment() {
    private lateinit var courseListView : View
    private lateinit var lnBack: LinearLayout
    private lateinit var rvCourses: RecyclerView
    private lateinit var etSearch: EditText

    private val courseList  = ArrayList<courseItem>()

    private var courseAdapter: courseAdapter? = null
    private var dataTransfer: dataTransfer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        courseListView = inflater.inflate(R.layout.fragment_course_list, container, false)

        courseListView.apply {
            lnBack = findViewById(R.id.lnBack)
            rvCourses = findViewById(R.id.rvCourses)
            etSearch = findViewById(R.id.etSearch)
        }

        lnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                filterCourses(s.toString())
            }
        })

        getCourses()

        return courseListView
    }

    private fun getCourses() {
        val courseResponseData = Gson().fromJson(arguments?.getString("courseJson"), CourseResponseData::class.java)
        val registrationFragment = requireActivity().supportFragmentManager.findFragmentByTag(REGISTRATION_FRAGMENT) as? dataTransfer
        dataTransfer = registrationFragment

        courseResponseData.course.forEach {
            courseList.add(courseItem(it.id,it.course,it.description))
        }

        courseAdapter = courseAdapter(this@CourseList,dataTransfer,courseList)
        rvCourses.layoutManager = LinearLayoutManager(requireContext())
        rvCourses.adapter = courseAdapter
    }

    private fun filterCourses(text: String) {
        val filteredList = ArrayList<courseItem>()
        for (item in courseList) {
            if (item.course.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredList.add(item)
            }
        }
        courseAdapter?.filterList(filteredList)
    }

    fun goBackToRegistration() {
        lnBack.performClick()
    }
}