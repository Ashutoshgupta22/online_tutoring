package com.aspark.clapingoassignment.ui

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.aspark.clapingoassignment.Helper
import com.aspark.clapingoassignment.R
import com.aspark.clapingoassignment.model.TimeSlot
import com.aspark.clapingoassignment.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var timeSlotMap : HashMap<String,ArrayList<TimeSlot>>
    private var showMore: Boolean = false
    private val helper = Helper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getTeacherData()

        binding.ivProfilePic.clipToOutline = true

        val calender = Calendar.getInstance()
        var day = calender.get(Calendar.DAY_OF_MONTH)
        var month = calender.get(Calendar.MONTH)
        var year = calender.get(Calendar.YEAR)
        var dayOfWeek = calender.get(Calendar.DAY_OF_WEEK)

        val todayDate = calender.timeInMillis
        val weekdays = helper.weekdays

        var selectedDate = helper.convertDateFormat(day, month+1, year)

        binding.tvDate.text = selectedDate

        binding.cvDate.setOnClickListener {

            val dialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    day = selectedDay
                    month = selectedMonth
                    year = selectedYear

                    selectedDate = helper.convertDateFormat(day, month+1, year)

                    calender.set(selectedYear,selectedMonth,selectedDay)
                    dayOfWeek = calender.get(Calendar.DAY_OF_WEEK)
                    binding.tvDate.text = selectedDate

                    showMore = false
                    binding.textShowMore.text = getString(R.string.show_more)
                    val list = timeSlotMap[weekdays[dayOfWeek-1]]!!

                    if (list.isNotEmpty()) {

                        binding.textShowMore.visibility = View.VISIBLE
                        binding.tvPlaceholder.visibility = View.INVISIBLE
                        binding.rvTimeSlots.visibility = View.VISIBLE
                        binding.rvTimeSlots.adapter = TimeSlotAdapter(list, 12)
                    }
                    else {
                        binding.rvTimeSlots.visibility = View.GONE
                        binding.tvPlaceholder.visibility = View.VISIBLE
                        binding.textShowMore.visibility = View.INVISIBLE
                    }

                }, year, month, day)

            dialog.datePicker.minDate = todayDate

            dialog.show()

        }

        binding.textShowMore.setOnClickListener {

            val list = timeSlotMap[weekdays[dayOfWeek-1]]!!

            if (showMore) {
                showMore = false
                binding.textShowMore.text = getString(R.string.show_more)
                binding.rvTimeSlots.adapter = TimeSlotAdapter(list, 12)

            }
            else {
                showMore = true
                binding.textShowMore.text = getString(R.string.show_less)
                binding.rvTimeSlots.adapter = TimeSlotAdapter(list, list.size)
            }

        }

        viewModel.teacherData.observe(this) {

            it?.let {
                Log.d("MAinActivity", "onCreate: observer called")

                Glide
                    .with(this)
                    .load(it.image)
                    .centerCrop()
                    .into(binding.ivProfilePic)

                binding.tvName.text = it.name
                binding.tvRatings.text = it.rating.toString()
                binding.tvAboutMe.text = it.description


                 timeSlotMap = it.timeslot
                val list = timeSlotMap[weekdays[dayOfWeek-1]]!!

                Log.i("MainActivity", "onCreate: $list")

                binding.rvTimeSlots.layoutManager = GridLayoutManager(this, 3,
                    VERTICAL, false)

                if (list.isNotEmpty()) {

                    binding.tvPlaceholder.visibility = View.INVISIBLE
                    binding.rvTimeSlots.adapter = TimeSlotAdapter(list, 12)
                }
                else {
                    binding.tvPlaceholder.visibility = View.VISIBLE
                    binding.textShowMore.visibility = View.INVISIBLE
                    binding.rvTimeSlots.visibility = View.GONE

                }
            }

        }
    }

}