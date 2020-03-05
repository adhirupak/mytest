package com.rupak.project.domain.usecase

import com.rupak.project.domain.Transformer
import io.reactivex.Observable

/**
 * Created By Rupak Adhikari
 */
abstract class UseCase<T>(private val transformer: Transformer<T>) {
    abstract fun createObservable(data: Map<String, Any>? = null): Observable<T>

    fun observable(withData: Map<String, Any>? = null): Observable<T> {
        return createObservable(withData).compose(transformer)
    }
}