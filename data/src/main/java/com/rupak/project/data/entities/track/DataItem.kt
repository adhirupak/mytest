package com.rupak.project.data.entities.track

import com.google.gson.annotations.SerializedName

/**
 * As gson converter convention is underscore like disk_number so using serialized in camelcase variables.
 */
data class DataItem(
	val readable: Boolean? = null,
	val preview: String? = null,
	val artist: Artist? = null,
	val link: String? = null,
	@SerializedName("explicit_content_cover")
	val explicitContentCover: Int? = null,
	val isrc: String? = null,
	val title: String? = null,
	@SerializedName("title_version")
	val titleVersion: String? = null,
	@SerializedName("explicit_lyrics")
	val explicitLyrics: Boolean? = null,

	val type: String? = null,
	@SerializedName("title_short")
	val titleShort: String? = null,
	val duration: Int? = null,
	@SerializedName("disk_number")
	val diskNumber: Int? = null,
	val rank: Int? = null,
	val id: Int? = null,
	val explicitContentLyrics: Int? = null,
	val trackPosition: Int? = null
)
