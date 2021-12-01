package com.example.myfastcampusstudy_android.basic.recorder

import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.myfastcampusstudy_android.R
import java.util.jar.Manifest

class RecorderMainActivity : AppCompatActivity() {
    private lateinit var btnRecord: RecordButton
    private var state = State.BEFORE_RECORDING
        set(value) {
            field = value
            btnRecord.updateIconWithState(value)
        }

    private val requiredPermissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)
    private val recordingFilePath: String by lazy {
        "${externalCacheDir?.absolutePath + "/recording.3gp"}"
    }

    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder_main)
        requestAudioPermission()
        initViews()
        bindViews()
    }

    private fun initViews() {
        initRecordButton()
    }


    private fun bindViews() {
        btnRecord.setOnClickListener {
            when (state) {
                State.BEFORE_RECORDING -> startRecording()
                State.ON_RECORDING -> stopRecording()
                State.AFTER_RECORDING -> startPlay()
                State.ON_PLAYING -> stopPlay()
            }
        }
    }

    private fun initRecordButton() {
        btnRecord = findViewById(R.id.btnRecord)
        btnRecord.updateIconWithState(state)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestAudioPermission() {
        requestPermissions(requiredPermissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val audioRecordPermissionGranted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION &&
                grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED

        if (!audioRecordPermissionGranted) {
            finish()
        } else {
            Toast.makeText(this, "마이크 권한을 허용하였습니다.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun startRecording() {
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(recordingFilePath)
            prepare()
        }

        recorder?.start()
        state = State.ON_RECORDING
    }

    private fun stopRecording() {
        recorder?.run {
            stop()
            release()
        }
        recorder = null
        state = State.AFTER_RECORDING
    }

    private fun startPlay() {
        player = MediaPlayer().apply {
            setDataSource(recordingFilePath)
            prepare()
        }

        player?.start()
        state = State.ON_PLAYING
    }

    private fun stopPlay() {
        player?.release()
        player = null
        state = State.AFTER_RECORDING
    }

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}