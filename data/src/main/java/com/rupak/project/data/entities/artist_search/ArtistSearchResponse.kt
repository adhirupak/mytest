package com.rupak.project.data.entities.artist_search

import com.google.gson.annotations.SerializedName


data class ArtistSearchResponse(
	@SerializedName("total")
	var total: Int,
	@SerializedName("data")
	var data: List<DataItem>
)
