package com.rupak.project.test.entity

import android.os.Parcelable
import com.rupak.project.test.player.model.ASong

import kotlinx.android.parcel.Parcelize

@Parcelize
data class Song(
    var id: Int?,
    var songName: String?,
    var path: String?,
    var artistName: String?,
    var albumArt: String?

) : ASong(id, songName, albumArt, artistName, path), Parcelable