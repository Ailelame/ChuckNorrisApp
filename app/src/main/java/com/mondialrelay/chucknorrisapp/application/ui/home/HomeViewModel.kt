package com.mondialrelay.chucknorrisapp.application.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondialrelay.chucknorrisapp.domain.port.api.JokeApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    lateinit var jokeApi: JokeApi

    val jokeText = MutableLiveData<String>()

    val progressBar = MutableLiveData<Boolean>()

    fun fireFetchButton() = doFetchJoke()

    private fun doFetchJoke() = viewModelScope.launch(Dispatchers.IO) {
        progressBar.postValue(true)
        jokeApi.fetch().apply {
            delay(500)
            jokeText.postValue(text)
        }
    }

}