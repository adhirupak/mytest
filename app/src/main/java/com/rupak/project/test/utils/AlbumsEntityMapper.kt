package com.rupak.project.test.utils

import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Album
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
class AlbumsEntityMapper @Inject constructor() : Mapper<AlbumEntity,Album>() {
    override fun mapFrom(from: AlbumEntity): Album {
        return Album(
            id = from.id,
            title = from.title,
            picture = from.picture,
            trackList = from.trackList
        )
    }
}