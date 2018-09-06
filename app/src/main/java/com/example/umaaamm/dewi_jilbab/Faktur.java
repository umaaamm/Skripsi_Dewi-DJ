package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class Faktur extends AppCompatActivity {

    TextView txtnama,txtdeskrip,txtharga;
    EditText rating_i;
    String id_barang;
    FloatingActionButton btn_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktur);
        txtnama = findViewById(R.id.Nama_Produk);
        txtdeskrip =  findViewById(R.id.tvDeskripsiProduk);
        txtharga =  findViewById(R.id.tvHargaProduk);
        rating_i = findViewById(R.id.edit_text_rating);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null) {
            id_barang = extras.getString("id_barang");
            String harga = extras.getString("harga");
            String nama_barang = extras.getString("nama_barang");
            String deskripsi = extras.getString("deskripsi_barang");
           txtnama.setText(nama_barang);
           txtharga.setText("Rp. " + getMoney(harga));
           txtdeskrip.setText(deskripsi);
        }


        btn_rating = findViewById(R.id.btn_beli);
        btn_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (MainActivity.id_user_s != "kosong") {
                    addEmployee(id_barang, MainActivity.id_user_s,rating_i.getText().toString());
                    finish();
                }else{
                    Toast.makeText(Faktur.this,"Anda Harus Login Terlebih Dahului",Toast.LENGTH_LONG).show();
                }
            }
        });

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

    private void addEmployee(final String id_barang, final String id_user, final String rating_b){



        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Faktur.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Faktur.this,s, Toast.LENGTH_LONG).show();
                Intent Faktur = new Intent(Faktur.this, MainActivity.class);
                startActivity(Faktur);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiTransaksi.KEY_EMP_ID_BARANG,id_barang);
                params.put(KonfigurasiTransaksi.KEY_EMP_ID_USER,id_user);
                params.put(KonfigurasiTransaksi.KEY_RATING,rating_b);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(KonfigurasiTransaksi.URL_ADD_RATING, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }
}
