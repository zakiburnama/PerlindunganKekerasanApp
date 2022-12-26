package com.example.perlindunganpelecehanapp.db

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class HomeworkColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "tampilan"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DESCRIPTION = "description"
            const val IMG = "img"
            const val ISSWITCH = "isSwitch"
        }
    }
}
