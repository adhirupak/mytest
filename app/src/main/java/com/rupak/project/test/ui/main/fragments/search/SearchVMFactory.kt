package com.rupak.project.test.ui.main.fragments.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.usecase.search_artist.SearchArtist
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Artist

/**
 * Created by Rupak Adhikari.
 */
class SearchVMFactory(val searchArtist: SearchArtist,
                      val mapper: Mapper<DataItemEntity, Artist>): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(searchArtist, mapper) as T
    }

}