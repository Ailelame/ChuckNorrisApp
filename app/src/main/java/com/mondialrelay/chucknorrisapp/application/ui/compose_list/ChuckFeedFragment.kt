package com.mondialrelay.chucknorrisapp.application.ui.compose_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.jetdev.codelab_basic_principles.ui.theme.ChuckNorris_Theme
import com.mondialrelay.chucknorrisapp.application.ui.home.SwipeAndRecyclerViewModel
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import org.koin.android.ext.android.inject

class ChuckFeedFragment : Fragment() {

    private val viewModel : SwipeAndRecyclerViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                CheckFeedScreen(viewModel)
            }
        }
    }
}


@Composable
fun CheckFeedScreen(
    viewModel: SwipeAndRecyclerViewModel
) {

    val jokeList by viewModel.liveJokesList.observeAsState()
    ChuckNorris_Theme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                LazyColumn(Modifier.weight(1f)) {
                    items(items = jokeList ?: listOf()) { joke ->
                        JokeCard(joke)
                    }
                }
                Button(onClick = {viewModel.actionAddJoke()} ) {
                    Text("Hello")
                }
            }
        }
    }
}




@Composable
fun JokeCard(joke: JokeModel?) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row {
            joke?.text?.let {
                Text(it)
            }
        }
        Row {
            joke?.createdAt?.let {
                Text(it)
            }
        }
    }
}