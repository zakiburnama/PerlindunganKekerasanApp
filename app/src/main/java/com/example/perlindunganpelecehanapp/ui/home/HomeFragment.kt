package com.example.perlindunganpelecehanapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.perlindunganpelecehanapp.CameraActivity
import com.example.perlindunganpelecehanapp.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.Perlindungan
import com.example.perlindunganpelecehanapp.timeStamp2
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //    val homeViewModel =
        //            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //    val textView: TextView = binding.textHome
        //    homeViewModel.text.observe(viewLifecycleOwner) {
        //      textView.text = it
        //    }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener{
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
//            getAllImage()
        }

        binding.button3.setOnClickListener {
        }
    }


//    private fun getAllImage() = CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val images = storageReference.listAll().await()
//            val imageUrls = mutableListOf<String>()
//            for(image in images.items) {
//                val url = image.downloadUrl.await()
//                imageUrls.add(url.toString())
//                Log.i("TAG", "#### ${image.name}")
//                Log.i("TAG", "#### $url")
//            }
//
//            withContext(Dispatchers.Main) {
////                val animalAdapter = AnimalAdapter(imageUrls)
////                if (animalAdapter.itemCount == 0) {
////                    binding.textViewNoData.visibility = View.VISIBLE
////                }
////                binding.progressLoadList.visibility = View.GONE
////                binding.recyclerViewImage.apply {
////                    adapter = animalAdapter
////                    layoutManager = LinearLayoutManager(this@ShowListPhotoActivity)
////                }
//            }
//        } catch(e: Exception) {
//            withContext(Dispatchers.Main) {
////                binding.progressLoadList.visibility = View.GONE
//                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
//            }
//        }
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}