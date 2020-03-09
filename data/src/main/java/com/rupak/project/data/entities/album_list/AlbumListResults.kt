package com.rupak.project.data.entities.album_list

import com.google.gson.annotations.SerializedName

data class AlbumListResults(
	@SerializedName("total")
	val total: Int,
	@SerializedName("data")
	val data: List<DataItem>
)
