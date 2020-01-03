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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import server.ConfigUrl;

public class RegistrasiActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private TextView txtData;
    private EditText edtNpm, edtNama, edtProdi, edtPassword;

    private Button btnLinkLogin;
    private Button btnDaftar;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);

        edtNpm = (EditText) findViewById(R.id.edtNpm);
        edtNama = (EditText) findViewById(R.id.edtNama);
        edtProdi = (EditText) findViewById(R.id.edtProdi);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnDaftar = (Button) findViewById(R.id.btndaftar);


        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNpm = edtNpm.getText().toString();
                String strNama = edtNama.getText().toString();
                String strProdi = edtProdi.getText().toString();
                String strPass = edtPassword.getText().toString();

                if (strNpm.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Npm Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strNama.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Nama Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strProdi.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Prodi Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else if (strPass.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                } else {
                    inputData(strNpm, strNama, strProdi, strPass);
                }
            }
        });

        getSupportActionBar().hide();

        btnLinkLogin = (Button) findViewById(R.id.btnLinkLogin);

        btnLinkLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegistrasiActivity.this, Loginactivity.class);
                startActivity(i);
                finish();
            }
        });
//        fetchJsonResponse();
    }

//    private void fetchJsonResponse() {
//        // Pass second argument as "null" for GET requests
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllMhs, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            String result = response.getString("data");
//                            Toast.makeText(RegistrasiActivity.this, result, Toast.LENGTH_SHORT).show();
//                            //Log.v("Ini data dari server", result.toString());
//
//                            JSONArray res = new JSONArray(result);
//                            for (int i = 0; i < res.length(); i++){
//                                JSONObject jobj = res.getJSONObject(i);
//                                txtData.append("Npm = " + jobj.getString("npm") + "\n"+
//                                        "Nama = " + jobj.getString("nama") + "\n\n");
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });
//
//        /* Add your Requests to the RequestQueue to
//            @Overrideexecute */
//        mRequestQueue.add(req);
//    }

    private void inputData(String npm, String nama, String prodi, String pass){

// Post params to be sent to the server
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("npm", npm);
        params.put("nama", nama);
        params.put("prodi", prodi);
        params.put("password", pass);

        pDialog.setMessage("Mohon Tunggu");
        showDialog();

        JsonObjectRequest req = new JsonObjectRequest(ConfigUrl.inputDataMhs, new JSONObject(params),
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
                                Intent i = new Intent(RegistrasiActivity.this,Loginactivity.class);
                                startActivity(i);
                                finish();
                            }
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                            VolleyLog.v("Response:%n %s", response.toString(4));
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
