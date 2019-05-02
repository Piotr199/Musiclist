package com.wit.musiclist.activities

import com.wit.musiclist.main.MainApp
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.wit.musiclist.R
import com.wit.musiclist.models.MusiclistModel
import kotlinx.android.synthetic.main.activity_musiclist_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult


class MusiclistListActivity : AppCompatActivity(), MusiclistListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musiclist_list)
        app = application as MainApp

        //layout and populate for display
        val layoutManager = LinearLayoutManager(this)
        //recyclerView is a widget in activity_musiclist_list.xml
        recyclerView.layoutManager = layoutManager
        loadMusiclists()

        //enable action bar and set title
        toolbarMain.title = title
        //setSupportActionBar(toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<MusiclistActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onMusiclistClick(musiclist: MusiclistModel) {
        startActivityForResult(intentFor<MusiclistActivity>().putExtra("musiclist_edit", musiclist), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadMusiclists()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun loadMusiclists() {
        showMusiclists(app.musiclists.findAll())
    }

    fun showMusiclists (musiclists: List<MusiclistModel>) {
        //recyclerView is a widget in activity_musiclist_list.xml
        recyclerView.adapter = MusiclistAdapter(musiclists, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}