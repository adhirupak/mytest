package com.rupak.project.data.repositary

import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.Repositary
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.entities.track.TrackEntity
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
class RepositaryImpl(private val remoteDataStore: WebDataStore): Repositary {
    override fun searchArtist(query: String): Observable<List<DataItemEntity>> {
        return remoteDataStore.searchArtist(query)
    }

    override fun getAlbumsViaArtist(id: Int): Observable<List<AlbumEntity>> {
        return remoteDataStore.getAlbums(id)
    }

    override fun getTracksViaAlbums(albumId: Int): Observable<List<TrackEntity>> {
       return remoteDataStore.getTracksViaAlbums(albumId)
    }


}