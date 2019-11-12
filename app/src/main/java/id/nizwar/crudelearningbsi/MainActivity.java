package id.nizwar.crudelearningbsi;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Biodata biodataActivity = new Biodata(this);
    TableLayout tableLayout;
    Button buttonTambahBiodata;
    ArrayList<Button> buttonEdit = new ArrayList<>();
    ArrayList<Button> buttonDelete = new ArrayList<>();
    JSONArray arrayBiodata;
    SwipeRefreshLayout swiper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 15) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Inisialisasi Komponen
        tableLayout = findViewById(R.id.tableBiodata);
        buttonTambahBiodata = findViewById(R.id.btnTambahMahasiswa);
        buttonTambahBiodata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tambahBiodata();
            }
        });
        swiper = findViewById(R.id.swiper);
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });

        //menambah baris untuk tabel
        // Mengubah data dari BiodataActivity yang berupa String menjadi array
        initData();
    }

    public void initData() {
        tableLayout.removeAllViews();
        new CountDownTimer(100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Volley.newRequestQueue(MainActivity.this).add(new JsonObjectRequest(0, Biodata.URL + "?operasi=view", null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    arrayBiodata = response.getJSONArray("data");
                                    TableRow barisTabel = new TableRow(MainActivity.this);
                                    tableLayout.addView(barisTabel, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                                    // Menambahkan tampilan teks untuk judul pada tabel
                                    TextView viewHeaderId = new TextView(MainActivity.this);
                                    TextView viewHeaderNim = new TextView(MainActivity.this);
                                    TextView viewHeaderNama = new TextView(MainActivity.this);
                                    TextView viewHeaderAlamat = new TextView(MainActivity.this);
                                    TextView viewHeaderAction = new TextView(MainActivity.this);

                                    viewHeaderId.setText(R.string.str_id);
                                    viewHeaderNim.setText(R.string.str_nim);
                                    viewHeaderNama.setText(R.string.str_nama);
                                    viewHeaderAlamat.setText(R.string.str_alamat);
                                    viewHeaderAction.setText(R.string.str_action);

                                    viewHeaderId.setPadding(5, 1, 5, 1);
                                    viewHeaderNim.setPadding(5, 1, 5, 1);
                                    viewHeaderNama.setPadding(5, 1, 5, 1);
                                    viewHeaderAlamat.setPadding(5, 1, 5, 1);
                                    viewHeaderAction.setPadding(5, 1, 5, 1);

                                    // Menampilkan tampilan TextView ke dalam tabel
                                    barisTabel.addView(viewHeaderId);
                                    barisTabel.addView(viewHeaderNim);
                                    barisTabel.addView(viewHeaderNama);
                                    barisTabel.addView(viewHeaderAlamat);
                                    barisTabel.addView(viewHeaderAction);

                                    for (int i = 0; i < arrayBiodata.length(); i++) {
                                        JSONObject jsonChildNode = arrayBiodata.getJSONObject(i);
                                        final String nim = jsonChildNode.optString("nim");
                                        final String nama = jsonChildNode.optString("nama");
                                        final String alamat = jsonChildNode.optString("alamat");
                                        final String id = jsonChildNode.optString("id");
                                        barisTabel = new TableRow(MainActivity.this);
                                        // Memberi warna pada baris tabel
                                        if (i % 2 == 0) {
                                            barisTabel.setBackgroundColor(Color.LTGRAY);
                                        }

                                        TextView viewId = new TextView(MainActivity.this);
                                        TextView viewNim = new TextView(MainActivity.this);
                                        TextView viewNama = new TextView(MainActivity.this);
                                        TextView viewAlamat = new TextView(MainActivity.this);

                                        viewId.setPadding(5, 1, 5, 1);
                                        viewNim.setPadding(5, 1, 5, 1);
                                        viewNama.setPadding(5, 1, 5, 1);
                                        viewAlamat.setPadding(5, 1, 5, 1);

                                        viewId.setText(id);
                                        viewNim.setText(nim);
                                        viewNama.setText(nama);
                                        viewAlamat.setText(alamat);

                                        barisTabel.addView(viewId);
                                        barisTabel.addView(viewNim);
                                        barisTabel.addView(viewNama);
                                        barisTabel.addView(viewAlamat);

                                        final int iButton = i;
                                        // Menambahkan button Edit
                                        buttonEdit.add(i, new Button(MainActivity.this));
                                        buttonEdit.get(i).setText(R.string.str_edit);
                                        buttonEdit.get(i).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(MainActivity.this, "Edit : " + buttonEdit.get(iButton).getId(),
                                                        Toast.LENGTH_SHORT).show();
                                                updateData(Integer.parseInt(id), nim, nama, alamat);
                                            }
                                        });
                                        barisTabel.addView(buttonEdit.get(i));
                                        buttonDelete.add(i, new Button(MainActivity.this));
                                        buttonDelete.get(i).setText(R.string.str_delete);
                                        buttonDelete.get(i).setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Toast.makeText(MainActivity.this, "Delete : " +
                                                        buttonDelete.get(iButton).getId(), Toast.LENGTH_SHORT).show();
                                                deleteBiodata(Integer.parseInt(id));
                                            }
                                        });
                                        barisTabel.addView(buttonDelete.get(i));
                                        tableLayout.addView(barisTabel, new TableLayout.LayoutParams
                                                (TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT));
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                swiper.setRefreshing(false);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                swiper.setRefreshing(false);
                            }
                        })
                );
            }
        }.start();
    }

    public void deleteBiodata(int id) {
        biodataActivity.deleteBiodata(id);
        initData();
    }

    public void updateData(final int id, String nim, String nama, String alamat) {
        LinearLayout layoutInput = new LinearLayout(MainActivity.this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);
        // Membuat id tersembunyi pada AlertDialog
        final TextView viewId = new TextView(MainActivity.this);
        final EditText editNim = new EditText(MainActivity.this);
        final EditText editNama = new EditText(MainActivity.this);
        final EditText editAlamat = new EditText(MainActivity.this);

        viewId.setText(String.valueOf(id));
        editNim.setText(nim);
        editNama.setText(nama);
        editAlamat.setText(alamat);

        viewId.setTextColor(Color.TRANSPARENT);

        layoutInput.addView(viewId);
        layoutInput.addView(editNim);
        layoutInput.addView(editNama);
        layoutInput.addView(editAlamat);

        AlertDialog.Builder builderEditBiodata = new AlertDialog.Builder(MainActivity.this);

        builderEditBiodata.setTitle("Update Biodata");
        builderEditBiodata.setView(layoutInput);
        builderEditBiodata.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                biodataActivity.updateBiodata(viewId.getText().toString(),
                        editNim.getText().toString(), editNama.getText().toString(),
                        editAlamat.getText().toString());
                initData();
            }
        });
        // Jika tidak ingin mengubah data pada Biodata
        builderEditBiodata.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builderEditBiodata.show();
    }

    public void tambahBiodata() {
        LinearLayout layoutInput = new LinearLayout(this);
        layoutInput.setOrientation(LinearLayout.VERTICAL);
        final EditText editNim = new EditText(this);
        final EditText editNama = new EditText(this);
        final EditText editAlamat = new EditText(this);

        editNim.setHint("NIM");
        editNama.setHint("Nama");
        editAlamat.setHint("Alamat");

        layoutInput.addView(editNim);
        layoutInput.addView(editNama);
        layoutInput.addView(editAlamat);
        // Membuat AlertDialog untuk menambahkan data pada Biodata
        AlertDialog.Builder builderInsertBiodata = new AlertDialog.Builder(this);
        //builderInsertBiodata.setIcon(R.drawable.webse);
        builderInsertBiodata.setTitle("Insert Biodata");
        builderInsertBiodata.setView(layoutInput);
        builderInsertBiodata.setPositiveButton("Insert", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String nim = editNim.getText().toString();
                        String nama = editNama.getText().toString();
                        String alamat = editAlamat.getText().toString();
                        System.out.println("NIM : " + nim + "Nama : " + nama + "Alamat : " + alamat);
                        String laporan = biodataActivity.insertBiodata(nim, nama, alamat);
                        Toast.makeText(MainActivity.this, laporan, Toast.LENGTH_SHORT).show();
                        initData();
                    }
                });
        builderInsertBiodata.setNegativeButton("Cancel", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        builderInsertBiodata.show();
    }
}
