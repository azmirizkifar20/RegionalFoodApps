package org.marproject.makanankhasindonesia.di

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodInteractor
import org.marproject.makanankhasindonesia.core.domain.usecase.FoodUseCase
import org.marproject.makanankhasindonesia.detail.DetailFoodViewModel
import org.marproject.makanankhasindonesia.favorite.FavoriteViewModel
import org.marproject.makanankhasindonesia.home.HomeViewModel

val useCaseModule = module {
    factory<FoodUseCase> { FoodInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { DetailFoodViewModel(get()) }
}