package com.mondialrelay.chucknorrisapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondialrelay.chucknorrisapp.manager.ChuckNorrisManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(private val manager : ChuckNorrisManager) : ViewModel() {

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    fun test() = viewModelScope.launch(Dispatchers.IO) {
        val joke = manager.getJoke()
        _url.postValue(joke.value)
    }




}