package com.example.inventorybarang

import com.google.gson.annotations.SerializedName





data class ListDataBarang(
    @SerializedName("id") val id: Int,
    @SerializedName("namabarang") val namabarang: String,
    @SerializedName("harga") val harga: String,
    @SerializedName("satuan") val satuan: String
)


