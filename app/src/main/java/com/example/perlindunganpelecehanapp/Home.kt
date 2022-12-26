package com.example.perlindunganpelecehanapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Home(
    var id: Int? = 0,
    var title: String? = null,
    var description: String? = null,
    var img: Int? = 0,
    var isSwitch: Int? = 0,
) : Parcelable
