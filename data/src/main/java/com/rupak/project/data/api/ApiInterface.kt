package com.rupak.project.data.api

import com.rupak.project.data.entities.album_list.AlbumListResults
import com.rupak.project.data.entities.artist_search.ArtistSearchResponse
import com.rupak.project.data.entities.track.TrackResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * Created By Rupak Adhikari
 */
interface ApiInterface {
    @GET("/search/artist")
    fun searchArtist(@Query("q") query:String): Observable<ArtistSearchResponse>

    @GET("/artist/{id}/albums")
    fun getAlbumsByArtist(@Path("id") artsitId:Int): Observable<AlbumListResults>

    @GET("/album/{id}/tracks")
    fun getTracksViaAlbum(@Path("id") albumId:Int): Observable<TrackResults>

}