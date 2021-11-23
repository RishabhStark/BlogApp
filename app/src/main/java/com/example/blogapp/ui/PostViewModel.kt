package com.example.blogapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.blogapp.Models.PostResponse
import com.example.blogapp.Repository.PostRepository
import com.example.blogapp.Utilities.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class PostViewModel(val postRepository: PostRepository):ViewModel() {
    val posts: MutableLiveData<Resource<PostResponse>> = MutableLiveData()
    var Page = 1

    var pResponse:PostResponse?=null
    init {
        getPosts()
    }
    fun getPosts() = viewModelScope.launch {
        posts.postValue(Resource.Loading())
        try{
        val response=postRepository.getPosts(Page)
        posts.postValue(handleResponse(response)) }
        catch (e:Exception) {
            //handle exception
        }

    }

    private fun handleResponse(response: Response<PostResponse>) : Resource<PostResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                Page++
                if(pResponse==null) {
                    pResponse=resultResponse
                }
                else {
                    val oldPosts =pResponse?.data
                    val newPosts= resultResponse.data
                    oldPosts?.addAll(newPosts)
                }

                return Resource.Success(pResponse?:resultResponse)
            }
        }
        return Resource.Error(response.message())
    }




}