package com.example.umaaamm.dewi_jilbab;

/**
 * Created by umaaamm on 15/07/18.
 */


public class Konfigurasi {

    public static final String URL_ADD="http://umam-developer.com/API/hijab/tambahKategori.php";
    public static final String URL_ADD_PRODUK="http://umam-developer.com/API/hijab/tambahHijab.php";
    public static final String URL_GET_ALL = "http://umam-developer.com/API/hijab/tampilSemuaKategori.php";
    public static final String URL_GET_EMP = "http://umam-developer.com/API/hijab/tampilKategori.php?kategori_id=";
    public static final String URL_UPDATE_EMP = "http://umam-developer.com/API/hijab/updateKategori.php";
    public static final String URL_DELETE_EMP = "http://umam-developer.com/API/hijab/hapusKategori.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID_KATEGORI = "kategori_id";
    public static final String KEY_EMP_NAMA = "barang_nama";
    public static final String KEY_EMP_GAMBAR = "gambar";
    public static final String KEY_EMP_HARGA = "harga";
    public static final String KEY_EMP_STOK = "stok";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id_kategori";
    public static final String TAG_NAMA = "nama_kategori";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
