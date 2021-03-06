package com.example.myfastcampusstudy_android.intermediate.used_transaction.home

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.myfastcampusstudy_android.databinding.ActivityAddArticleBinding
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.DB_ARTICLES
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.DB_ARTICLE_PHOTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class AddArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddArticleBinding

    private var selectedUri: Uri? = null
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }

    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        initAddImageButton()
        initSubmitButton()
    }

    private fun initSubmitButton() {
        binding.apply {
            btnSubmit.setOnClickListener {
                val title = etTitle.text.toString()
                val price = etPrice.text.toString()
                val sellerId = auth.currentUser?.uid.orEmpty()

                showProgressBar()
                // ???????????? ????????? ????????? ????????? ??????
                if (selectedUri != null) {
                    val photoUri = selectedUri ?: return@setOnClickListener
                    uploadPhoto(photoUri,
                        successHandler = { url ->
                            uploadArticle(sellerId, title, price, url)
                        },
                        errorHandler = {
                            Toast.makeText(
                                this@AddArticleActivity,
                                "?????? ???????????? ??????????????????.",
                                Toast.LENGTH_SHORT
                            ).show()
                            hideProgressBar()
                        }
                    )
                }else{
                    uploadArticle(sellerId, title, price, "")
                }
            }
        }
    }

    private fun uploadPhoto(uri: Uri, successHandler: (String) -> Unit, errorHandler: () -> Unit) {
        val fileName = "${System.currentTimeMillis()}.png"
        storage.reference.child(DB_ARTICLE_PHOTO).child(fileName)
            .putFile(uri)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    storage.reference.child(DB_ARTICLE_PHOTO).child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { url ->
                            successHandler(url.toString())
                        }.addOnFailureListener {
                            errorHandler()
                        }
                }else{
                    errorHandler()
                }
            }
    }

    private fun uploadArticle(sellerId: String, title: String, price: String, imageUrl: String){
        val model =
            ArticleModel(sellerId, title, System.currentTimeMillis(), "$price ???", imageUrl)
        articleDB.push().setValue(model)
        hideProgressBar()
        finish()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initAddImageButton() {
        binding.btnAddImage.apply {
            setOnClickListener {
                when {
                    ContextCompat.checkSelfPermission(
                        this@AddArticleActivity,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED -> {
                        startContentProvider()
                    }

                    shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                        showPermissionContextPopup()
                    }

                    else -> {
                        requestPermissions(
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            1010
                        )
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1010 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startContentProvider()
                } else {
                    Toast.makeText(this, "????????? ?????????????????????.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun startContentProvider() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2020)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != RESULT_OK)
            return

        when (requestCode) {
            2020 -> {
                val imageUri = data?.data
                if (imageUri != null) {
                    binding.ivPhoto.setImageURI(imageUri)
                    selectedUri = imageUri
                } else {
                    Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "????????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("????????? ???????????????.")
            .setMessage("????????? ???????????? ?????? ????????? ???????????????.")
            .setPositiveButton("??????") { _, _ ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1010)
            }
            .create()
            .show()
    }

    private fun showProgressBar(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
            progressBarBackground.visibility = View.VISIBLE
        }
    }

    private fun hideProgressBar(){
        binding.apply {
            progressBar.visibility = View.GONE
            progressBarBackground.visibility = View.GONE
        }
    }
}