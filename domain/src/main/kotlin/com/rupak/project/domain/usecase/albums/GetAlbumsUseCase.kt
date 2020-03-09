package com.rupak.project.domain.usecase.albums

import com.rupak.project.domain.Repositary
import com.rupak.project.domain.Transformer
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.usecase.UseCase
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
open class GetAlbumsUseCase(transformer: Transformer<List<AlbumEntity>>,
                            private val repositary: Repositary): UseCase<List<AlbumEntity>>(transformer){
    companion object{
        private const val PARAM_ALBUM_ENTITY = "param:AlbumEntity"
    }

    fun getById(artistId: Int) : Observable<List<AlbumEntity>>{
        val data = HashMap<String, Int>()
        data[PARAM_ALBUM_ENTITY] = artistId
        return observable(data)
    }
    override fun createObservable(data: Map<String, Any>?): Observable<List<AlbumEntity>> {
        val movieId = data?.get(PARAM_ALBUM_ENTITY)
        movieId?.let {
            return repositary.getAlbumsViaArtist(it as Int)
        } ?: return Observable.error({IllegalAccessException("Artist Id must be provided")})
    }

}