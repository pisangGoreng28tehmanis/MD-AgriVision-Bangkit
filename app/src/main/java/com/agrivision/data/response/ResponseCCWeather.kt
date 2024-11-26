package com.agrivision.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCCWeather(

	@field:SerializedName("kota")
	val kota: String,

	@field:SerializedName("ramalan")
	val ramalan: List<RamalanItem>
)

data class RamalanItem(

	@field:SerializedName("tanggal")
	val tanggal: String,

	@field:SerializedName("ramalan")
	val ramalan: List<RamalanItem>,

	@field:SerializedName("waktu")
	val waktu: String,

	@field:SerializedName("suhu")
	val suhu: String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("kelembapan")
	val kelembapan: String
)
