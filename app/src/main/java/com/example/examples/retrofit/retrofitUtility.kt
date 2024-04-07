package com.example.examples.retrofit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface CommentsApi {

    @GET("/comments")
    suspend fun getComments(): Response<List<Comment>>
} 


object RetrofitHelper {
    val baseUrl = "https://jsonplaceholder.typicode.com"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
}