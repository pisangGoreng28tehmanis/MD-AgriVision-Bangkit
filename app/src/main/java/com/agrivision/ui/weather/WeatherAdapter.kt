package com.agrivision.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.agrivision.R
import com.agrivision.data.response.CuacaItemItem

class WeatherAdapter : ListAdapter<CuacaItemItem, WeatherAdapter.WeatherViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val weather = getItem(position)  // Use getItem() for ListAdapter
        holder.bind(weather)
    }

    // ViewHolder class
    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val temperatureText: TextView = itemView.findViewById(R.id.tv_temperature)
        private val humidityText: TextView = itemView.findViewById(R.id.tv_humidity)
        private val weatherDescText: TextView = itemView.findViewById(R.id.tv_weather_desc)
        private val timeText: TextView = itemView.findViewById(R.id.tv_time)
        private val weatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)

        fun bind(weather: CuacaItemItem) {
            temperatureText.text = "${weather.t}°C"
            humidityText.text = "Kelembapan: ${weather.hu}%"
            weatherDescText.text = weather.weatherDesc
            timeText.text = weather.localDatetime

            // Set icon based on weather description
            val weatherIconResId = when (weather.weatherDescEn.lowercase()) {
                "clear" -> R.drawable.ic_clear
                "cloudy" -> R.drawable.ic_cloudy
                "rainy" -> R.drawable.ic_rain
                "stormy" -> R.drawable.ic_storm
                else -> R.drawable.ic_unknown
            }
            weatherIcon.setImageResource(weatherIconResId)
        }
    }

    // DiffCallback to compare data
    class DiffCallback : DiffUtil.ItemCallback<CuacaItemItem>() {
        override fun areItemsTheSame(oldItem: CuacaItemItem, newItem: CuacaItemItem): Boolean {
            // Compare based on unique identifier, assuming datetime is unique
            return oldItem.datetime == newItem.datetime
        }

        override fun areContentsTheSame(oldItem: CuacaItemItem, newItem: CuacaItemItem): Boolean {
            // Check if the content is the same
            return oldItem == newItem
        }
    }
}
