package com.example.examples.retrofit

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CommentsRepository {
    suspend fun getComments(): List<Comment>
}

class CommentsRepositoryImpl @Inject constructor(
    private val commentsApi: CommentsApi
): CommentsRepository {

    override suspend fun getComments(): List<Comment> {
        return withContext(Dispatchers.IO){
            val response = commentsApi.getComments()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                response.body()!!
            } else {
                emptyList()
            }
        }
    }

}