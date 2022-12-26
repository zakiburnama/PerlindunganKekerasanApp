package com.example.perlindunganpelecehanapp.helper

import android.database.Cursor
import com.example.perlindunganpelecehanapp.Home
import com.example.perlindunganpelecehanapp.db.DatabaseContract

object MappingHelper {

    fun mapCursorToArrayList(homeworkCursor: Cursor?): ArrayList<Home> {
        val homeworkList = ArrayList<Home>()

        homeworkCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.HomeworkColumns._ID))
                val title = getString(getColumnIndexOrThrow(DatabaseContract.HomeworkColumns.TITLE))
                val description = getString(getColumnIndexOrThrow(DatabaseContract.HomeworkColumns.DESCRIPTION))
                val img = getInt(getColumnIndexOrThrow(DatabaseContract.HomeworkColumns.IMG))
                val isswitch = getInt(getColumnIndexOrThrow(DatabaseContract.HomeworkColumns.ISSWITCH))
                homeworkList.add(Home(id, title, description, img, isswitch))
            }
        }
        return homeworkList
    }
}