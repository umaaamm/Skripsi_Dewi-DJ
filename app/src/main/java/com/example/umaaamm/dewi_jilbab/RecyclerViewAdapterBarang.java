package com.example.umaaamm.dewi_jilbab;

/**
 * Created by umaaamm on 15/07/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterBarang extends RecyclerView.Adapter<RecyclerViewAdapterBarang.ViewHolder>{
    private ArrayList<String> id_barang;
    private ArrayList<String> stok_barang;
    private ArrayList<String> nama_barang;
    private ArrayList<String> gambar_barang;

    public RecyclerViewAdapterBarang(ArrayList<String> idbrg,ArrayList<String> stok, ArrayList<String> nama,ArrayList<String> gambar) {
        id_barang = idbrg;
        stok_barang = stok;
        nama_barang = nama;
        gambar_barang = gambar;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView title,deskripsi_barang,harga,stok;
        public TextView tvSubtitle;
        public CardView cvMain;
        public ImageView img;

        public ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) v.findViewById(R.id.stok);
            cvMain = (CardView) v.findViewById(R.id.cv_main);
            img = (ImageView) v.findViewById(R.id.icon);

        }
    }

    @Override
    public RecyclerViewAdapterBarang.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_barang, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        RecyclerViewAdapterBarang.ViewHolder vh = new RecyclerViewAdapterBarang.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        final String name = nama_barang.get(position);
        final String id = id_barang.get(position);
        holder.title.setText(nama_barang.get(position));
        holder.tvSubtitle.setText("Stok : "+stok_barang.get(position));
        holder.img.setImageBitmap(StringToBitMap(gambar_barang.get(position)));
        // Set onclicklistener pada view cvMain (CardView)
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clicked element "+name, Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), ProdukDetail.class);
                intent.putExtra("id_barang",id);
                view.getContext().startActivity(intent);
                ((Activity)view.getContext()).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return nama_barang.size();
    }

    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}