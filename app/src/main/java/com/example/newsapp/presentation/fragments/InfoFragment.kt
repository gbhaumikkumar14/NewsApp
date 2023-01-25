package com.example.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsapp.presentation.MainActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentInfoBinding
import com.example.newsapp.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding : FragmentInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        val args : InfoFragmentArgs by navArgs()
        val article = args.selectedArticle

        if(args.isSaved){
            Log.d(TAG, "onViewCreated: already saved item")
            binding.saveButton.visibility = View.GONE
        }

        binding.newsWebview.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                if (article.url != "") {
                    loadUrl(article.url)
                }
            }
        }

        binding.saveButton.setOnClickListener {
            Log.d(TAG, "onViewCreated: saveButton clicked")
            viewModel.saveNewsToDatabase(article)
            Snackbar.make(view, R.string.message_saved, Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val TAG = "InfoFragment"
    }
}