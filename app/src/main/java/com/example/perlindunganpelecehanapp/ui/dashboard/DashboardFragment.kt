package com.example.perlindunganpelecehanapp.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.perlindunganpelecehanapp.Kekerasan
import com.example.perlindunganpelecehanapp.PerlindunganAdapter
import com.example.perlindunganpelecehanapp.databinding.FragmentDashboardBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DashboardFragment : Fragment() {

private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val storageReference = FirebaseStorage.getInstance().getReference("uploads")

    init {
        getAllImage()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
//        val dashboardViewModel =
//                ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner) {
//          textView.text = it
//        }

        return root
    }



    private fun getAllImage() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val images = storageReference.listAll().await()
            val listPerlindungan = ArrayList<Kekerasan>()
            for(image in images.items) {
                val url = image.downloadUrl.await()
                listPerlindungan.add(Kekerasan(image.name, url.toString()))
            }

            withContext(Dispatchers.Main) {
                val adapterPerlindungan = PerlindunganAdapter(listPerlindungan)
                binding.rvPerlindungan.adapter = adapterPerlindungan
                binding.rvPerlindungan.apply {
                    adapter = adapterPerlindungan
                    layoutManager = LinearLayoutManager(context)
                }
//                val animalAdapter = AnimalAdapter(imageUrls)
//                if (animalAdapter.itemCount == 0) {
//                    binding.textViewNoData.visibility = View.VISIBLE
//                }
//                binding.progressLoadList.visibility = View.GONE
//                binding.recyclerViewImage.apply {
//                    adapter = animalAdapter
//                    layoutManager = LinearLayoutManager(this@ShowListPhotoActivity)
//                }
            }
        } catch(e: Exception) {
            withContext(Dispatchers.Main) {
//                binding.progressLoadList.visibility = View.GONE
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}