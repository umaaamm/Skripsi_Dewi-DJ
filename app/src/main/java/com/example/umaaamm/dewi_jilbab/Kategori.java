package com.example.umaaamm.dewi_jilbab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by umaaamm on 17/07/18.
 */

public class Kategori extends AppCompatActivity {

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> namakate;
    private ArrayList<String> idkate;

    private String JSON_STRING;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.pDarkGreen, R.color.colorAccent, R.color.colorPB, R.color.pFullLightGreen);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        startActivity(getIntent());
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });


        namakate = new ArrayList<>();
        idkate = new ArrayList<>();

        rvView = (RecyclerView) findViewById(R.id.rv_main);
        rvView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        rvView.setLayoutManager(layoutManager);


        getJSON();


        //Toast.makeText(Kategori.this,"Get Json : "+namakate.size(),Toast.LENGTH_SHORT).show();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                showInputDialog();
            }
        });
    }

    private void showEmployee() {
        if (!namakate.isEmpty()) {
            namakate.clear();
        }
        if (!idkate.isEmpty()) {
            idkate.clear();
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

                namakate.add(nama_kategor);
                idkate.add(id);

//                HashMap<String,String> employees = new HashMap<>();
//                employees.put(Konfigurasi.TAG_ID,id);
//                employees.put(Konfigurasi.TAG_NAMA,nama_kategor);
//                list.add(employees);
            }
            adapter = new RecyclerViewAdapter(namakate, idkate);
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

    private void getJSON() {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Kategori.this, "Mengambil Data", "Mohon Tunggu...", false, false);
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

    protected void showInputDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(Kategori.this);
        View promptView = layoutInflater.inflate(R.layout.kategori_tambah_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Kategori.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (editText.getText().toString().trim().isEmpty()) {
                            //editText.setError("Harap isi nama kategori...");
                            Toast.makeText(Kategori.this, "Harap isi nama kategori...", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(Kategori.this, "Hello, " + editText.getText(), Toast.LENGTH_SHORT).show();
                            addEmployee(editText.getText().toString());
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    adapter = new RecyclerViewAdapter(namakate, idkate);
                                    rvView.setAdapter(adapter);
                                    getJSON();
                                }
                            }, 5000);
                            getJSON();

                        }
                    }
                })
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create an alert dialog
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    //Dibawah ini merupakan perintah untuk Menambahkan Pegawai (CREATE)
    private void addEmployee(String nama) {

        final String namakat = nama;

        class AddEmployee extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Kategori.this, "Menambahkan...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Kategori.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String, String> params = new HashMap<>();
                params.put(Konfigurasi.KEY_EMP_NAMAKAT, namakat);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(Konfigurasi.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }

}
