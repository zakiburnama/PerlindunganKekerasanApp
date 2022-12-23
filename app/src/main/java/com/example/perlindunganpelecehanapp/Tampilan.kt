package com.example.perlindunganpelecehanapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tampilan(
    var title: String,
    var description: String,
    var img: Int,
    var isSwitch: Int,
) : Parcelable
