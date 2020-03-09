package com.rupak.project.test.di.module.tracks

import com.rupak.project.domain.Repositary
import com.rupak.project.domain.usecase.track_list.GetTracksUsecase
import com.rupak.project.test.common.ASyncTransformer
import com.rupak.project.test.ui.track_list.TrackListVMFactory
import com.rupak.project.test.utils.TracksEntityMapper
import dagger.Module
import dagger.Provides

/**
 * Created By Rupak Adhikari
 */
@Module
class TracksModule {

    @Provides
    fun providesGetTrackUseCase(repositary: Repositary) : GetTracksUsecase {
        return GetTracksUsecase(ASyncTransformer(),repositary)
    }

    @Provides
    fun provideTrackVMFactory(useCase: GetTracksUsecase, mapper: TracksEntityMapper) : TrackListVMFactory {
        return TrackListVMFactory(useCase,mapper)
    }
}