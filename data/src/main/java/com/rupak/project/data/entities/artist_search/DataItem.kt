package com.rupak.project.data.entities.artist_search

import com.google.gson.annotations.SerializedName

data class DataItem(
	@SerializedName("picture_xl")
	var pictureXl: String,
	@SerializedName("tracklist")
	var tracklist: String,
	@SerializedName("link")
	var link: String,
	@SerializedName("picture_small")
	var pictureSmall: String,
	@SerializedName("type")
	var type: String,
	@SerializedName("nb_album")
	var nbAlbum: Int,
	@SerializedName("picture")
	var picture: String,
	@SerializedName("nb_fan")
	var nbFan: Int,
	@SerializedName("radio")
	var radio: Boolean,
	@SerializedName("picture_big")
	var pictureBig: String,
	@SerializedName("name")
	var name: String,
	@SerializedName("id")
	var id: Int,
	@SerializedName("picture_medium")
	var pictureMedium: String
)
