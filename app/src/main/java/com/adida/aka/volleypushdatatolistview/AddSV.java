package com.adida.aka.volleypushdatatolistview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddSV extends AppCompatActivity {

    EditText edtName, edtBirth, edtPlace;
    Button   btnAdd, btnCancel;
    String mName, mBirth, mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sv);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName =  edtName.getText().toString().trim();
                mBirth =   edtBirth.getText().toString().trim();
                mPlace = edtPlace.getText().toString().trim();
                if(mName.isEmpty() | mBirth.isEmpty() | mPlace.isEmpty()){
                    Toast.makeText(AddSV.this, "Khong dc de trong!!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                addSV("http://proakashj.esy.es/insertDB.php");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void addSV(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("1")){
                            Toast.makeText(AddSV.this, "Add Success!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddSV.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(AddSV.this, "Add fail!!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddSV.this, "Error: "+ error.toString(), Toast.LENGTH_SHORT).show();
            }
        })

            //Post params to server {}
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hoten", edtName.getText().toString().trim());
                params.put("namsinh", edtBirth.getText().toString().trim());
                params.put("diachi", edtPlace.getText().toString().trim());
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void addControls() {
        edtName   = (EditText) findViewById(R.id.editTextName);
        edtBirth  = (EditText) findViewById(R.id.editTextBirthday);
        edtPlace  = (EditText) findViewById(R.id.editTextPlace);

        btnAdd    = (Button) findViewById(R.id.buttonAdd);
        btnCancel = (Button) findViewById(R.id.buttonCancel);
    }
}
