package com.mondialrelay.chucknorrisapp.application.ui.home

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class SwipeAndRecyclerViewModel(
    private val jokeApi: JokeApi
) : ViewModel(), KoinComponent {

    // region -------- VM LIVEDATA AND ACTIONS

    private val _liveJokesList = MutableLiveData<SnapshotStateList<JokeModel>>() // Here using SnapshotStateList because mutableStateList not recomposing the view
    val liveJokesList: LiveData<SnapshotStateList<JokeModel>> = _liveJokesList

    val actionAddJoke = { doFetchJoke() }
    val actionRate = { id: String, rating: Float -> doRate(id, rating) }
    val actionClear = { doClear() }


    // endregion

    // region -------- PRIVATE

    private val jokesList = SnapshotStateList<JokeModel>()

    private fun doFetchJoke() = viewModelScope.launch(Dispatchers.IO) {
        jokeApi
            .fetch()
            .apply {
                jokesList.add(0, this)
                _liveJokesList.postValue(jokesList)
            }
    }

    private fun doRate(id: String, rating: Float) {
        jokesList.find { it.id == id }?.rating = rating
        _liveJokesList.value = jokesList
    }

    private fun doClear() = viewModelScope.launch(Dispatchers.IO) {
        jokesList.clear()
        _liveJokesList.postValue(jokesList)
    }

    // endregion

}

