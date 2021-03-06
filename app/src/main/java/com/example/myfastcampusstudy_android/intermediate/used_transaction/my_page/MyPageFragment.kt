package com.example.myfastcampusstudy_android.intermediate.used_transaction.my_page


import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentMyPageBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment(R.layout.fragment_my_page) {

    private lateinit var binding: FragmentMyPageBinding
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPageBinding.bind(view)
        Log.d("MyPageFragment", "onViewCreated: auth.currentUser: ${auth.currentUser} ")
        initViews()
    }

    private fun initViews() {
        initEmailEditText()
        initPasswordEditText()
        initSignInOutButton()
        initSignUpButton()
    }

    private fun initEmailEditText() {
        binding.apply {
            etEmail.addTextChangedListener {
                val enable = etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
                btnSignInOut.isEnabled = enable
                btnSignUp.isEnabled = enable
            }
        }
    }

    private fun initPasswordEditText() {
        binding.apply {
            etPassword.addTextChangedListener {
                val enable = etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
                btnSignInOut.isEnabled = enable
                btnSignUp.isEnabled = enable
            }
        }
    }

    private fun initSignInOutButton() {
        binding.apply {
            btnSignInOut.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                Log.d("MyPageFragment", "auth.currentUser: ${auth.currentUser} ")
                if (auth.currentUser == null) {
                    // ?????????
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity()) { task ->
                            if (task.isSuccessful) {
                                successSignIn()
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    "???????????? ??????????????????. ????????? ?????? ??????????????? ??????????????????.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    // ????????????
                    auth.signOut()

                    etEmail.text.clear()
                    etEmail.isEnabled = true

                    etPassword.text.clear()
                    etPassword.isEnabled = true

                    btnSignInOut.text = "?????????"
                    btnSignInOut.isEnabled = false
                    btnSignUp.isEnabled = true

                }
            }
        }
    }

    private fun initSignUpButton() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {
                            successSignIn()
                            Toast.makeText(context, "??????????????? ????????????, ????????? ???????????????.", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                context,
                                "??????????????? ??????????????????. ?????? ????????? ???????????? ??? ????????????.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    private fun successSignIn() {
        if (auth.currentUser == null) {
            // ????????????
            Toast.makeText(context, "???????????? ??????????????????. ?????? ??????????????????.", Toast.LENGTH_SHORT).show()
            return
        }

        binding.apply {
            etEmail.isEnabled = false
            etPassword.isEnabled = false
            btnSignUp.isEnabled = false
            btnSignInOut.text = "????????????"
        }
    }

    override fun onStart() {
        super.onStart()
        // ????????? ????????????
        if (auth.currentUser == null) {
            binding.apply {
                etEmail.text.clear()
                etEmail.isEnabled = true

                etPassword.text.clear()
                etPassword.isEnabled = true

                btnSignInOut.text = "?????????"
                btnSignInOut.isEnabled = true
                btnSignUp.isEnabled = false
            }
        } else {
            binding.apply {
                etEmail.setText(auth.currentUser!!.email)
                etEmail.isEnabled = false

                etPassword.setText("**************")
                etPassword.isEnabled = false

                btnSignInOut.text = "????????????"
                btnSignInOut.isEnabled = true
                btnSignUp.isEnabled = false
            }

        }
    }

}