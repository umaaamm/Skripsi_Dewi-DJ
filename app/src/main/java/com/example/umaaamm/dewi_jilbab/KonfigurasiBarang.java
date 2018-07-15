package com.example.umaaamm.dewi_jilbab;

/**
 * Created by umaaamm on 15/07/18.
 */

public class KonfigurasiBarang {

    public static final String URL_ADD = "http://umam-developer.com/API/hijab/tambahHijab.php";
    public static final String URL_GET_ALL = "http://umam-developer.com/API/hijab/tampilSemuaHijab.php";
    public static final String URL_GET_EMP = "http://umam-developer.com/API/hijab/tampilHijab.php?Hijab_id=";
    public static final String URL_UPDATE_EMP = "http://umam-developer.com/API/hijab/updateHijab.php";
    public static final String URL_DELETE_EMP = "http://umam-developer.com/API/hijab/hapusHijab.php";


    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID_BARANG = "Hijab_id";
    public static final String KEY_EMP_ID_KATEGORI = "kategori_id";
    public static final String KEY_EMP_NAMA = "barang_nama";
    public static final String KEY_EMP_GAMBAR = "gambar";
    public static final String KEY_EMP_HARGA = "harga";
    public static final String KEY_EMP_STOK = "stok";
    public static final String KEY_EMP_DESKRIPSI = "deskripsi";

    //JSON Tags
    public static final String TAG_JSON_ARRAY = "result";
    public static final String TAG_ID_BARANG = "id_barang";
    public static final String TAG_ID_KATEGORI = "id_kategori";
    public static final String TAG_STOK = "stok";
    public static final String TAG_NAMA = "nama_barang";
    public static final String TAG_GAMBAR = "gambar";
    public static final String TAG_GAMBAR_BMP = "gambarbmp";
    public static final String TAG_DESKRIPSI = "deskripsi";
    public static final String TAG_HARGA = "harga";

    //ID
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";

}
