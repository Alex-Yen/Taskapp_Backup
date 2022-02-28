package com.example.taskapp01.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.taskapp01.R;

import java.util.HashMap;
import java.util.Map;

public class job_detail_user extends AppCompatActivity {

    TextView _Usertype02, _Username02, _House02, _House_Area02, _Tenant_tel_no02, _Job02, _Location02;
    ToggleButton _Message02, _Job_Status02;
    Button _Edit02_but, _Remove02_but;
    String s_Usertype02, s_Username02, s_House02, s_House_Area02, s_Tenant_tel_no02, s_Job02, s_Location02;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Job Detail");

        _Usertype02 = (TextView) findViewById(R.id.Usertype02);
        _Username02 = (TextView) findViewById(R.id.Username02);
        _House02 = (TextView) findViewById(R.id.House02);
        _House_Area02 = (TextView) findViewById(R.id.House_Area02);
        _Tenant_tel_no02 = (TextView) findViewById(R.id.Tenant_tel_no02);
        _Job02 = (TextView) findViewById(R.id.Job02);
        _Location02 = (TextView) findViewById(R.id.Location02);

        _Edit02_but = (Button) findViewById(R.id.Edit02_but);
        _Edit02_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(job_detail_user.this, job_detail_edit_user.class).putExtra("position", position));
                finish();
            }
        });
        _Remove02_but = (Button) findViewById(R.id.Remove02_but);
        _Remove02_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_job(s_Usertype02, s_Username02, s_House02, s_House_Area02, s_Tenant_tel_no02, s_Job02, s_Location02);

                startActivity(new Intent(job_detail_user.this, User.class));
                finish();
            }
        });

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        s_Usertype02 = User.UserArrayList.get(position).getUsertype();
        s_Username02 = User.UserArrayList.get(position).getUsername();
        s_House02 = User.UserArrayList.get(position).getHouse();
        s_House_Area02 = User.UserArrayList.get(position).getHouse_Area();
        s_Tenant_tel_no02 = User.UserArrayList.get(position).getTenant_tel_no();
        s_Job02 = User.UserArrayList.get(position).getJob();
        s_Location02 = User.UserArrayList.get(position).getLocation();

        _Usertype02.setText("Usertype: " + s_Usertype02);
        _Username02.setText("Username: " + s_Username02);
        _House02.setText("House: " + s_House02);
        _House_Area02.setText("House Area: " + s_House_Area02);
        _Tenant_tel_no02.setText("Tenant tel no: " + s_Tenant_tel_no02);
        _Job02.setText("Job: " + s_Job02);
        _Location02.setText("Location: " + s_Location02);

        _Message02 = (ToggleButton) findViewById(R.id.Message02);
        _Message02.setChecked(Boolean.parseBoolean(String.valueOf(User.UserArrayList.get(position).getMessage().matches("1"))));
        _Message02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Message02.isChecked()) {
                    //When switch is flipped on
                    String s_Message1 = "1";
                    updateMessage(s_House02, s_Message1);
                }
                else {
                    //When switch is off
                    String s_Message0 = "0";
                    updateMessage(s_House02, s_Message0);
                }
            }
        });

        _Job_Status02 = (ToggleButton) findViewById(R.id.Job_Status02);
        _Job_Status02.setChecked(Boolean.parseBoolean(String.valueOf(User.UserArrayList.get(position).getStatus().matches("1"))));
        _Job_Status02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Job_Status02.isChecked()) {
                    //When switch is flipped on
                    String s_Status1 = "1";
                    updateStatus(s_House02, s_Status1);
                }
                else {
                    //When switch is off
                    String s_Status0 = "0";
                    updateStatus(s_House02, s_Status0);
                }
            }
        });
    }

    private void remove_job(final String Usertype, final String Username, final String House, final String House_Area, final String Tenant_tel_no, final String Job, final String Location) {
        String url = "http://192.168.0.105/taskapp00/delete_job.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully delete")) {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("Usertype", Usertype);
                param.put("Username", Username);
                param.put("House", House);
                param.put("House_Area", House_Area);
                param.put("Tenant_tel_no", Tenant_tel_no);
                param.put("Job", Job);
                param.put("Location", Location);
                return param;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_user.this);
        requestQueue.add(request);
    }

    public void updateMessage(final String House, final String Message) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_message.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update message")) {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_user.this);
        requestQueue.add(request);
    }

    public void updateStatus(final String House, final String Status) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_status.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update status")) {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_user.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_user.this);
        requestQueue.add(request);
    }
}
