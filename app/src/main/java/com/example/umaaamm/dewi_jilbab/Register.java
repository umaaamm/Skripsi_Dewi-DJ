package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by umaaamm on 15/07/18.
 */

public class Register extends AppCompatActivity {
    EditText email,password,nama;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);


        email = findViewById(R.id.txtemail);
        password = findViewById(R.id.txt_password);
        nama = findViewById(R.id.txtnama);

    }

    public void Daftar(View view) {
        //Toast.makeText(this,"nama : "+nama.getText()+" email : "+email.getText()+" password : "+password.getText(),Toast.LENGTH_LONG).show();
        addEmployee(nama.getText().toString(),email.getText().toString(),password.getText().toString());
        //Toast.makeText(this,"",Toast.LENGTH_LONG).show();
    }

    private void addEmployee(final String nama, final String email, final String password){



        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Register.this,"Menambahkan...","Tunggu...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(Register.this,s, Toast.LENGTH_LONG).show();
                Intent kelogin = new Intent(Register.this, Login.class);
                startActivity(kelogin);
                finish();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put(KonfigurasiUser.KEY_EMP_NAMA,nama);
                params.put(KonfigurasiUser.KEY_EMP_UName,email);
                params.put(KonfigurasiUser.KEY_EMP_PW,password);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(KonfigurasiUser.URL_ADD, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();

    }
}