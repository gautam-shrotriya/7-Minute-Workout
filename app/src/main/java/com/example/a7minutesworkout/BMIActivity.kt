package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    private var currentVisibleView: String = METRIC_UNITS_VIEW // A variable to hold a value to make a selected view visible"

    private var binding: ActivityBmiBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolBarBMI)
        if(supportActionBar != null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "CALCULATE BMI"
        }

        binding?.toolBarBMI?.setNavigationOnClickListener {
            onBackPressed()
        }

        makeVisibleUSUnitView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits){
                makeVisibleMetricUnitView()
            }else{
                makeVisibleUSUnitView()
            }
        }

        binding?.btnCalculate?.setOnClickListener {
            calculateUnits()
        }

    }

    private fun makeVisibleMetricUnitView(){
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.VISIBLE
        binding?.tilUSUnitWeight?.visibility = View.INVISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.llUSLength?.visibility = View.INVISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitView(){
        currentVisibleView = US_UNITS_VIEW
        binding?.tilMetricUnitWeight?.visibility = View.INVISIBLE
        binding?.tilUSUnitWeight?.visibility = View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.llUSLength?.visibility = View.VISIBLE

        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.etMetricUnitWeight?.text!!.clear()

        binding?.llDisplayBMIResult?.visibility = View.INVISIBLE
    }

    private fun displayBMIResult(bmi : Float){
        val bmiLabel : String
        val bmiDescription : String

        if(bmi.compareTo(15f) <= 0){
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0){
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0){
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        }
        else if(bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0){
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        }
        else if(bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0){
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout maybe!"
        }
        else if(bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0){
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take better care of yourself! Workout maybe!"
        }
        else if(bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        else{
            bmiLabel = "Obese Class ||| (Very severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDisplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue
        binding?.tvBMIType?.text = bmiLabel
        binding?.tvBMIDescription?.text = bmiDescription

    }

    private fun validateMetricUnits() : Boolean {
        var isValid = true

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun validateUSUnits() : Boolean {
        var isValid = true

        if(binding?.etUSUnitWeight?.text.toString().isEmpty()){
            isValid = false
        }
        else if(binding?.etUSUnitHeightFeet?.text.toString().isEmpty() ||
            binding?.etUSUnitHeightInches?.text.toString().isEmpty()){
            isValid = false
        }

        return isValid
    }

    private fun calculateUnits(){
        if(currentVisibleView == METRIC_UNITS_VIEW && validateMetricUnits()){
            val heightValue : Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100

            val weightValue : Float = binding?.etMetricUnitWeight?.text.toString().toFloat()

            val bmi : Float = weightValue / (heightValue*heightValue)

            displayBMIResult(bmi)
        }
        else if(currentVisibleView == US_UNITS_VIEW && validateUSUnits()){
            val usWeightValue : Float = binding?.etUSUnitWeight?.text.toString().toFloat()
            val usFeetValue : Float = binding?.etUSUnitHeightFeet?.text.toString().toFloat()
            val usInchesValue : Float = binding?.etUSUnitHeightInches?.text.toString().toFloat()

            val heightValue = usInchesValue + usFeetValue*12

            val bmi = 703*(usWeightValue / (heightValue*heightValue))

            displayBMIResult(bmi)
        }
        else{
            Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }
}