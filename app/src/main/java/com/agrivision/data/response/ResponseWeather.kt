package com.agrivision.data.response

import com.google.gson.annotations.SerializedName

data class ResponseWeather(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("lokasi")
	val lokasi: Lokasi
)

data class CuacaItemItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("vs_text")
	val vsText: String,

	@field:SerializedName("analysis_date")
	val analysisDate: String,

	@field:SerializedName("time_index")
	val timeIndex: String,

	@field:SerializedName("local_datetime")
	val localDatetime: String,

	@field:SerializedName("wd_deg")
	val wdDeg: Int, // Arah angin dalam derajat (0-360)

	@field:SerializedName("wd")
	val wd: String, // Arah angin dalam teks (misalnya, "Utara")

	@field:SerializedName("hu")
	val hu: Int, // Kelembapan (%)

	@field:SerializedName("utc_datetime")
	val utcDatetime: String, // Waktu UTC

	@field:SerializedName("datetime")
	val datetime: String, // Waktu lokal dalam format ISO

	@field:SerializedName("t")
	val t: Double, // Suhu udara (°C)

	@field:SerializedName("tcc")
	val tcc: Int, // Tutupan awan (%)

	@field:SerializedName("weather_desc_en")
	val weatherDescEn: String, // Deskripsi cuaca (Bahasa Inggris)

	@field:SerializedName("wd_to")
	val wdTo: String, // Arah angin ke

	@field:SerializedName("weather")
	val weather: Int, // Kode cuaca

	@field:SerializedName("weather_desc")
	val weatherDesc: String, // Deskripsi cuaca (Bahasa Indonesia)

	@field:SerializedName("tp")
	val tp: Double?, // Curah hujan (jika ada)

	@field:SerializedName("ws")
	val ws: Double?, // Kecepatan angin (km/jam)

	@field:SerializedName("vs")
	val vs: Double // Jarak pandang (km)
)

data class Lokasi(

	@field:SerializedName("provinsi")
	val provinsi: String,

	@field:SerializedName("desa")
	val desa: String,

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("adm3")
	val adm3: String,

	@field:SerializedName("adm2")
	val adm2: String,

	@field:SerializedName("timezone")
	val timezone: String,

	@field:SerializedName("adm4")
	val adm4: String,

	@field:SerializedName("kecamatan")
	val kecamatan: String,

	@field:SerializedName("adm1")
	val adm1: String,

	@field:SerializedName("lon")
	val lon: Double?, // Longitude

	@field:SerializedName("lat")
	val lat: Double?, // Latitude

	@field:SerializedName("kotkab")
	val kotkab: String,

	@field:SerializedName("type")
	val type: String
)

data class DataItem(
	@field:SerializedName("cuaca")
	val cuaca: List<List<CuacaItemItem>>, // Revisi: Array dua dimensi
	@field:SerializedName("lokasi")
	val lokasi: Lokasi
)
