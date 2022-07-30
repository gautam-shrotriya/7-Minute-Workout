package com.example.a7minutesworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCustomBinding
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var binding: ActivityExerciseBinding? = null

    private var restTimer : CountDownTimer? = null
    private var restProgress = 0

    private var exerciseRestTimer : CountDownTimer? = null
    private var exerciseRestProgress = 0

    private var restTimerDuration: Long = 10
    private var exerciseTimerDuration: Long = 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var tts : TextToSpeech? = null

    private var player : MediaPlayer? = null

    private var exerciseAdapter: ExerciseStatusAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        tts = TextToSpeech(this, this)

        player = MediaPlayer.create(this, R.raw.clock_alarm)

        exerciseList = Constants.defaultExerciseList()

        setSupportActionBar(binding?.toolBarExercise)

        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.toolBarExercise?.setNavigationOnClickListener {
            customDialogForBackButton()
        }

        setupRestView()
        setUpExerciseStatusRecyclerView()
    }

    private fun setUpExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)

        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setupRestView(){

        player?.start()

        binding?.FLRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseTag?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.FLExerciseProgressBar?.visibility = View.INVISIBLE
        binding?.IVExercise?.visibility = View.INVISIBLE
        if(restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }

        binding?.tvUpcomingExerciseName?.text = exerciseList!![currentExercisePosition+1].getName()

        setRestProgressBar()
    }

    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress

        restTimer = object : CountDownTimer(restTimerDuration*1000, 1000){
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress = 10-restProgress
                binding?.TVTimer?.text = (10-restProgress).toString()
            }
            override fun onFinish() {
                currentExercisePosition++

                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView(){
        binding?.FLRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseTag?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.FLExerciseProgressBar?.visibility = View.VISIBLE
        binding?.IVExercise?.visibility = View.VISIBLE

        speakOutExerciseName(exerciseList!![currentExercisePosition].getName())

        if(exerciseRestTimer != null){
            exerciseRestTimer?.cancel()
            exerciseRestProgress=0
        }

        binding?.IVExercise?.setImageResource(exerciseList!![currentExercisePosition].getImage())
        binding?.tvExerciseName?.text = exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar(){
        binding?.exerciseProgressBar?.progress = exerciseRestProgress

        exerciseRestTimer = object : CountDownTimer(exerciseTimerDuration*1000, 1000){
            override fun onTick(p0: Long) {
                exerciseRestProgress++
                binding?.exerciseProgressBar?.progress = 30-exerciseRestProgress
                binding?.TVExerciseTimer?.text = (30-exerciseRestProgress).toString()
            }
            override fun onFinish() {


                if(currentExercisePosition < exerciseList!!.size!! - 1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else{
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        if(restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }

        if(exerciseRestTimer != null){
            exerciseRestTimer?.cancel()
            exerciseRestProgress = 0
        }

        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }

        if(player != null){
            player!!.stop()
        }

        binding = null
    }

    override fun onInit(status : Int) {
        if(status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.UK)

            if(result == TextToSpeech.LANG_NOT_SUPPORTED ||
                result == TextToSpeech.LANG_MISSING_DATA){
                Log.e("Problem", "Specified language is not supported.")
            }
        }
        else{
            Log.e("Problem", "TTS Initialization Failed!")
        }
    }

    private fun speakOutExerciseName(exerciseName : String){
        tts?.speak(exerciseName, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun onBackPressed() {
        customDialogForBackButton()
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCustomBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()

    }
}