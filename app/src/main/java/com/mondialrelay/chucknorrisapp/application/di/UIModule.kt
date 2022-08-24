package com.mondialrelay.chucknorrisapp.application.di

import com.mondialrelay.chucknorrisapp.application.ui.home.SwipeAndRecyclerViewModel
import com.mondialrelay.chucknorrisapp.application.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiModule = module {

    viewModel { LoginViewModel(userApi = get()) }

    viewModel { SwipeAndRecyclerViewModel(jokeApi = get()) }

}