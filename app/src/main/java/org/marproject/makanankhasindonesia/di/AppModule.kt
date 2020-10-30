package org.marproject.makanankhasindonesia.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodInteractor
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase
import org.marproject.makanankhasindonesia.ui.detail.DetailFoodViewModel
import org.marproject.makanankhasindonesia.ui.home.HomeViewModel
import org.marproject.makanankhasindonesia.ui.search.SearchViewModel

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { DetailFoodViewModel(get()) }
}