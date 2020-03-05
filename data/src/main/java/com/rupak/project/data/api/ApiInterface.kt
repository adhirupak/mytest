package com.rupak.project.data.api

import com.rupak.project.data.entities.ArtistSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created By Rupak Adhikari
 */
interface ApiInterface {
    @GET("/search/artist")
    fun searchArtist(@Query("q") query:String): Observable<ArtistSearchResponse>

}