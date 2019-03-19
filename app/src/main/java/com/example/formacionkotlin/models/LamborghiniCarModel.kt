package com.example.formacionkotlin.models

import java.io.Serializable

/*
 * MENU_ITEM_GROUP_ID es indiferente, simplemente lo utilizamos siempre que añadamos una opción en el menú
 * MENU_ITEM_ID_ALL es el identidicador que manejamos como color "All" para poder saber cuando pulsamos en dicha opción
 */
const val MENU_ITEM_GROUP_ID = 0
const val MENU_ITEM_ID_ALL = 0

/**
 * Las dataclasses se utlizan con la intención de ser asignadas una única vez, en este caso, al obtener respuesta del servicio
 * por ello no se debería modificar sus valores de ahí que sean val (inmutables)
 *
 * Haremos los objetos Serializable para poder pasarlos vía intent.
 */
data class CarItemResponse(
    val cars: List<CarItem>
) : Serializable

data class CarItem(
    var carColorId: String,
    val carImageUrl: String,
    val carModelName: String,
    val carSpecs: CarItemSpecs
) : Serializable

data class CarItemSpecs(
    val carHorsepower: String,
    val carSalePrice: String,
    val carWeight: String
) : Serializable