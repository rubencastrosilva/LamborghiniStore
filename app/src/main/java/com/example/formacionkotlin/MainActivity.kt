package com.example.formacionkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import com.example.formacionkotlin.adapters.MediaAdapter
import com.example.formacionkotlin.models.CarItemResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.io.InputStream
import android.view.Menu
import android.view.MenuItem
import com.example.formacionkotlin.Utils.*
import com.example.formacionkotlin.models.MENU_ITEM_GROUP_ID
import com.example.formacionkotlin.models.MENU_ITEM_ID_ALL
import com.example.formacionkotlin.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var carResponse: CarItemResponse
    lateinit var adapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLamborghiniData()
    }

//    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
//        return if(::carResponse.isInitialized){
//            val dynamicMenu = setMenuContents(menu)
//            super.onPrepareOptionsMenu(dynamicMenu)
//        }else {
//            menu.add(R.string.color_white)
//            super.onPrepareOptionsMenu(menu)
//        }
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        adapter.items = carResponse.cars.let { carItem ->
//            when (item?.itemId){
//                MENU_ITEM_ID_ALL -> carItem
//                COLOR_YELLOW.toInt() -> carItem.filter { it.carColorId == COLOR_YELLOW }
//                COLOR_WHITE.toInt() -> carItem.filter { it.carColorId == COLOR_WHITE }
//                COLOR_GREEN.toInt() -> carItem.filter { it.carColorId == COLOR_GREEN }
//                COLOR_BLACK.toInt() -> carItem.filter { it.carColorId == COLOR_BLACK }
//                COLOR_GRAY.toInt() -> carItem.filter { it.carColorId == COLOR_GRAY }
//                COLOR_BLUE.toInt() -> carItem.filter { it.carColorId == COLOR_BLUE }
//                else -> emptyList()
//            }
//        }
//        adapter.notifyDataSetChanged()
//        return super.onOptionsItemSelected(item)
//    }

    private fun getLamborghiniData(){

        val _apiService = ApiClient.service


    }

    private fun setCarAdapter(carItemResponse: CarItemResponse){
    }


//    private fun setMenuContents(menu: Menu): Menu{
//        menu.clear()
//        menu.add(MENU_ITEM_GROUP_ID, MENU_ITEM_ID_ALL, 0, R.string.all_menu_items)
//        val colorMap = HashMap<String, String>()
//        carResponse.cars.map { colorMap.put(it.carColorId, it.carColorId) }
//        colorMap.toList().map { carItem ->
//            menu.add(MENU_ITEM_GROUP_ID, carItem.first.toInt(), 0, getCarColorNameByID(carItem.first))
//        }
//        return menu
//    }
//
//    private fun getCarColorNameByID(carColorID: String): String{
//
//        when (carColorID){
//
//            COLOR_YELLOW -> return this.getString(R.string.color_yellow)
//            COLOR_WHITE -> return this.getString(R.string.color_white)
//            COLOR_BLACK -> return this.getString(R.string.color_black)
//            COLOR_GREEN -> return this.getString(R.string.color_green)
//            COLOR_BLUE -> return this.getString(R.string.color_blue)
//            COLOR_GRAY -> return this.getString(R.string.color_gray)
//        }
//        return this.getString(R.string.color_white)
//    }
}
