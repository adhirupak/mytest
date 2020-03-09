package com.rupak.project.test.ui.track_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rupak.project.test.R
import com.rupak.project.test.entity.Song
import com.rupak.project.test.entity.Tracks
import com.rupak.project.test.player.ui.PlayActivity
import com.rupak.project.test.ui.track_list.TrackListAdapter.BaseViewHolder
import com.rupak.project.test.ui.track_list.view_utils.HeaderItem
import com.yuyang.stickyheaders.AdapterDataProvider
import java.util.*

/**
 * Created By Rupak Adhikari.
 */
class TrackListAdapter(private val context: Context, private val artist: String,private val albumPic: String) :
    RecyclerView.Adapter<BaseViewHolder>(), AdapterDataProvider {
    private val dataList: MutableList<Any> =
        ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return if (viewType == 0) {
            ItemViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.track_list_row,
                    parent,
                    false
                )
            )
        } else {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_view_tracks,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = dataList[position]
        if (item is HeaderItem) {
            val headerViewHolder =
                holder as HeaderViewHolder
            headerViewHolder.titleTextView.text = item.title
        } else if (item is Tracks) {
            val itemViewHolder = holder as ItemViewHolder
            itemViewHolder.titleTextView.text = item.title
            itemViewHolder.messageTextView.text = artist
            itemViewHolder.serialNoTextView.text = item.serialNumber.toString()
            itemViewHolder.itemView.setOnClickListener(View.OnClickListener {
                val song = Song(item.id,item.title,item.preview,artist,albumPic)
                PlayActivity.start(context,song)
            })
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getAdapterData(): List<*> {
        return dataList
    }

    override fun getItemViewType(position: Int): Int {
        return if (dataList[position] is Tracks) 0 else 1
    }

    fun setDataList(items: List<Any>) {
        dataList.clear()
        dataList.addAll(items)
        notifyDataSetChanged()
    }

    fun addDataList(items: List<Any>) {
        if (items != null) {
            val start = dataList.size
            dataList.addAll(items)
            notifyItemRangeInserted(start, items.size)
        }
    }

    private class ItemViewHolder internal constructor(itemView: View) :
        BaseViewHolder(itemView) {
        var titleTextView: TextView
        var messageTextView: TextView
        var serialNoTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.name)
            messageTextView = itemView.findViewById(R.id.artist_name)
            serialNoTextView = itemView.findViewById(R.id.serail_no)

        }
    }

    private class HeaderViewHolder internal constructor(itemView: View) :
        BaseViewHolder(itemView) {
        var titleTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.volume_text)
        }
    }

    open class BaseViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

}