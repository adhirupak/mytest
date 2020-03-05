package com.rupak.project.data.repositary

import com.rupak.project.data.api.ApiInterface
import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.data.mapper.ArtistMapper
import com.rupak.project.domain.DataStore
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
class WebDataStore(private val apiInterface: ApiInterface ) :DataStore {
    private val artistMapper:ArtistMapper = ArtistMapper()

    override fun searchArtist(query: String): Observable<List<DataItemEntity>> {
        return apiInterface.searchArtist(query).map { results->
            results.data.map { artistMapper.mapFrom(it) }
        }
    }
}