package com.example.myfastcampusstudy_android.upper_intermediate.youtube.service

import com.example.myfastcampusstudy_android.upper_intermediate.youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/8a68bfc1-9e6b-4f63-bdcd-3dbaaf6480ef")
    fun listVideos(): Call<VideoDto>
}