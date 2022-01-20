package com.example.myfastcampusstudy_android.upper_intermediate.youtube

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityYoutubeMainBinding
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.adapter.VideoAdapter
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.dto.VideoDto
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YoutubeMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYoutubeMainBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYoutubeMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayerFragment()
        initView()

    }

    private fun initPlayerFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PlayerFragment())
            .commit()
    }

    private fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        videoAdapter = VideoAdapter(callback = {url, title ->
            supportFragmentManager.fragments.find{it is PlayerFragment}?.let{
                (it as PlayerFragment).play(url, title)
            }
        })
        binding.recyclerViewMain.adapter = videoAdapter
        getVideoList()
    }



    private fun getVideoList(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also{
            it.listVideos()
                .enqueue(object : Callback<VideoDto>{
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()){
                            Log.d("YoutubeMainActivity/", "onResponse: Response Fail")
                            return
                        }
                        response.body()?.let {
                            Log.d("YoutubeMainActivity", "onResponse: ${it.videos}")
                            videoAdapter.submitList(it.videos)
                        }
                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        Log.d("YoutubeMainActivity", "onFailure: $t")
                    }
                })
        }

    }
}