package com.example.belajarfirebaseb

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.belajarfirebaseb.databinding.ActivityMainBinding
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inisialisasi ID (Button)
        binding.logout.setOnClickListener(this)
        binding.save.setOnClickListener(this)
        binding.showdata.setOnClickListener(this)
        //Mendapatkan Instance Firebase Autentifikasi
        auth = FirebaseAuth.getInstance()

    }

    private fun isEmpty(s: String): Boolean {
        return TextUtils.isEmpty(s)
    }

    override fun onClick(p0: View?) {
        when (p0?.getId()) {
            R.id.save -> {
                // Statement program untuk simpan data
                //Mendapatkan UserID dari pengguna yang Terautentikasi
                val getUserID = auth!!.currentUser!!.uid

                //Mendapatkan Instance dari Database
                val database = FirebaseDatabase.getInstance()

                //Menyimpan Data yang diinputkan User kedalam Variable
                val getNIM: String = binding.nim.getText().toString()
                val getNama: String = binding.nama.getText().toString()
                val getJurusan: String = binding.jurusan.getText().toString()

                // Mendapatkan Referensi dari Database
                val getReference: DatabaseReference
                getReference = database.reference

                // Mengecek apakah ada data yang kosong
                if (isEmpty(getNIM) || isEmpty(getNama) || isEmpty(getJurusan)) {
                    //Jika Ada, maka akan menampilkan pesan singkan seperti berikut ini.
                    Toast.makeText(this@MainActivity, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
                } else {
                    /* Jika Tidak, maka data dapat diproses dan meyimpannya pada Database        Menyimpan data referensi pada Database berdasarkan User ID dari masing-masing Akun
                    */
                    getReference.child("Admin").child(getUserID).child("Mahasiswa").push()
                        .setValue(data_mahasiswa(getNIM, getNama, getJurusan))
                        .addOnCompleteListener(this) { //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                            binding.nim.setText("")
                            binding.nama.setText("")
                            binding.jurusan.setText("")
                            Toast.makeText(this@MainActivity, "Data Tersimpan", Toast.LENGTH_SHORT).show()
                        }
                }
            }

            R.id.logout -> {
                // Statement program untuk logout/keluar
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener { task ->
                        Toast.makeText(this@MainActivity, "Logout Berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    }
            }
            R.id.showdata -> {
                startActivity(Intent(this@MainActivity, MyListData::class.java))
            }

        }
    }
}
