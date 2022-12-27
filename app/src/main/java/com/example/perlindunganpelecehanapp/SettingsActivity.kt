package com.example.perlindunganpelecehanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.databinding.ActivitySettingsBinding
import com.example.perlindunganpelecehanapp.db.TampilanHelper
import com.example.perlindunganpelecehanapp.helper.MappingHelper
import com.example.perlindunganpelecehanapp.ui.notifications.NotificationsFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
//    private lateinit var tampilanHelper: TampilanHelper
    private lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        tampilanHelper = TampilanHelper.getInstance(applicationContext)
//        tampilanHelper.open()

        binding.rvContact.layoutManager = LinearLayoutManager(this)
        binding.rvContact.setHasFixedSize(true)

        adapter = ContactAdapter(object : ContactAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedHomework: Home?, position: Int?) {
                Toast.makeText(this@SettingsActivity, "KLIK", Toast.LENGTH_SHORT).show()
            }
        })
//        binding.rvContact.adapter = adapter

        if (savedInstanceState == null) {
            Log.i("TAG", "##### savedInstanceState NULL")
            loadHomeworkAsync()
        } else {
            Log.i("TAG", "##### savedInstanceState ELSE")
            val list = savedInstanceState.getParcelableArrayList<Home>(EXTRA_STATES)
            Log.i("TAG", "##### savedInstanceState ELSE $list")
            if (list != null){
                adapter.listContact = list
                binding.rvContact.adapter = adapter
            }
        }

        binding.switchPhoto.isChecked = true
        binding.switchBubble.isChecked = true
        binding.switchVideo.isChecked = true
        binding.switchAudio.isChecked = true

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

//    private fun addNewTampilan(id: Int, title: String, description: String, img: Int, isSwitch: Int) {
//        val values = ContentValues()
////        values.put(DatabaseContract.HomeworkColumns._ID, id)
//        values.put(DatabaseContract.HomeworkColumns.TITLE, title)
//        values.put(DatabaseContract.HomeworkColumns.DESCRIPTION, description)
//        values.put(DatabaseContract.HomeworkColumns.IMG, img)
//        values.put(DatabaseContract.HomeworkColumns.ISSWITCH, isSwitch)
//
//        val result = tampilanHelper.insert(values)
//        if (result > 0) {
//            Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
//        }
//    }


    private fun loadHomeworkAsync() {
        lifecycleScope.launch {
            val tampilanHelper = TampilanHelper.getInstance(applicationContext)
//                activity?.let { TampilanHelper.getInstance(it.applicationContext) }
            tampilanHelper.open()
            val deferredHomework = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById2()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val contact = deferredHomework.await()
            Log.i("TAG", "##### $contact")
            if (contact.size > 0) {
                adapter.listContact = contact
                binding.rvContact.adapter = adapter
                Log.i("TAG", "##### adapter.listContact ${adapter.listContact}")
            } else {
                adapter.listContact = ArrayList()
                Log.i("TAG", "##### KOSONG")
            }
            tampilanHelper.close()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATES, adapter.listContact)
    }

    companion object {
        private const val EXTRA_STATES = "EXTRA_STATES"
    }
}