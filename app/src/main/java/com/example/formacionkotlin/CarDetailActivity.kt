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
        setContentView(R.layout.activity_car_detail)

        /*
         * Estos dos métodos añadirán la opción de "<--" en la parte superior izquierda de la toolbar
         */
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        /*
         * La información que queremos manejar en esta actividad viene de otra, para ello, como vimos previamente, serializamos el objeto y lo pasamos vía intent
         * y ahora, dada una "key" para identificarla (saber exactamente cual es, ya que podríamos mover múltiples datos)
         * comprobamos que no sea nulo y que el objeto de recogemos sea realmente el que queremos tratar "is CarItem" (casting de Kotlin)
         * para poder hacer luego la asignación sin podibilidad de fallos "as CarItem"
         *
         * Luego volvemos a utilizar la librería de Picasso para pintar el coche en esta pantalla dada una URL de una imagen almacenada en una web
         */
        if (intent?.getSerializableExtra(CAR_DETAIL_KEY) != null && intent?.getSerializableExtra(CAR_DETAIL_KEY) is CarItem){

            val car = intent?.getSerializableExtra(CAR_DETAIL_KEY) as CarItem
            Picasso.with(this).load(car.carImageUrl).into(iv_car_detail_image)
            setCarSpecsDetail(car.carSpecs)
        }
    }

    /**
     * Sobreescribimos este método para realizar la acción de "Volver atras" al pulsar en el botón que asignamos en el "onCreate"
     * -> supportActionBar?.setDisplayHomeAsUpEnabled(true)
    -> supportActionBar?.setHomeButtonEnabled(true)
     */
    override fun onSupportNavigateUp(): Boolean {
        //simula pulsar el botón físico " <- atras"
        onBackPressed()
        return true
    }

    /**
     * Asignamos la información de cada coche a los textos que tenemos en esta actividad.
     */
    fun setCarSpecsDetail(carSpecs: CarItemSpecs){

        tv_car_horsepower_info.text = carSpecs.carHorsepower
        tv_car_weight_info.text = carSpecs.carWeight
        tv_car_price_info.text = carSpecs.carSalePrice
    }



}