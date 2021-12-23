package com.example.myfastcampusstudy_android.intermediate.tinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myfastcampusstudy_android.databinding.ActivityLikeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LikeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikeBinding
    private var auth = FirebaseAuth.getInstance()
    private lateinit var usersDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usersDB = Firebase.database.reference.child("Users")
        val currentUserDB = usersDB.child(getCurrentUserId())
        currentUserDB.addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child("name").value == null){
                    showNameInputPopup()
                    return
                }
                // todo 유저정보를 갱신해라
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getCurrentUserId(): String {
        if (auth.currentUser == null) {
            Toast.makeText(this, "로그인이 되어있지 않습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        return auth.currentUser?.uid.orEmpty()
    }

    private fun showNameInputPopup(){
        var editText = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("이름을 입력해주세요")
            .setView(editText)
            .setPositiveButton("확인"){ _, _, ->
                if (editText.text.isEmpty()){
                    showNameInputPopup()
                }else{
                    saveUserName(editText.text.toString())
                }


            }
            .setCancelable(false)
            .show()
    }

    private fun saveUserName(name: String){
        val userId = getCurrentUserId()
        val user = mutableMapOf<String, Any>()
        user["userId"] = userId
        user["name"] = name
        usersDB.child(userId).updateChildren(user)
    }
}