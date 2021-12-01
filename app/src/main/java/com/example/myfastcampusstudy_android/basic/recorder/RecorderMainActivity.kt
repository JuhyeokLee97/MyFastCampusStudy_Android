package com.example.myfastcampusstudy_android.basic.recorder

import android.content.pm.PackageManager
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
    private val requiredPermissions = arrayOf(android.Manifest.permission.RECORD_AUDIO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recorder_main)
        requestAudioPermission()
        initViews()
    }

    private fun initViews() {
        initRecordButton()
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

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 201
    }
}