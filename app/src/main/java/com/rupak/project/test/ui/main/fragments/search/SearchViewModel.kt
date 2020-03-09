package com.rupak.project.test.ui.main.fragments.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.usecase.search_artist.SearchArtist
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.common.BaseViewModel
import com.rupak.project.test.common.SingleLiveEvent
import com.rupak.project.test.entity.Artist

/**
 * Created By Rupak Adhikari
 */
class SearchViewModel(private val searchArtist: SearchArtist,
                      private val entityArtistMapper: Mapper<DataItemEntity,Artist>): BaseViewModel()  {
    var viewState: MutableLiveData<SearchStateWithView> = MutableLiveData()
    var errorState: SingleLiveEvent<Throwable?> = SingleLiveEvent()

    init {
        viewState.value = SearchStateWithView()
    }

    fun search(query: String){
        errorState.value = null

        if(query.isEmpty()){
            viewState.value = viewState.value?.copy(
                isLoading = false,
                showNoResultMessage = false,
                lastSearchArtist = query,
                artistList = null
            )
        }else {
            viewState.value = viewState.value?.copy(
                isLoading = true,
                showNoResultMessage = false
            )

            performSearchArtist(query)
        }
    }

    private fun performSearchArtist(query: String) {
        Log.d(javaClass.simpleName, "Searching $query")
        addDisposable(searchArtist.search(query)
            .flatMap { entityArtistMapper.observable(it) }
            .subscribe({ artistList ->
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    artistList = artistList,
                    lastSearchArtist = query,
                    showNoResultMessage = artistList.isEmpty()
                )
            }, {
                viewState.value = viewState.value?.copy(
                    isLoading = false,
                    artistList = null,
                    lastSearchArtist = query
                )
            }
            ))

    }


}