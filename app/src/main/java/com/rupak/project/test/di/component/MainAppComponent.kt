package com.rupak.project.test.di.component

import com.rupak.project.test.di.module.ApplicationModule
import com.rupak.project.test.di.module.DataModule
import com.rupak.project.test.di.module.NetModule
import com.rupak.project.test.di.module.albums.AlbumModule
import com.rupak.project.test.di.module.albums.GetAlbumsComponent
import com.rupak.project.test.di.module.search.SearchModule
import com.rupak.project.test.di.module.search.SearchSubComponent
import com.rupak.project.test.di.module.tracks.GetTracksComponent
import com.rupak.project.test.di.module.tracks.TracksModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created By Rupak Adhikari
 */
@Singleton
@Component(modules = [
    (ApplicationModule::class),
    (NetModule::class),
    (DataModule::class)])
interface MainAppComponent {
    fun plus(searchModule: SearchModule): SearchSubComponent
    fun plus(albumModule: AlbumModule) : GetAlbumsComponent
    fun plus (tracksModule: TracksModule) : GetTracksComponent
}