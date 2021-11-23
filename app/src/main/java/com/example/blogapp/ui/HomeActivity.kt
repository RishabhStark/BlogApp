package com.example.blogapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider

import com.example.blogapp.R
import com.example.blogapp.Repository.PostRepository
import com.example.blogapp.ui.fragments.HFragment


class HomeActivity : AppCompatActivity() {
    lateinit var viewModel: PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val postRepository = PostRepository()
        val viewmodelFactory = PostViewModelFactory(postRepository)

        viewModel = ViewModelProvider(this, viewmodelFactory).get(PostViewModel::class.java)

    }}


