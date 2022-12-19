package com.example.perlindunganpelecehanapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Perlindungan(
    var key: String? = null,
    var date: String? = null,
    var position: String? = null,
    var isImage: Boolean? = false,
    var isAudio: Boolean? = false,
    var isVideo: Boolean? = false,
    var lat: Double? = 0.0,
    var lang: Double? = 0.0,
) : Parcelable
