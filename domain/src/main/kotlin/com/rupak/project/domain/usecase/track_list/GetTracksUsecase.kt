package com.rupak.project.domain.usecase.track_list

import com.rupak.project.domain.Repositary
import com.rupak.project.domain.Transformer
import com.rupak.project.domain.entities.track.TrackEntity
import com.rupak.project.domain.usecase.UseCase
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
class GetTracksUsecase (transformer: Transformer<List<TrackEntity>>,
                        private val repositary: Repositary
): UseCase<List<TrackEntity>>(transformer) {
    companion object {
        private const val PARAM_ALBUM_ENTITY = "param:TrackEntity"
    }

    fun getTracksByAlbumId(artistId: Int): Observable<List<TrackEntity>> {
        val data = HashMap<String, Int>()
        data[PARAM_ALBUM_ENTITY] = artistId
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<TrackEntity>> {
        val movieId = data?.get(PARAM_ALBUM_ENTITY)
        movieId?.let {
            return repositary.getTracksViaAlbums(it as Int)
        } ?: return Observable.error({ IllegalAccessException("Album Id must be provided") })
    }
}