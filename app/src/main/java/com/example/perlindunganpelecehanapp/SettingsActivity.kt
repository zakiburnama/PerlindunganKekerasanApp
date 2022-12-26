package com.example.perlindunganpelecehanapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.perlindunganpelecehanapp.databinding.ActivitySettingsBinding
import com.example.perlindunganpelecehanapp.db.DatabaseContract
import com.example.perlindunganpelecehanapp.db.TampilanHelper

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var tampilanHelper: TampilanHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tampilanHelper = TampilanHelper.getInstance(applicationContext)
        tampilanHelper.open()

        binding.settingPhoto.setOnClickListener {
//            Toast.makeText(this, "TEKAN", Toast.LENGTH_SHORT).show()
            addNewTampilan()
        }

        binding.settingBubble.setOnClickListener {
        }
    }


    private fun addNewTampilan() {
        val id = 100
        val title = "awd"
        val description = "awd"
        val img = 0
        val isSwitch = 0

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
}