package com.devv.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devv.newsmvvm.R
import com.devv.newsmvvm.adapters.NewsAdapters
import com.devv.newsmvvm.databinding.FragmentBreakingNewsBinding
import com.devv.newsmvvm.ui.NewsActivity
import com.devv.newsmvvm.ui.NewsViewModel
import com.devv.newsmvvm.util.Constants.Companion.QUERY_PAGE_SIZE
import com.devv.newsmvvm.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var binding: FragmentBreakingNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapters

    val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBreakingNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
                bundle
            )
        }

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressbar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLoading = viewModel.breakingNewsPage == totalPages
                        if (isLastPage) {
                        binding.rvBreakingNews.setPadding(0, 0, 0, 0)
                    }
                    }
                }
                is Resource.Error -> {
                    hideProgressbar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressbar()
                }
            }

        })
    }

    private fun hideProgressbar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressbar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = false
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndMotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndMotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if (shouldPaginate) {
                viewModel.getBreakingNews("ru")
                isScrolling = false
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapters()
        binding.rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}