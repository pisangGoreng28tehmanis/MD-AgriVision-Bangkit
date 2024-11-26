package com.agrivision.ui.weather

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agrivision.R
import com.agrivision.data.response.RamalanItem
import com.agrivision.databinding.ItemWeatherBinding

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private val weatherList = ArrayList<RamalanItem>()

    fun setWeatherList(list: List<RamalanItem>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val binding = ItemWeatherBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WeatherViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(weatherList[position])
    }

    override fun getItemCount(): Int = weatherList.size

    inner class WeatherViewHolder(private val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RamalanItem) {
            Log.d("WeatherAdapter", "Binding item: ${item.tanggal}, ${item.waktu}, ${item.suhu}, ${item.deskripsi}, ${item.kelembapan}")
            binding.tvDate.text = item.tanggal
            binding.tvTime.text = item.waktu
            binding.tvTemperature.text = item.suhu
            binding.tvDescription.text = item.deskripsi
            binding.tvHumidity.text = item.kelembapan
            // Menampilkan Kota
            /*binding.tvCity.text = item.kota // Pastikan item.kota ada di data model*/

            // Menentukan ikon berdasarkan deskripsi cuaca
            when (item.deskripsi) {
                "light rain" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_rain)
                "overcast clouds" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_cloudy)
                "moderate rain" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_storm)
                "broken clouds" -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_clock_local)
                else -> binding.ivWeatherIcon.setImageResource(R.drawable.ic_clear)
            }
        }
    }
}
