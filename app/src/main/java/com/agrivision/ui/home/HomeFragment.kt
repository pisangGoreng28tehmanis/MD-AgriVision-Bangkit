package com.agrivision.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.agrivision.ui.weather.WeatherActivity
import com.agrivision.utils.fetchLatestWeatherForecast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.

    private val binding get() = _binding!!
    private lateinit var rvTrick: RecyclerView
    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false -> {
                    getMyLastLocation()
                }
                else -> {
                    Toast.makeText(requireActivity(),"Anda belum mengizinkan lokasi", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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
        binding.btnCuaca.setOnClickListener {
            val intent = Intent(requireActivity(), WeatherActivity::class.java)
            startActivity(intent)
        }
        showRecyclerList()
        getMyLastLocation()

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

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireActivity(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun getMyLastLocation() {
        if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ){
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null){
                    getWeatherData(location.latitude,location.longitude)
                } else {
                    Toast.makeText(requireActivity(), "Gagal mendapatkan lokasi", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(requireActivity(), "Gagal mendapatkan lokasi: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }
    fun getWeatherData(lat: Double, long: Double){
        if(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                lifecycleScope.launch {
                    try {
                        // Panggil fetchLatestWeatherForecast dengan latitude dan longitude
                        val (cityName, temperature, description, humidity) = fetchLatestWeatherForecast(
                            lat,
                            long
                        )

                        binding.tvCity.text = cityName
                        binding.tvTemperature.text = temperature
                        when (description) {
                            "light rain" -> binding.tvDescription.text = "Hujan Ringan"
                            "overcast clouds" -> binding.tvDescription.text = "Mendung"
                            "moderate rain" -> binding.tvDescription.text = "Hujan Lebat"
                            "broken clouds" -> binding.tvDescription.text = "Berawan"
                            else -> binding.tvDescription.text = "Cerah"
                        }
                        binding.tvHumidity.text = humidity

                        when (description) {
                            "light rain" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_rain_home)
                            "overcast clouds" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_cloudy_home)
                            "moderate rain" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_storm_home)
                            "broken clouds" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_morning_cloud)
                            else -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_clear_home)
                        }


                    } catch (e: Exception) {
                        Toast.makeText(requireActivity(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}