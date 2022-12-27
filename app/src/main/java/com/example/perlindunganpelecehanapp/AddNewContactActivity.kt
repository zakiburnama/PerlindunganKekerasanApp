package com.example.perlindunganpelecehanapp

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.perlindunganpelecehanapp.databinding.ActivityAddNewCallBinding
import com.example.perlindunganpelecehanapp.db.DatabaseContract
import com.example.perlindunganpelecehanapp.db.TampilanHelper

class AddNewContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewCallBinding
    private lateinit var tampilanHelper: TampilanHelper

    private var isEdit = false
    private var home: Home? = null
    private var _id: String = "0"
    private var position: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewCallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tampilanHelper = TampilanHelper.getInstance(applicationContext)
        tampilanHelper.open()

        home = intent.getParcelableExtra(EXTRA_HOMEWORK)
        if (home != null){
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            Log.i("TAG", "#### alert onCre $position")
            isEdit = true
            binding.btnDelete.isEnabled = true
            binding.btnSubmit.text = "EDIT"
            supportActionBar?.title = "EDIT"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            home?.let {
                _id = it.id.toString()
                binding.edtTitle.setText(it.title)
                binding.edtDescription.setText(it.description)
                binding.btnDelete.setOnClickListener {
                    val result = tampilanHelper.deleteById(_id)
                    if (result > 0) {
                        Toast.makeText(this, "Berhasil menghapus data", Toast.LENGTH_SHORT).show()
                        val intent = Intent()
                        intent.putExtra(EXTRA_POSITION, position)
                        Log.i("TAG", "#### alert del $position")
                        setResult(RESULT_DELETE, intent)
                        finish()
                    } else
                        Toast.makeText(this, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else
            home = Home()

        binding.btnSubmit.setOnClickListener { addNewTampilan() }
    }

    private fun addNewTampilan() {
        val name = binding.edtTitle.text.toString().trim()
        val phone = binding.edtDescription.text.toString().trim()
        val img = 2131230877
        val isSwitch = 0

        if (name.isEmpty()) {
            binding.edtTitle.error = "Title tidak boleh kosong"
            return
        }

        val intent = Intent()
            .putExtra(EXTRA_HOMEWORK, home)
            .putExtra(EXTRA_POSITION, position)
        Log.i("TAG", "#### alert add $position")

        val values = ContentValues()
        values.put(DatabaseContract.HomeworkColumns.TITLE, name)
        values.put(DatabaseContract.HomeworkColumns.DESCRIPTION, phone)
        values.put(DatabaseContract.HomeworkColumns.IMG, img)
        values.put(DatabaseContract.HomeworkColumns.ISSWITCH, isSwitch)

        if (isEdit) {
            val result = tampilanHelper.update(_id, values)
            if (result > 0) {
                Toast.makeText(this, "Berhasil memperbaharui data", Toast.LENGTH_SHORT).show()
                setResult(RESULT_UPDATE, intent)
                finish()
            }
            else
                Toast.makeText(this, "Gagal memperbaharui data", Toast.LENGTH_SHORT).show()
        } else {
            val result = tampilanHelper.insert(values)
            if (result > 0) {
                Toast.makeText(this, "Berhasil menambah data", Toast.LENGTH_SHORT).show()
                setResult(RESULT_ADD, intent)
                finish()
            }
            else
                Toast.makeText(this, "Gagal menambah data", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        finish()
    }

    companion object {
        const val EXTRA_HOMEWORK = "extra_homework"
        const val EXTRA_POSITION = "extra_position"
        const val RESULT_ADD = 101
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

}