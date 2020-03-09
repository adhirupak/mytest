package com.rupak.project.test.player.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import androidx.lifecycle.Observer
import com.rupak.project.test.R
import com.rupak.project.test.entity.Song
import com.rupak.project.test.player.BaseSongPlayerActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_song_player.*

/**
 * Created By Rupak Adhikari
 */
class PlayActivity : BaseSongPlayerActivity() {
    private var mSong: Song? = null
    private lateinit var  imageView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_player)
        imageView = back
        imageView.setOnClickListener(View.OnClickListener { onBackPressed() })
        supportActionBar?.hide()
        mSong = intent?.getParcelableExtra("song")
        mSong?.let {
            play(it)
        }

        loadInitialData(mSong?.title, mSong?.artistName, mSong?.albumPic)

        playerViewModel.songDurationTextData.observe(this, Observer<String> { t ->
            song_player_total_time_text_view.text = t
        })

        playerViewModel.songDurationData.observe(this, Observer {
            song_player_progress_seek_bar.max = it
        })

        playerViewModel.songPositionTextData.observe(this,
            Observer<String> { t -> song_player_passed_time_text_view.text = t })

        playerViewModel.songPositionData.observe(this, Observer {
            song_player_progress_seek_bar.progress = it
        })

        playerViewModel.isPlayData.observe(this, Observer {
            song_player_toggle_image_view.setImageResource(if (it) R.drawable.ic_pause_vector else R.drawable.ic_play_vector)
        })

        playerViewModel.playerData.observe(this, Observer {
            loadInitialData(it?.title, it?.artist, it?.albumPic)
        })



        song_player_skip_next_image_view.setOnClickListener {
            playerViewModel.seekTo((song_player_progress_seek_bar.progress + 10000).toLong())
        }

        song_player_skip_back_image_view.setOnClickListener {
            playerViewModel.seekTo((song_player_progress_seek_bar.progress - 10000).toLong())
        }

        song_player_toggle_image_view.setOnClickListener {
            playerViewModel.play()
        }

        song_player_progress_seek_bar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "onProgressChanged: p0: $p0 p1: $p1, p2: $p2")
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                Log.i(TAG, "onStartTrackingTouch: p0: $p0")
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                Log.i(TAG, "onStopTrackingTouch: p0: $p0")
                playerViewModel.seekTo(song_player_progress_seek_bar.progress.toLong())
            }

        })


    }

    private fun loadInitialData(title: String?, singerName: String?, image: String?) {
        song_player_title_text_view.text = title
        song_player_singer_name_text_view.text = singerName

        image?.let {
            Picasso.with(this).load(it)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(song_player_image_view)
        }
    }

    companion object {

        private val TAG = PlayActivity::class.java.name

        fun start(context: Context, song: Song) {
            val intent = Intent(context, PlayActivity::class.java)
            intent.putExtra("song", song)
            context.startActivity(intent)
        }
    }


}

