package com.rupak.project.test.ui.album_list.activity

import androidx.lifecycle.MutableLiveData
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.usecase.albums.GetAlbumsUseCase
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.common.BaseViewModel
import com.rupak.project.test.common.SingleLiveEvent
import com.rupak.project.test.entity.Album

/**
 * Created By Rupak Adhikari
 */
class AlbumViewModel(private val getAlbums: GetAlbumsUseCase, private val artistId: Int,
                     private val mapper: Mapper<AlbumEntity,Album>) : BaseViewModel(){

    var viewState: MutableLiveData<GetAlbumsViewState> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = GetAlbumsViewState()
    }

    fun getAlbums(){
        addDisposable(getAlbums.getById(artistId)
            .flatMap { mapper.observable(it) }
            .subscribe ({ t: List<Album>? -> viewState.value?.let {
                val newState = this.viewState.value?.copy(showLoading = false,albums = t)
                this.viewState.value = newState
                this.errorState.value = null
            }
         },{
            viewState.value = viewState.value?.copy(showLoading = false)
                errorState.value = it
        }))

    }
}