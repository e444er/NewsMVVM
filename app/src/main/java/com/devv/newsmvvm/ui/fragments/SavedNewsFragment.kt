package com.devv.newsmvvm.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devv.newsmvvm.R
import com.devv.newsmvvm.ui.NewsActivity
import com.devv.newsmvvm.ui.NewsViewModel

class SavedNewsFragment: Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel : NewsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).viewModel
    }
}