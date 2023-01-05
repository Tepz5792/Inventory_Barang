package com.example.inventorybarang


import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIcall {
    @GET("getdata")
    fun allData(): Call<MutableList<ListDataBarang>>



//    Untuk input barang
    @POST("insertdata")
    fun postData(@Body body: JsonObject): Call<ResponseBody>


//    Untuk Login
    @POST("logindatauser")
    fun getUser(@Body body: JsonObject): Call<ResponseBody>


    @POST("insertdatauser")
    fun insertUser(@Body body: JsonObject):Call<ResponseBody>


    @HTTP(method = "DELETE", path = "deletedatabarang", hasBody = true)
    fun deletaDataBarang(@Body body: JsonObject):Call<ResponseBody>


    @HTTP(method = "PUT", path = "updatedatabarang", hasBody = true)
    fun updatedataBarang(@Body body: JsonObject):Call<ResponseBody>


}


