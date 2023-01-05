package com.example.inventorybarang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UpdateBarang : AppCompatActivity() {
    var jsonObjectUpdate = JsonObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.updatebarang)


        var inputupdateid = findViewById<EditText>(R.id.inputupdateid)
        var inputupdatebarang = findViewById<EditText>(R.id.inputupdatebarang)
        var inputupdateharga = findViewById<EditText>(R.id.inputupdateharga)
        var inputupdatesatuan = findViewById<EditText>(R.id.inputupdatesatuan)
        var btnselesai = findViewById<Button>(R.id.buttonkirim)

//        Tampil data dari Recyler View ambil data ID
        var iddata=intent.getStringExtra("id")
        inputupdateid.setText(iddata)

        btnselesai.setOnClickListener {

            jsonObjectUpdate.addProperty("namabarangupdate", inputupdatebarang.text.toString())
            jsonObjectUpdate.addProperty("hargabarangupdate", inputupdateharga.text.toString())
            jsonObjectUpdate.addProperty("satuanbarangupdate", inputupdatesatuan.text.toString())
            jsonObjectUpdate.addProperty("idbarang", inputupdateid.text.toString())

            val retrofit = Retrofit.Builder()
                .baseUrl("https://apikelompok3.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()


            val retrofitAPI = retrofit.create(APIcall::class.java)


            retrofitAPI.updatedataBarang(jsonObjectUpdate).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@UpdateBarang, "Koneksi internet bermasalah, coba lagi ", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@UpdateBarang, "Sukses", Toast.LENGTH_LONG).show()

                        intent= Intent(this@UpdateBarang,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            })
        }
    }
}