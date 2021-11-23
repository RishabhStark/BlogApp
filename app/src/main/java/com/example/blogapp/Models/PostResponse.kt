package com.example.blogapp.Models

data class PostResponse(
    val data: MutableList<Posts>,
    val meta: Meta
)