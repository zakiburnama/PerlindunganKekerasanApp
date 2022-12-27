package com.example.perlindunganpelecehanapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.databinding.ActivitySettingsBinding
import com.example.perlindunganpelecehanapp.db.DatabaseContract
import com.example.perlindunganpelecehanapp.db.TampilanHelper
import com.example.perlindunganpelecehanapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var tampilanHelper: TampilanHelper

    private var isPhoto = false
    private var isBubble = false
    private var isVideo = false
    private var isAudio = false

    val resultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.data != null) {
            when (result.resultCode) {
                AddNewContactActivity.RESULT_ADD -> {
                    val home =
                        result.data?.getParcelableExtra<Home>(AddNewContactActivity.EXTRA_HOMEWORK) as Home
                    adapter.addItem(home)
                    binding.rvContact.smoothScrollToPosition(adapter.itemCount - 1)
//                    showSnackbarMessage("Data berhasil ditambahkan")
                }
                AddNewContactActivity.RESULT_UPDATE -> {
                    val home =
                        result.data?.getParcelableExtra<Home>(AddNewContactActivity.EXTRA_HOMEWORK) as Home
                    val position =
                        result?.data?.getIntExtra(AddNewContactActivity.EXTRA_POSITION, 0) as Int
                    Log.i("TAG", "#### Settings pos $position")
                    adapter.updateItem(position, home)
                    binding.rvContact.smoothScrollToPosition(position)
//                    showSnackbarMessage("Data berhasil diubah")
                }
                AddNewContactActivity.RESULT_DELETE -> {
                    val position =
                        result?.data?.getIntExtra(AddNewContactActivity.EXTRA_POSITION, 0) as Int
                    adapter.removeItem(position)
//                    showSnackbarMessage("Data berhasil dihapus")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tampilanHelper = TampilanHelper.getInstance(applicationContext)
        tampilanHelper.open()

        binding.rvContact.layoutManager = LinearLayoutManager(this)
        binding.rvContact.setHasFixedSize(true)

        adapter = ContactAdapter(object : ContactAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedHome: Home?, position: Int?) {
                val intent = Intent(this@SettingsActivity, AddNewContactActivity::class.java)
                intent.putExtra(AddNewContactActivity.EXTRA_HOMEWORK, selectedHome)
                intent.putExtra(AddNewContactActivity.EXTRA_POSITION, position)
                resultLauncher.launch(intent)
            }
        })
        binding.rvContact.adapter = adapter

        if (savedInstanceState == null) {
            loadHomeworkAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Home>(EXTRA_STATES)
            if (list != null){
                adapter.listContact = list
                binding.rvContact.adapter = adapter
            }
        }

        binding.switchPhoto.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isPhoto) {
                    isPhoto = true
                } else {
                    isPhoto = true
                    val id = "1"
                    val title = "Photo"
                    val description = "Open Camera"
                    val img = 2131230874
                    val isSwitch = 0
                    addNewTampilan(id, title, description, img, isSwitch)
                    Toast.makeText(this, "foto diaktifkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (isPhoto) {
                    isPhoto = false
                    tampilanHelper.deleteById("1")
                    Toast.makeText(this, "foto dinonaktifkan", Toast.LENGTH_SHORT).show()
                } else {
                    isPhoto = false
                }
            }
        }
        binding.switchBubble.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isBubble) {
                    isBubble = true
                } else {
                    isBubble = true
                    val id = "2"
                    val title = "Bubble"
                    val description = "Active Floating Icon"
                    val img = 2131230872
                    val isSwitch = 1
                    addNewTampilan(id, title, description, img, isSwitch)
                    Toast.makeText(this, "bubble diaktifkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (isBubble) {
                    isBubble = false
                    tampilanHelper.deleteById("2")
                    Toast.makeText(this, "bubble dinonaktifkan", Toast.LENGTH_SHORT).show()
                } else {
                    isBubble = false
                }
            }
        }
        binding.switchVideo.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isVideo) {
                    isVideo = true
                } else {
                    isVideo = true
                    val id = "3"
                    val title = "Video"
                    val description = "Open Camera"
                    val img = 2131230880
                    val isSwitch = 0
                    addNewTampilan(id, title, description, img, isSwitch)
                    Toast.makeText(this, "video diaktifkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (isVideo) {
                    isVideo = false
                    tampilanHelper.deleteById("3")
                    Toast.makeText(this, "video dinonaktifkan", Toast.LENGTH_SHORT).show()
                } else {
                    isVideo = false
                }
            }
        }
        binding.switchAudio.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if (isAudio) {
                    isAudio = true
                } else {
                    isAudio = true
                    val id = "4"
                    val title = "Audio"
                    val description = "Audio Record"
                    val img = 2131230878
                    val isSwitch = 0
                    addNewTampilan(id, title, description, img, isSwitch)
                    Toast.makeText(this, "audio diaktifkan", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (isAudio) {
                    isAudio = false
                    tampilanHelper.deleteById("4")
                    Toast.makeText(this, "audio dinonaktifkan", Toast.LENGTH_SHORT).show()
                } else {
                    isAudio = false
                }
            }
        }

    }

    private fun addNewTampilan(id: String, title: String, description: String, img: Int, isSwitch: Int) {
        val values = ContentValues()
        values.put(DatabaseContract.HomeworkColumns._ID, id)
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
//            tampilanHelper.open()

            val id1 = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById("1")
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val id2 = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById("2")
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val id3 = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById("3")
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val id4 = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById("4")
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val _id1 = id1.await() //photo
            val _id2 = id2.await() //bubble
            val _id3 = id3.await() //video
            val _id4 = id4.await() //audio

            if (_id1.size > 0) {
                isPhoto = true
            }
            if (_id2.size > 0) {
                isBubble = true
            }
            if (_id3.size > 0) {
                isVideo = true
            }
            if (_id4.size > 0) {
                isAudio = true
            }

            binding.switchPhoto.isChecked = isPhoto
            binding.switchBubble.isChecked = isBubble
            binding.switchVideo.isChecked = isVideo
            binding.switchAudio.isChecked = isAudio

            val deferredHomework = async(Dispatchers.IO) {
                val cursor = tampilanHelper.queryById2()
                MappingHelper.mapCursorToArrayList(cursor)
            }

            val contact = deferredHomework.await()
            Log.i("TAG", "##### contact $contact")
            if (contact.size > 0) {
                adapter.listContact = contact
                binding.rvContact.adapter = adapter
                Log.i("TAG", "##### adapter.listContact ${adapter.listContact}")
            } else {
                adapter.listContact = ArrayList()
                Log.i("TAG", "##### KOSONG")
            }
//            tampilanHelper.close()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATES, adapter.listContact)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        tampilanHelper.close()
        finish()
    }

    companion object {
        private const val EXTRA_STATES = "EXTRA_STATES"
    }
}