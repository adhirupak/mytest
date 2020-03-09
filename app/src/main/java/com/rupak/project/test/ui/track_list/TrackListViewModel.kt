package com.rupak.project.test.ui.track_list

import androidx.lifecycle.MutableLiveData
import com.rupak.project.domain.entities.track.TrackEntity
import com.rupak.project.domain.usecase.track_list.GetTracksUsecase
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.common.BaseViewModel
import com.rupak.project.test.common.SingleLiveEvent
import com.rupak.project.test.entity.Tracks

/**
 * Created By Rupak Adhikari
 */
class TrackListViewModel(private val getTracksUsecase: GetTracksUsecase,private val albumId: Int,
                         private val mapper: Mapper<TrackEntity,Tracks>) : BaseViewModel() {
    var viewState: MutableLiveData<GetTracksViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = GetTracksViewState()
    }

    fun getTracks(){
        addDisposable(getTracksUsecase.getTracksByAlbumId(albumId)
            .flatMap { mapper.observable(it) }
            .subscribe({ t: List<Tracks>? -> viewState.value?.let {
                val newState = this.viewState.value?.copy(showLoading = false,tracks = t)
                this.viewState.value = newState
                this.errorState.value = null
            }},{
                viewState.value = viewState.value?.copy(showLoading = false)
                errorState.value = it
            }))
    }
}