package com.gayan.mvvmnewsapp.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.gayan.mvvmnewsapp.R
import com.gayan.mvvmnewsapp.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    val args: ArticleFragmentArgs by navArgs()

    private val TAG = "ArticleFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentArticleBinding.bind(view)
        val article = args.article
        binding.webView.apply{
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}