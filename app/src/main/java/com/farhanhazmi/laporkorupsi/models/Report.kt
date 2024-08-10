package com.farhanhazmi.laporkorupsi.models

data class Report(
    val reportId : String,
    val nama : String,
    val alamat : String,
    val telepon : String,
    val deskripsi : String,
    val tempat : String,
    val tanggal : String,
    val pelaku : String,
    val modus : String

)
