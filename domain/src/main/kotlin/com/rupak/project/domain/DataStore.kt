package com.rupak.project.domain

import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.entities.track.TrackEntity
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
interface DataStore {
    fun searchArtist(query: String): Observable<List<DataItemEntity>>
    fun getAlbums(id:Int) : Observable<List<AlbumEntity>>
    fun getTracksViaAlbums(albumId:Int): Observable<List<TrackEntity>>

}