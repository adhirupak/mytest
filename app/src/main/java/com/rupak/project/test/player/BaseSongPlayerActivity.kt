package com.rupak.project.test.player

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rupak.project.test.player.PlayerViewModel.Companion.getPlayerViewModelInstance
import com.rupak.project.test.player.model.ASong
import com.rupak.project.test.player.service.OnPlayerServiceListener
import com.rupak.project.test.player.service.PlayerService


open class BaseSongPlayerActivity : AppCompatActivity(), OnPlayerActionCallback,
    OnPlayerServiceListener {

    private var mService: PlayerService? = null
    private var mBound = false
    val playerViewModel: PlayerViewModel = getPlayerViewModelInstance()
    private var mSong: ASong? = null
    private var mSongList: MutableList<ASong>? = null
    private var msg = 0

    private val mHandler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                ACTION_PLAY_SONG -> mSong?.let { play(it) }
            }
        }
    }

    /**
     * Defines callbacks for service binding, passed to bindService()
     */
    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to PlayerService, cast the IBinder and get PlayerService instance
            val binder = service as PlayerService.LocalBinder
            mService = binder.service
            mBound = true
            mHandler.sendEmptyMessage(msg)
            mService?.addListener(this@BaseSongPlayerActivity)
        }

        override fun onServiceDisconnected(classname: ComponentName) {
            mBound = false
        }
    }


    override fun onStart() {
        super.onStart()
        playerViewModel.setPlayer(this)
        // Bind to PlayerService
        val intent = Intent(this, PlayerService::class.java)
        ContextCompat.startForegroundService(this, intent)
        if (!mBound) bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
    }

    override fun setBufferingData(isBuffering: Boolean) {
        playerViewModel.setBuffering(isBuffering)
    }

    override fun setVisibilityData(isVisibility: Boolean) {
        playerViewModel.setVisibility(isVisibility)
    }

    override fun onDestroy() {
        // Unbind from the service
        if (mBound) {
            unbindService(mConnection)
            mBound = false
        }
        super.onDestroy()
    }



    override fun play(song: ASong) {
        msg = ACTION_PLAY_SONG
        mSong = song
        mService?.play(song)
    }



    override fun updateSongData(song: ASong?) {
        playerViewModel.setData(song)
    }

    override fun setPlayStatus(isPlay: Boolean) {
        playerViewModel.setPlayStatus(isPlay)
    }

    override fun updateSongProgress(duration: Long, position: Long) {
        playerViewModel.setChangePosition(position, duration)
    }

    override fun onSongEnded() {
        playerViewModel.onComplete()
    }




    override fun pause() {
        mService?.pause()
    }

    override fun stop() {
        mService?.stop()
    }



    override fun seekTo(position: Long?) {
        position?.let { nonNullPosition ->
            mService?.seekTo(nonNullPosition)
        }
    }

    companion object {

        private val TAG = BaseSongPlayerActivity::class.java.name
        const val SONG_LIST_KEY = "SONG_LIST_KEY"
        private const val ACTION_PLAY_SONG = 1

    }
}