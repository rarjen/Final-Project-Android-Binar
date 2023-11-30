package com.example.finalprojectbinar.di

import com.example.finalprojectbinar.api.APIClient
import com.example.finalprojectbinar.repository.MyRepository
import com.example.finalprojectbinar.viewmodel.MyViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object KoinModule {
    val dataModule
        get() = module {
            single { APIClient.instance }

            factory { MyRepository() }
        }

    val uiModule
        get() = module {
            viewModel { MyViewModel(get()) }
        }
}