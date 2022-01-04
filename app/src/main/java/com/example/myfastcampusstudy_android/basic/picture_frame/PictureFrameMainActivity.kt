package com.example.myfastcampusstudy_android.basic.picture_frame

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivityPictureFrameMainBinding

class PictureFrameMainActivity : AppCompatActivity() {

    lateinit var imageViewList: List<ImageView>
    private val imageUriList: MutableList<Uri> = mutableListOf()
    private val requestCode = 1000
    private lateinit var binding: ActivityPictureFrameMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPictureFrameMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {
        initAddPhotoButton()
        initStartPhotoFrameModeButton()
        initImageViewList()
    }

    private fun initImageViewList() {
        imageViewList = mutableListOf<ImageView>().apply {
            add(findViewById(R.id.ivPhoto11))
            add(findViewById(R.id.ivPhoto12))
            add(findViewById(R.id.ivPhoto13))
            add(findViewById(R.id.ivPhoto21))
            add(findViewById(R.id.ivPhoto22))
            add(findViewById(R.id.ivPhoto23))
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initAddPhotoButton() {
        binding.btnAddPhoto.setOnClickListener {
            when {
                // 갤러리 접근 권한이 있는 경우
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
                        == PackageManager.PERMISSION_GRANTED
                -> {
                    navigatePhoto()
                }

                // 권한이 부여되지 않은 상황에서, 교육용 팝업을 보여줘야 하는 경우
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                -> {
                    showPermissionContextPopup()
                }

                // 권한 요청 하기
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        requestCode
                    )
                }

            }
        }
    }

    private fun initStartPhotoFrameModeButton() {
        binding.btnStartPhotoFrameMode.setOnClickListener {
            val intent  = Intent(this, PhotoFrameActivity::class.java)
            imageUriList.forEachIndexed{idx, uri ->
                intent.putExtra("photo$idx", uri.toString())
            }
            intent.putExtra("photoListSize", imageUriList.size)

            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            this.requestCode -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    navigatePhoto()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {

            }
        }
    }

    private fun navigatePhoto() {
        // Action GET Content: 이 인텐트는 SAP를 실행시켜서 컨테츠를 가져오는 안드로ㅓ이드 내장 액티비티를 시작한다
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"     // 이미지만 필터링
        startActivityForResult(intent, 2000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when (requestCode) {
            2000 -> {
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    // 예외처리
                    if (imageUriList.size == 6) {
                        Toast.makeText(this, "이미 사진이 꽉 찼습니다.", Toast.LENGTH_SHORT).show()
                        return
                    }
                    imageUriList.add(selectedImageUri)
                    imageViewList[imageUriList.size - 1].setImageURI(selectedImageUri)

                } else {
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("전자액자 앱에서 사진을 불러오리 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            }
            .setNegativeButton("취소하기") { _, _ -> }
            .create()
            .show()

    }


}