package com.rupak.project.domain

import com.rupak.project.data.entities.DataItemEntity
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
interface Repositary {
 fun searchArtist(query: String): Observable<List<DataItemEntity>>
}