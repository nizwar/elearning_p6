package id.nizwar.crudelearningbsi;

import android.content.Context;
import android.util.Log;

class Biodata extends Koneksi {
    // sourcecode untuk URL ->URL menggunakan IP address default eclipse
    public static String URL = "http://172.20.1.28/elearning_6.php";
    private String url = "";
    private String response = "";

    private final Context context;

    Biodata(Context context) {
        this.context = context;
    }

    String tampilBiodata() {
        try {
            url = URL + "?operasi=view";
            response = call(context, url);
        } catch (Exception ignored) {
        }
        return response;
    }

    //memasukan biodata baru ke dlama database
    String insertBiodata(String nim, String nama, String alamat) {
        try {
            url = URL + "?operasi=insert&nim=" + nim + "&nama=" + nama + "&alamat=" + alamat;
            response = call(context, url);
        } catch (Exception ignored) {
        }
        return response;
    }

    //melihat biodata berdasarkan ID
    String getBiodataById(int id) {
        try {
            url = URL + "?operasi=get_biodata_by_id&id=" + id;
            response = call(context, url);
        } catch (Exception ignored) {
        }
        return response;
    }

    //mengubah isi biodata
    String updateBiodata(String id, String nim, String nama, String alamat) {
        try {
            url = URL + "?operasi=update&id=" + id + "&nim=" + nim + "&nama=" + nama + "&alamat=" +
                    alamat;
            response = call(context, url);
        } catch (Exception ignored) {
        }
        return response;
    }

    //coding hapus
    void deleteBiodata(int id) {
        try {
            url = URL + "?operasi=delete&id=" + id;
            response = call(context, url);
        } catch (Exception ignored) {
        }
    }
}
