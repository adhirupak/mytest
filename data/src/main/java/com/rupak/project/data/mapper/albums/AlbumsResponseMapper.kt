package com.rupak.project.data.mapper.albums

import com.rupak.project.data.entities.album_list.DataItem
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.utils.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
class AlbumsResponseMapper @Inject constructor() : Mapper<DataItem, AlbumEntity>() {
    override fun mapFrom(from: DataItem): AlbumEntity {
        return AlbumEntity(
            id = from.id,
            title = from.title,
            picture = from.coverMedium,
            trackList = from.tracklist


        )
    }
}