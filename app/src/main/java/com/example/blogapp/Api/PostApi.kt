package com.example.blogapp.Api

import com.example.blogapp.Models.PostResponse
import com.example.blogapp.Models.Posts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("public/v1/posts")
    suspend fun getPosts(@Query("page") pageNumber:Int=1):Response<PostResponse>
}