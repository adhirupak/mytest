package com.rupak.project.domain

import io.reactivex.ObservableTransformer

/**
 * Created By Rupak Adhikari
 */
abstract class Transformer<T> : ObservableTransformer<T,T>
