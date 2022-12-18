package com.example.perlindunganpelecehanapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Kekerasan(
    var key: String?,
    var url: String?
) : Parcelable