package com.adida.aka.volleypushdatatolistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.adida.aka.volleypushdatatolistview.adapter.adapterStudent;
import com.adida.aka.volleypushdatatolistview.model.SinhVien;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ListView lvStudent;
    private adapterStudent adapterStudent;
    private ArrayList<SinhVien> listSV;
    private String urlLoad = "http://proakashj.esy.es/loadDB.php";
    private String urlDelete = "http://proakashj.esy.es/deleteDB.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
        loadData(urlLoad);
    }

    private void addControls() {
        lvStudent = (ListView) findViewById(R.id.lvSV);
        listSV   = new ArrayList<SinhVien>();
        adapterStudent = new adapterStudent(MainActivity.this, R.layout.item_sv, listSV);
        lvStudent.setAdapter(adapterStudent);
    }

    private void loadData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                        listSV.clear();

//                        try {
//                            for (int i =0; i< response.length(); i++){
//                                JSONObject object = response.getJSONObject(i);
//                                String id = object.getString("id");
//                                String ten = object.getString("ten");
//                                Toast.makeText(MainActivity.this, ten , Toast.LENGTH_SHORT).show();
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }

                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject objectSV = response.getJSONObject(i);
                                String id  = objectSV.getString("id");
                                String name = objectSV.getString("ten");
                                String birthday = objectSV.getString("namsinh");
                                String place = objectSV.getString("diachi");
                                listSV.add(new SinhVien(id, name, birthday, place ));
                                adapterStudent.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    public void deleteSV(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("ok")){
                            Toast.makeText(MainActivity.this, "Delete success", Toast.LENGTH_SHORT).show();
                            loadData(urlLoad);
                        }else{
                            Toast.makeText(MainActivity.this, "Delete fail!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }

        };

        requestQueue.add(stringRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_sv, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            Intent intent = new Intent(MainActivity.this, AddSV.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
