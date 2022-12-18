package com.example.perlindunganpelecehanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import com.example.perlindunganpelecehanapp.databinding.ActivityCameraBinding
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.captureImage.setOnClickListener { takePhoto() }
        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun takePhoto() {
        // takePhoto
        val imageCapture = imageCapture ?: return

        val photoFile = createFile(application)

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {

                    val uriFile = photoFile.toUri()
                    val nameFile = photoFile.name

                    storageReference.child(nameFile).putFile(uriFile).addOnSuccessListener(
                        object : ValueEventListener,
                            OnSuccessListener<UploadTask.TaskSnapshot>
                    {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            TODO("Not yet implemented")
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
                            saveData(nameFile)
//                            storageReference.child(nameFile).putFile(uriFile)
//                            Log.i("TAG", "##### isSuccessful ${p0?.storage?.downloadUrl?.isSuccessful}")
//                            Log.i("TAG", "##### isComplete ${p0?.storage?.downloadUrl?.isComplete}")
//                            Log.i("TAG", "##### isCanceled ${p0?.storage?.downloadUrl?.isCanceled}")
//                            Log.i("TAG", "##### exception ${p0?.storage?.downloadUrl?.exception}")
                        }
                    })

//                    Log.i("TAG", "##### onSuccess ${storageReference.downloadUrl}")
//
//                    val intent = Intent()
//                    intent.putExtra("picture", photoFile)
//                    intent.putExtra(
//                        "isBackCamera",
//                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                    )
//                    setResult(MainActivity.CAMERA_X_RESULT, intent)
//
//                    finish()
                }
            }
        )
    }

    private fun saveData(key: String) {
        Log.i("TAG", "##### saveData $key")
        database = Firebase.database.reference.child("data")

        val date = timeStamp2
        val position = "000"
        val isImage = true
        val isAudio = false
        val isVideo = false

        val data = Perlindungan( key, date, position, isImage, isAudio, isVideo )

        database.child(key).setValue(data)
        Log.i("TAG", "##### data $data")

        finish()
    }

    private fun startCamera() {
        // showCamera
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )

            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}