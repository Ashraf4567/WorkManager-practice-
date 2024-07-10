package com.example.workmanagerpractice

import retrofit2.Response
import retrofit2.http.GET

interface WebServices {

    @GET("posts/1")
    suspend fun getPost(): Response<Post>
}