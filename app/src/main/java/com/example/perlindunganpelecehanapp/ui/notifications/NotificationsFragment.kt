package com.example.perlindunganpelecehanapp.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.perlindunganpelecehanapp.R
import com.example.perlindunganpelecehanapp.Tampilan
import com.example.perlindunganpelecehanapp.TampilanAdapter
import com.example.perlindunganpelecehanapp.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val list = ArrayList<Tampilan>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //    val notificationsViewModel =
        //            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //    val textView: TextView = binding.textNotifications
        //    notificationsViewModel.text.observe(viewLifecycleOwner) {
        //      textView.text = it
        //    }

        list.addAll(listTampilan)
        showRecycler()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private val listTampilan: ArrayList<Tampilan>
    get() {
        val dataTitle = resources.getStringArray(R.array.title)
        val dataDesc = resources.getStringArray(R.array.description)
        val dataIcons = resources.obtainTypedArray(R.array.icons)
        val dataIsSwitch = resources.getIntArray(R.array.isSwitch)

        val listTampilanku = ArrayList<Tampilan>()
        for (i in dataTitle.indices) {
            val data = Tampilan(dataTitle[i], dataDesc[i], dataIcons.getResourceId(i, -1), dataIsSwitch[i])
            listTampilanku.add(data)
        }

        return listTampilanku
    }

    private fun showRecycler() {

        val adapter = TampilanAdapter(list)
        binding.rvPerlindungan.adapter = adapter
        adapter.setOnItemClickCallback(object : TampilanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Tampilan) {
                Toast.makeText(context, "Kamu memilih " + data.title, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}