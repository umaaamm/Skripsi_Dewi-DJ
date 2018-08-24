package com.example.umaaamm.dewi_jilbab;

/**
 * Created by fujimiya on 7/18/18.
 */

public class KonfigurasiUser {

    public static final String URL_ADD="http://umam-developer.com/API/hijab/tambahUser.php";
    public static final String URL_GET_ALL = "http://umam-developer.com/API/hijab/tampilSemuaUser.php";
    public static final String URL_GET_EMP = "http://umam-developer.com/API/hijab/tampilUser.php?User_id=";
    public static final String URL_UPDATE_EMP = "http://umam-developer.com/API/hijab/updateUser.php";
    public static final String URL_DELETE_EMP = "http://umam-developer.com/API/hijab/hapusUser.php";

    //Dibawah ini merupakan Kunci yang akan digunakan untuk mengirim permintaan ke Skrip PHP
    public static final String KEY_EMP_ID_User = "id_user";
    public static final String KEY_EMP_NAMA = "nama";
    public static final String KEY_EMP_UName = "email";
    public static final String KEY_EMP_PW = "password";


    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_ID = "id_user";
    public static final String TAG_NAMA = "nama_user";
    //public static final String TAG_UserName = "username";
    public static final String TAG_Email = "email";
    public static final String TAG_PASSWORD = "password";

    //ID karyawan
    //emp itu singkatan dari Employee
    public static final String EMP_ID = "emp_id";
}
