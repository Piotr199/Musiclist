package com.wit.musiclist.activities

import com.wit.musiclist.models.MusiclistModel

interface MusiclistListener {
    fun onMusiclistClick(musiclist: MusiclistModel)
}