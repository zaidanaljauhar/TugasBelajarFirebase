package com.example.belajarfirebaseb

class data_mahasiswa {
    //Deklarasi Variable
    var nim: String? = null
    var nama: String? = null
    var jurusan: String? = null
    var key: String? = null

    //Membuat Konstuktor kosong untuk membaca data snapshot
    constructor() {}

    //Konstruktor dengan beberapa parameter, untuk mendapatkan Input Data dari User
    constructor(nim: String?, nama: String?, jurusan: String?) {
        this.nim = nim
        this.nama = nama
        this.jurusan = jurusan
    }
}

