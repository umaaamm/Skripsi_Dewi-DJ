//package com.example.umaaamm.dewi_jilbab;
//
///**
// * Created by umaaamm on 15/07/18.
// */
//
//import android.app.AlertDialog;
//import android.app.ProgressDialog;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Base64;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class DetailBarang extends AppCompatActivity {
//
//    String id_barang_ambil,id_kategori_now,nama_now,deskripsi_now,stok_now,harga_now,gambar_now,gambarbmp_now;
//
//    Spinner dynamicSpinner;
//    ImageView gambar;
//    private static final int PICK_IMAGE_REQUEST = 234;
//
//    EditText txtnama,txtdeskrip,txtharga,txt_stok;
//    Spinner txtKategori;
//
//    String SIDKategori;
//
//    Bitmap bitmap;
//    private Uri filePath;
//    private String JSON_STRING;
//
//    ArrayList<String> idKat = new ArrayList<String>();
//    ArrayList<String> namaKat = new ArrayList<String>();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.detail_produk);
//        Intent intent = getIntent();
//        id_barang_ambil = intent.getStringExtra("id_barang");
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("Nama Toko");
//        toolbar.setSubtitle("Detail Barang");
//
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        gambar = findViewById(R.id.gambarBarang);
//        txtnama = findViewById(R.id.nama_barang);
//        txtdeskrip = findViewById(R.id.deskripsiBarang);
//        txtKategori = findViewById(R.id.KategoriBarang);
//        txtharga = findViewById(R.id.hargaBarang);
//        txt_stok = findViewById(R.id.stok_barang);
//
//        getKategori();
//
//
//        txtKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view,
//                                       int position, long id) {
//                SIDKategori = idKat.get(position);
//                //Log.v("item", (String) parent.getItemAtPosition(position));
////                Toast.makeText(TambahHijab.this,"Isi : "+idKat.get(position),Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                // TODO Auto-generated method stub
//            }
//        });
//
//        getEmployee();
//
//    }
//
//    public Bitmap StringToBitMap(String encodedString){
//        try{
//            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
//            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//            return bitmap;
//        }catch(Exception e){
//            e.getMessage();
//            return null;
//        }
//    }
//    public String getStringImage(Bitmap bitmap){
//        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
//        byte[] imagebyte = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(imagebyte,Base64.DEFAULT);
//        return  encodedImage;
//    }
//
//    public void uploadGambar(View view) {
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//                filePath = data.getData();
//
//                try {
//                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
////                    editTextName.setText(getStringImage(bitmap));
//                    gambar.setImageBitmap(bitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } catch (Exception e) {
//            Toast.makeText(DetailBarang.this, e.toString(), Toast.LENGTH_LONG).show();
//        }
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        finish();
//    }
//
//    private void getEmployee(){
//        class GetEmployee extends AsyncTask<Void,Void,String> {
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(DetailBarang.this,"Fetching...","Wait...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                showEmployee(s);
//                //Toast.makeText(DetailBarang.this, ""+s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequestParam(KonfigurasiBarang.URL_GET_EMP,id_barang_ambil);
//                return s;
//            }
//        }
//        GetEmployee ge = new GetEmployee();
//        ge.execute();
//    }
//
//    private void showEmployee(String json){
//        try {
//            JSONObject jsonObject = new JSONObject(json);
//            JSONArray result = jsonObject.getJSONArray(KonfigurasiBarang.TAG_JSON_ARRAY);
//            JSONObject c = result.getJSONObject(0);
//
//            id_kategori_now = c.getString(KonfigurasiBarang.TAG_ID_KATEGORI);
//            nama_now = c.getString(KonfigurasiBarang.TAG_NAMA);
//            deskripsi_now = c.getString(KonfigurasiBarang.TAG_DESKRIPSI);
//            harga_now = c.getString(KonfigurasiBarang.TAG_HARGA);
//            stok_now =  c.getString(KonfigurasiBarang.TAG_STOK);
//            gambar_now =  c.getString(KonfigurasiBarang.TAG_GAMBAR);
//            gambarbmp_now =  c.getString(KonfigurasiBarang.TAG_GAMBAR_BMP);
//
//
//            txtnama.setText(nama_now);
//            txtdeskrip.setText(deskripsi_now);
//            txtharga.setText(harga_now);
//            txt_stok.setText(stok_now);
//
//            for(int a = 0; a < idKat.size(); a++){
//                if(idKat.get(a).equals(id_kategori_now)){
//                    txtKategori.setSelection(a);
//                }
//            }
//            bitmap = StringToBitMap(gambarbmp_now);
//            gambar.setImageBitmap(bitmap);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void kategori(){
//        if(!idKat.isEmpty()){
//            idKat.clear();
//        }
//        if(!namaKat.isEmpty()){
//            namaKat.clear();
//        }
//        JSONObject jsonObject = null;
////        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
//        try {
//            jsonObject = new JSONObject(JSON_STRING);
//            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
//
//            for(int i = 0; i<result.length(); i++){
//                JSONObject jo = result.getJSONObject(i);
//                String id = jo.getString(Konfigurasi.TAG_ID);
//                String nama_kategor = jo.getString(Konfigurasi.TAG_NAMA);
//
//                namaKat.add(nama_kategor);
//                idKat.add(id);
//
//            }
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                    android.R.layout.simple_spinner_item, namaKat);
//
//            txtKategori.setAdapter(adapter);
//
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
////        ListAdapter adapter = new SimpleAdapter(
////                TampilSemuaPgw.this, list, R.layout.list_item,
////                new String[]{konfigurasi.TAG_ID,konfigurasi.TAG_NAMA},
////                new int[]{R.id.id, R.id.name});
////
////        listView.setAdapter(adapter);
//    }
//
//    private void getKategori(){
//        class GetJSON extends AsyncTask<Void,Void,String> {
//
//            ProgressDialog loading;
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(DetailBarang.this,"Mengambil Data","Mohon Tunggu...",false,false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                JSON_STRING = s;
//                kategori();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                RequestHandler rh = new RequestHandler();
//                String s = rh.sendGetRequest(Konfigurasi.URL_GET_ALL);
//                return s;
//            }
//        }
//        GetJSON gj = new GetJSON();
//        gj.execute();
//    }
//
//    private void deleteEmployee(){
//        class DeleteEmployee extends AsyncTask<Void,Void,String> {
//            ProgressDialog loading;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                loading = ProgressDialog.show(DetailBarang.this, "Delete data...", "Tunggu Sebentar...", false, false);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                loading.dismiss();
//                Toast.makeText(DetailBarang.this, s, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            protected String doInBackground(Void... params) {
//                HashMap<String,String> hashMap = new HashMap<>();
//                hashMap.put(KonfigurasiBarang.KEY_EMP_ID_BARANG,id_barang_ambil);
//                hashMap.put(KonfigurasiBarang.KEY_EMP_GAMBAR,gambar_now);
//                RequestHandler rh = new RequestHandler();
//
//                String s = rh.sendPostRequest(KonfigurasiBarang.URL_DELETE_EMP,hashMap);
//
//                return s;
//            }
//        }
//
//        DeleteEmployee de = new DeleteEmployee();
//        de.execute();
//    }
//
//    private void confirmDeleteEmployee(){
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
//        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Barang Ini?");
//
//        alertDialogBuilder.setPositiveButton("Ya",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//                        deleteEmployee();
//                        startActivity(new Intent(DetailBarang.this,MainActivity.class));
//                        finish();
//                    }
//                });
//
//        alertDialogBuilder.setNegativeButton("Tidak",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface arg0, int arg1) {
//
//                    }
//                });
//
//        AlertDialog alertDialog = alertDialogBuilder.create();
//        alertDialog.show();
//    }
//
//    public void Ubah(View view) {
//        Toast.makeText(DetailBarang.this,"ID KATEGORI : "+SIDKategori,Toast.LENGTH_LONG).show();
//    }
//
//    public void Hapus(View view) {
//        confirmDeleteEmployee();
//    }
//}
