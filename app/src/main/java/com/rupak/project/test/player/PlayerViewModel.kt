package com.rupak.project.test.player

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rupak.project.test.common.BaseViewModel
import com.rupak.project.test.player.model.ASong
import com.rupak.project.test.player.utils.AppConstants


class PlayerViewModel : BaseViewModel() {

    private val _playerData = MutableLiveData<ASong>()
    val playerData: LiveData<ASong> = _playerData

    private val _isVisibleData = MutableLiveData<Boolean>()
    val isVisibleData: LiveData<Boolean> = _isVisibleData

    private val _isBufferingData = MutableLiveData<Boolean>()
    val isBufferingData: LiveData<Boolean> = _isBufferingData

    private val _isPlayData = MutableLiveData<Boolean>()
    val isPlayData: LiveData<Boolean> = _isPlayData

    private val _playingPercentData = MutableLiveData<Int>()
    val playingPercentData: LiveData<Int> = _playingPercentData

    private val _songDurationTextData = MutableLiveData<String>()
    val songDurationTextData: LiveData<String> = _songDurationTextData

    private val _songPositionTextData = MutableLiveData<String>()
    val songPositionTextData: LiveData<String> = _songPositionTextData

    private val _songDurationData = MutableLiveData<Int>()
    val songDurationData: LiveData<Int> = _songDurationData

    private val _songPositionData = MutableLiveData<Int>()
    val songPositionData: LiveData<Int> = _songPositionData

    private val _isShuffleData = MutableLiveData<Boolean>()
    val isShuffleData: LiveData<Boolean> = _isShuffleData

    private val _isRepeatAllData = MutableLiveData<Boolean>()
    val isRepeatAllData: LiveData<Boolean> = _isRepeatAllData

    private val _isRepeatData = MutableLiveData<Boolean>()
    val isRepeatData: LiveData<Boolean> = _isRepeatData

    private val _isCompletedData = MutableLiveData<Boolean>()
    val isCompletedData: LiveData<Boolean> = _isCompletedData

    private var mNavigator: OnPlayerActionCallback? = null

    val song: ASong?
        get() = _playerData.value

    init {
        _isPlayData.value = false
        _isRepeatData.value = false
        _isVisibleData.value = false
    }

    fun setData(song: ASong?) {
        if (song == _playerData.value) return
        this._playerData.value = song
        this._isRepeatData.value = false

        _songPositionTextData.value = AppConstants.formatTimeInMillisToString(0)
        _songPositionData.value = 0
        _songDurationTextData.value = AppConstants.formatTimeInMillisToString(0)
        _songDurationData.value = 0
    }






    fun setPlayer(onAudioPlayerActionCallback: OnPlayerActionCallback) {
        this.mNavigator = onAudioPlayerActionCallback
    }

    fun setVisibility(isVisible: Boolean) {
        this._isVisibleData.value = isVisible
    }

    fun setBuffering(isBuffering: Boolean) {
        this._isBufferingData.value = isBuffering
    }

    fun setPlayStatus(playStatus : Boolean){
        playerData.value?.isPlay = playStatus
        _isPlayData.value = playStatus
    }



    fun pause() {
        setPlayStatus(false)
        mNavigator?.pause()
    }

    fun play(song: ASong?) {
        song?.let {
            mNavigator?.play(it)
        }
    }

    fun play() {
        if (_isPlayData.value == true) {
            mNavigator?.pause()
        } else {
            _playerData.value?.let {
                mNavigator?.play(it)
            }
        }
    }










    fun seekTo(position: Long) {

        _songPositionTextData.value = AppConstants.formatTimeInMillisToString(position)
        _songPositionData.value = position.toInt()
        mNavigator?.seekTo(position)
    }

    fun stop() {
        setPlayStatus(false)
        _songPositionData.value = 0
        _songPositionTextData.value = AppConstants.formatTimeInMillisToString(_songPositionData.value?.toLong() ?: 0)
        mNavigator?.stop()
        _isVisibleData.value = false
    }

    fun setPlayingPercent(playingPercent: Int) {
        if (this._playingPercentData.value == 100) return
        this._playingPercentData.value = playingPercent
    }

    fun setChangePosition(currentPosition: Long, duration: Long) {
        Log.i(TAG, "currentPosition: $currentPosition >>>>> duration: $duration")
        if (currentPosition > duration) return
        _songPositionTextData.value = AppConstants.formatTimeInMillisToString(currentPosition)
        _songPositionData.value = currentPosition.toInt()

        val durationText = AppConstants.formatTimeInMillisToString(duration)
        if (!_songDurationTextData.value.equals(durationText)) {
            _songDurationTextData.value = durationText
            _songDurationData.value = duration.toInt()
        }
    }

    fun onComplete() {
        _songPositionTextData.value = _songDurationTextData.value
        _isCompletedData.value = true
    }

    companion object {

        private val TAG = PlayerViewModel::class.java.name
        private var mInstance: PlayerViewModel? = null

        @Synchronized
        fun getPlayerViewModelInstance(): PlayerViewModel {
            if (mInstance == null) {
                mInstance = PlayerViewModel()
            }
            return mInstance as PlayerViewModel
        }
    }

}