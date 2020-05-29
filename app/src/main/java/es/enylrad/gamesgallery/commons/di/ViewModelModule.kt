package es.enylrad.gamesgallery.commons.di

import es.enylrad.gamesgallery.ui.MainViewModel
import es.enylrad.gamesgallery.ui.dashboard.DashboardViewModel
import es.enylrad.gamesgallery.ui.library.LibraryViewModel
import es.enylrad.gamesgallery.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(get(), get())
    }

    viewModel {
        DashboardViewModel(get())
    }

    viewModel {
        LibraryViewModel(get())
    }

    viewModel {
        ProfileViewModel(get(), get())
    }


}