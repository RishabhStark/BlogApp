package com.example.blogapp.Models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Posts(

    var id:Int,
    val body: String,
    val title: String,

)