package com.devv.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devv.newsmvvm.R
import com.devv.newsmvvm.adapters.NewsAdapters
import com.devv.newsmvvm.ui.NewsActivity
import com.devv.newsmvvm.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*
import kotlinx.android.synthetic.main.fragment_search_news.*

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel : NewsViewModel
    lateinit var newsAdapter: NewsAdapters

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapters()
        rvSavedNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}