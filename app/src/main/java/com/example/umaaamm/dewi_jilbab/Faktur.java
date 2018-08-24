package com.example.umaaamm.dewi_jilbab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class Faktur extends AppCompatActivity {

    TextView txtnama,txtdeskrip,txtharga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktur);
        txtnama = findViewById(R.id.Nama_Produk);
        txtdeskrip =  findViewById(R.id.tvDeskripsiProduk);
        txtharga =  findViewById(R.id.tvHargaProduk);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            String harga = extras.getString("harga");
            String nama_barang = extras.getString("nama_barang");
            String deskripsi = extras.getString("deskripsi_barang");
           txtnama.setText(nama_barang);
           txtharga.setText("Rp. " + getMoney(harga));
           txtdeskrip.setText(deskripsi);
        }

    }
    private String getMoney(String str2) {
        StringBuilder str = new StringBuilder(str2);
        int idx = str.length() - 3;
        while (idx > 0) {
            str.insert(idx, ".");
            idx = idx - 3;
        }
        return str.toString();
    }
}
