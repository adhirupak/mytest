package com.rupak.project.test.common

import android.app.Application
import com.rupak.project.test.R
import com.rupak.project.test.di.component.DaggerMainAppComponent
import com.rupak.project.test.di.component.MainAppComponent
import com.rupak.project.test.di.module.ApplicationModule
import com.rupak.project.test.di.module.DataModule
import com.rupak.project.test.di.module.NetModule
import com.rupak.project.test.di.module.albums.AlbumModule
import com.rupak.project.test.di.module.albums.GetAlbumsComponent
import com.rupak.project.test.di.module.search.SearchModule
import com.rupak.project.test.di.module.search.SearchSubComponent
import com.rupak.project.test.di.module.tracks.GetTracksComponent
import com.rupak.project.test.di.module.tracks.TracksModule

/**
 * Created By Rupak Adhikari
 */
class MainApplication: Application() {

     var getTracksComponent: GetTracksComponent? = null
    lateinit var mainComponent: MainAppComponent
    private var searchComponent: SearchSubComponent? = null
    private var getAlbumComponent: GetAlbumsComponent? = null

   companion object{
        lateinit var mInstance : MainApplication

       @Synchronized
       fun getApp(): MainApplication? {
           return mInstance
       }
   }

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        initDependencies()
    }

    private fun initDependencies() {
        mainComponent = DaggerMainAppComponent.builder()
            .applicationModule(ApplicationModule(applicationContext))
            .netModule(NetModule(getString(R.string.api_base_url)))
            .dataModule(DataModule())
            .build()

    }

    fun createSearchComponent(): SearchSubComponent {
        searchComponent = mainComponent.plus(SearchModule())
        return searchComponent!!
    }

    fun createAlbumListComponent() : GetAlbumsComponent{
        getAlbumComponent = mainComponent.plus(AlbumModule())
        return getAlbumComponent!!
    }

    fun createTrackListComponent() : GetTracksComponent{
        getTracksComponent = mainComponent.plus(TracksModule())
        return getTracksComponent!!
    }

    fun releaseSearchComponent() {
        searchComponent = null
    }

    fun releaseAlbumListComponent(){
        getAlbumComponent = null
    }

    fun releaseTrackListComponent(){
        getTracksComponent = null
    }


}