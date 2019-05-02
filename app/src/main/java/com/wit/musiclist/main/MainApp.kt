package com.wit.musiclist.main

import android.app.Application
import com.wit.musiclist.models.MusiclistJSONStore
import com.wit.musiclist.models.MusiclistStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class MainApp: Application(), AnkoLogger {

    lateinit var musiclists : MusiclistStore

    override fun onCreate() {
        super.onCreate()
        musiclists = MusiclistJSONStore(applicationContext)
        info("Musiclist Started")


    }
}