package com.rupak.project.test.utils

import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Artist
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
class ArtistEntityMapper @Inject constructor() : Mapper<DataItemEntity,Artist>() {
    override fun mapFrom(from: DataItemEntity): Artist {
        return Artist(
            id = from.id,
            link = from.link,
            name = from.name,
            nbAlbum = from.nbAlbum,
            nbFan = from.nbFan,
            picture = from.picture,
            pictureBig = from.pictureBig,
            pictureMedium = from.pictureMedium,
            pictureSmall = from.pictureSmall,
            pictureXl = from.pictureXl,
            radio = from.radio,
            tracklist = from.tracklist,
            type = from.type


        )
    }
}