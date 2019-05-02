package com.wit.musiclist.activities

import com.wit.musiclist.main.MainApp
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.wit.musiclist.R
import com.wit.musiclist.helpers.readImage
import com.wit.musiclist.helpers.readImageFromPath
import com.wit.musiclist.helpers.showImagePicker
import com.wit.musiclist.models.MusiclistModel
import kotlinx.android.synthetic.main.activity_musiclist.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast




class MusiclistActivity : AppCompatActivity(), AnkoLogger {

    var musiclist = MusiclistModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musiclist)
        app = application as MainApp

        //Add action bar and set title
        toolbarAdd.title = title
        //setSupportActionBar(toolbarAdd)
        if (intent.hasExtra("musiclist_edit"))
        {
            musiclist = intent.extras.getParcelable<MusiclistModel>("musiclist_edit")
            musiclistTitle.setText(musiclist.title)
            musiclistDescription.setText(musiclist.description)
            musiclistArtist.setText(musiclist.artist)
            musiclistGenre.setText(musiclist.genre)
            musiclistImage.setImageBitmap(readImageFromPath(this, musiclist.image))
            btnAdd.setText(R.string.button_saveMusiclist)
            chooseImage.setText(R.string.button_changeImage)
            edit = true
        }

        btnAdd.setOnClickListener() {
            musiclist.title = musiclistTitle.text.toString()
            musiclist.artist = musiclistArtist.text.toString()
            musiclist.genre = musiclistGenre.text.toString()
            musiclist.description = musiclistDescription.text.toString()

            if (musiclist.title.isNotEmpty()) {
                if (edit){
                    app.musiclists.update(musiclist.copy())
                }
                else {
                    app.musiclists.create(musiclist.copy())
                }
                info("Add Button Pressed. name: ${musiclist.title}")
                setResult(AppCompatActivity.RESULT_OK)
                finish()
            }
            else {
                toast (R.string.message_enter_title)
            }
        }

        chooseImage.setOnClickListener{
            showImagePicker(this, IMAGE_REQUEST)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_musiclist, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                app.musiclists.delete(musiclist)
                finish()
            }
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
            IMAGE_REQUEST -> {
                if (data !=null){
                    musiclist.image = data.getData().toString()
                    musiclistImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.button_changeImage)
                }
            }
        }
    }
}