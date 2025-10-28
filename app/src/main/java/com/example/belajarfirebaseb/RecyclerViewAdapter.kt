package com.example.belajarfirebaseb

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val listMahasiswa: ArrayList<data_mahasiswa>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val NIM: TextView = itemView.findViewById(R.id.nimx)
        val Nama: TextView = itemView.findViewById(R.id.namax)
        val Jurusan: TextView = itemView.findViewById(R.id.jurusanx)
        val ListItem: LinearLayout = itemView.findViewById(R.id.list_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Membuat View untuk Menyiapkan & Memasang Layout yang digunakan pada RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.view_design,
            parent,
            false
        )
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        // Mengambil Nilai/Value pada RecyclerView berdasarkan Posisi Tertentu
        val mahasiswa = listMahasiswa[position]
        val nim = mahasiswa.nim
        val nama = mahasiswa.nama
        val jurusan = mahasiswa.jurusan

        // Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.NIM.text = "NIM: $nim"
        holder.Nama.text = "Nama: $nama"
        holder.Jurusan.text = "Jurusan: $jurusan"

        // Set OnLongClickListener untuk menampilkan dialog Update/Delete
        holder.ListItem.setOnLongClickListener { view ->
            val action = arrayOf("Update", "Delete")
            val alert = AlertDialog.Builder(view.context)
            alert.setItems(action) { dialog, i ->
                when (i) {
                    0 -> {
                        /* Berpindah Activity pada halaman layout updateData dan mengambil data pada listMahasiswa, berdasarkan posisinya untuk dikirim pada activity selanjutnya */
                        val bundle = Bundle()
                        bundle.putString("dataNIM", listMahasiswa[position].nim)
                        bundle.putString("dataNama", listMahasiswa[position].nama)
                        bundle.putString("dataJurusan", listMahasiswa[position].jurusan)
                        bundle.putString("getPrimaryKey", listMahasiswa[position].key)
                        val intent = Intent(view.context, UpdateData::class.java)
                        intent.putExtras(bundle)
                        context.startActivity(intent)
                    }
                    1 -> {
                        // Menggunakan interface untuk mengirim data mahasiswa yang akan dihapus
                        listener?.onDeleteData(listMahasiswa[position], position)
                    }
                }
            }
            alert.create()
            alert.show()
            true
        }
    }

    override fun getItemCount(): Int {
        // Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return listMahasiswa.size
    }

    // Membuat Interface
    interface dataListener {
        fun onDeleteData(data: data_mahasiswa?, position: Int)
    }

    // Deklarasi objek dari Interface
    var listener: dataListener? = null

    // Membuat Konstruktor, untuk menerima input dari Database
    init {
        this.listener = context as MyListData
    }
}