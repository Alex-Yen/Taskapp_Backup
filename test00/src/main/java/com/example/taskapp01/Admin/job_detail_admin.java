package com.example.taskapp01.Admin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class job_detail_admin extends AppCompatActivity {

    TextView _Usertype00, _Username00, _House00, _House_Area00, _Tenant_tel_no00, _Job00, _Location00;
    Button _Edit_but, _Remove_but;
    ToggleButton _Message00, _Job_Status00;
    String s_Usertype00, s_Username00, s_House00, s_House_Area00, s_Tenant_tel_no00, s_Job00, s_Location00, test00;
    Boolean test;
    ImageView imageView, imageView1, imageView2;

    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_admin);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Job Detail");

        _Usertype00 = (TextView) findViewById(R.id.Usertype00);
        _Username00 = (TextView) findViewById(R.id.Username00);
        _House00 = (TextView) findViewById(R.id.House00);
        _House_Area00 = (TextView) findViewById(R.id.House_Area00);
        _Tenant_tel_no00 = (TextView) findViewById(R.id.Tenant_tel_no00);
        _Job00 = (TextView) findViewById(R.id.Job00);
        _Location00 = (TextView) findViewById(R.id.Location00);
        imageView = (ImageView) findViewById(R.id.imageView);
        if (Admin.UserArrayList.get(position).getImage0().contains("http://")) {
            Picasso.with(this).load(Admin.UserArrayList.get(position).getImage0()).error(R.drawable.ic_baseline_image_24).into(imageView);
        }

        imageView1 = (ImageView) findViewById(R.id.imageView1);
        if (Admin.UserArrayList.get(position).getImage1().contains("http://")) {
            Picasso.with(this).load(Admin.UserArrayList.get(position).getImage1()).error(R.drawable.ic_baseline_image_24).into(imageView1);
        }

        imageView2 = (ImageView) findViewById(R.id.imageView2);
        if (Admin.UserArrayList.get(position).getImage2().contains("http://")){
            Picasso.with(this).load(Admin.UserArrayList.get(position).getImage2()).error(R.drawable.ic_baseline_image_24).into(imageView2);
        }

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        s_Usertype00 = Admin.UserArrayList.get(position).getUsertype();
        s_Username00 = Admin.UserArrayList.get(position).getUsername();
        s_House00 = Admin.UserArrayList.get(position).getHouse();
        s_House_Area00 = Admin.UserArrayList.get(position).getHouse_Area();
        s_Tenant_tel_no00 = Admin.UserArrayList.get(position).getTenant_tel_no();
        s_Job00 = Admin.UserArrayList.get(position).getJob();
        s_Location00 = Admin.UserArrayList.get(position).getLocation();
        test00 = Admin.UserArrayList.get(position).getMessage();
        test = Admin.UserArrayList.get(position).getMessage().matches("1");

        _Usertype00.setText("Usertype: " + s_Usertype00);
        _Username00.setText("Username: " + s_Username00);
        _House00.setText("House: " + s_House00);
        _House_Area00.setText("House Area: " + s_House_Area00);
        _Tenant_tel_no00.setText("Tenant tel no: " + s_Tenant_tel_no00);
        _Job00.setText("Job: " + s_Job00);
        _Location00.setText("Location: " + s_Location00);

        _Message00 = (ToggleButton) findViewById(R.id.Message00);
        _Message00.setChecked(Boolean.parseBoolean(String.valueOf(Admin.UserArrayList.get(position).getMessage().matches("1"))));
        _Message00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Message00.isChecked()) {
                    //When switch is flipped on
                    String s_Message1 = "1";
                    updateMessage(s_House00, s_Message1);
                }
                else {
                    //When switch is off
                    String s_Message0 = "0";
                    updateMessage(s_House00, s_Message0);
                }
            }
        });

        _Job_Status00 = (ToggleButton) findViewById(R.id.Job_Status00);
        _Job_Status00.setChecked(Boolean.parseBoolean(String.valueOf(Admin.UserArrayList.get(position).getStatus().matches("1"))));
        _Job_Status00.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(_Job_Status00.isChecked()) {
                    //When switch is flipped on
                    String s_Status1 = "1";
                    updateStatus(s_House00, s_Status1);
                }
                else {
                    //When switch is off
                    String s_Status0 = "0";
                    updateStatus(s_House00, s_Status0);
                }
            }
        });

        _Edit_but = (Button) findViewById(R.id.Edit_but);
        _Edit_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(job_detail_admin.this, job_detail_edit_admin.class).putExtra("position", position));
                finish();
            }
        });

        _Remove_but = (Button) findViewById(R.id.Remove_but);
        _Remove_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove_job(s_Usertype00, s_Username00, s_House00, s_House_Area00, s_Tenant_tel_no00, s_Job00, s_Location00);

                startActivity(new Intent(job_detail_admin.this, Admin.class));
                finish();
            }
        });
    }

    private void remove_job(final String Usertype, final String Username, final String House, final String House_Area, final String Tenant_tel_no, final String Job, final String Location) {
        String url = "http://192.168.0.105/taskapp00/delete_job.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully delete")) {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_admin.this);
        requestQueue.add(request);
    }

    public void updateMessage(final String House, final String Message) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_message.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update message")) {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_admin.this);
        requestQueue.add(request);
    }

    public void updateStatus(final String House, final String Status) {
        String url= "http://192.168.0.105/taskapp00/togglebutton_status.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update status")) {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_admin.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_admin.this);
        requestQueue.add(request);
    }
}