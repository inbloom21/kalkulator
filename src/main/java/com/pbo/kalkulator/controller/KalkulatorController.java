package com.pbo.kalkulator.controller;

import com.pbo.kalkulator.model.Kalkulator;
import com.pbo.kalkulator.repository.KalkulatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/kalkulator")
public class KalkulatorController {

    @Autowired
    private KalkulatorRepository repo;

    // Menampilkan Form dan Tabel di 1 Halaman
    @GetMapping
    public String index(Model model) {
        // Jika tidak ada data dari hasil submit sebelumnya, buat form kosong baru
        if (!model.containsAttribute("kalkulatorForm")) {
            model.addAttribute("kalkulatorForm", new Kalkulator());
        }
        // Kirim riwayat dari database ke tabel
        model.addAttribute("riwayat", repo.findAll());
        return "kalkulator";
    }

    // Eksekusi Hitung & Simpan
    @PostMapping("/hitung")
    public String hitungDanSimpan(@ModelAttribute Kalkulator kalkulator, RedirectAttributes redirectAttributes) {
        // Simpan ke database (mendapatkan ID otomatis)
        repo.save(kalkulator);
        
        // --- REVISI DI SINI ---
        // Buat objek baru dengan isi (value) yang persis sama, tapi TANPA ID.
        // Tujuannya agar saat tombol '=' ditekan lagi, Spring Boot menganggapnya sebagai data baru (Insert), bukan Update.
        Kalkulator formBaru = new Kalkulator();
        formBaru.setBil1(kalkulator.getBil1());
        formBaru.setBil2(kalkulator.getBil2());
        formBaru.setOperator(kalkulator.getOperator());
        
        // Kirim kembali data tanpa ID ini ke form agar nilainya tetap bertahan di layar
        redirectAttributes.addFlashAttribute("kalkulatorForm", formBaru);
        // Kirim hasil perhitungannya menggunakan objek asli
        redirectAttributes.addFlashAttribute("hasilAkhir", kalkulator.getHasil());
        
        return "redirect:/kalkulator";
    }

    // Menarik data ke form untuk di-edit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Kalkulator kalkulator = repo.findById(id).orElse(new Kalkulator());
        model.addAttribute("kalkulatorForm", kalkulator);
        model.addAttribute("riwayat", repo.findAll());
        return "kalkulator"; // Tetap di halaman yang sama
    }

    // Menghapus data
    @GetMapping("/hapus/{id}")
    public String hapus(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/kalkulator";
    }
}