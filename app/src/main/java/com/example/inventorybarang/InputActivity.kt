package com.example.inventorybarang

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InputActivity : AppCompatActivity() {


    var jsonObj = JsonObject()

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)
//        Deklarasikan Edit text dan button

        val inputid = findViewById<EditText>(R.id.inputid)
        val inputbarang = findViewById<EditText>(R.id.inputbarang)
        val inputharga = findViewById<EditText>(R.id.inputharga)
        val inputsatuan = findViewById<EditText>(R.id.inputsatuan)
        val btnselesai = findViewById<Button>(R.id.buttonkirim)


//        Deklarasikan pesan di input data
        val salahid = findViewById<TextView>(R.id.salahid)
        val salahbarang = findViewById<TextView>(R.id.salahbarang)
        val salahharga = findViewById<TextView>(R.id.salahharga)
        val salahsatuan = findViewById<TextView>(R.id.salahsatuan)




//        Terima data dari input use


            btnselesai.setOnClickListener {

                val pattern1 = Regex("^[1-9]\\d*\$")
                        if (inputid.text.isEmpty()) {
                            salahid.text = "ID masih kosong"
                        }

                        else if (inputbarang.text.isEmpty()) {
                            salahbarang.text = "Nama barang masih kosong"
                        }
                        else if (inputharga.text.isEmpty()) {
                            salahharga.text = "Harga barang masih kosong"
                        }
                        else if (inputsatuan.text.isEmpty()) {
                            salahsatuan.text = "Satuan barang masih kosong"
                        }

                         else if(!inputid.text.matches("^[1-9]\\d*\$".toRegex())) {

                             salahid.text="ID Tidak boleh dimulai dari angka 0"

                         }




                        else {
                            val getinputid = inputid.text.toString()
                            val getinputnama = inputbarang.text.toString()
                            val getinputharga = inputharga.text.toString()
                            val getinputsatuanbarang = inputsatuan.text.toString()
                            jsonObj.addProperty("id", getinputid)
                            jsonObj.addProperty("namabarang", getinputnama)
                            jsonObj.addProperty("hargabarang", getinputharga)
                            jsonObj.addProperty("satuanbarang", getinputsatuanbarang)


                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()


                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://apikelompok3.pythonanywhere.com/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()


                            val retrofitAPI = retrofit.create(APIcall::class.java)


                            retrofitAPI.postData(jsonObj).enqueue(object : Callback<ResponseBody> {
                                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                                    Toast.makeText(this@InputActivity, "Koneksi Internet Bermasalah", Toast.LENGTH_LONG).show()
                                }

                                override fun onResponse(
                                    call: Call<ResponseBody>,
                                    response: Response<ResponseBody>
                                ) {
                                    if (response.isSuccessful) {
                                        val msg = response.body()?.string()
                                        println("---TTTT :: POST msg from server :: " + msg)
                                        Log.d("hasil: ", msg.toString())
                                    }
                                }
                            })
                        }
                    }

    }
}




