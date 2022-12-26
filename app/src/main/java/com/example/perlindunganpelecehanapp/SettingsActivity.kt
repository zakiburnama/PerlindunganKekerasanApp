package com.example.perlindunganpelecehanapp

import android.content.ContentValues
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.perlindunganpelecehanapp.databinding.ActivitySettingsBinding
import com.example.perlindunganpelecehanapp.db.DatabaseContract
import com.example.perlindunganpelecehanapp.db.TampilanHelper
import com.example.perlindunganpelecehanapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var tampilanHelper: TampilanHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tampilanHelper = TampilanHelper.getInstance(applicationContext)
        tampilanHelper.open()

        loadHomeworkAsync()

        binding.settingPhoto.setOnClickListener {
            val id = 1
            val title = "Photo"
            val description = "Open Camera"
            val img = 2131230872
            val isSwitch = 0
//            addNewTampilan(id, title, description, img, isSwitch)
        }
        binding.settingBubble.setOnClickListener {
            val id = 2
            val title = "Bubble"
            val description = "Active Floating Icon"
            val img = 2131230962
            val isSwitch = 1
//            addNewTampilan(id, title, description, img, isSwitch)
//            tampilanHelper.deleteById("103")
//            tampilanHelper.deleteById("104")
//            tampilanHelper.deleteById("105")
        }
        binding.settingVideo.setOnClickListener {
            val id = 3
            val title = "Video"
            val description = "Open Camera"
            val img = 2131230876
            val isSwitch = 0
//            addNewTampilan(id, title, description, img, isSwitch)
        }
        binding.settingAudio.setOnClickListener {
            val id = 4
            val title = "Audio"
            val description = "Audio Record"
            val img = 2131230875
            val isSwitch = 0
//            addNewTampilan(id, title, description, img, isSwitch)
        }
    }

    private fun addNewTampilan(id: Int, title: String, description: String, img: Int, isSwitch: Int) {
        val values = ContentValues()
//        values.put(DatabaseContract.HomeworkColumns._ID, id)
        values.put(DatabaseContract.HomeworkColumns.TITLE, title)
        values.put(DatabaseContract.HomeworkColumns.DESCRIPTION, description)
        values.put(DatabaseContract.HomeworkColumns.IMG, img)
        values.put(DatabaseContract.HomeworkColumns.ISSWITCH, isSwitch)

        val result = tampilanHelper.insert(values)
        if (result > 0) {
            Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loadHomeworkAsync() {
        lifecycleScope.launch {
//            val tampilanHelper = TampilanHelper.getInstance(applicationContext)
//                activity?.let { TampilanHelper.getInstance(it.applicationContext) }
//            tampilanHelper.open()
            val deferredHomework = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById2()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val tampilan = deferredHomework.await()
            Log.i("TAG", "##### $tampilan")
            if (tampilan.size > 0) {
//                adapter.listHome = tampilan
            } else {
//                adapter.listHome = ArrayList()
            }
            tampilanHelper.close()
        }
    }
}