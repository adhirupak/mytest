package com.rupak.project.test.player.controller

import android.os.Handler
import android.util.Log
import com.rupak.project.test.player.exo.OnExoPlayerManagerCallback
import com.rupak.project.test.player.model.ASong

import java.util.*


/**
 * This class is used to interact with [ExoPlayerManager] & [QueueManager]
 *
 * @author ZARA
 * */
class MediaController(
    private val onExoPlayerManagerCallback: OnExoPlayerManagerCallback,
    private val mediaControllerCallback: OnMediaControllerCallback
) : OnExoPlayerManagerCallback.OnSongStateCallback {


    val mMediaControllersCallbacksHashSet = HashSet<OnMediaControllerCallback>()


    init {
        this.onExoPlayerManagerCallback.setCallback(this)


    }

    fun registerCallback(onMediaControllerCallback: OnMediaControllerCallback?) {
        onMediaControllerCallback?.let { nonNullCallback ->
            mMediaControllersCallbacksHashSet.add(nonNullCallback)
        }


        onExoPlayerManagerCallback.getCurrentSong()?.let { nonNullSong ->
            Handler().postDelayed({
                runOnSongChanged(onMediaControllerCallback)
                runOnPlaybackStateChanged(
                    onMediaControllerCallback
                )
            }, 1000)
        }
    }

    fun unregisterCallback(callback: OnMediaControllerCallback) {
        mMediaControllersCallbacksHashSet.remove(callback)
    }

    fun getSongPlayingState(): Int {
        return onExoPlayerManagerCallback.getCurrentSongState()
    }

    fun play(song: ASong) {
        this.onExoPlayerManagerCallback.play(song)
        val iterator = mMediaControllersCallbacksHashSet.iterator()
        while (iterator.hasNext()) {
            runOnSongChanged(iterator.next())
        }
        mediaControllerCallback.onNotificationRequired()
    }









    fun pause() {
        this.onExoPlayerManagerCallback.pause()
    }

    fun seekTo(position: Long) {
        this.onExoPlayerManagerCallback.seekTo(position)
    }

    fun stop() {
        this.onExoPlayerManagerCallback.stop()
        mediaControllerCallback.onPlaybackStop()
        val iterator = mMediaControllersCallbacksHashSet.iterator()
        while (iterator.hasNext()) {
            runOnPlaybackStateChanged(
                iterator.next()
            )
        }
    }



    override fun getCurrentSong(): ASong? {
        return onExoPlayerManagerCallback.getCurrentSong()
    }


    override fun setCurrentPosition(position: Long, duration: Long) {
        mediaControllerCallback.setDuration(duration, position)
    }










    override fun onCompletion() {





        this.onExoPlayerManagerCallback.stop()
        mediaControllerCallback.onSongComplete()
    }

    override fun onPlaybackStatusChanged(state: Int) {
        val iterator = mMediaControllersCallbacksHashSet.iterator()
        while (iterator.hasNext()) {
            runOnPlaybackStateChanged(
                iterator.next()
            )
        }
    }


    override fun onError(error: String) {
        Log.i(TAG, "error: $error")
    }

    private fun runOnSongChanged(callback: OnMediaControllerCallback?) {
        callback?.onSongChanged()
    }

    private fun runOnPlaybackStateChanged(mediaControllerCallback: OnMediaControllerCallback?) {
        mediaControllerCallback?.onPlaybackStateChanged()
    }


    companion object{
        private val TAG = MediaController::class.java.name
    }
}