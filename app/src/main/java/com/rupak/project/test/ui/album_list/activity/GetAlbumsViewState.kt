package com.rupak.project.test.ui.album_list.activity

import com.rupak.project.test.entity.Album

/**
 * Created By Rupak Adhikari
 */
data class GetAlbumsViewState (
    var showLoading: Boolean = true,
    var albums: List<Album>? = null
)