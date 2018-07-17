package com.example.umaaamm.dewi_jilbab;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by umaaamm on 17/07/18.
 */

public class DetailKategori extends AppCompatActivity {

    String id_kategori_ambil, id_kat, nama_kat;
    TextView txt_id;
    TextView txt_nama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kategori);

        txt_id = findViewById(R.id.txt_id_kategori);
        txt_nama = findViewById(R.id.txt_nama_kategori);

        Intent intent = getIntent();
        id_kategori_ambil = intent.getStringExtra("id_kategori");

        getEmployee();
        getEmployee();
        getEmployee();

    }

    private void getEmployee() {
        class GetEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKategori.this, "Fetching...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showEmployee(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(Konfigurasi.URL_GET_EMP, id_kategori_ambil);
                return s;
            }
        }
        GetEmployee ge = new GetEmployee();
        ge.execute();
    }

    private void showEmployee(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray(Konfigurasi.TAG_JSON_ARRAY);
            JSONObject c = result.getJSONObject(0);
            id_kat = c.getString(Konfigurasi.TAG_ID);
            nama_kat = c.getString(Konfigurasi.TAG_NAMA);
            Toast.makeText(this, "Berhasil ambil data", Toast.LENGTH_SHORT).show();
            txt_id.setText(id_kat);
            txt_nama.setText(nama_kat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void deleteEmployee() {
        class DeleteEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKategori.this, "Updating...", "Tunggu...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKategori.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_EMP_ID_KATEGORI, id_kategori_ambil);
                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_DELETE_EMP, hashMap);

                return s;
            }
        }

        DeleteEmployee de = new DeleteEmployee();
        de.execute();
    }

    private void confirmDeleteEmployee() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Apakah Kamu Yakin Ingin Menghapus Kategori ini?");

        alertDialogBuilder.setPositiveButton("Ya",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        deleteEmployee();
                        startActivity(new Intent(DetailKategori.this, Kategori.class));
                        finish();
                    }
                });

        alertDialogBuilder.setNegativeButton("Tidak",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void updateEmployee(final String nama) {
        class UpdateEmployee extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DetailKategori.this, "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DetailKategori.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Konfigurasi.KEY_EMP_ID_KATEGORI, id_kategori_ambil);
                hashMap.put(Konfigurasi.KEY_EMP_NAMAKAT, nama);

                RequestHandler rh = new RequestHandler();

                String s = rh.sendPostRequest(Konfigurasi.URL_UPDATE_EMP, hashMap);

                return s;
            }
        }

        UpdateEmployee ue = new UpdateEmployee();
        ue.execute();
    }

    protected void showEditDialog() {

        // get prompts.xml view
        LayoutInflater layoutInflater = LayoutInflater.from(DetailKategori.this);
        View promptView = layoutInflater.inflate(R.layout.kategori_tambah_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DetailKategori.this);
        alertDialogBuilder.setView(promptView);

        final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
        // setup a dialog window
        editText.setText(nama_kat);
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (editText.getText().toString().trim().isEmpty()) {
                            Toast.makeText(DetailKategori.this, "Harap isi nama kategori...", Toast.LENGTH_SHORT).show();
                        } else {
                            updateEmployee(editText.getText().toString());
                            getEmployee();
                            getEmployee();
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

    public void ubah(View view) {
        showEditDialog();
    }

    public void hapus(View view) {
        confirmDeleteEmployee();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), Kategori.class));
        finish();
    }
}

