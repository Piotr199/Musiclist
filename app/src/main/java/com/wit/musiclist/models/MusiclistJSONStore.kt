package com.wit.musiclist.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.wit.musiclist.helpers.exists
import com.wit.musiclist.helpers.read
import com.wit.musiclist.helpers.write
import org.jetbrains.anko.AnkoLogger
import java.util.*

val JSON_FILE = "musiclists.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<ArrayList<MusiclistModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MusiclistJSONStore : MusiclistStore, AnkoLogger {

    val context: Context
    var musiclists = mutableListOf<MusiclistModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<MusiclistModel> {
        return musiclists
    }

    override fun create(musiclist: MusiclistModel) {
        musiclist.id = generateRandomId()
        musiclists.add(musiclist)
        serialize()
    }


    override fun update(musiclist: MusiclistModel) {
        val musiclistsList = findAll() as ArrayList<MusiclistModel>
        var foundMusiclist: MusiclistModel? = musiclistsList.find { p -> p.id == musiclist.id }
        if (foundMusiclist != null) {
            foundMusiclist.title = musiclist.title
            foundMusiclist.artist = musiclist.artist
            foundMusiclist.genre = musiclist.genre
            foundMusiclist.description = musiclist.description
            foundMusiclist.image = musiclist.image
        }
        serialize()
    }

    override fun delete(musiclist: MusiclistModel) {
        musiclists.remove(musiclist)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(musiclists, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        musiclists = Gson().fromJson(jsonString, listType)
    }
}