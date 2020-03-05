package com.rupak.project.test.di.module.search

import com.rupak.project.domain.Repositary
import com.rupak.project.domain.usecase.SearchArtist
import com.rupak.project.test.common.ASyncTransformer
import com.rupak.project.test.ui.fragments.search.SearchVMFactory
import com.rupak.project.test.utils.ArtistEntityMapper
import dagger.Module
import dagger.Provides

/**
 * Created by Rupak Adhikari.
 */
@Module
class SearchModule {

    @Provides
    fun provideSearchMovieUseCase(repository: Repositary): SearchArtist {
        return SearchArtist(ASyncTransformer(), repository)
    }

    @Provides
    fun provideSearchVMFactory(useCase: SearchArtist, mapper: ArtistEntityMapper): SearchVMFactory {
        return SearchVMFactory(useCase, mapper)
    }
}