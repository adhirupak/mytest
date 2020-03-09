package com.rupak.project.test.ui.track_list

import com.rupak.project.test.entity.Tracks

/**
 * Created By Rupak Adhikari
 */
data class GetTracksViewState(
    var showLoading: Boolean = true,
    var tracks: List<Tracks>? = null
)
