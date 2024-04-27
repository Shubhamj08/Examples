package com.example.examples.base

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.examples.notification.NotificationActivity
import com.example.examples.persistence.PersistenceActivity
import com.example.examples.retrofit.CommentsActivity
import com.example.examples.searchable_list.SearchableListActivity
import com.example.examples.ui.theme.ExamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExamplesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Activities(){ index ->
                        openActivity(index)
                    }
                }
            }
        }
    }

    fun openActivity(index: Int) {
        val intent: Intent? = when(index){
            0 -> Intent(this, SearchableListActivity::class.java)
            1 -> Intent(this, CommentsActivity::class.java)
            2 -> Intent(this, PersistenceActivity::class.java)
            3 -> Intent(this, NotificationActivity::class.java)
            else -> null
        }

        if(intent != null){
            intent.let {
                it.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(it)
            }
        } else {
            Toast.makeText(this, "Unknown Button", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Activities(
    openActivity: (Int) -> Unit
) {

    val buttons = listOf("Searchable List", "Retrofit", "Persistence", "Notification")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(8.dp),
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.Start,
            content = {
                items(buttons.size) { index ->
                    Button(
                        onClick = { openActivity(index) },
                        modifier = Modifier
                            .padding(4.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(text = buttons[index])
                    }
                }

            }
        )
    }
}