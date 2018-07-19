package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;



import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by umaaamm on 12/07/18.
 */




public class UploadProdukClass extends AppCompatActivity {

    Spinner dynamicSpinner;
    ImageView gambarBarang;

    private File actualImage;
    private File compressedImage;
    private ImageView actualImageView;
    private ImageView compressedImageView;
    private static final int PICK_IMAGE_REQUEST = 234;

    EditText txtnama,txtdeskrip,txtharga,txt_stok;
    Spinner txtKategori;
    ImageView gmbbarang;

    String SIDKategori;

    Bitmap bitmap;
    private Uri filePath;
    private String JSON_STRING;

    TextView actualSizeTextView,compressedSizeTextView;

    ArrayList<String> idKat = new ArrayList<String>();
    ArrayList<String> namaKat = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_product);


        gmbbarang = findViewById(R.id.gambarBarang);
        txtnama = findViewById(R.id.nama_barang);
        txtdeskrip = findViewById(R.id.deskripsiBarang);
        txtKategori = findViewById(R.id.KategoriBarang);
        txtharga = findViewById(R.id.hargaBarang);
        txt_stok = findViewById(R.id.stok_barang);

        getJSON();

        actualImageView = (ImageView) findViewById(R.id.actual);
        actualSizeTextView = (TextView) findViewById(R.id.actual_size);

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
    }


    public void chooseImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    public void chooseImagecompores(View view) {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            new Compressor(this)
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            setCompressedImage();

                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });
        }
    }

    private void setCompressedImage() {
        compressedImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
        compressedSizeTextView.setText(String.format("Size : %s", getReadableFileSize(compressedImage.length())));
        Toast.makeText(this, "ukuran image save in " + String.format("Size : %s", getReadableFileSize(compressedImage.length())), Toast.LENGTH_LONG).show();

        Toast.makeText(this, "Compressed image save in " + compressedImage.getPath(), Toast.LENGTH_LONG).show();
        Log.d("Compressor", "Compressed image save in " + compressedImage.getPath());


    }

    private void clearImage() {
        actualImageView.setBackgroundColor(getRandomColor());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            filePath = data.getData();
            try {

                actualImage = FileUtil.from(this, data.getData());
                actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                clearImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                loading = ProgressDialog.show(UploadProdukClass.this,"Mengambil Data","Mohon Tunggu...",false,false);
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

    public void simpan(View view) {
        addEmployee();
    }

    private void addEmployee(){

        final String namaBarang = txtnama.getText().toString().trim();
        final String deskripsiBarang = txtdeskrip.getText().toString().trim();
        final String idKategori = SIDKategori;
        final String harga = txtharga.getText().toString().trim();
        final String stok = txt_stok.getText().toString().trim();


        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(UploadProdukClass.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(UploadProdukClass.this,s, Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),Produk.class));
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiBarang.KEY_EMP_NAMA,namaBarang);
                params.put(KonfigurasiBarang.KEY_EMP_ID_KATEGORI,idKategori);
                params.put(KonfigurasiBarang.KEY_EMP_HARGA,harga);
                params.put(KonfigurasiBarang.KEY_EMP_STOK,stok);
                params.put(KonfigurasiBarang.KEY_EMP_DESKRIPSI,deskripsiBarang);
                params.put(KonfigurasiBarang.KEY_EMP_GAMBAR,getStringImage(bitmap));
//
                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(KonfigurasiBarang.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos =  new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] imagebyte = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imagebyte,Base64.DEFAULT);
        return  encodedImage;
    }


}
