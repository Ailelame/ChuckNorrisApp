package com.mondialrelay.chucknorrisapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel() : ViewModel() {

    private val _url = MutableLiveData<String>()
    val url: LiveData<String> = _url

    fun test() = viewModelScope.launch(Dispatchers.IO) {
        _url.postValue("https://images.theconversation.com/files/376100/original/file-20201221-15-12n8lgz.jpg?ixlib=rb-1.1.0&rect=104%2C150%2C863%2C573&q=45&auto=format&w=926&fit=clip")
    }


}