package com.example.formacionkotlin.models

const val MENU_ITEM_GROUP_ID = 0
const val MENU_ITEM_ID_ALL = 0


data class CarItemResponse(
    val cars: List<CarItem>
)

data class CarItem(
    var carColorId: String,
    val carImageUrl: String,
    val carModelName: String,
    val carSpecs: CarItemSpecs
)

data class CarItemSpecs(
    val carHorsepower: String,
    val carSalePrice: String,
    val carWeight: String
)