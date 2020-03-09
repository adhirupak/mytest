package com.rupak.project.data.mapper.track_list

import com.rupak.project.data.entities.track.DataItem
import com.rupak.project.domain.entities.track.TrackEntity
import com.rupak.project.domain.utils.Mapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
class TrackResponseMapper @Inject constructor() : Mapper<DataItem,TrackEntity>() {
    override fun mapFrom(from: DataItem): TrackEntity {
        return TrackEntity(
            id = from.id,
            title = from.title,
            artistName = from.artist?.name,
            preview = from.preview,
            diskNumber = from.diskNumber

        )
    }
}