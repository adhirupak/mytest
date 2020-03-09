package com.rupak.project.test.ui.album_list.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rupak.project.test.R
import com.rupak.project.test.entity.Album
import com.rupak.project.test.utils.ImageLoader
import kotlinx.android.synthetic.main.album_row_layout.view.*

/**
 * Created By Rupak Adhikari
 */
class AlbumsListAdapter constructor(private val imageLoader: ImageLoader,private val artistName: String,
                                    private val onAlbumSelected:(Album, View) -> Unit)
    : RecyclerView.Adapter<AlbumsListAdapter.AlbumRowViewHolder>() {

    private val albums: MutableList<Album> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumRowViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(
            R.layout.album_row_layout,
            parent,
            false)
        return AlbumRowViewHolder(view)
    }

    override fun getItemCount(): Int {
       return albums.size
    }

    override fun onBindViewHolder(holder: AlbumRowViewHolder, position: Int) {
       val album = albums[position]
        holder?.bind(album,artistName,imageLoader,onAlbumSelected)
    }

    fun addAlbums(albums: List<Album>){
        this.albums.addAll(albums)
        notifyDataSetChanged()
    }




    class AlbumRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(album: Album,artistName: String,imageLoader: ImageLoader,listener:(Album,View) -> Unit) = with(itemView){
            title.text = album.title
            artist.text= artistName
            album.picture?.let{
                imageLoader.load(it,image)
            }
            setOnClickListener{listener(album,itemView)}
        }
    }






}