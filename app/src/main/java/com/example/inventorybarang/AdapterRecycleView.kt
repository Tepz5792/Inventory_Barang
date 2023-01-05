package com.example.inventorybarang

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.inventorybarang.*
import com.google.gson.JsonObject


import kotlinx.android.synthetic.main.item_list.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AdapterRecycleView(private val context: Context, private val getdatalist: MutableList<ListDataBarang>) :
    RecyclerView.Adapter<AdapterRecycleView.ViewHolder>(){

    var jsonObjecthapusdatabarang = JsonObject()
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var id: TextView
        var namabarang1:TextView
        var harga1:TextView
        var satuan1:TextView
        var btnhapus:ImageView
        var btnupdate:ImageView

        init {
            id= itemView.idbarang
            namabarang1 = itemView.nama
            harga1= itemView.harga
            satuan1=itemView.satuan
            btnhapus=itemView.btnhapus
            btnupdate=itemView.btnpencilupdate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.id.text = "ID Barang: " + getdatalist[position].id.toString()
        holder.namabarang1.text = "Nama Barang: " + getdatalist[position].namabarang
        holder.harga1.text = "Harga Barang: " + getdatalist[position].harga
        holder.satuan1.text = "Satuan Barang: " + getdatalist[position].satuan

        holder.btnhapus.setOnClickListener {


            jsonObjecthapusdatabarang.addProperty("idbarang", getdatalist[position].id.toString())
            Log.d("JSONBARANG", jsonObjecthapusdatabarang.toString())

            val retrofit = Retrofit.Builder()
                .baseUrl("https://apikelompok3.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val retrofitAPI = retrofit.create(APIcall::class.java)
            retrofitAPI.deletaDataBarang(jsonObjecthapusdatabarang)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                        Log.d("Gagal: ", t.message.toString())
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        Log.d("HAPUS", response.toString())
                        if (response.isSuccessful) {
                            var msg = response.body()?.string()
                            Log.d("HAPUS", msg.toString())
                            getdatalist.removeAt(holder.absoluteAdapterPosition)
                            notifyDataSetChanged()
                            notifyItemRangeRemoved(holder.absoluteAdapterPosition,getdatalist.size)

                        }
                    }
                })

        }

        holder.btnupdate.setOnClickListener {
            val context=holder.btnhapus.context
            val intent = Intent( context, UpdateBarang::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("id",getdatalist[position].id.toString())
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return getdatalist.size
    }



}




