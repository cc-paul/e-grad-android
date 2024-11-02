package com.jmr.e_grad.fragments

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.gson.Gson
import com.jmr.e_grad.Constants.Companion.REGISTRATION_FRAGMENT
import com.jmr.e_grad.MainActivity
import com.jmr.e_grad.R
import com.jmr.e_grad.data.Achievement
import com.jmr.e_grad.data.CourseResponseData
import com.jmr.e_grad.data.YearBookRelatedData
import com.jmr.e_grad.data.loginAccountData
import com.jmr.e_grad.helper.sharedHelper
import com.jmr.e_grad.helper.sharedHelper.getInt
import com.jmr.e_grad.`interface`.dataTransfer
import com.jmr.e_grad.recycleview.adapter.courseAdapter
import com.jmr.e_grad.recycleview.adapter.courseHorizontalAdapter
import com.jmr.e_grad.recycleview.adapter.gradsAdapter
import com.jmr.e_grad.recycleview.data.achievementPassItem
import com.jmr.e_grad.recycleview.data.courseItem
import com.jmr.e_grad.recycleview.data.getGradItem
import com.jmr.e_grad.services.apiServices
import com.jmr.e_grad.services.utils
import org.w3c.dom.Text

class YearBookDetails(private val mainActivity: MainActivity) : Fragment() {
    lateinit var etSearch: EditText
    lateinit var rvCourses: RecyclerView
    lateinit var rvGraduatePics: RecyclerView
    lateinit var yearBookDetailsView:View
    lateinit var crdDetails: CardView
    lateinit var lnCloseDetails: LinearLayout
    lateinit var imgGradPic: ImageView
    lateinit var tvFullName: TextView
    lateinit var lnMore: LinearLayout

    private val courseList  = ArrayList<courseItem>()
    private val gradList = ArrayList<getGradItem>()
    private val apiServices = apiServices()
    private val utils = utils()
    private val gson = Gson()

    var courseId: Int = 0
    private var courseHorizontalAdapter: courseHorizontalAdapter? = null
    private var gradsAdapter: gradsAdapter? = null
    private lateinit var popupMenu: PopupMenu

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
            crdDetails = findViewById(R.id.crdDetails)
            lnCloseDetails = findViewById(R.id.lnCloseDetails)
            imgGradPic = findViewById(R.id.imgGradPic)
            tvFullName = findViewById(R.id.tvFullName)
            lnMore = findViewById(R.id.lnMore)
        }

        popupMenu = PopupMenu(
            requireContext(),
            lnMore
        )

        getCourses()

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                loadAllGrads()
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        lnCloseDetails.setOnClickListener {
            rvGraduatePics.visibility = View.VISIBLE
            crdDetails.visibility = View.GONE
        }

        lnMore.setOnClickListener {
            popupMenu.show()
        }

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
        rvGraduatePics.visibility = View.VISIBLE
        crdDetails.visibility = View.GONE

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

                    gradsAdapter = gradsAdapter(this,mainActivity,gradList)
                    rvGraduatePics.layoutManager = LinearLayoutManager(requireContext())
                    rvGraduatePics.adapter = gradsAdapter
                }

                mainActivity.allowSwitching = true
            }
        }
    }

    fun showPicDetails(
        link:String,
        fullName: String,
        studentNumber: String,
        addDownload: Boolean,
        addAchievement: Boolean
    ) {
        Glide.with(requireContext())
            .load(Uri.parse(link))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imgGradPic)

        tvFullName.text = fullName

        popupMenu.menu.clear()

        if (addDownload) {
            popupMenu.menu.add(Menu.NONE, 0, 0, "Download Picture")
        }

        if (addAchievement) {
            popupMenu.menu.add(Menu.NONE, 1, 1, "View Achievement")
        }

        popupMenu.setOnMenuItemClickListener { menuItem ->
            val id = menuItem.itemId

            if (id == 0) {
                val filename = "${System.currentTimeMillis()}_my_grad_pic.jpg"
                mainActivity.downloadImageNew(
                    filename,
                    link
                )
            } else if (id == 1) {
                val achievementPassItem = ArrayList<achievementPassItem>()

                achievementPassItem.add(
                    achievementPassItem(
                        studentNumber = studentNumber,
                        imageLink = link
                    )
                )

                mainActivity.getAchievement(achievementPassItem)
            }

            false

        }


        lnMore.visibility = if (popupMenu.menu.size() != 0) {
            View.VISIBLE
        } else {
            View.GONE
        }

        rvGraduatePics.visibility = View.GONE
        crdDetails.visibility = View.VISIBLE
    }
}