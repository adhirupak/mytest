package com.rupak.project.test.ui.album_list.activity

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rupak.project.test.R
import com.rupak.project.test.common.MainApplication
import com.rupak.project.test.entity.Album
import com.rupak.project.test.ui.track_list.TrackListActivity
import com.rupak.project.test.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_album_list.*
import kotlinx.android.synthetic.main.content_album_list.*
import javax.inject.Inject


class AlbumListActivity : AppCompatActivity() {

    private  lateinit var artistName: String
    @Inject
    lateinit var factory: AlbumsListVMFactory
    @Inject
    lateinit var imageLoader: ImageLoader
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var viewModel:AlbumViewModel
    private lateinit var albumListAdapter: AlbumsListAdapter

    companion object {
            private const val ARTIST_ID: String = "extra_artist_id"
            private const val ARTIST_NAME: String = "extra_artist_name"


            fun newIntent(context: Context?, artistId: Int?, artistName: String?): Intent {
                val i = Intent(context, AlbumListActivity::class.java)
                i.putExtra(ARTIST_ID, artistId)
                i.putExtra(ARTIST_NAME, artistName)
                return i
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Albums"
        (application as MainApplication).createAlbumListComponent().inject(this)
        factory.artistId = intent.getIntExtra(ARTIST_ID,0)
        viewModel = ViewModelProvider(this,factory).get(AlbumViewModel::class.java)
        artistName = intent.getStringExtra(ARTIST_NAME)
        if(artistName == null) artistName = "Unknown"
        albumListAdapter = AlbumsListAdapter(imageLoader,artistName) { album, view -> onAlbumClicked(album,view)  }


        progressBar = get_album_progress
        recyclerView = albums_list_view
        recyclerView.layoutManager = GridLayoutManager(this,2)

        recyclerView.adapter = albumListAdapter



        if (savedInstanceState != null) {
            observeViewState()
        } else {
            viewModel.getAlbums()

        }


    }

    override fun onResume() {
        super.onResume()
        observeViewState()
    }


    private fun onAlbumClicked(
        album: Album,
        view: View
    ) {

        var activityOptions: ActivityOptions? = null

        val imageForTransition: View? = view.findViewById(R.id.image)
        imageForTransition?.let {
            val posterSharedElement: Pair<View, String> = Pair.create(it, getString(R.string.transition_poster))
            activityOptions = ActivityOptions.makeSceneTransitionAnimation(this, posterSharedElement)
        }
        startActivity(TrackListActivity.newIntent(
            this,
            album.id,
            artistName,album.title,album.picture), activityOptions?.toBundle())
            overridePendingTransition(0, 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        //Toast.makeText(this,"back button clicked",Toast.LENGTH_LONG).show()
        return true
    }

    private fun observeViewState() {
        viewModel.viewState.observe(this, Observer { viewState -> handleViewState(viewState) })
        viewModel.errorState.observe(this, Observer { throwable ->
            throwable?.let {
                Toast.makeText(this, throwable.message, Toast.LENGTH_LONG).show()
            }
        })


    }

    private fun handleViewState(state: GetAlbumsViewState) {
        progressBar.visibility = if (state.showLoading) View.VISIBLE else View.GONE
        state.albums?.let { albumListAdapter.addAlbums(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        (application as MainApplication).releaseAlbumListComponent()
    }


}
