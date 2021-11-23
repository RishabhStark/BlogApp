package com.example.blogapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blogapp.Adapters.PostAdapter
import com.example.blogapp.R
import com.example.blogapp.Repository.PostRepository
import com.example.blogapp.Utilities.Constants.Companion.QUERY_PAGE_SIZE
import com.example.blogapp.Utilities.Resource
import com.example.blogapp.ui.PostViewModel
import com.example.blogapp.ui.PostViewModelFactory

class HFragment : Fragment(R.layout.fragment_h) {

    lateinit var rv: RecyclerView

    lateinit var  viewModel: PostViewModel

    lateinit var postAdapter: PostAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        val Repository = PostRepository()
        val vmProviderFactory = PostViewModelFactory(Repository)
        viewModel = ViewModelProvider(this,
            vmProviderFactory).get(PostViewModel::class.java)

        rv=view.findViewById<RecyclerView>(R.id.blogrv);
        setupRecyclerView(rv)
        viewModel.posts.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {

                    response.data?.let { postResponse ->
                        postAdapter.differ.submitList(postResponse.data.toList())
                        val totalPages=postResponse.meta.pagination.pages
                        isLastPage=viewModel.Page==totalPages

                    }
                }
                is Resource.Error -> {

                    response.message?.let { message ->
                        Log.e("Error", "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    // can  show progress bar
                }
            }
        })



    }


    var isLastPage = false
    var isScrolling = false

    val scrollListner=object:RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val NotLastPage = !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate =NotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getPosts()
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

    }



    private fun setupRecyclerView(rv: RecyclerView) {
        postAdapter = PostAdapter()
        rv.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@HFragment.scrollListner)
        }
    }
}