package com.farhanhazmi.laporkorupsi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.farhanhazmi.laporkorupsi.databinding.FragmentAddReportBinding
import com.farhanhazmi.laporkorupsi.models.Report
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddReportFragment : Fragment() {

    private var _binding: FragmentAddReportBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddReportBinding.inflate(inflater, container, false)
        firebaseRef = FirebaseDatabase.getInstance("https://laporkorupsi-f01d9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Report")

        binding.btnSubmit.setOnClickListener {
            saveData()
        }

        return binding.root
    }

    private fun saveData() {
        val nama = binding.editNama.text.toString()
        val alamat = binding.editAlamat.text.toString()
        val telepon = binding.editTelepon.text.toString()
        val deskripsi = binding.editDeskripsiKejadian.text.toString()
        val tempat = binding.editTempat.text.toString()
        val tanggal = binding.editTanggal.text.toString()
        val pelaku = binding.editPelaku.text.toString()
        val modus = binding.editModusOperandi.text.toString()

        var isValid = true

        if (nama.isEmpty()) {
            binding.editNama.error = "Masukkan nama pelapor"
            isValid = false
        }
        if (alamat.isEmpty()) {
            binding.editAlamat.error = "Masukkan alamat pelapor"
            isValid = false
        }
        if (telepon.isEmpty()) {
            binding.editTelepon.error = "Masukkan nomor telepon pelapor"
            isValid = false
        }
        if (deskripsi.isEmpty()) {
            binding.editDeskripsiKejadian.error = "Deskripsikan kejadian secara jelas"
            isValid = false
        }
        if (tempat.isEmpty()) {
            binding.editTempat.error = "Masukkan tempat kejadian"
            isValid = false
        }
        if (tanggal.isEmpty()) {
            binding.editTanggal.error = "Masukkan tanggal kejadian"
            isValid = false
        }
        if (pelaku.isEmpty()){
            binding.editPelaku.error = "Masukkan pelaku dugaan korupsi"
            isValid = false
        }
        if (modus.isEmpty()) {
            binding.editModusOperandi.error = "Masukkan modus operandi pelaku"
            isValid = false
        }

        if (isValid) {
            val reportId = firebaseRef.push().key!!
            val reports =
                Report(reportId, nama, alamat, telepon, deskripsi, tempat, tanggal, pelaku, modus)

            firebaseRef.child(reportId).setValue(reports)
                .addOnCompleteListener {
                    Toast.makeText(context, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show()
                    (activity as? MainActivity)?.replaceFragment(HomeFragment())
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }else {
            Toast.makeText(context, "Tolong perbaiki data yang salah", Toast.LENGTH_SHORT).show()
        }
    }
}