package com.example.examples.retrofit

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentsViewModel: ViewModel() {

    private val TAG = "Comments Viewmodel"

    private var _comments: List<Comment> by mutableStateOf(listOf())
    val comments: List<Comment>
        get() = _comments

//    Uncomment the init when you want to load the data just after creation of Composable
//    init {
//        getCommentsFromBackend()
//    }

    fun getCommentsFromBackend() {
        val commentsApi = RetrofitHelper.getInstance().create(CommentsApi::class.java)
        viewModelScope.launch(Dispatchers.IO) {
            val response = commentsApi.getComments()

            withContext(Dispatchers.Main) {
                if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                    _comments = response.body()!!
                    Log.d(TAG, comments.size.toString())
                }
            }
        }
    }

}