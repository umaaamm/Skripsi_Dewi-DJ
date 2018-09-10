package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView rvView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> idbarang;
    private ArrayList<String> stokbarang;
    private ArrayList<String> namabarang;
    private ArrayList<String> gambarbarang;
    private ArrayList<String> hargabarang;
    private ArrayList<String> rating;
    public static String id_user_s = "kosong";
    public static final String nama_user_s = "kosong";

    private String JSON_STRING;
    SwipeRefreshLayout swipeRefreshLayout;
    android.widget.SearchView searchView;

    Sesion sesi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary);
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

        searchView = (android.widget.SearchView) findViewById(R.id.svCari);

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"wkowkwo ini searfh = "+ searchView.getQuery(),Toast.LENGTH_LONG).show();
                //addEmployee(searchView.getQuery().toString());


                if (searchView.getQuery().toString().equals(""))
                {
                    getJSON("semua");
                }else{
                    getJSON(searchView.getQuery().toString());
                }
            }
        });



        //Toast.makeText(MainActivity.this,"sisseion = "+id_user_s,Toast.LENGTH_LONG).show();
        idbarang = new ArrayList<>();
        stokbarang = new ArrayList<>();
        namabarang = new ArrayList<>();
        gambarbarang = new ArrayList<>();
        hargabarang = new ArrayList<>();
        rating = new ArrayList<>();

        rvView = (RecyclerView) findViewById(R.id.rv_main_barang);
        rvView.setHasFixedSize(true);

        rvView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        getJSON("semua");


        //floating button
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Intent ke_upload_produk = new Intent(MainActivity.this, UploadProdukClass.class);
//                startActivity(ke_upload_produk);
                rvView.smoothScrollToPosition(0);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }
    private void showEmployee_cari() {

        if (!stokbarang.isEmpty()) {
            stokbarang.clear();
        }

        if (!namabarang.isEmpty()) {
            namabarang.clear();
        }

        if (!gambarbarang.isEmpty()) {
            gambarbarang.clear();
        }
        if (!hargabarang.isEmpty()) {
            hargabarang.clear();
        }
        if (!rating.isEmpty()) {
            rating.clear();
        }

        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiBarang.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KonfigurasiBarang.TAG_ID_BARANG);
                String stok = jo.getString(KonfigurasiBarang.TAG_STOK);
                String nama = jo.getString(KonfigurasiBarang.TAG_NAMA);
                String gambar = jo.getString(KonfigurasiBarang.TAG_GAMBAR);
                String harga = jo.getString(KonfigurasiBarang.TAG_HARGA);
                String rating_temp = jo.getString(KonfigurasiBarang.TAG_RATING);

                //Toast.makeText(Barang.this,"Get Json : "+nama,Toast.LENGTH_SHORT).show();
                idbarang.add(id);
                stokbarang.add(stok);
                namabarang.add(nama);
                gambarbarang.add(gambar);
                hargabarang.add(harga);
                rating.add(rating_temp);

//                HashMap<String,String> employees = new HashMap<>();
//                employees.put(Konfigurasi.TAG_ID,id);
//                employees.put(Konfigurasi.TAG_NAMA,nama_kategor);
//                list.add(employees);
            }

            //Toast.makeText(Barang.this,"Get Json : "+namabarang.size(),Toast.LENGTH_SHORT).show();
            adapter = new RecyclerViewAdapterBarang(idbarang, stokbarang, namabarang, gambarbarang, hargabarang,rating);
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

    private void showEmployee() {

        if (!stokbarang.isEmpty()) {
            stokbarang.clear();
        }

        if (!namabarang.isEmpty()) {
            namabarang.clear();
        }

        if (!gambarbarang.isEmpty()) {
            gambarbarang.clear();
        }
        if (!hargabarang.isEmpty()) {
            hargabarang.clear();
        }
        if (!rating.isEmpty()) {
            rating.clear();
        }

        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiBarang.TAG_JSON_ARRAY);

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KonfigurasiBarang.TAG_ID_BARANG);
                String stok = jo.getString(KonfigurasiBarang.TAG_STOK);
                String nama = jo.getString(KonfigurasiBarang.TAG_NAMA);
                String gambar = jo.getString(KonfigurasiBarang.TAG_GAMBAR);
                String harga = jo.getString(KonfigurasiBarang.TAG_HARGA);
                String rating_temp = jo.getString(KonfigurasiBarang.TAG_RATING);

                //Toast.makeText(Barang.this,"Get Json : "+nama,Toast.LENGTH_SHORT).show();
                idbarang.add(id);
                stokbarang.add(stok);
                namabarang.add(nama);
                gambarbarang.add(gambar);
                hargabarang.add(harga);
                rating.add(rating_temp);

//                HashMap<String,String> employees = new HashMap<>();
//                employees.put(Konfigurasi.TAG_ID,id);
//                employees.put(Konfigurasi.TAG_NAMA,nama_kategor);
//                list.add(employees);
            }

            //Toast.makeText(Barang.this,"Get Json : "+namabarang.size(),Toast.LENGTH_SHORT).show();
            adapter = new RecyclerViewAdapterBarang(idbarang, stokbarang, namabarang, gambarbarang, hargabarang,rating);
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


    private void getJSON(final String nama_barang_c) {

        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Mengambil Data", "Mohon Tunggu...", false, false);
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
                String s;
                if(nama_barang_c !="semua"){
                    s = rh.sendGetRequestParam(KonfigurasiBarang.URL_CARI,nama_barang_c);
                }else if(nama_barang_c == ""){
                    String nama_barang_c = "semua";
                   s  = rh.sendGetRequestParam(KonfigurasiBarang.URL_CARI,nama_barang_c);
                }else {
                    s  = rh.sendGetRequestParam(KonfigurasiBarang.URL_CARI,nama_barang_c);
                }
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.beranda) {
            Intent ke_beranda = new Intent(MainActivity.this, MainActivity.class);
            startActivity(ke_beranda);
        }else if (id == R.id.keluar) {
            id_user_s = "kosong";
            Intent kehome = new Intent(MainActivity.this, MainActivity.class);
            startActivity(kehome);
            Toast.makeText(MainActivity.this,"Anda Telah Logout.",Toast.LENGTH_LONG).show();


        }else if (id == R.id.tentang) {
            Intent ke_tentang = new Intent(MainActivity.this, Tentang.class);
            startActivity(ke_tentang);
        }else if (id == R.id.login) {
            Intent kelogin = new Intent(MainActivity.this, Login.class);
            startActivity(kelogin);
        }else if (id == R.id.register) {
            Intent keregister = new Intent(MainActivity.this, Register.class);
            startActivity(keregister);
        }else if (id==R.id.action_settings){
            Toast.makeText(MainActivity.this,"ini setting",Toast.LENGTH_LONG).show();
        }else if (id == R.id.video) {
            Intent kevideo = new Intent(MainActivity.this, Video.class);
            startActivity(kevideo);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addEmployee(final String nama_barang){



        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this,"Mencari...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();
                Intent Faktur = new Intent(MainActivity.this, MainActivity.class);
                startActivity(Faktur);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
//                params.put(KonfigurasiTransaksi.KEY_EMP_ID_BARANG,id_barang);
//                params.put(KonfigurasiTransaksi.KEY_EMP_ID_USER,id_user);
                params.put(KonfigurasiBarang.KEY_CARI,nama_barang);
                RequestHandler rh = new RequestHandler();
                //String res = rh.sendPostRequest(KonfigurasiBarang.URL_CARI, params);
                String res = rh.sendGetRequestParam(KonfigurasiBarang.URL_CARI,nama_barang);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }
}
