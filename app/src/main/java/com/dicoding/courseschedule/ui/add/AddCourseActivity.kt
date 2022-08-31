package com.dicoding.courseschedule.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.dicoding.courseschedule.util.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var  viewModel : AddCourseViewModel

    private lateinit var startTime: String
    private lateinit var endTime: String
    private lateinit var courseTxtView : TextView
    private lateinit var spinnerDay : Spinner
    private lateinit var startTxtView : TextView
    private lateinit var endTxtView : TextView
    private lateinit var lecturerTxtView : TextView
    private lateinit var noteTxtView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)
        supportActionBar?.title = getString(R.string.add_course)

        val vmFactory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, vmFactory).get(AddCourseViewModel::class.java)
        viewModel.saved.observe(this) {

            if (it.getContentIfNotHandled() == true) {
                onBackPressed()
            } else {
                Toast.makeText(this@AddCourseActivity,"please fill the date for the course", Toast.LENGTH_SHORT).show()
            }

        }

        courseTxtView = findViewById(R.id.title_edttext)
        spinnerDay = findViewById(R.id.date_spinner)
        startTxtView = findViewById(R.id.start_time_txt)
        endTxtView = findViewById(R.id.end_time_txt)
        lecturerTxtView = findViewById(R.id.lecturer_edttxt)
        noteTxtView = findViewById(R.id.note_edttxt)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_insert -> {
                if(courseTxtView.text.isNotEmpty() && startTxtView.text.isNotEmpty() && endTxtView.text.isNotEmpty() && lecturerTxtView.text.isNotEmpty()
                    && noteTxtView.text.isNotEmpty()) {
                    viewModel.insertCourse(
                        courseTxtView.text.toString(),
                        spinnerDay.selectedItemPosition,
                        startTime,
                        endTime,
                        lecturerTxtView.text.toString(),
                        noteTxtView.text.toString()
                    )
                    finish()
                }else{

                    Toast.makeText(applicationContext,"Please Complete the Data", Toast.LENGTH_SHORT).show()

                }


            }
            else -> return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun pickStart(view:View) {
        val fragment = TimePickerFragment()
        fragment.show(supportFragmentManager, "startPicker")
    }

    fun pickEnd(view:View) {
        val fragment = TimePickerFragment()
        fragment.show(supportFragmentManager, "endPicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        when (tag) {

            "startPicker" -> {
                startTime = timeFormat.format(calendar.time)
                startTxtView.text = startTime

            }

            "endPicker" -> {
                endTime = timeFormat.format(calendar.time)
                endTxtView.text = endTime
            }
        }
    }

}