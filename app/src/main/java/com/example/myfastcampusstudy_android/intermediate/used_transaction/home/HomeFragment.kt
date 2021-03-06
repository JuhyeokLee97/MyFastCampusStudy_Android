package com.example.myfastcampusstudy_android.intermediate.used_transaction.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentHomeBinding
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.CHILD_CHAT
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.DB_ARTICLES
import com.example.myfastcampusstudy_android.intermediate.DBKey.Companion.DB_USERS
import com.example.myfastcampusstudy_android.intermediate.used_transaction.chat_list.ChatItemModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var articleDB: DatabaseReference
    private lateinit var userDB: DatabaseReference
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var binding: FragmentHomeBinding

    private val articleList = mutableListOf<ArticleModel>()
    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val articleModel = snapshot.getValue(ArticleModel::class.java)
            articleModel ?: return

            articleList.add(articleModel)
            articleAdapter.submitList(articleList)
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onChildRemoved(snapshot: DataSnapshot) {}
        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
        override fun onCancelled(error: DatabaseError) {}
    }

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        userDB = Firebase.database.reference.child(DB_USERS)
        articleDB = Firebase.database.reference.child(DB_ARTICLES)
        // onViewCreated??? ????????? ????????? View?????? ???????????????, articleList??? ????????? ???????????? ?????? ???????????? ?????????
        // ????????? ????????? ?????? ??????????????? ????????? ?????????, `articleDB`?????? ???????????? ???????????? ?????? ????????? ????????????.
        // ????????? `articleList`??? ????????? ???????????? ??????.
        articleList.clear()
        /**
         * Add a listener for child events occurring at this location.
         * When child locations are added, removed, changed, or moved,
         * the listener will be triggered for the appropriate event
         */
        articleDB.addChildEventListener(listener)

        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            if (auth.currentUser != null) { // ????????? ??????

                if (auth.currentUser!!.uid != articleModel.sellerId) { // ???????????? ???????????? ?????? ??????
                    val chatRoom = ChatItemModel(
                        buyerId = auth.currentUser!!.uid,
                        sellerId = articleModel.sellerId,
                        itemTitle = articleModel.title,
                        key = System.currentTimeMillis()
                    )

                    userDB.child(auth.currentUser!!.uid)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    userDB.child(articleModel.sellerId)
                        .child(CHILD_CHAT)
                        .push()
                        .setValue(chatRoom)

                    Snackbar.make(
                        binding.root,
                        "???????????? ?????????????????????. ??????????????? ??????????????????.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else { // ???????????? ???????????? ?????? ??????
                    Snackbar.make(binding.root, "?????? ?????? ??????????????????.", Snackbar.LENGTH_SHORT).show()
                }
            } else { // ???????????? ?????? ??????
                Snackbar.make(binding.root, "????????? ??? ??????????????????.", Snackbar.LENGTH_SHORT).show()

            }

        })
        binding.articleRecyclerView.adapter = articleAdapter

        binding.btnAddArticle.setOnClickListener {
            if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "????????? ??? ??????????????????.", Snackbar.LENGTH_SHORT).show()
            }
        }

    }


    override fun onResume() {
        super.onResume()

        articleAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        articleDB.removeEventListener(listener)
    }
}