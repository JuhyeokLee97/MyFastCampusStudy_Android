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
        // onViewCreated를 통해서 새롭게 View들을 그려주지만, articleList는 기존에 생성되어 있던 객체이기 떄문에
        // 기존에 가지고 있던 아이템들을 가지고 있어서, `articleDB`에서 데이터를 가져오게 되면 중복이 발생한다.
        // 그래서 `articleList`를 초기화 해주어야 한다.
        articleList.clear()
        /**
         * Add a listener for child events occurring at this location.
         * When child locations are added, removed, changed, or moved,
         * the listener will be triggered for the appropriate event
         */
        articleDB.addChildEventListener(listener)

        articleAdapter = ArticleAdapter(onItemClicked = { articleModel ->
            if (auth.currentUser != null) { // 로그인 상태

                if (auth.currentUser!!.uid != articleModel.sellerId) { // 구매자와 판매자가 다른 경우
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
                        "채팅방이 생성되었습니다. 채팅탭에서 확인해주세요.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else { // 구매자와 판매자가 같은 경우
                    Snackbar.make(binding.root, "내가 올린 아이템입니다.", Snackbar.LENGTH_SHORT).show()
                }
            } else { // 로그인이 안된 상태
                Snackbar.make(binding.root, "로그인 후 사용해주세요.", Snackbar.LENGTH_SHORT).show()

            }

        })
        binding.articleRecyclerView.adapter = articleAdapter

        binding.btnAddArticle.setOnClickListener {
            if (auth.currentUser != null) {
                val intent = Intent(requireContext(), AddArticleActivity::class.java)
                startActivity(intent)
            } else {
                Snackbar.make(binding.root, "로그인 후 사용해주세요.", Snackbar.LENGTH_SHORT).show()
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