package com.rupak.project.test.ui.track_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rupak.project.domain.entities.track.TrackEntity
import com.rupak.project.domain.usecase.track_list.GetTracksUsecase
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Tracks

/**
 * Created By Rupak Adhikari
 */
class TrackListVMFactory(val getTracksUsecase: GetTracksUsecase,
                         val mapper: Mapper<TrackEntity,Tracks>) : ViewModelProvider.Factory {
    var albumId: Int = -1
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TrackListViewModel(getTracksUsecase,albumId,mapper) as T

    }
}