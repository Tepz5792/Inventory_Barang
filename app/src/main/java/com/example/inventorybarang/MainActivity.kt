package com.example.inventorybarang



import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(){
    // creating variables for our textview,
    // imageview,cardview and progressba

    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        var recyclerView: RecyclerView = findViewById(R.id.my_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        val retrofit = Retrofit.Builder()
       .baseUrl("https://apikelompok3.pythonanywhere.com/")
        .addConverterFactory(GsonConverterFactory.create())
       .build()

        val retrofitAPI = retrofit.create(APIcall::class.java)

        val call: Call<MutableList<ListDataBarang>> = retrofitAPI.allData()
        call.enqueue(object: Callback<MutableList<ListDataBarang>>{
            override fun onResponse(call: Call<MutableList<ListDataBarang>>, response: Response<MutableList<ListDataBarang>>) {
                val responseBody = response?.body()!!
                Log.d("aaaaaaa",responseBody.toString())

                val myAdapter = AdapterRecycleView(baseContext,responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerView.adapter=myAdapter


            }
            override fun onFailure(call: Call<MutableList<ListDataBarang>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi internet bermasalah, coba lagi ", Toast.LENGTH_LONG).show()

            }
        })











        var tambahdata: ImageView
        tambahdata=findViewById(R.id.tambahdata)
        tambahdata.setOnClickListener {
            intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
            finish()
        }
    }




}

