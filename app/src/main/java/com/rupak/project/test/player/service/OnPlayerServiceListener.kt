package com.rupak.project.test.player.service

import com.rupak.project.test.player.model.ASong

/**
 * To make an interaction between [PlayerService]
 * & [BaseSongPlayerActivity]
 *
 * @author ZARA
 * */
interface OnPlayerServiceListener {

    fun updateSongData(song: ASong?)

    fun updateSongProgress(duration: Long, position: Long)

    fun setBufferingData(isBuffering: Boolean)

    fun setVisibilityData(isVisibility: Boolean)

    fun setPlayStatus(isPlay: Boolean)

    fun onSongEnded()

}