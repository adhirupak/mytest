package com.rupak.project.domain.entities.common

/**
 * Created by Rupak Adhikari.
 * Reference from yossi post.
 */
class Optional<out T>(val value: T? = null) {

    companion object {

        fun <T> of(value: T?): Optional<T> {
            return Optional(value)
        }

        fun <T> empty(): Optional<T> {
            return Optional()
        }
    }

    fun hasValue(): Boolean {
        return value != null
    }

}