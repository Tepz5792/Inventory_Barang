package com.example.inventorybarang

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.inventorybarang.R
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;


class Login : AppCompatActivity() {
    var jsonObjectlogin = JsonObject()
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
//





//        Deklarasikan Edit text dan button


        var inputemail = findViewById<EditText>(R.id.inputEmail)
        var inputpassword = findViewById<EditText>(R.id.inputPassword)

        var btnlogin = findViewById<Button>(R.id.btnLogin)


        var getemail: EditText = findViewById(R.id.getdataemail)
        var getpassword: EditText = findViewById(R.id.getdatapassword)

        //       ke Form Daftar
        var redirecttodaftar = findViewById<TextView>(R.id.daftarintent)
        redirecttodaftar.setOnClickListener {
            intent = Intent(this@Login, Register::class.java)
            startActivity(intent)
        }

            btnlogin.setOnClickListener {
                getemail.text = inputemail.text
                getpassword.text = inputpassword.text


                jsonObjectlogin.addProperty("email_user", getemail.text.toString())
                jsonObjectlogin.addProperty("password_user", getpassword.text.toString())

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://apikelompok3.pythonanywhere.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val retrofitAPI = retrofit.create(APIcall::class.java)
                    retrofitAPI.getUser(jsonObjectlogin).enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@Login, "Koneksi internet bermasalah, coba lagi ", Toast.LENGTH_LONG).show()
                        }

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                var msg = response.body()?.string()
                                var message = msg.toString()
                                var key = "82jkfn0k0qo7l0k9"

                                Log.d("RESPON", message)

                                if (message == key) {
                                    intent = Intent(this@Login, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                                else{
                                    Toast.makeText(applicationContext, "Username atau Password Salah", Toast.LENGTH_LONG).show()
                                }

                            }
                        }
                    })



            }
    }

}









