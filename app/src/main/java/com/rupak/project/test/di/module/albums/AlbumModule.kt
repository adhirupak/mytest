package com.rupak.project.test.di.module.albums

import com.rupak.project.domain.Repositary
import com.rupak.project.domain.usecase.albums.GetAlbumsUseCase
import com.rupak.project.test.common.ASyncTransformer
import com.rupak.project.test.ui.album_list.activity.AlbumsListVMFactory
import com.rupak.project.test.utils.AlbumsEntityMapper
import dagger.Module
import dagger.Provides

/**
 * Created By Rupak Adhikari
 */
@Module
class AlbumModule {

    @Provides
    fun providesGetAlbumsUseCase(repositary: Repositary) : GetAlbumsUseCase{
        return GetAlbumsUseCase(ASyncTransformer(),repositary)
    }

    @Provides
    fun provideAlbumVMFactory(useCase: GetAlbumsUseCase, mapper: AlbumsEntityMapper) : AlbumsListVMFactory{
        return AlbumsListVMFactory(useCase,mapper)
    }
}