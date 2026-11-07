package com.jmapps.dayclose.di





import com.jmapps.dayclose.data.local_data.AppDataBase
import com.jmapps.dayclose.repository.DayDataRepository
import com.jmapps.dayclose.repository.DayExpenseRepository
import com.jmapps.dayclose.viewmodel.DayDataViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel


val appModule = module {
//  Define dependency injection here

    single{ AppDataBase.getDatabase(get()).dayDataDao() }
    single { DayDataRepository(get()) }
    viewModel{ DayDataViewModel(get())}



    single{ AppDataBase.getDatabase(get()).dayExpenseDao() }
    single { DayExpenseRepository(get()) }
    viewModel{ DayDataViewModel(get())}


}