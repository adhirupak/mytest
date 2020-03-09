package com.rupak.project.test.utils

import com.rupak.project.domain.entities.track.TrackEntity
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Tracks
import javax.inject.Inject

/**
 * Created By Rupak Adhikari
 */
class TracksEntityMapper @Inject constructor():Mapper<TrackEntity,Tracks>() {
    override fun mapFrom(from: TrackEntity): Tracks {
        return Tracks(
            id = from.id,
            title = from.title,
            preview = from.preview,
            diskNumber = from.diskNumber,
            serialNumber = 0

        )
    }
}