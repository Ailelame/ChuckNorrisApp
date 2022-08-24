package com.mondialrelay.chucknorrisapp.application.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mondialrelay.chucknorrisapp.application.ui.helpers.LiveDataWithParam
import com.mondialrelay.chucknorrisapp.domain.model.UserModel
import com.mondialrelay.chucknorrisapp.domain.port.api.UserApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class LoginViewModel(
    private val userApi: UserApi
) : ViewModel(), KoinComponent {

    // region -------- VM LIVEDATA AND ACTIONS

    val liveUser = LiveDataWithParam<UserModel, Unit>()

    val actionLogin = { user: String, password: String -> doLogin(user, password) }

    // endregion

    // region -------- PRIVATE

    private fun doLogin(user: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        userApi
            .login(user, password)
            .apply {
                liveUser.postValueOnly(this)
            }
    }

}