package com.rupak.project.data.repositary

import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.Repositary
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
class RepositaryImpl(private val remoteDataStore: WebDataStore): Repositary {
    override fun searchArtist(query: String): Observable<List<DataItemEntity>> {
        return remoteDataStore.searchArtist(query)
    }
}