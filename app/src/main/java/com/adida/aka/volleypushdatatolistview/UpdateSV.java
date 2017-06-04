package com.adida.aka.volleypushdatatolistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adida.aka.volleypushdatatolistview.model.SinhVien;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateSV extends AppCompatActivity {

    private EditText edtName, edtBirth, edtPlace;
    private Button btnUpdate, btnCancel;
    private SinhVien sinhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sv);
        addControl();
        addEvents();



    }

    private void addEvents() {
        Intent intent = getIntent();
        sinhVien = (SinhVien) intent.getSerializableExtra("dataSV");
        edtName.setText(sinhVien.getTen());
        edtBirth.setText(sinhVien.getNamSinh());
        edtPlace.setText(sinhVien.getDiaChi());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String birth = edtBirth.getText().toString().trim();
                String place = edtPlace.getText().toString().trim();
                if (name.isEmpty() | birth.isEmpty() | place.isEmpty()){
                    Toast.makeText(UpdateSV.this, "Thong tin khong duoc trong!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateSV("http://proakashj.esy.es/updateDB.php", name, birth, place);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void updateSV(String url, final String name, final String birth, final String place){
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("ok")){
                            Toast.makeText(UpdateSV.this, "Update success!!!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UpdateSV.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(UpdateSV.this, "Update fail!!!!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", sinhVien.getId());
                params.put("name", name);
                params.put("birth", birth);
                params.put("place", place);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }



    private void addControl() {
        edtName  = (EditText) findViewById(R.id.editTextName);
        edtBirth = (EditText) findViewById(R.id.editTextBirth);
        edtPlace = (EditText) findViewById(R.id.editTextPlace);

        btnUpdate = (Button) findViewById(R.id.buttonUpdate);
        btnCancel = (Button) findViewById(R.id.buttonCancel);
    }

}
