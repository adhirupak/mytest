package com.rupak.project.data.mapper

import com.rupak.project.data.entities.DataItem
import com.rupak.project.data.entities.DataItemEntity
import com.rupak.project.domain.utils.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
class ArtistMapper @Inject constructor():Mapper<DataItem,DataItemEntity>()  {

    override fun mapFrom(from: DataItem): DataItemEntity {
        return DataItemEntity(
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