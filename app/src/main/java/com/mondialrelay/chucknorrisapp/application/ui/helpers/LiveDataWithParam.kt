package com.mondialrelay.chucknorrisapp.application.ui.helpers

import androidx.lifecycle.MutableLiveData

class LiveDataWithParam<T, TP> : MutableLiveData<Pair<T?, TP?>>() {

    fun postValueAndParam(value: T, param: TP?) {
        postValue(Pair(value, param))
    }

    fun postValueOnly(value: T) = postValue(Pair(value, null))

    fun postParamOnly(param: TP) = postValue(Pair(null, param))

}