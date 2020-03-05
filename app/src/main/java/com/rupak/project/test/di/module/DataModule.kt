package com.rupak.project.test.di.module

import com.rupak.project.data.api.ApiInterface
import com.rupak.project.data.repositary.RepositaryImpl
import com.rupak.project.data.repositary.WebDataStore
import com.rupak.project.domain.Repositary
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Module
@Singleton
class DataModule {
    @Provides
    @Singleton
    fun provideMovieRepository(api: ApiInterface): Repositary {
        val remoteDataStore = WebDataStore(api)
        return RepositaryImpl(remoteDataStore)
    }
}