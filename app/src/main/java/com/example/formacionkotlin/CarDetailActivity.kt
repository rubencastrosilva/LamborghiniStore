package com.example.formacionkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.formacionkotlin.models.CarItem
import com.example.formacionkotlin.models.CarItemSpecs
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_car_detail.*

const val CAR_DETAIL_KEY = "car_detail_key"

class CarDetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}