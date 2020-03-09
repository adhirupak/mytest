package com.rupak.project.test.player.service

import android.app.Service
import android.content.Intent
import android.media.session.PlaybackState
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.rupak.project.test.player.controller.MediaController
import com.rupak.project.test.player.controller.OnMediaControllerCallback
import com.rupak.project.test.player.exo.ExoPlayerManager
import com.rupak.project.test.player.model.ASong
import com.rupak.project.test.player.notification.MediaNotificationManager
import java.util.*


class PlayerService : Service(), OnMediaControllerCallback {

    private var mMediaController: MediaController? = null
    var mNotificationManager: MediaNotificationManager? = null
    private val mMediaControllerCallbackHashSet = HashSet<OnMediaControllerCallback>()
    var mListener: OnPlayerServiceListener? = null
    var command: String? = null


    override fun onCreate() {
        super.onCreate()
        val exoPlayerManager = ExoPlayerManager(this)
        mMediaController = MediaController(exoPlayerManager, this)
        mNotificationManager = MediaNotificationManager(this)
        registerMediaControllerCallbacks()

        /*
        * However the system allows apps to call Context.startForegroundService() even while the app is in the background,
        * in android O+ the app must call that service's startForeground() method within five seconds after the service is created.
        * */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager?.startNotification()
            if (getCurrentSong() == null) {
                mNotificationManager?.stopNotification()
            }
        }
    }

    override fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    private fun registerMediaControllerCallbacks() {
        mMediaController?.registerCallback(this)
        for (callback in mMediaControllerCallbackHashSet) {
            mMediaController?.registerCallback(callback)
        }
        mMediaControllerCallbackHashSet.clear()
    }


    private fun unregisterAllControllerCallback() {
        for (callback in mMediaControllerCallbackHashSet) {
            mMediaController?.unregisterCallback(callback)
        }
    }

    fun addListener(listener: OnPlayerServiceListener) {
        mListener = listener
    }

    fun getCurrentSong(): ASong? {
        return mMediaController?.getCurrentSong()
    }





    override fun onSongChanged() {
        mListener?.updateSongData(getCurrentSong())
        mNotificationManager?.updateNotification()
    }

    override fun getSongPlayingState(): Int {
        return mMediaController?.getSongPlayingState() ?: 0
    }

    override fun onPlaybackStateChanged() {
        when (mMediaController?.getSongPlayingState()) {
            PlaybackState.STATE_BUFFERING -> {
                mListener?.setBufferingData(true)
                mListener?.setVisibilityData(true)
                mListener?.setPlayStatus(true)
            }

            PlaybackState.STATE_PLAYING -> {
                mListener?.setBufferingData(false)
                mListener?.setVisibilityData(true)
                mListener?.setPlayStatus(true)
            }

            PlaybackState.STATE_PAUSED -> {
                mListener?.setBufferingData(false)
                mListener?.setVisibilityData(true)
                mListener?.setPlayStatus(false)
            }

            else -> {
                mListener?.setBufferingData(false)
                mListener?.setVisibilityData(false)
                mListener?.setPlayStatus(false)
            }
        }
    }



    fun play(song: ASong) {
        mMediaController?.play(song)
    }

    fun pause() {
        mMediaController?.pause()
        mNotificationManager?.updateNotification()
    }

    fun stop() {
        mMediaController?.stop()
    }

    override fun setDuration(duration: Long, position: Long) {
        mListener?.updateSongProgress(duration, position)
    }


    fun seekTo(position: Long) {
        mMediaController?.seekTo(position)
    }

    override fun onBind(intent: Intent): IBinder? {
        val action = intent.action
        command = intent.getStringExtra(CMD_NAME)
        if (ACTION_CMD == action && CMD_PAUSE == command) {
            mMediaController?.pause()
        }
        return LocalBinder()
    }

    override fun onNotificationRequired() {
        mNotificationManager?.startNotification()
    }

    override fun onSongComplete() {
        mListener?.onSongEnded()
        onPlaybackStop()
    }

    override fun onPlaybackStop() {
        mNotificationManager?.stopNotification()

    }

    inner class LocalBinder : Binder() {
        // Return this instance of PlayerService so clients can call public methods
        val service: PlayerService
            get() = this@PlayerService
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
        unregisterAllControllerCallback()
        mMediaController?.stop()
        mListener = null
    }

    companion object {

        private val TAG = PlayerService::class.java.name
        const val ACTION_CMD = "app.ACTION_CMD"
        const val CMD_NAME = "CMD_NAME"
        const val CMD_STOP_CASTING = "CMD_STOP_CASTING"
        const val CMD_PAUSE = "CMD_PAUSE"
    }
}
