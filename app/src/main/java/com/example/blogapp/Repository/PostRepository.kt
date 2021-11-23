package com.example.blogapp.Repository

import androidx.lifecycle.MutableLiveData
import com.example.blogapp.Api.RetrofitInstance

import com.example.blogapp.Models.PostResponse
import com.example.blogapp.Models.Posts
import com.example.blogapp.Utilities.Resource

class PostRepository {
    suspend fun getPosts(page:Int)=RetrofitInstance.api.getPosts(page)

}