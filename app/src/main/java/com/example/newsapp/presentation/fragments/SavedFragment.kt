package com.example.newsapp.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.presentation.MainActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSavedBinding
import com.example.newsapp.presentation.adapter.NewsAdapter
import com.example.newsapp.presentation.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class SavedFragment : Fragment() {
    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var savedNewsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: ")
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        savedNewsAdapter = (activity as MainActivity).newsAdapter
        savedNewsAdapter.setOnItemClickListener {
            Log.d(TAG, "onViewCreated: article Clicked")
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
                putBoolean("isSaved", true)
            }
            findNavController().navigate(
                R.id.action_savedFragment_to_infoFragment,
                bundle
            )
        }

        initRecyclerView()
        viewNewsList()

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.savedNewsRecyclerview)
        }
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val article = savedNewsAdapter.differ.currentList[position]
            Log.d(TAG, "onSwiped: pos: $position")
            viewModel.deleteNewsFromDatabase(article)
            view?.let {
                Snackbar.make(view!!, R.string.message_deleted, Snackbar.LENGTH_SHORT)
                    .apply {
                        setAction(R.string.action_undo) {
                            Log.d(TAG, "onSwiped: clicked UNDO")
                            viewModel.saveNewsToDatabase(article)
                            Snackbar.make(
                                view,
                                R.string.message_reverted,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }
                    }.show()
            }
        }
    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView: ")
        binding.savedNewsRecyclerview.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(
                activity, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun viewNewsList() {
        Log.d(TAG, "viewNewsList: ")
        viewModel.getSavedNews().observe(viewLifecycleOwner) {
            savedNewsAdapter.differ.submitList(it)
        }
    }

    companion object{
        const val TAG = "SavedFragment"
    }
}