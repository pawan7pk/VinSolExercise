package com.example.vinsol

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.format.Time
import android.util.Log
import android.view.ContextMenu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.vinsol.model.ScheduleMeeting
import com.example.vinsol.viewmodels.MeetingViewModel
import kotlinx.android.synthetic.main.activity_schedule_meeting.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ScheduleMeetingActivity : BaseActivity(), View.OnClickListener {

    private val meetingDate: Calendar = Calendar.getInstance()
    private val startTime: Calendar = Calendar.getInstance()
    private val endTime: Calendar = Calendar.getInstance()
    private lateinit var meetingViewModel: MeetingViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_meeting)

        setUpViewModel()
        setOnClickListener()

    }

    private fun setUpViewModel() {
        meetingViewModel = ViewModelProvider(this).get(MeetingViewModel::class.java)
        meetingViewModel.scheduleMeetingList.observe(this, androidx.lifecycle.Observer {
            checkSlots(it)
        })
    }

    private fun checkSlots(list: ArrayList<ScheduleMeeting>) {
        val dateFormat = SimpleDateFormat("hh:mm", Locale.US)
        list.forEach {
            val afterCalendar = Calendar.getInstance().apply {
                time = dateFormat.parse(it.start_time)
                add(Calendar.DATE, 1)
            }
            val beforeCalendar = Calendar.getInstance().apply {
                time = dateFormat.parse(it.end_time)
                add(Calendar.DATE, 1)
            }
            if (startTime.time.after(afterCalendar.time) && endTime.time.before(beforeCalendar.time)) {
                showMessageDialog("Slots Available")
            }

        }
//        showMessageDialog("Slots Not available")
    }

    private fun setOnClickListener() {
        tv_meeting_date.setOnClickListener(this)
        tv_start_time.setOnClickListener(this)
        tv_end_time.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_meeting_date -> {
                val dpd = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        // Display Selected date in textbox
                        meetingDate.set(Calendar.YEAR, year)
                        meetingDate.set(Calendar.MONTH, monthOfYear)
                        meetingDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        tv_meeting_date.text = SimpleDateFormat(
                            "dd/MM/yyyy",
                            Locale.getDefault()
                        ).format(meetingDate.time)
                    },
                    meetingDate.get(Calendar.YEAR),
                    meetingDate.get(Calendar.MONTH),
                    meetingDate.get(Calendar.DAY_OF_MONTH)
                )
                dpd.show()
            }

            R.id.tv_start_time -> {
                showTimePicker(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    startTime.set(Calendar.HOUR, hourOfDay)
                    startTime.set(Calendar.MINUTE, minute)

                    tv_start_time.text = SimpleDateFormat(
                        "hh:mm",
                        Locale.getDefault()
                    ).format(startTime.time)
                }, startTime)
            }

            R.id.tv_end_time -> {
                showTimePicker(TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                    endTime.set(Calendar.HOUR, hourOfDay)
                    endTime.set(Calendar.MINUTE, minute)

                    tv_end_time.text = SimpleDateFormat(
                        "hh:mm",
                        Locale.getDefault()
                    ).format(endTime.time)
                }, endTime)
            }

            R.id.btn_submit -> if (et_desciption.text.isNotEmpty()) {
                meetingViewModel.callScheduleMeetingAPI(meetingDate.toString())
            }

        }
    }

    private fun showTimePicker(
        timePickerListener: TimePickerDialog.OnTimeSetListener,
        cal: Calendar
    ): TimePickerDialog {
        val tpd = TimePickerDialog(
            this,
            timePickerListener,
            cal.get(Calendar.HOUR),
            cal.get(Calendar.MINUTE),
            false
        )
        tpd.show()
        return tpd
    }

}