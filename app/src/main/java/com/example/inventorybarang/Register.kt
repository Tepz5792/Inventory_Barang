package com.example.inventorybarang

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inventorybarang.R
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Register: AppCompatActivity() {
    var jsonObjectRegister = JsonObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        var redirectlogin=findViewById<TextView>(R.id.txtLogin)
        redirectlogin.setOnClickListener {
            intent= Intent(applicationContext,Login::class.java)
            startActivity(intent)
        }

        //        Deklarasikan Edit text dan button

        var inputusername= findViewById<EditText>(R.id.registernama)
        var inputemail = findViewById<EditText>(R.id.registeremail)
        var inputpassword = findViewById<EditText>(R.id.registerpassword)
        var btndaftar = findViewById<Button>(R.id.btnDaftar)


        var getusername: EditText = findViewById(R.id.getregisterusername)
        var getemail: EditText = findViewById(R.id.getregisteremail)
        var getpassword:EditText=findViewById(R.id.getregisterpassword)



        btndaftar.setOnClickListener {

            getusername.text=inputusername.text
            getemail.text=inputemail.text
            getpassword.text=inputpassword.text

            jsonObjectRegister.addProperty("username", getusername.text.toString())
            jsonObjectRegister.addProperty("email", getemail.text.toString())
            jsonObjectRegister.addProperty("password", getpassword.text.toString())
            Log.d("JSON",jsonObjectRegister.toString())


            val retrofit = Retrofit.Builder()
                .baseUrl("https://apikelompok3.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val retrofitAPI = retrofit.create(APIcall::class.java)
            retrofitAPI.insertUser(jsonObjectRegister).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@Register, "Koneksi internet bermasalah, coba lagi ", Toast.LENGTH_LONG).show()
                }
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    Log.d("RESPON", response.toString())
                    if (response.isSuccessful) {
                        if (response.isSuccessful) {
                            var msg = response.body()?.string()
                            var message = msg.toString()
                            Log.d("RESPON", msg.toString())
                            var key = "7jo97ksb301mla03"



                            if (message == key) {
                                intent = Intent(this@Register,Login::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(applicationContext, msg, Toast.LENGTH_LONG).show()
                            }

                        }
                    }
                }
            })

        }
    }
}