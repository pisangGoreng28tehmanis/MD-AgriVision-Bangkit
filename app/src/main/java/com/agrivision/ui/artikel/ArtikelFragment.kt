package com.agrivision.ui.artikel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.agrivision.R
import com.agrivision.data.News
import com.agrivision.databinding.FragmentArtikelBinding
import com.agrivision.ui.detail.DetailActivity

class ArtikelFragment : Fragment() {

    private var _binding: FragmentArtikelBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvNews: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val artikelViewModel =
            ViewModelProvider(this).get(ArtikelViewModel::class.java)

        _binding = FragmentArtikelBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvNews = binding.rvNews  // Initialize RecyclerView from the binding
        rvNews.setHasFixedSize(true)

        // Show the list of articles
        showRecyclerList()

        return root
    }

    private fun getListHeroes(): ArrayList<News> {
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

    private fun showRecyclerList() {
        val list = getListHeroes()  // Get the list of heroes
        rvNews.layoutManager = LinearLayoutManager(context)
        val listNewsAdapter = ListNewsAdapter(list)
        rvNews.adapter = listNewsAdapter

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
