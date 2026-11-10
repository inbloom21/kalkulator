package com.pbo.kalkulator.model;

import jakarta.persistence.*;

@Entity
@Table(name = "riwayat_kalkulator")
public class Kalkulator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double bil1;
    private Double bil2;
    private String operator;

    // Tipe data diubah menjadi String agar bisa mengembalikan teks "Tidak Terdefinisi"
    @Transient
    public String getHasil() {
        if (bil1 == null || bil2 == null || operator == null) return null;
        
        // Membuang angka .0 di belakang koma jika hasilnya bulat (opsional, agar lebih rapi)
        double hasil;
        
        switch (operator) {
            case "+": 
                hasil = bil1 + bil2;
                break;
            case "-": 
                hasil = bil1 - bil2;
                break;
            case "x": 
                hasil = bil1 * bil2;
                break;
            case "/": 
                if (bil2 == 0) {
                    return "Tidak Terdefinisi"; // Kondisi saat pembagian dengan 0
                }
                hasil = bil1 / bil2;
                break;
            default: 
                return "0";
        }
        
        // Format agar angka bulat (seperti 15.0) tampil sebagai 15 saja
        if (hasil == Math.floor(hasil)) {
            return String.format("%.0f", hasil);
        }
        return String.valueOf(hasil);
    }

    // --- Generate Getter dan Setter ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getBil1() { return bil1; }
    public void setBil1(Double bil1) { this.bil1 = bil1; }

    public Double getBil2() { return bil2; }
    public void setBil2(Double bil2) { this.bil2 = bil2; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }
}   