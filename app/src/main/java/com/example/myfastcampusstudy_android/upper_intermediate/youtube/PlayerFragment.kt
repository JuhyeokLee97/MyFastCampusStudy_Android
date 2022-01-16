package com.example.myfastcampusstudy_android.upper_intermediate.youtube

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentPlayerBinding
import kotlin.math.abs

class PlayerFragment : Fragment(R.layout.fragment_player) {
    private lateinit var binding: FragmentPlayerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPlayerBinding.bind(view)

        binding.playerMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener{
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                (requireActivity() as YoutubeMainActivity).also { youtubeMainActivity ->
                    youtubeMainActivity.findViewById<MotionLayout>(R.id.mainMotionLayout).progress = abs(progress)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {}
        })

    }

}