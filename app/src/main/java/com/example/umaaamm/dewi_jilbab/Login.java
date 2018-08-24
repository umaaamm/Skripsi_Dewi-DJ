package com.example.umaaamm.dewi_jilbab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by umaaamm on 15/07/18.
 */

public class Login extends AppCompatActivity {
    private String JSON_STRING;
    TextView txt_username,txt_password;
    Sesion sesi;
    Button daftar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        txt_username = findViewById(R.id.edit_text_username_activity_main);
        txt_password = findViewById(R.id.edit_text_password_activity_main);
        //daftar = findViewById(R.id.button_sign_up_activity_main);
        Toast.makeText(Login.this,"ID Sesion : "+MainActivity.id_user_s,Toast.LENGTH_SHORT).show();

        //daftar.setOnClickListener();

    }

    public void Buatakun(View view) {
        Intent keregister = new Intent(Login.this, Register.class);
        startActivity(keregister);
        finish();
    }

    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(Login.this,"Mengambil Data","Mohon Tunggu...",false,false);
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
                String s = rh.sendGetRequest(KonfigurasiUser.URL_GET_ALL);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee(){

        JSONObject jsonObject = null;
//        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(KonfigurasiUser.TAG_JSON_ARRAY);
            //Toast.makeText(Login.this,"Get Jsoaaan : "+result,Toast.LENGTH_LONG).show();
            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String id = jo.getString(KonfigurasiUser.TAG_ID);
                String nama = jo.getString(KonfigurasiUser.TAG_NAMA);
                String username = jo.getString(KonfigurasiUser.TAG_Email);
                String password = jo.getString(KonfigurasiUser.TAG_PASSWORD);
                //Toast.makeText(Login.this,"Get Json : "+nama,Toast.LENGTH_LONG).show();

                if(username.equals(txt_username.getText().toString()) && password.equals(txt_password.getText().toString())) {
                    Toast.makeText(Login.this, "Email : " + txt_username.getText() + " Password : " + txt_password.getText() + " Anda berhasil Login", Toast.LENGTH_SHORT).show();
                    //sesi.setId_user(id);
                    MainActivity.id_user_s = id;
                    Intent keregister = new Intent(Login.this, MainActivity.class);
                    startActivity(keregister);
                    finish();
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void masuk(View view) {
        getJSON();
        //Toast.makeText(Login.this,"Username : "+txt_username.getText()+" Password : "+txt_password.getText(),Toast.LENGTH_SHORT).show();
    }
}