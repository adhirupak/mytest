package com.rupak.project.test.ui.fragments.search

import com.rupak.project.test.entity.Artist

/**
 * Created By Rupak Adhikari
 */
data class SearchStateWithView(
    val isLoading: Boolean = false,
    val artistList: List<Artist>? = null,
    val lastSearchArtist: String? = null,
    val showNoResultMessage: Boolean = false
)
