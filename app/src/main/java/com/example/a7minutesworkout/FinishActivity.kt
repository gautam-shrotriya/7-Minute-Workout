package com.example.a7minutesworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private var binding: ActivityFinishBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarFinishActivity)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarFinishActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.buttonFinish?.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val workoutHistoryDao = (application as WorkoutApp).db.historyDao()
        addHistoryDateToDatabase(workoutHistoryDao)
    }

    private fun addHistoryDateToDatabase(workoutHistoryDao : HistoryDao){

        val calendar = Calendar.getInstance()
        val dateTime = calendar.time
        Log.e("Date: ", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("Formatted Date : ", "" + date)

        lifecycleScope.launch {
            workoutHistoryDao.insert(HistoryEntity(date))
            Log.e("Date:", "Added")
        }
    }
}