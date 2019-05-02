package com.wit.musiclist.models

interface MusiclistStore {
    fun findAll(): List<MusiclistModel>
    fun create(musiclist: MusiclistModel)
    fun update(musiclist: MusiclistModel)
    fun delete(musiclist: MusiclistModel)
}