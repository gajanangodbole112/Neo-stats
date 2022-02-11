package com.gajanan.asteroid_neostats.ui

import android.app.DatePickerDialog
import android.icu.text.DateTimePatternGenerator.PatternInfo.OK
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.gajanan.asteroid_neostats.databinding.ActivityHomeBinding
import com.gajanan.asteroid_neostats.models.NearEarthObjects
import com.gajanan.asteroid_neostats.models.NeoStats

import com.gajanan.asteroid_neostats.network.NeoApi

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

const val TAG = "MainActivity"

class HomeActivity : AppCompatActivity() {

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchApi()
       getDate()
    }


    private fun getDate(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        binding.apply {
            tvStartDate.setOnClickListener {
                val dpd = DatePickerDialog(applicationContext,  { view, year, monthOfYear, dayOfMonth ->

                    // Display Selected date in textbox
                    val date = "$year $monthOfYear $dayOfMonth"
                    Toast.makeText(applicationContext,
                        date,
                        Toast.LENGTH_LONG).show()
                }, year, month, day)

                dpd.show()
            }
        }
    }

    private fun updateLabel(myCalender: Calendar) {
        val mFormat = "yyyy-mm-dd"
        val sdf = SimpleDateFormat(mFormat,Locale.UK)
        val tDate : String = sdf.format(myCalender.time)
        Toast.makeText(applicationContext,
        tDate,
        Toast.LENGTH_LONG)
            .show()
    }

    private fun fetchApi() {

        val result = NeoApi.api.getNeoStats(
            "DEMO_KEY",
            "2015-02-28",
            "2015-02-29"
        )
        result.enqueue(object : Callback<NeoStats>{
            override fun onResponse(
                call: Call<NeoStats>,
                response: Response<NeoStats>
            ) {
                if (response.isSuccessful) {
                    Log.d(TAG,response.body().toString())
                    Toast.makeText(
                        applicationContext,
                        response.body().toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }else{
                    Log.d(TAG,response.errorBody().toString())
                    Toast.makeText(
                        applicationContext,
                        "error : ${response.errorBody().toString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<NeoStats>, t: Throwable) {
                Log.d(TAG,t.message.toString())
                Toast.makeText(
                    applicationContext,
                    t.message.toString(),
                    Toast.LENGTH_LONG
                ).show()
            }


        })
    }
}