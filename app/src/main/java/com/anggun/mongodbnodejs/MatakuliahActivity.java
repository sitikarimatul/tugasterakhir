package com.anggun.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;

public class MatakuliahActivity extends AppCompatActivity {
  private RequestQueue mRequestQueue;
  private TextView txtData;
  private EditText edtKodemk, edtNamamk, edtJam, edtHari, edtRuangan, edtNidn, edtNama;

  private Button btnMenu;
  private Button btnTambah;
  private ProgressDialog pDialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_matakuliah);

    getSupportActionBar().hide();
    mRequestQueue = Volley.newRequestQueue(this);

    edtKodemk = (EditText) findViewById(R.id.edtKodemk);
    edtNamamk = (EditText) findViewById(R.id.edtNamamk);
    edtJam = (EditText) findViewById(R.id.edtJam);
    edtHari = (EditText) findViewById(R.id.edtHari);
    edtRuangan = (EditText) findViewById(R.id.edtRuangan);
    edtNidn = (EditText) findViewById(R.id.edtNidn);
    edtNama = (EditText) findViewById(R.id.edtNama);

    btnTambah = (Button) findViewById(R.id.btnTambah);
    pDialog = new ProgressDialog(this);
    pDialog.setCancelable(false);

    btnTambah.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String strKodemk = edtKodemk.getText().toString();
        String strNamamk = edtNamamk.getText().toString();
        String strJam = edtJam.getText().toString();
        String strHari = edtHari.getText().toString();
        String strRuangan = edtRuangan.getText().toString();
        String strNidn = edtNidn.getText().toString();
        String strNama = edtNama.getText().toString();

        if (strKodemk.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Kode Matakuliah idak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strNamamk.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Nama Matakuliah Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strJam.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Jam Kuliah Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strHari.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Hari Kuliah Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strRuangan.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Ruangan Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strNidn.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Nidn Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else if (strNama.isEmpty()) {
          Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong",
            Toast.LENGTH_LONG).show();
        } else {
          inputData(strKodemk, strNamamk, strJam, strHari, strRuangan, strNidn, strNama);
          edtKodemk.setText("");
          edtNamamk.setText("");
          edtJam.setText("");
          edtHari.setText("");
          edtRuangan.setText("");
          edtNidn.setText("");
          edtNama.setText("");
          edtKodemk.requestFocus();
        }
      }
    });

    getSupportActionBar().hide();

    btnMenu = (Button) findViewById(R.id.btnMenu);

    btnMenu.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent i = new Intent(MatakuliahActivity.this, MainActivity.class);
        startActivity(i);
        finish();
      }
    });
//        fetchJsonResponse();
  }

  private void inputData(String Kodemk, String Namamk, String Jam, String Hari, String Ruangan, String Nidn, String Nama){

// Post params to be sent to the server
    HashMap<String, String> params = new HashMap<String, String>();
    params.put("kodematakuliah", Kodemk);
    params.put("namamatakuliah", Namamk);
    params.put("jam", Jam);
    params.put("hari", Hari);
    params.put("ruangan", Ruangan);
    params.put("nidn", Nidn);
    params.put("nama", Nama);

    pDialog.setMessage("Mohon Tunggu");
    showDialog();

    JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.inputDataMk, new JSONObject(params),
      new Response.Listener<JSONObject>() {

        @Override
        public void onResponse(JSONObject response) {
          hideDialog();
          try {
            boolean status = response.getBoolean("eror");
            String msg;
            if (status == true) {
              msg = response.getString("pesan");
            } else {
              msg = response.getString("pesan");
              Intent i = new Intent(MatakuliahActivity.this,MainActivity.class);
              startActivity(i);
              finish();
            }
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            VolleyLog.v("Response:%n %s", response.toString(7));
          } catch (JSONException e) {
            e.printStackTrace();
          }
        }
      }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        hideDialog();

        VolleyLog.e("Error: ", error.getMessage());
      }
    });

// add the request object to the queue to be executed
    //ApplicationController.getInstance().addToRequestQueue(req);

    mRequestQueue.add(req);
  }

  private void showDialog() {
    if (!pDialog.isShowing())
      pDialog.show();
  }
  private void hideDialog() {
    if (pDialog.isShowing())
      pDialog.dismiss();
  }
}

