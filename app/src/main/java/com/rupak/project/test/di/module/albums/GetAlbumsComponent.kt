package com.rupak.project.test.di.module.albums

import com.rupak.project.test.ui.album_list.activity.AlbumListActivity
import dagger.Subcomponent

/**
 * Created By Rupak Adhikari
 */
@AlbumsScope
@Subcomponent(modules = [AlbumModule::class])
interface GetAlbumsComponent {
    fun inject(albumListActivity: AlbumListActivity)
}