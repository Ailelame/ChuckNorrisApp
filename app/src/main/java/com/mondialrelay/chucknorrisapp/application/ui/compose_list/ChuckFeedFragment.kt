package com.mondialrelay.chucknorrisapp.application.ui.compose_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.jetdev.codelab_basic_principles.ui.theme.Chartreuse
import com.jetdev.codelab_basic_principles.ui.theme.ChuckNorris_Theme
import com.mondialrelay.chucknorrisapp.R
import com.mondialrelay.chucknorrisapp.application.ui.home.SwipeAndRecyclerViewModel
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import org.koin.android.ext.android.inject

class ChuckFeedFragment : Fragment() {

    private val viewModel: SwipeAndRecyclerViewModel by inject()

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


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CheckFeedScreen(
    viewModel: SwipeAndRecyclerViewModel
) {

    val jokeList by viewModel.liveJokesList.observeAsState()
    ChuckNorris_Theme {
        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.actionAddJoke() }) {
                Icon(Icons.Filled.Add, "")
            }
        }, content = {
            Column(
                Modifier
                    .background(Chartreuse)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(it)
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                ) {
                    items(items = jokeList ?: listOf()) { joke ->
                        JokeCard(joke)
                    }
                }
            }
        })
    }
}


@Preview("JokeCard")
@Composable
fun JokeCard(@PreviewParameter(PreviewJokeModelProvider::class) joke: JokeModel?) {
    Card(modifier = Modifier.background(Color.White)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            JokeCardImageHeader(joke = joke)
            Column() {
                Row {
                    joke?.text?.let {
                        JokeCardContentText(it)
                    }
                }

                joke?.createdAt?.let {
                    Text(it, modifier = Modifier.padding(start = 16.dp))
                }
            }


        }
    }

}


@Composable
fun JokeCardContentText(content: String) {
    Text(
        text = content, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 16.dp), textAlign = TextAlign.Center
    )
}

@Preview(name = "Card Header")
@Composable
fun JokeCardImageHeader(joke: JokeModel?) {
    Box() {
        Image(
            painter = painterResource(R.drawable.ic_chuck),
            contentDescription = "itz chuck",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
    }
}

class PreviewJokeModelProvider : PreviewParameterProvider<JokeModel> {
    override val values: Sequence<JokeModel>
        get() = sequenceOf(
            JokeModel(
                "test",
                5.0f,
                "created",
                "id"
            ),
            JokeModel(
                "2nd test",
                0.0f,
                "uncreated",
                "id2"
            )

        )
}