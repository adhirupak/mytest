package com.rupak.project.test.player

import com.rupak.project.test.player.model.ASong

/**
 * This class make an interaction [PlayerViewModel] & [BaseSongPlayerActivity]
 *
 * @author Zara
 * */
interface OnPlayerActionCallback {


    fun play(song: ASong)

    fun pause()

    fun stop()

    fun seekTo(position: Long?)





}