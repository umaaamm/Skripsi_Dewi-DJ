package com.example.umaaamm.dewi_jilbab;

/**
 * Created by fujimiya on 8/23/18.
 */

public class KonfigurasiTransaksi {
    public static final String URL_ADD="http://umam-developer.com/API/hijab/tambahTransaksi.php";
    public static final String URL_ADD_RATING="http://umam-developer.com/API/hijab/tambahRating.php";
    public static final String URL_GET_ALL = "http://umam-developer.com/API/hijab/tampilSemuaTransaksi.php";
    public static final String URL_GET_EMP = "http://umam-developer.com/API/hijab/tampilTransaksi.php?Transaksi_id=";
    public static final String URL_UPDATE_EMP = "http://umam-developer.com/API/hijab/updateTransaksi.php";
    public static final String URL_DELETE_EMP = "http://umam-developer.com/API/hijab/hapusTransaksi.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID_Transaksi = "transaksi_id";
    public static final String KEY_EMP_ID_FAKTUR = "faktur_id";
    public static final String KEY_EMP_ID_BARANG = "id_barang";
    public static final String KEY_EMP_ID_USER = "id_user";
    public static final String KEY_RATING = "rating";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID_TRANSAKSI = "id_Transaksi";
    public static final String TAG_ID_FAKTUR = "id_faktur";
    public static final String TAG_ID_BARANG = "id_barang";
    public static final String TAG_ID_USER = "id_user";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
