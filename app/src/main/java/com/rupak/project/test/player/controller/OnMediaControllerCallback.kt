package com.rupak.project.test.player.controller


/**
 * To return the result of [MediaController]
 *
 * and also to make an interaction between [PlayerService] & [MediaController]
 *
 * @author ZARA
 * */
interface OnMediaControllerCallback {

    fun onSongChanged()

    fun onPlaybackStateChanged()

    fun onNotificationRequired()

    fun onPlaybackStop()

    fun setDuration(duration: Long, position: Long)


    fun getSongPlayingState(): Int

    fun onSongComplete()




}