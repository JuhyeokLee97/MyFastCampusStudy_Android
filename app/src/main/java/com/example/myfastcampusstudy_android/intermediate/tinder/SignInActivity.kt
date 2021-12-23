package com.example.myfastcampusstudy_android.intermediate.tinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.ActivitySignInBinding
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        callbackManager = CallbackManager.Factory.create()
        initViews()
    }

    private fun initViews() {
        initSignInButton()
        initSignUpButton()
        initFacebookSignInButton()
    }


    private fun initSignInButton() {
        binding.btnSignIn.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        handleSuccessLogin()
                    } else {
                        Toast.makeText(
                            this,
                            "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    private fun initSignUpButton() {
        binding.btnSignUp.setOnClickListener {
            val email = getInputEmail()
            val password = getInputPassword()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "회원가입에 성공했습니다. 로그인 버튼을 눌러 로그인해주세요.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(this, "이미 가입한 이메일이거나, 회원가입에 실패했습니다.", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

    }

    private fun initFacebookSignInButton(){

        binding.btnFacebookSignIn.apply {
            setReadPermissions("email", "public_profile")
            registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
                override fun onSuccess(result: LoginResult) {
                    val credential = FacebookAuthProvider.getCredential(result.accessToken.token)
                    auth.signInWithCredential(credential)
                        .addOnCompleteListener(this@SignInActivity){ task ->
                            if (task.isSuccessful){
                                handleSuccessLogin()
                            }else{
                                Toast.makeText(this@SignInActivity, "페이스북 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                }

                override fun onCancel() {
                }

                override fun onError(error: FacebookException?) {
                    Toast.makeText(this@SignInActivity, "페이스북 로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    private fun getInputEmail(): String = binding.etEmail.text.toString()
    private fun getInputPassword(): String = binding.etPassword.text.toString()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleSuccessLogin(){
        if (auth.currentUser == null){
            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val userId = auth.currentUser?.uid.orEmpty()
        val currentUserDB = Firebase.database.reference.child("Users").child(userId)
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        currentUserDB.updateChildren(user)
        finish()
    }

}