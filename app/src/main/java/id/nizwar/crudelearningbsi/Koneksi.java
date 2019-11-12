package id.nizwar.crudelearningbsi;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

class Koneksi {
    String call(Context context, String url) {
        return openHttpConnection(context, url);
    }

    //membuka URL dan meminta respon dari input streamreader
    private String output = "";

    private String openHttpConnection(Context context, String url) {
        output = "";
        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                output = response;
                Log.e("Response", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Koneksi", "error terjadi " + error.getMessage());
            }
        });

        Volley.newRequestQueue(context).add(req);
        return output;
    }
}