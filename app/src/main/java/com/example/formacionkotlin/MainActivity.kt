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

    // carResponse será el tipo de objeto de respuesta que vamos a manejar una vez obtengamos la respuesta correcta del servicio,
    // o cuado transfgormemos el json de Assets en un objeto para tratarlo en Kotlin
    lateinit var carResponse: CarItemResponse

    // Adaptador que utlizaremos para generar un listado (definido en detalle en la clase MediaAdapter)
    lateinit var adapter: MediaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Tenemos llamadas a dos métodos (o utlizamos uno u otro)
         *
         * 1- La primera obtiene el json del servicio y lo transforma a objeto CarItemResponse
         * 2- La segunda obtiene el json de la carpeta assets (situada dentro del proyecto) ya que estando en el interior de este
         *    se podría modificar la información a mostrar y podríais jugar un poco más con la respuesta.
         */
        getLamborghiniDataFromService()
        //getLamborghiniDataFromAssets()
    }


    /**
     * Este método saltará automáticamente al crear el menú, pero también cuando pulsemos en el botón del menú, sobreescribimos este método
     * porque vamos a montar el menú de manera dinámica (según los códigos de colores que vengan del servicio)
     * para ello, hasta que no tengamos respuesta del servicio, no podremos construir el menú, por ello, utilizamos onPrepareOptionsMenu en lugar de onCreateOptionsMenu
     */
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        return if(::carResponse.isInitialized){
            val dynamicMenu = setMenuContents(menu)
            super.onPrepareOptionsMenu(dynamicMenu)
        }else {
            menu.add(R.string.color_white)
            super.onPrepareOptionsMenu(menu)
        }
    }


    /**
     * Cada vez que pulsemos en un elemento del menú, gestionaremos toda la lógica de filtrado de información a mostrar.
     * Para ello, recorremos cada elemento de la respuesta del servicio "carResponse.cars.let { carItem ->" utilizando "carItem" como
     * el clásico cars.get(i) y comprobaremos si el "id" de la opción del menú seleccionada coincide con la
     * asignada previamente para poder filtrar la lista por aquellos que contengan dicho código de color.
     *
     * Al final, todo este filtrado se los estamos aplicando a "adapter.items" que es la lista que maneja el adaptador,
     * con lo cual una vez modificada dicha lista, hacemos un "adapter.notifyDataSetChanged()" para refrescar la lista en base a sus nuevos elementos.
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        adapter.items = carResponse.cars.let { carItem ->
            when (item?.itemId){
                MENU_ITEM_ID_ALL -> carItem
                COLOR_YELLOW.toInt() -> carItem.filter { it.carColorId == COLOR_YELLOW }
                COLOR_WHITE.toInt() -> carItem.filter { it.carColorId == COLOR_WHITE }
                COLOR_GREEN.toInt() -> carItem.filter { it.carColorId == COLOR_GREEN }
                COLOR_BLACK.toInt() -> carItem.filter { it.carColorId == COLOR_BLACK }
                COLOR_GRAY.toInt() -> carItem.filter { it.carColorId == COLOR_GRAY }
                COLOR_BLUE.toInt() -> carItem.filter { it.carColorId == COLOR_BLUE }
                else -> emptyList()
            }
        }
        adapter.notifyDataSetChanged()
        return super.onOptionsItemSelected(item)
    }


    /**
     * Llamada a retrofir para optener la respuesta del servicio en el ApiClient tenemos especificada la llamada de tipo GET y el tipo de Objeto de respuesta que maneja.
     * De todo lo demás se encargará retrofit.
     *
     * Cuando optenemos la respuesta del servicio llamamos al método que instancia el adaptador (setCarAdapter(it))
     */
    private fun getLamborghiniDataFromService(){

        val _apiService = ApiClient.service

        val call = _apiService.getLamborghinis()
        call.enqueue(object : Callback<CarItemResponse> {

            override fun onResponse(call: Call<CarItemResponse>, response: Response<CarItemResponse>) {
                response.body()?.takeIf { response.isSuccessful }.apply {
                    response.body()?.let {
                        setCarAdapter(it)
                    }
                }
            }
            override fun onFailure(call: Call<CarItemResponse>, t: Throwable) {
                //Clase que genera una alerta de diálogo nativa de Android
                AlertDialog.Builder(this@MainActivity).setMessage("Error Loading Lambos").create().show()
            }
        })
    }

    /**
     * Hacemos el objeto de respuesta variable global para poder utilizaro en el método onPrepareOptionsMenu y poder montar así el menú dinámico.
     *
     * Hacemos el adaptador global para poder gestionar el filtrado del menú también, ya que dicho filtrado se aplica a adapter.items (los objetos que maneja el adaptador)
     */
    private fun setCarAdapter(carItemResponse: CarItemResponse){
        this.carResponse = carItemResponse
        this.adapter = MediaAdapter(this, carItemResponse.cars)
        recycler.adapter = adapter
    }


    /**
     * Para poder montar el menú de manera dinámica:
     *
     * 1- Limpiamos el menú por defecto
     *
     * 2- Le añadimos la opción "All" como primera opción estática (no es dinámica, ya que estamos asumiento que siempre habrá un "todos")
     *
     * 3- Creamos un HashMap para saber cuantos colores tenemos, ¿por qué un Hashmap y no una lista? porque necesitamos solo un código de color,
     *    si este se repitiera, al usar una lista mostraríamos más de un mismo color en el menú si los hubiera.
     *
     * 4- carResponse.cars.map { colorMap.put(it.carColorId, it.carColorId) } recorremos el listado de coches y añadimos el color de cada elemento del listado
     *
     * 5- Transformamos a lista el mapa y volvemos a recorrerlo, para ahora sí, completar las opciones que contendrá el menú
     *
     * Ojo! Si os fijais, la segunda opción del método "add" del menú -> "it.first.toInt()" es el código del color, este asigna el itemID del menú
     * para que cuando salte el método onOptionsItemSelected y hagamos el "when" (el switch de java) podamos saber que se seleccionó.
     *
     */
    private fun setMenuContents(menu: Menu): Menu{
        menu.clear()
        menu.add(MENU_ITEM_GROUP_ID, MENU_ITEM_ID_ALL, 0, R.string.all_menu_items)
        val colorMap = HashMap<String, String>()
        carResponse.cars.map { colorMap.put(it.carColorId, it.carColorId) }
        colorMap.toList().map {
            menu.add(MENU_ITEM_GROUP_ID, it.first.toInt(), 0, getCarColorNameByID(it.first))
        }
        return menu
    }


    /**
     * Simplemente lo que hace este método es asignar el nombre del color según su código
     * lo útilizamos como 4rto parámetro del "add" de la opción del menú que sería el nombre de la opción del menú que estamos creando.
     */
    private fun getCarColorNameByID(carColorID: String): String{

        return when (carColorID){

            COLOR_YELLOW -> this.getString(R.string.color_yellow)
            COLOR_WHITE -> this.getString(R.string.color_white)
            COLOR_BLACK -> this.getString(R.string.color_black)
            COLOR_GREEN -> this.getString(R.string.color_green)
            COLOR_BLUE -> this.getString(R.string.color_blue)
            COLOR_GRAY -> this.getString(R.string.color_gray)
            else -> this.getString(R.string.color_white)
        }
    }


    /***************************************************************************************
     *  Estos dos métodos solo se utilizan si tiramos de assets en lugar del servicio real *
     ***************************************************************************************/



    /**
     * Recogemos el json de assets y con gson lo transformamos en un objeto de Kotlin
     */
    fun getLamborghiniDataFromAssets(){

        val gson = Gson()
        val json = this.assets.open("carsamplemodel")
        val carItemResponse = gson.fromJson(inputStreamToString(json), CarItemResponse::class.java)
        setCarAdapter(carItemResponse)
    }


    /**
     * Assets nos devuelve el json como un opjeto de tipo "InputStream" para ello,
     * tenemos que transformarlo a bytes y luego a string para que la tranformación al objeto sea correcta.
     */
    fun inputStreamToString(inputStream: InputStream): String {

        return try {
            val bytes = ByteArray(inputStream.available())
            inputStream.read(bytes, 0, bytes.size)
            String(bytes)
        } catch (e: IOException) {
            "Parse Error"
        }
    }
}
