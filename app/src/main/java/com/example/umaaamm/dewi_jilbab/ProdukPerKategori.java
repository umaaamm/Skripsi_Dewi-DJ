package com.example.umaaamm.dewi_jilbab;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by umaaamm on 12/07/18.
 */

public class ProdukPerKategori extends AppCompatActivity {


    Spinner txtKategori;
    String SIDKategori;
    ArrayList<String> idKat = new ArrayList<String>();
    ArrayList<String> namaKat = new ArrayList<String>();
    private String JSON_STRING;
    //KategoriBarang
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produk_per_kategori);

        txtKategori = findViewById(R.id.spUrut);
        txtKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                SIDKategori = idKat.get(position);
                //Log.v("item", (String) parent.getItemAtPosition(position));
//                Toast.makeText(TambahHijab.this,"Isi : "+idKat.get(position),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
getJSON();

    }
    private void showEmployee(){
        if(!idKat.isEmpty()){
            idKat.clear();
        }
        if(!namaKat.isEmpty()){
            namaKat.clear();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
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
    }
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProdukPerKategori.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
