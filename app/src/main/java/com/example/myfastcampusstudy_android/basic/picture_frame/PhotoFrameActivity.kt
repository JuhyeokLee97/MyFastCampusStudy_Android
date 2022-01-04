package com.example.myfastcampusstudy_android.basic.picture_frame

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityPhotoFrameBinding
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private var currentPosition = 0
    private val photoList = mutableListOf<Uri>()
    private var timer: Timer? = null

    private lateinit var binding: ActivityPhotoFrameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoFrameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getPhotoUriFromPrevIntent()
    }

    private fun getPhotoUriFromPrevIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 2500) {
            runOnUiThread {
                val current = currentPosition
                val next = (currentPosition + 1) % (photoList.size)

                binding.ivBackgroundPhoto.setImageURI(photoList[current])

                // alpha 값은 투명도
                with(binding.ivPhoto) {
                    alpha = 0f
                    setImageURI(photoList[next])
                    animate().alpha(1.0f)
                        .setDuration(1000)
                        .start()
                }
                currentPosition = next
            }
        }
    }

    override fun onStop() {
        super.onStop()

        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()

        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()

        timer?.cancel()
    }

}