package com.rupak.project.data.repositary

import com.rupak.project.data.api.ApiInterface
import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.data.entities.album_list.AlbumListResults
import com.rupak.project.data.entities.artist_search.ArtistSearchResponse
import com.rupak.project.data.mapper.albums.AlbumsResponseMapper
import com.rupak.project.data.mapper.artsist.ArtistMapper
import com.rupak.project.data.mapper.track_list.TrackResponseMapper
import com.rupak.project.domain.DataStore
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.entities.track.TrackEntity
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
class WebDataStore(private val apiInterface: ApiInterface ) :DataStore {
    private val artistMapper: ArtistMapper =
        ArtistMapper()
    private val albumsMapper: AlbumsResponseMapper = AlbumsResponseMapper()
    private val tracksMapper: TrackResponseMapper = TrackResponseMapper()

    override fun searchArtist(query: String): Observable<List<DataItemEntity>> {
        return apiInterface.searchArtist(query).map { results: ArtistSearchResponse ->
            results.data.map { artistMapper.mapFrom(it) }
        }
    }

    override fun getAlbums(id: Int): Observable<List<AlbumEntity>> {
        return apiInterface.getAlbumsByArtist(id).map{t: AlbumListResults ->
            t.data.map{albumsMapper.mapFrom(it)}
        }
    }

    override fun getTracksViaAlbums(albumId: Int): Observable<List<TrackEntity>> {
        return apiInterface.getTracksViaAlbum(albumId).map { t ->
            t.data.map { tracksMapper.mapFrom(it) }
        }
    }


}