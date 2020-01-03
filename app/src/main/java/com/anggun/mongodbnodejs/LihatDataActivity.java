package com.anggun.mongodbnodejs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import server.ConfigUrl;

public class LihatDataActivity extends AppCompatActivity {

  private RequestQueue mRequestQueue;
  private TextView vdatamk;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_lihat_data);

    getSupportActionBar().hide();

    mRequestQueue = Volley.newRequestQueue(this);

  vdatamk = (TextView) findViewById(R.id.tvdatamk);
    fetchJsonResponse();
  }
  @Override
  public void onBackPressed(){
    Intent i = new Intent(LihatDataActivity.this, MainActivity.class);
    startActivity(i);
    finish();

    super.onBackPressed();
  }
  private void fetchJsonResponse() {
      // Pass second argument as "null" for GET requests
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, ConfigUrl.getAllMk, null,
                new Listener<JSONObject>() {
                  @Override
                  public void onResponse(JSONObject response) {
                    try {
                      String result = response.getString("data");
                      //           Toast.makeText(LihatDataActivity.this, result, Toast.LENGTH_SHORT).show();
                      //Log.v("Ini data dari server", result.toString());

                      JSONArray res = new JSONArray(result);
                      for (int i = 0; i < res.length(); i++) {
                        JSONObject jobj = res.getJSONObject(i);
                        vdatamk.append("kodematakuliah = " + jobj.getString("kodematakuliah") + "\n" +
                          "namamatakuliah = " + jobj.getString("namamatakuliah") + "\n" +
                          "jam = " + jobj.getString("jam") + "\n" +
                          "hari = " + jobj.getString("hari") + "\n" +
                          "jam = " + jobj.getString("ruangan") + "\n\n"
                   //       "ruangan = " + jobj.getString("ruangan") + "\n"
    //                      "nidn = " + jobj.getString("Nidn") + "\n" +
      //                    "nama = " + jobj.getString("Nama") + "\n"
                        );
                      }

                    } catch (JSONException e) {
                      e.printStackTrace();
                    }
                  }

  }, new Response.ErrorListener() {
          @Override
          public void onErrorResponse(VolleyError error) {

            VolleyLog.e("Error: ", error.getMessage());
          }
        });
            mRequestQueue.add(req);

          }
        }


