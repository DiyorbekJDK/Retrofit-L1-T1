package com.example.retrofit_l1_t1.model

import com.google.gson.annotations.SerializedName

data class FoodResponse(
    val foodList: List<Food>
)
data class Food(
    @SerializedName("id")
    val foodId: Int,
    val title: String,
    val image: String
)
