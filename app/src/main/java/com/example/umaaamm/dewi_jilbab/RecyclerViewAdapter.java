package com.example.umaaamm.dewi_jilbab;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by umaaamm on 17/07/18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> nama_kategori;
    private ArrayList<String> id_kategori;

    public RecyclerViewAdapter(ArrayList<String> nama, ArrayList<String> id) {
        nama_kategori = nama;
        id_kategori = id;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // di tutorial ini kita hanya menggunakan data String untuk tiap item
        public TextView tvTitle;
        public TextView tvSubtitle;
        public CardView cvMain;

        public ViewHolder(View v) {
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_title);
            tvSubtitle = (TextView) v.findViewById(R.id.tv_subtitle);
            cvMain = (CardView) v.findViewById(R.id.cv_main);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // membuat view baru
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_rv_item, parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - mengambil elemen dari dataset (ArrayList) pada posisi tertentu
        // - mengeset isi view dengan elemen dari dataset tersebut
        final String name = nama_kategori.get(position);
        final String id = id_kategori.get(position);
        holder.tvTitle.setText(nama_kategori.get(position));
        holder.tvSubtitle.setText(id_kategori.get(position));
        // Set onclicklistener pada view cvMain (CardView)
        holder.cvMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Clicked element " + name, Snackbar.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), DetailKategori.class);
                intent.putExtra("id_kategori", id);
                view.getContext().startActivity(intent);
                ((Activity) view.getContext()).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        // menghitung ukuran dataset / jumlah data yang ditampilkan di RecyclerView
        return nama_kategori.size();
    }

}
