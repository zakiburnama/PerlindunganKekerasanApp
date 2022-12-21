package com.example.perlindunganpelecehanapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.perlindunganpelecehanapp.databinding.FragmentHomeBinding
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.*
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
            val intent = Intent(activity, CameraActivity::class.java)
            startActivity(intent)
        }
        binding.button3.setOnClickListener {
            Toast.makeText(context, "Not Avaiable Yet", Toast.LENGTH_SHORT).show()
        }
        binding.button4.setOnClickListener {
            val phoneNumber = "081234567890"
            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
            startActivity(dialPhoneIntent)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}