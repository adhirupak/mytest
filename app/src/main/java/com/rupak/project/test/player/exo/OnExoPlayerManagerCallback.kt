package com.rupak.project.test.player.exo

import com.rupak.project.test.player.model.ASong

/**
 * To make an interaction between [ExoPlayerManager]
 * & [MediaController]
 *
 * and to return result from [ExoPlayerManager]
 *
 * @author Zara
 * */
interface OnExoPlayerManagerCallback {

    fun getCurrentSongState(): Int

    fun isPlaying(): Boolean

    fun getCurrentStreamPosition(): Long

    fun getCurrentSong(): ASong?

    fun start()

    fun stop()

    fun updateLastKnownStreamPosition()

    fun play(aSong: ASong)

    fun pause()

    fun seekTo(position: Long)


    /**
     * This class gives the information about current song
     * (position, state of completion, when it`s changed, ...)
     *
     * */
    interface OnSongStateCallback {

        fun onCompletion()

        fun onPlaybackStatusChanged(state: Int)

        fun onError(error: String)

        fun setCurrentPosition(position: Long, duration: Long)

        fun getCurrentSong(): ASong?



    }

    fun setCallback(callback: OnSongStateCallback)
}
