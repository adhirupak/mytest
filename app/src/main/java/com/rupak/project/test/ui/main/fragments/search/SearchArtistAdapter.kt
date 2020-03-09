package com.rupak.project.test.ui.main.fragments.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rupak.project.test.R
import com.rupak.project.test.entity.Artist
import com.rupak.project.test.utils.ImageLoader
import kotlinx.android.synthetic.main.artists_search_row_layout.view.*

/**
 * Created By Rupak Adhikari
 */
class SearchArtistAdapter constructor(private val imageLoader: ImageLoader,
                                      private val onArtistSelected: (Artist) -> Unit):RecyclerView.Adapter<SearchArtistAdapter.ArtsistCellViewHolder>()  {

    private var artists:List<Artist> = listOf()
    var query: String? = null

    fun setArtist(artists:List<Artist>,query:String? ){
        this.artists = artists
        this.query = query
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtsistCellViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.artists_search_row_layout,parent,false)
        return ArtsistCellViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artists.size
    }

    override fun onBindViewHolder(holder: ArtsistCellViewHolder, position: Int) {
        val artist = artists[position]
        holder?.bindArtist(artist,imageLoader,onArtistSelected)
    }


    class ArtsistCellViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindArtist(artist: Artist, imageLoader: ImageLoader, onArtistSelected: (Artist) -> Unit) {
            itemView.title.text = artist.name
            artist.pictureMedium?.let {
                imageLoader.load(it,itemView.image)
            }

            itemView.setOnClickListener{onArtistSelected(artist)}

        }


    }
}