package com.rupak.project.domain.usecase.search_artist

import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.Repositary
import com.rupak.project.domain.Transformer
import com.rupak.project.domain.usecase.UseCase
import io.reactivex.Observable
import java.util.*


/**
 * Created By Rupak Adhikari
 */
class SearchArtist(transformer: Transformer<List<DataItemEntity>>,
                   private val repositary: Repositary): UseCase<List<DataItemEntity>>(transformer) {
    companion object {
        private const val PARAM_SEARCH_QUERY = "param:search_query"
    }

    fun search(query: String): Observable<List<DataItemEntity>> {
        val data = HashMap<String, String>()
        data[PARAM_SEARCH_QUERY] = query
        return observable(data)
    }

    override fun createObservable(data: Map<String, Any>?): Observable<List<DataItemEntity>> {
        val query = data?.get(PARAM_SEARCH_QUERY)
        query?.let {
            return repositary.searchArtist(it as String)
        } ?: return Observable.just(null)
    }
}


