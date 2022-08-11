package com.mondialrelay.chucknorrisapp.di

import com.mondialrelay.chucknorrisapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel{
        HomeViewModel(get())
    }
}