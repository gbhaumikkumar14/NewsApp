package com.example.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.presentation.MainActivity
import com.example.newsapp.R
import com.example.newsapp.data.util.Resource
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.presentation.adapter.NewsAdapter
import com.example.newsapp.presentation.viewmodel.NewsViewModel

class NewsFragment : Fragment() {
    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val country = "in"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            Log.d(TAG, "onViewCreated: article clicked")
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(
                R.id.action_newsFragment_to_infoFragment,
                bundle
            )
        }
        setupSearchView()
        initRecyclerView()
        viewNewsList()
    }

    private fun setupSearchView() {
        Log.d(TAG, "setupSearchView: ")
        binding.newsSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(TAG, "onQueryTextChange: $newText")
                newText?.let {
                    viewModel.getSearchedNewsHeadlines(country, page, newText)
                }
                return false
            }
        })

        binding.newsSearch.setOnCloseListener {
            Log.d(TAG, "setupSearchView: closeClicked")
            page = 1
            viewModel.getNewsHeadlines(country, page)
            false
        }
    }

    private fun viewNewsList() {
        Log.d(TAG, "viewNewsList: ")
        viewModel.getNewsHeadlines(country, page)
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    Log.d(TAG, "viewNewsList: SUCCESS")
                    handleProgressBar(false)
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles.toList())
                        pages = if(it.totalResults%20 == 0) {
                            it.totalResults / 20
                        }else{
                            it.totalResults/20 + 1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Loading -> {
                    Log.d(TAG, "viewNewsList: LOADING")
                    handleProgressBar(true)
                }
                is Resource.Error -> {
                    Log.d(TAG, "viewNewsList: ERROR")
                    handleProgressBar(false)
                    response.message?.let {
                        Toast.makeText(activity, "An Error Occurred: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: ")
        binding.newsRecyclerview.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(onScrollListener)
        }
    }

    private fun handleProgressBar(show: Boolean){
        if(show){
            isLoading = true
            binding.newsProgressBar.visibility = View.VISIBLE
        }else{
            isLoading = false
            binding.newsProgressBar.visibility = View.INVISIBLE
        }
    }

    private val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = binding.newsRecyclerview.layoutManager as LinearLayoutManager
            val sizeOfCurrentList = layoutManager.itemCount
            val topPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItems = layoutManager.childCount

            val hasReachEnd = topPosition+visibleItems >= sizeOfCurrentList
            val shouldPaginate = !isLoading && !isLastPage && hasReachEnd && isScrolling
            if(shouldPaginate){
                page++
                viewModel.getNewsHeadlines(country, page)
                isScrolling = false
            }
        }
    }

    companion object{
        const val TAG = "NewsFragment"
    }
}