package com.example.perlindunganpelecehanapp.ui.notifications

import android.content.Intent
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
    private val list = ArrayList<Tampilan>()
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
//                val intent =
//                    Intent(this@MainActivity, AddHomeworkActivity::class.java)
//                intent.putExtra(AddHomeworkActivity.EXTRA_HOMEWORK, selectedHomework)
//                intent.putExtra(AddHomeworkActivity.EXTRA_POSITION, position)
//                resultLauncher.launch(intent)
                Toast.makeText(context, "KLIK", Toast.LENGTH_SHORT).show()
                Log.i("TAG", "##### KLIK")
            }
        })
        binding.rvPerlindungan.adapter = adapter


//        list.addAll(listTampilan)
//        showRecycler()
        loadHomeworkAsync()

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

//    private val listTampilan: ArrayList<Tampilan>
//    get() {
//        val dataTitle = resources.getStringArray(R.array.title)
//        val dataDesc = resources.getStringArray(R.array.description)
//        val dataIcons = resources.obtainTypedArray(R.array.icons)
//        val dataIsSwitch = resources.getIntArray(R.array.isSwitch)
//
//        val listTampilanku = ArrayList<Tampilan>()
//        for (i in dataTitle.indices) {
//            val data = Tampilan(dataTitle[i], dataDesc[i], dataIcons.getResourceId(i, -1), dataIsSwitch[i])
//            listTampilanku.add(data)
//        }
//
//        return listTampilanku
//    }
//
//    private fun showRecycler() {
//        val adapter = TampilanAdapter(list)
//        binding.rvPerlindungan.adapter = adapter
//        adapter.setOnItemClickCallback(object : TampilanAdapter.OnItemClickCallback {
//            override fun onItemClicked(data: Tampilan) {
//                Toast.makeText(context, "Kamu memilih " + data.title, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}