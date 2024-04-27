package com.example.examples.retrofit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel @Inject constructor(
    private val commentsRepository: Lazy<CommentsRepository>
): ViewModel() {

    private var _comments: List<Comment> by mutableStateOf(listOf())
    val comments: List<Comment>
        get() = _comments

//    Uncomment the init when you want to load the data just after creation of Composable
//    init {
//        getCommentsFromBackend()
//    }

    fun getCommentsFromBackend() {
        viewModelScope.launch(Dispatchers.IO) {
            val comments = commentsRepository.get().getComments()

            withContext(Dispatchers.Main) {
                    _comments = comments
            }
        }
    }

}