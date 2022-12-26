package com.example.perlindunganpelecehanapp.ui.notifications

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.perlindunganpelecehanapp.*
import com.example.perlindunganpelecehanapp.databinding.FragmentNotificationsBinding
import com.example.perlindunganpelecehanapp.db.TampilanHelper
import com.example.perlindunganpelecehanapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //    val notificationsViewModel =
        //            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //    val textView: TextView = binding.textNotifications
        //    notificationsViewModel.text.observe(viewLifecycleOwner) {
        //      textView.text = it
        //    }

//        binding.rvPerlindungan.layoutManager = LinearLayoutManager(context)
//        binding.rvPerlindungan.setHasFixedSize(true)

        adapter = HomeAdapter(object : HomeAdapter.OnItemClickCallback {
            override fun onItemClicked(selectedHomework: Home?, position: Int?) {
                if (selectedHomework != null) {
                    when (selectedHomework.id) {
                        1 -> {
                            val intent = Intent(context, CameraActivity::class.java)
                            startActivity(intent)
                        }
                        2 -> {
                            val intent = Intent(context, CameraActivity::class.java)
                            startActivity(intent)
                        }
                        3 -> {
                            val intent = Intent(context, CameraActivity::class.java)
                            startActivity(intent)
                        }
                        4 -> {
                            val intent = Intent(context, CameraActivity::class.java)
                            startActivity(intent)
                        }
                        else -> {
                            val number = selectedHomework.description
                            val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
                            startActivity(dialPhoneIntent)
                        }
                    }
                }
            }
        })
        binding.rvPerlindungan.adapter = adapter

        if (savedInstanceState == null) {
            loadHomeworkAsync()
        } else {
            val list = savedInstanceState.getParcelableArrayList<Home>(EXTRA_STATE)
            if (list != null)
                adapter.listHome = list
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadHomeworkAsync() {
        lifecycleScope.launch {
            val tampilanHelper = activity?.let { TampilanHelper.getInstance(it.applicationContext) }
            if (tampilanHelper != null) {
                tampilanHelper.open()
                val deferredHomework = async(Dispatchers.IO) {
                    val cursor = tampilanHelper.queryAll()
                    MappingHelper.mapCursorToArrayList(cursor)
                }

                val tampilan = deferredHomework.await()
                if (tampilan.size > 0) {
                    adapter.listHome = tampilan
                } else {
                    adapter.listHome = ArrayList()
                }
                tampilanHelper.close()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listHome)
    }

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }
}