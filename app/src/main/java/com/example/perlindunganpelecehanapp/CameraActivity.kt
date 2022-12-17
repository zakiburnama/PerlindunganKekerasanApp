package com.example.perlindunganpelecehanapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import com.example.perlindunganpelecehanapp.databinding.ActivityCameraBinding
import android.content.Intent
import android.util.Log
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
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.File

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")

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

//                    Log.i("TAG", "#### name ${photoFile.name}")
//                    Log.i("TAG", "#### path ${photoFile.path}")
//
//                    val x = storageReference.child("18-Dec-2022.jpg")
//                    Log.i("TAG", "#### ${x}")


                    val myFile = photoFile.toUri()
                    storageReference.child(photoFile.name).putFile(myFile)

//                    storageReference.child(photoFile.name).putFile(myFile).addOnSuccessListener(object : ValueEventListener,
//                        OnSuccessListener<UploadTask.TaskSnapshot> {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            TODO("Not yet implemented")
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            TODO("Not yet implemented")
//                        }
//
//                        override fun onSuccess(p0: UploadTask.TaskSnapshot?) {
//                            Log.i("TAG", "##### onSuccess P $p0")
//                            val x = p0?.storage?.downloadUrl.toString()
//                            Log.i("TAG", "##### onSuccess X $x")
//                            val y = p0?.storage?.downloadUrl?.result
//                            Log.i("TAG", "##### onSuccess Y $y")
//                            val z = p0?.storage?.downloadUrl?.result.toString()
//                            Log.i("TAG", "##### onSuccess Z $z")
//                        }
//                    })



//                    Log.i("TAG", "##### onSuccess ${storageReference.downloadUrl}")

//                    val intent = Intent()
//                    intent.putExtra("picture", photoFile)
//                    intent.putExtra(
//                        "isBackCamera",
//                        cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA
//                    )
//                    setResult(MainActivity.CAMERA_X_RESULT, intent)

                    finish()
                }
            }
        )
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