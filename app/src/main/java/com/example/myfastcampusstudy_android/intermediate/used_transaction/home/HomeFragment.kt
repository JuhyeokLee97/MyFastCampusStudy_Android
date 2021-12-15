package com.example.myfastcampusstudy_android.intermediate.used_transaction.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfastcampusstudy_android.R
import com.example.myfastcampusstudy_android.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null
    private lateinit var articleAdapter: ArticleAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // onViewCreated 에서 null 체크를 하지 않기 위해서 지역변수 fragmentHomeBinding 사용
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        binding = fragmentHomeBinding

        fragmentHomeBinding.articleRecyclerView.layoutManager = LinearLayoutManager(context)

        articleAdapter = ArticleAdapter()
        articleAdapter.submitList(mutableListOf<ArticleModel>().apply {
            add(ArticleModel("0", "aaaa", 100000, "5000원", ""))
            add(ArticleModel("0", "bbbb", 100000, "10000원", ""))
        })
        fragmentHomeBinding.articleRecyclerView.adapter = articleAdapter
    }
}