package com.wit.musiclist.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusiclistModel (var id: Long =0,
                           var title: String = "",
                           var description: String = "",
                           var artist: String ="",
                           var genre: String = "",
                           var image: String = "") : Parcelable {
}


