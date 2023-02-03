package com.example.retrofit_l1_t1.network

import com.example.retrofit_l1_t1.model.Data
import com.example.retrofit_l1_t1.model.FoodResponse
import com.example.retrofit_l1_t1.model.OneUserResponse
import com.example.retrofit_l1_t1.model.UserResponse
import retrofit2.*
import retrofit2.http.*

interface ApiService {

    @GET("api/users")
    fun getUsers(): Call<UserResponse>

    @GET("/api/users/{id}")
    fun getUserById(@Path("id") id: Int): Call<OneUserResponse>

    @POST("/api/users")
    fun postUser(@Body data: Data): Call<OneUserResponse>

    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body data: Data): Call<OneUserResponse>

    @DELETE("/api/users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<Any>

    @FormUrlEncoded
    @PATCH("/api/users/{id}")
    fun postWithFields(
        @Path("id") id: Int,
        @Field("avatar") avatar: String
    )

    @GET("/recipes/complexSearch")
    fun getFoods(
        @Query("apiKey") apiKey: String = ""
    ): Call<FoodResponse>

}