package com.rupak.project.test.ui.track_list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import com.google.android.material.appbar.AppBarLayout
import com.rupak.project.test.R
import com.rupak.project.test.common.MainApplication
import com.rupak.project.test.common.SimpleTransitionEndedCallback
import com.rupak.project.test.entity.Tracks
import com.rupak.project.test.ui.track_list.view_utils.HeaderItem
import com.rupak.project.test.utils.AppBarStateChangeListener
import com.rupak.project.test.utils.ImageLoader
import com.yuyang.stickyheaders.StickyLinearLayoutManager
import com.yuyang.stickyheaders.StickyLinearLayoutManager.StickyHeaderListener
import kotlinx.android.synthetic.main.activity_track_list.*
import kotlinx.android.synthetic.main.content_track_list.*
import javax.inject.Inject


class TrackListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel: TrackListViewModel
    private   var artistName: String? = null
    private   var albumName: String? = null
    private lateinit var posterImage: ImageView
    private lateinit var titleTView: TextView
    private lateinit var artistView: TextView


    private lateinit var trackListAdapter: TrackListAdapter

    @Inject
    lateinit var factory: TrackListVMFactory

    @Inject
    lateinit var imageLoader: ImageLoader



    private lateinit var  appBarLayout: AppBarLayout
    private lateinit var titleView:TextView

    companion object {
        private const val ALBUM_ID: String = "extra_album_id"
        private const val ARTIST_NAME: String = "extra_artist_name"
        private const val ALBUM_NAME: String = "extra_album_name"
        private const val PICTURE_ALBUM: String = "extra_album_picture"


        fun newIntent(context: Context?, albumId: Int?, artistName: String?,albumName: String?,picture:String?): Intent {
            val i = Intent(context, TrackListActivity::class.java)
            i.putExtra(ALBUM_ID, albumId)
            i.putExtra(ARTIST_NAME, artistName)
            i.putExtra(ALBUM_NAME,albumName)
            i.putExtra(PICTURE_ALBUM,picture)
            return i
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_list)
        (application as MainApplication).createTrackListComponent().inject(this)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        titleView = toolbar.findViewById(R.id.toolbar_title)
        appBarLayout = app_bar
        supportActionBar?.title = ""

        val id = intent.getIntExtra(ALBUM_ID,0)
        factory.albumId=id
        viewModel = ViewModelProvider(this,factory).get(TrackListViewModel::class.java)

        artistName = intent.getStringExtra(ARTIST_NAME)
        if(artistName == null) artistName="Unknown"
        albumName = intent.getStringExtra(ALBUM_NAME)
        titleView.text = albumName

        posterImage = image
        titleTView = title_tv
        artistView = artist

        titleTView.text = albumName
        artistView.text = artistName

        val posterUrl = intent.getStringExtra(PICTURE_ALBUM)
        posterUrl?.let {
            imageLoader.load(it,posterImage){
                startPostponedEnterTransition()
            }
        } ?: run { startPostponedEnterTransition() }
         window.sharedElementEnterTransition.addListener(SimpleTransitionEndedCallback{
             observeViewState()
         })

        trackListAdapter = TrackListAdapter(
            this,
            artistName!!,posterUrl
        )
        progressBar = get_track_progress
        recyclerView = track_list_view
        recyclerView.adapter = trackListAdapter

        val layoutManager: StickyLinearLayoutManager =
            object : StickyLinearLayoutManager(this, trackListAdapter) {
                override fun isAutoMeasureEnabled(): Boolean {
                    return true
                }

                override fun smoothScrollToPosition(
                    recyclerView: RecyclerView,
                    state: RecyclerView.State,
                    position: Int
                ) {
                    val smoothScroller: SmoothScroller =
                        TopSmoothScroller(recyclerView.context)
                    smoothScroller.targetPosition = position
                    startSmoothScroll(smoothScroller)
                }

                inner class TopSmoothScroller(context: Context?) :
                    LinearSmoothScroller(context) {
                    override fun calculateDtToFit(
                        viewStart: Int,
                        viewEnd: Int,
                        boxStart: Int,
                        boxEnd: Int,
                        snapPreference: Int
                    ): Int {
                        return boxStart - viewStart
                    }
                }
            }
        layoutManager.elevateHeaders(5)

        layoutManager.setStickyHeaderListener(object : StickyHeaderListener {
            override fun headerAttached(
                headerView: View,
                adapterPosition: Int
            ) {
                Log.d("StickyHeader", "Header Attached : $adapterPosition")
            }

            override fun headerDetached(
                headerView: View,
                adapterPosition: Int
            ) {
                Log.d("StickyHeader", "Header Detached : $adapterPosition")
            }
        })
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = trackListAdapter

        appBarLayout.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            var isShow  = false
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                Log.d("State == ", state?.name)
                when(state) {
                    State.COLLAPSED -> { /* Do something */
                        isShow = true
                        titleView.visibility = View.VISIBLE
                        }
                    State.EXPANDED -> { /* Do something */
                        titleView.visibility = View.INVISIBLE
                        isShow = false
                        }
                    State.IDLE -> {
                       if(!isShow) titleView.visibility = View.INVISIBLE
                       else titleView.visibility = View.VISIBLE

                    }
                }
            }
        })


        if (savedInstanceState != null) {
            observeViewState()
        } else {
            viewModel.getTracks()

        }

    }

    override fun onResume() {
        super.onResume()
        observeViewState()
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleViewState(state: GetTracksViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.tracks?.let { addDataToAdapter(it) }
    }

    private fun addDataToAdapter(trackList:List<Tracks>){
        val itemsWithHeader = getArrayWithHeaders(trackList)
        trackListAdapter.setDataList(itemsWithHeader)

    }

    private fun getArrayWithHeaders(items: List<Tracks>): List<Any> {
        val itemsWithHeader = ArrayList<Any>()
        var lastDiskId: Int? = -1
        var serialNumber = 0
        items.forEach {
            if (it.diskNumber != lastDiskId) {
                serialNumber=0
                itemsWithHeader.add(HeaderItem("Volume ${it.diskNumber}"))
                lastDiskId = it.diskNumber
            }
            serialNumber++
            it.serialNumber = serialNumber
            itemsWithHeader.add(it)
        }
        return itemsWithHeader
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as MainApplication).releaseTrackListComponent()
    }


}

