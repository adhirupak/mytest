package com.rupak.project.test.player.exo

import com.rupak.project.test.player.model.ASong


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



    interface OnSongStateCallback {

        fun onCompletion()

        fun onPlaybackStatusChanged(state: Int)

        fun onError(error: String)

        fun setCurrentPosition(position: Long, duration: Long)

        fun getCurrentSong(): ASong?



    }

    fun setCallback(callback: OnSongStateCallback)
}
