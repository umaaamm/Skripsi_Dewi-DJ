package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by umaaamm on 16/07/18.
 */

public class Produk extends AppCompatActivity {

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> idbarang;
    private ArrayList<String> stokbarang;
    private ArrayList<String> namabarang;
    private ArrayList<String> gambarbarang;
    private ArrayList<String> hargabarang;
    private String JSON_STRING;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_produk);


        idbarang = new ArrayList<>();
        stokbarang = new ArrayList<>();
        namabarang = new ArrayList<>();
        gambarbarang = new ArrayList<>();
        hargabarang = new ArrayList<>();

        rvView = (RecyclerView) findViewById(R.id.rv_main_barang);
        rvView.setHasFixedSize(true);

        rvView.setLayoutManager(new GridLayoutManager(Produk.this, 2));
        //layoutManager = new LinearLayoutManager(this);
        //rvView.setLayoutManager(layoutManager);

//        idbarang.add("ID");
//        namabarang.add("Nama");
//        gambarbarang.add("Gambar");

        getJSON();
        //Toast.makeText(Barang.this,"Get Json : "+namabarang.size(),Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(new Intent(getApplicationContext(),UploadProdukClass.class));
                finish();
            }
        });

    }


    private void showEmployee(){

        if(!stokbarang.isEmpty()){
            stokbarang.clear();
        }

        if(!namabarang.isEmpty()){
            namabarang.clear();
        }

        if(!gambarbarang.isEmpty()){
            gambarbarang.clear();
        }
        if(!hargabarang.isEmpty()){
            hargabarang.clear();
        }

        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiBarang.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KonfigurasiBarang.TAG_ID_BARANG);
                String stok = jo.getString(KonfigurasiBarang.TAG_STOK);
                String nama = jo.getString(KonfigurasiBarang.TAG_NAMA);
                String gambar = jo.getString(KonfigurasiBarang.TAG_GAMBAR);
                String harga = jo.getString(KonfigurasiBarang.TAG_HARGA);

                //Toast.makeText(Barang.this,"Get Json : "+nama,Toast.LENGTH_SHORT).show();
                idbarang.add(id);
                stokbarang.add(stok);
                namabarang.add(nama);
                gambarbarang.add(gambar);
                hargabarang.add(harga);

//                HashMap<String,String> employees = new HashMap<>();
//                employees.put(Konfigurasi.TAG_ID,id);
//                employees.put(Konfigurasi.TAG_NAMA,nama_kategor);
//                list.add(employees);
            }

            //Toast.makeText(Barang.this,"Get Json : "+namabarang.size(),Toast.LENGTH_SHORT).show();
            adapter = new RecyclerViewAdapterBarangDetail(idbarang,stokbarang,namabarang,gambarbarang,hargabarang);
            rvView.setAdapter(adapter);

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

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Produk.this,"Mengambil Data","Mohon Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                showEmployee();

            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(KonfigurasiBarang.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}