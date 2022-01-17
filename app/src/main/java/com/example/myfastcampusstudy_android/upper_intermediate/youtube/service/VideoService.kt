package com.example.myfastcampusstudy_android.upper_intermediate.youtube.service

import com.example.myfastcampusstudy_android.upper_intermediate.youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/966d3d23-25ab-443e-87a0-a0f2abdf30ac")
    fun listVideos(): Call<VideoDto>
}