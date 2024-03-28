package com.example.examples.retrofit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.examples.ui.theme.ExamplesTheme

class CommentsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamplesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Comments()
                }
            }
        }
    }
}

@Composable
fun CommentCard(comment: Comment){
    Surface(
        shape = RoundedCornerShape(8.dp),
        tonalElevation = 4.dp,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ){
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ){
            Text(
                text = comment.name,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = comment.email,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = comment.body
            )
        }
    }
}


@Composable
fun Comments(
    commentsViewModel: CommentsViewModel = viewModel()
){
    if(commentsViewModel.comments.isEmpty()){
        Button(
            modifier = Modifier
                .wrapContentSize(),
            shape = RoundedCornerShape(8.dp),
            onClick = {
                commentsViewModel.getCommentsFromBackend()
            }
        ) {
            Text(
                text = "Get Comments",
                fontSize = 20.sp
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(commentsViewModel.comments) { comment ->
                CommentCard(comment = comment)
            }
        }
    }
}

@Preview
@Composable
fun PreviewComments(){
    Comments()
}