package com.example.perlindunganpelecehanapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.text.FieldPosition

@Parcelize
data class Perlindungan(
    var key: String? = null,
    var date: String? = null,
    var position: String? = null,
    var isImage: Boolean? = false,
    var isAudio: Boolean? = false,
    var isVideo: Boolean? = false,
) : Parcelable
