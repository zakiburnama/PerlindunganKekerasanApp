package com.example.perlindunganpelecehanapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.perlindunganpelecehanapp.db.DatabaseContract.HomeworkColumns
import com.example.perlindunganpelecehanapp.db.DatabaseContract.HomeworkColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
    null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbtampilan"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " (${HomeworkColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${HomeworkColumns.TITLE} TEXT NOT NULL," +
                " ${HomeworkColumns.DESCRIPTION} TEXT NOT NULL," +
                " ${HomeworkColumns.IMG} INTEGER NOT NULL," +
                " ${HomeworkColumns.ISSWITCH} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}