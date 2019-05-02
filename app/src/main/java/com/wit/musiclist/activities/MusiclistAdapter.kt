package com.wit.musiclist.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wit.musiclist.R
import com.wit.musiclist.helpers.readImageFromPath
import com.wit.musiclist.models.MusiclistModel
import kotlinx.android.synthetic.main.card_musiclist.view.*


class MusiclistAdapter constructor(private var musiclists: List<MusiclistModel>,
                                   private val listener: MusiclistListener) : RecyclerView.Adapter<MusiclistAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_musiclist, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val musiclist = musiclists[holder.adapterPosition]
        holder.bind(musiclist, listener)
    }

    override fun getItemCount(): Int = musiclists.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(musiclist: MusiclistModel,  listener : MusiclistListener) {
            itemView.musiclistTitle.text= musiclist.title
            itemView.musiclistDescription.text = musiclist.description
            itemView.musiclistArtist.text= musiclist.artist
            itemView.musiclistGenre.text = musiclist.genre
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, musiclist.image))
            itemView.setOnClickListener { listener.onMusiclistClick(musiclist) }
        }
    }
}
