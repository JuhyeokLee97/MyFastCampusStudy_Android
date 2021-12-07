package com.example.myfastcampusstudy_android.intermediate.abnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {
    @GET("/v3/11fdc86b-2ac3-41d7-91e7-184265d8c43b")
    fun getHouseList(): Call<HouseDto>
}