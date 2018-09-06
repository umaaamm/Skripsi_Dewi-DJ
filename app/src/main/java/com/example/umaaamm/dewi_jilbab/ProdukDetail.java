package com.example.umaaamm.dewi_jilbab;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by umaaamm on 12/07/18.
 */

public class ProdukDetail extends AppCompatActivity {

    String id_barang_ambil, id_kategori_now, nama_now, deskripsi_now, stok_now, harga_now, gambar_now, gambarbmp_now;

    Spinner dynamicSpinner;
    ImageView gambar;
    private static final int PICK_IMAGE_REQUEST = 234;

    TextView txtnama, txtdeskrip, txtharga, txt_stok;
    Spinner txtKategori;
    String Snama,Sdeskripso,Sharga;
    String SIDKategori;

    Bitmap bitmap;
    private Uri filePath;
    private String JSON_STRING;
    FloatingActionButton btn_beli;
    ArrayList<String> idKat = new ArrayList<String>();
    ArrayList<String> namaKat = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_produk);

        gambar = findViewById(R.id.imageView);
        txtnama = findViewById(R.id.Nama_Produk);
        txtdeskrip = findViewById(R.id.tvDeskripsiProduk);
        //txtKategori = findViewById(R.id.KategoriBarang);
        txtharga = findViewById(R.id.tvHargaProduk);
        txt_stok = findViewById(R.id.stok);

        Intent intent = getIntent();
        id_barang_ambil = intent.getStringExtra("id_barang");

        //getKategori();
        btn_beli = findViewById(R.id.btn_beli);


            btn_beli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(ProdukDetail.this,"Klik",Toast.LENGTH_SHORT).show();
                    //id_barang = id_barang_ambil;
//                Intent intent = new Intent(ProdukDetail.this, ProdukDetail.class);
//                intent.putExtra("id_barang", id_barang_ambil);
//                ProdukDetail.this.startActivity(intent);
                    if (MainActivity.id_user_s != "kosong") {
                        addEmployee(id_barang_ambil, MainActivity.id_user_s);
                        finish();
                    }else{
                        Toast.makeText(ProdukDetail.this,"Anda Harus Login Terlebih Dahului",Toast.LENGTH_LONG).show();
                    }
                }
            });

        getEmployee();

    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imagebyte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imagebyte, Base64.DEFAULT);
        return encodedImage;
    }


    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProdukDetail.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
                //Toast.makeText(DetailBarang.this, ""+s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(KonfigurasiBarang.URL_GET_EMP, id_barang_ambil);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiBarang.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            id_kategori_now = c.getString(KonfigurasiBarang.TAG_ID_KATEGORI);
            nama_now = c.getString(KonfigurasiBarang.TAG_NAMA);
            deskripsi_now = c.getString(KonfigurasiBarang.TAG_DESKRIPSI);
            harga_now = c.getString(KonfigurasiBarang.TAG_HARGA);
            stok_now = c.getString(KonfigurasiBarang.TAG_STOK);
            gambar_now = c.getString(KonfigurasiBarang.TAG_GAMBAR);
            gambarbmp_now = c.getString(KonfigurasiBarang.TAG_GAMBAR_BMP);

            Snama =  nama_now;
            Sdeskripso = deskripsi_now;
            Sharga = harga_now;

            txtnama.setText(nama_now);
            txtdeskrip.setText(deskripsi_now);
            txtharga.setText("Rp. " + getMoney(harga_now));
            txt_stok.setText(stok_now + " Barang");

            for (int a = 0; a < idKat.size(); a++) {
                if (idKat.get(a).equals(id_kategori_now)) {
                    txtKategori.setSelection(a);
                }
            }
            bitmap = StringToBitMap(gambarbmp_now);
            gambar.setImageBitmap(bitmap);

        } catch (JSONException e) {
            e.printStackTrace();
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

    private void kategori() {
        if (!idKat.isEmpty()) {
            idKat.clear();
        }
        if (!namaKat.isEmpty()) {
            namaKat.clear();
        }
        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(Konfigurasi.TAG_ID);
                String nama_kategor = jo.getString(Konfigurasi.TAG_NAMA);

                namaKat.add(nama_kategor);
                idKat.add(id);

            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, namaKat);

            txtKategori.setAdapter(adapter);


        } catch (JSONException e) {
            e.printStackTrace();
        }

//        ListAdapter adapter = new SimpleAdapter(
//                TampilSemuaPgw.this, list, R.layout.list_item,
//                new String[]{konfigurasi.TAG_ID,konfigurasi.TAG_NAMA},
//                new int[]{R.id.id, R.id.name});
//
//        listView.setAdapter(adapter);
    }

    private void getKategori() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProdukDetail.this, "Mengambil Data", "Mohon Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                kategori();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    private void addEmployee(final String id_barang, final String id_user){



        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProdukDetail.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(ProdukDetail.this,s, Toast.LENGTH_LONG).show();
                Intent Faktur = new Intent(ProdukDetail.this, Faktur.class);
                Faktur.putExtra("id_barang",id_barang_ambil);
                Faktur.putExtra("nama_barang",Snama);
                Faktur.putExtra("deskripsi_barang",Sdeskripso);
                Faktur.putExtra("harga",Sharga);
                startActivity(Faktur);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiTransaksi.KEY_EMP_ID_BARANG,id_barang);
                params.put(KonfigurasiTransaksi.KEY_EMP_ID_USER,id_user);
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(KonfigurasiTransaksi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }




}
