package com.devv.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.devv.newsmvvm.R
import com.devv.newsmvvm.databinding.FragmentArticleBinding
import com.devv.newsmvvm.ui.NewsActivity
import com.devv.newsmvvm.ui.NewsViewModel
import com.google.android.material.snackbar.Snackbar


class ArticleFragment: Fragment(R.layout.fragment_article) {

    private lateinit var binding : FragmentArticleBinding
    lateinit var viewModel : NewsViewModel
    val args: ArticleFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) } //loadUrl(article.url)
        }
        binding.fab.setOnClickListener{
            viewModel.saveArticle(article)
            Snackbar.make(view, "Saved", Snackbar.LENGTH_SHORT).show()
        }
    }
}