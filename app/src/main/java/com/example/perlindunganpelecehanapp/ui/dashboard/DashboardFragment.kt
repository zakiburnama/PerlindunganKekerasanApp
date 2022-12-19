package com.example.perlindunganpelecehanapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.Kekerasan
import com.example.perlindunganpelecehanapp.MapsActivity
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

                adapterPerlindungan.setOnItemClickCallback(object : PerlindunganAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Kekerasan) {
                        Log.i("TAG", "##### KEYYY ${data.key}")
                        Log.i("TAG", "##### URLLL ${data.url}")
                        val intent = Intent(activity, MapsActivity::class.java)
                            .putExtra(MapsActivity.EXTRA_KEY, data.key)
                            .putExtra(MapsActivity.EXTRA_URL, data.url)
                        startActivity(intent)
                    }
                })

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