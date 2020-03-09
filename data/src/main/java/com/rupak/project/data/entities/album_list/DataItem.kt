package com.rupak.project.data.entities.album_list

import com.google.gson.annotations.SerializedName

data class DataItem(
	@SerializedName("tracklist")
	val tracklist: String? = null,
	@SerializedName("link")
	val link: String? = null,
	@SerializedName("cover_small")
	val coverSmall: String? = null,
	@SerializedName("title")
	val title: String? = null,
	@SerializedName("explicit_lyrics")
	val explicitLyrics: Boolean? = null,
	@SerializedName("type")
	val type: String? = null,
	@SerializedName("genre_id")
	val genreId: Int? = null,
	@SerializedName("record_type")
	val recordType: String? = null,
	@SerializedName("fans")
	val fans: Int? = null,
	@SerializedName("cover")
	val cover: String? = null,
	@SerializedName("cover_xl")
	val coverXl: String? = null,
	@SerializedName("release_date")
	val releaseDate: String? = null,
	@SerializedName("cover_medium")
	val coverMedium: String? = null,
	@SerializedName("id")
	val id: Int? = null,
	@SerializedName("cover_big")
	val coverBig: String? = null
)
