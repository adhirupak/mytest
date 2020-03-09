package com.rupak.project.test.ui.album_list.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rupak.project.domain.entities.album_entity.AlbumEntity
import com.rupak.project.domain.usecase.albums.GetAlbumsUseCase
import com.rupak.project.domain.utils.Mapper
import com.rupak.project.test.entity.Album

/**
 * Created By Rupak Adhikari
 */
class AlbumsListVMFactory(val listAlbums:GetAlbumsUseCase,
                          val mapper: Mapper<AlbumEntity,Album>):ViewModelProvider.Factory {
    var artistId: Int = -1

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumViewModel(listAlbums,artistId,mapper) as T
    }
}