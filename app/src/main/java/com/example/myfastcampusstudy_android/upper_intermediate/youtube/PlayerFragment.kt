package com.example.myfastcampusstudy_android.upper_intermediate.youtube

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentPlayerBinding
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.adapter.VideoAdapter
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.dto.VideoDto
import com.example.myfastcampusstudy_android.upper_intermediate.youtube.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var videoAdapter: VideoAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerBinding.bind(view)

        initMotionLayoutEvent()
        initRecyclerView()

        getVideoList()

    }

    private fun initMotionLayoutEvent() {
        binding.playerMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                (requireActivity() as YoutubeMainActivity).also { youtubeMainActivity ->
                    youtubeMainActivity.findViewById<MotionLayout>(R.id.mainMotionLayout).progress =
                        abs(progress)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })
    }

    private fun initRecyclerView() {
        videoAdapter = VideoAdapter(callback = { url, title ->
            play(url, title)
        })

        binding.fragmentRecyclerView.apply {
            adapter = videoAdapter
        }
    }

    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also {
            it.listVideos()
                .enqueue(object : Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
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

    fun play(url: String, title: String){
        binding.apply {
            playerMotionLayout.transitionToEnd()
            tvBottomTitle.text = title
        }

    }
}