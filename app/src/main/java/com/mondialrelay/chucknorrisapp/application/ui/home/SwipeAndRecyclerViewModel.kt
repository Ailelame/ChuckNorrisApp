package com.mondialrelay.chucknorrisapp.application.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondialrelay.chucknorrisapp.application.ui.helpers.LiveDataWithParam
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SwipeAndRecyclerViewModel : ViewModel() {

    // region -------- DOMAIN APIs

    lateinit var jokeApi: JokeApi

    // endregion

    // region -------- VM PROPERTIES AND ACTIONS

    val liveJokesList = LiveDataWithParam<MutableList<JokeModel>, Int>()

    val actionRefresh = { doFetchJoke() }
    val actionRate = { rating: Float -> doRate(rating) }
    val actionClear = { doClear() }

    // endregion

    // region -------- PRIVATE

    private val jokesList = mutableListOf<JokeModel>()

    private fun doFetchJoke() = viewModelScope.launch(Dispatchers.IO) {
        jokeApi.fetch().apply {
            jokesList.add(0, this)
            liveJokesList.postValueAndParam(jokesList, 0)
        }
    }

    private fun doRate(rating: Float) = viewModelScope.launch(Dispatchers.IO) {
    }

    private fun doClear() = viewModelScope.launch(Dispatchers.IO) {
        jokesList.clear()
        liveJokesList.postParamOnly(0)
    }

    // endregion

}

