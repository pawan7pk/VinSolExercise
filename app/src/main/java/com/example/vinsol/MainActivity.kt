package com.example.vinsol

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinsol.adapters.MeetingAdapter
import androidx.lifecycle.Observer
import com.example.vinsol.model.ScheduleMeeting

import com.example.vinsol.viewmodels.MeetingViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity(), View.OnClickListener {
    private lateinit var meetingViewModel: MeetingViewModel
    private lateinit var meetingAdapter: MeetingAdapter
    private var list: ArrayList<ScheduleMeeting> = ArrayList()
    private var currentDate: String = SimpleDateFormat("dd-MM-yyyy", Locale.US).format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(topAppBar)
        setUpViewModel()
        setUpObservers()
        setUpRecyclerView()
        setOnClickListeners()
    }

    private fun setUpViewModel() {
        meetingViewModel = ViewModelProvider(this).get(MeetingViewModel::class.java)

        callApiAndSetDate(currentDate)
    }

    private fun setUpObservers() {
        meetingViewModel.scheduleMeetingList.observe(this, Observer {
            list = it
            if (list.isEmpty())
                showMessageDialog("No Data Found")
            setUpRecyclerView()
        })
    }

    private fun setUpRecyclerView() {

        rv_scheduleMeeting.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            meetingAdapter = MeetingAdapter(list)
            adapter = meetingAdapter
        }

    }

    private fun setOnClickListeners() {
        iv_next.setOnClickListener(this)
        iv_back.setOnClickListener(this)
        tv_scheduleMeeting.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_next -> callApiAndSetDate(
                updateDate(currentDate, true)
            )

            R.id.iv_back -> callApiAndSetDate(
                updateDate(currentDate, false)
            )

            R.id.tv_scheduleMeeting -> Intent(this, ScheduleMeetingActivity::class.java).apply {
                putExtra("date", currentDate)
                startActivity(this)
            }
        }
    }

    private fun callApiAndSetDate(date: String) {
        meetingViewModel.callScheduleMeetingAPI(date)
        tv_date.text = date
        setUpObservers()
    }

    private fun updateDate(currDate: String, isNextDate: Boolean): String {
        val format: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date: Date = format.parse(currDate)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date
        if (isNextDate)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        else
            calendar.add(Calendar.DAY_OF_YEAR, -1)
        currentDate = format.format(calendar.time)
        return currentDate
    }

//    fun showMessageDialog(msg: String) {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle("Vin Solution")
//        builder.setMessage(msg)
//        builder.setPositiveButton("OK", null)
//        builder.show()
//    }
}