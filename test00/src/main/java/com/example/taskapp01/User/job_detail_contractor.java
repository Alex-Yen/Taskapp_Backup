package com.example.taskapp01.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.taskapp01.Admin.Admin;
import com.example.taskapp01.R;

import java.util.HashMap;
import java.util.Map;

public class job_detail_contractor extends AppCompatActivity {

    TextView _Usertype01, _Username01, _House01, _House_Area01, _Tenant_tel_no01, _Job01, _Location01;
    ToggleButton _Message01, _Job_Status01;
    String s_House01;
    private final Handler handler = new Handler();

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_contractor);
        
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Job Detail");

        _Usertype01 = (TextView) findViewById(R.id.Usertype01);
        _Username01 = (TextView) findViewById(R.id.Username01);
        _House01 = (TextView) findViewById(R.id.House01);
        _House_Area01 = (TextView) findViewById(R.id.House_Area01);
        _Tenant_tel_no01 = (TextView) findViewById(R.id.Tenant_tel_no01);
        _Job01 = (TextView) findViewById(R.id.Job01);
        _Location01 = (TextView) findViewById(R.id.Location01);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        _Usertype01.setText("Usertype: " + Contractor.UserArrayList.get(position).getUsertype());
        _Username01.setText("Username: " + Contractor.UserArrayList.get(position).getUsername());
        _House01.setText("House: " + Contractor.UserArrayList.get(position).getHouse());
        _House_Area01.setText("House Area: " + Contractor.UserArrayList.get(position).getHouse_Area());
        _Tenant_tel_no01.setText("Tenant tel no: " + Contractor.UserArrayList.get(position).getTenant_tel_no());
        _Job01.setText("Job: " + Contractor.UserArrayList.get(position).getJob());
        _Location01.setText("Location: " + Contractor.UserArrayList.get(position).getLocation());

        s_House01=Contractor.UserArrayList.get(position).getHouse();

        _Message01 = (ToggleButton) findViewById(R.id.Message01);
        _Message01.setChecked(Boolean.parseBoolean(String.valueOf(Contractor.UserArrayList.get(position).getMessage().matches("1"))));
        _Message01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Message01.isChecked()) {
                    //When switch is flipped on
                    String s_Message1 = "1";
                    updateMessage(s_House01, s_Message1);
                }
                else {
                    //When switch is off
                    String s_Message0 = "0";
                    updateMessage(s_House01, s_Message0);
                }
            }
        });

        _Job_Status01 = (ToggleButton) findViewById(R.id.Job_Status01);
        _Job_Status01.setChecked(Boolean.parseBoolean(String.valueOf(Contractor.UserArrayList.get(position).getStatus().matches("1"))));
        _Job_Status01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Job_Status01.isChecked()) {
                    //When switch is flipped on
                    String s_Status1 = "1";
                    updateStatus(s_House01, s_Status1);
                }
                else {
                    //When switch is off
                    String s_Status0 = "0";
                    updateStatus(s_House01, s_Status0);
                }
            }
        });
        doTheAutoRefresh();
    }

    public void updateMessage(final String House, final String Message) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_message.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update message")) {
                    Toast.makeText(job_detail_contractor.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_contractor.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("House", House);
                param.put("Message", Message);

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_contractor.this);
        requestQueue.add(request);
    }

    public void updateStatus(final String House, final String Status) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_status.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update status")) {
                    Toast.makeText(job_detail_contractor.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_contractor.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_SHORT).show();
                }
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("House", House);
                param.put("Status", Status);

                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_contractor.this);
        requestQueue.add(request);
    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Write code for your refresh logic
                doTheAutoRefresh();
            }
        }, 3000);
    }
}