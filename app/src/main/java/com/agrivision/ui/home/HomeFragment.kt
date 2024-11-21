package com.agrivision.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrivision.R
import com.agrivision.data.News
import com.agrivision.databinding.FragmentHomeBinding
import com.agrivision.ui.FormFertilizerActivity
import com.agrivision.ui.artikel.ArtikelFragment
import com.agrivision.ui.artikel.ListNewsAdapter
import com.agrivision.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private lateinit var rvTrick: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvTrick = binding.rvTrick
        rvTrick.setHasFixedSize(true)

//        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }
        binding.btnPupuk.setOnClickListener {
            val Intent = Intent(requireActivity(), FormFertilizerActivity::class.java)
            startActivity(Intent)
        }
        showRecyclerList()
        return root
    }
    private fun getListTrick(): ArrayList<News> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHero = ArrayList<News>()
        for (i in dataName.indices) {
            val hero = News(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHero.add(hero)  // Fixed incorrect variable name from 'news' to 'hero'
        }
        return listHero
    }
    fun showRecyclerList() {
        val list = getListTrick()  // Get the list of heroes
        rvTrick.layoutManager = LinearLayoutManager(context)
        val listNewsAdapter = ListNewsAdapter(list)
        rvTrick.adapter = listNewsAdapter

        // Handle item click
        listNewsAdapter.onItemClick = { news ->
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("EXTRA_NAME", news.name)
                putExtra("EXTRA_DESCRIPTION", news.description)
                putExtra("EXTRA_PHOTO", news.photo)
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}