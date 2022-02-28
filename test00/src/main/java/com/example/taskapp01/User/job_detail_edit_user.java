package com.example.taskapp01.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

public class job_detail_edit_user extends AppCompatActivity {

    Spinner _Spinner_selection02_edit;
    EditText _Username02_edit, _House02_edit, _House_Area02_edit, _Tenant_tel_no02_edit, _Job02_edit, _Location02_edit;
    Button _Update02_but;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_edit_user);

        _Spinner_selection02_edit = (Spinner) findViewById(R.id.Spinner_selection02_edit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_user_type, R.layout.support_simple_spinner_dropdown_item);
        _Spinner_selection02_edit.setAdapter(adapter);
        _Username02_edit = (EditText) findViewById(R.id.Username02_edit);
        _House02_edit = (EditText) findViewById(R.id.House02_edit);
        _House_Area02_edit = (EditText) findViewById(R.id.House_Area02_edit);
        _Tenant_tel_no02_edit = (EditText) findViewById(R.id.Tenant_tel_no02_edit);
        _Job02_edit = (EditText) findViewById(R.id.Job02_edit);
        _Location02_edit = (EditText) findViewById(R.id.Location02_edit);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        _Username02_edit.setText(User.UserArrayList.get(position).getUsername());
        _House02_edit.setText(User.UserArrayList.get(position).getHouse());
        _House_Area02_edit.setText(User.UserArrayList.get(position).getHouse_Area());
        _Tenant_tel_no02_edit.setText(User.UserArrayList.get(position).getTenant_tel_no());
        _Job02_edit.setText(User.UserArrayList.get(position).getJob());
        _Location02_edit.setText(User.UserArrayList.get(position).getLocation());


        _Update02_but = (Button) findViewById(R.id.Update02_but);
        _Update02_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_Usertype = _Spinner_selection02_edit.getSelectedItem().toString();
                String s_Username = _Username02_edit.getText().toString();
                String s_House = _House02_edit.getText().toString();
                String s_House_Area = _House_Area02_edit.getText().toString();
                String s_Tenant_tel_no = _Tenant_tel_no02_edit.getText().toString();
                String s_Job = _Job02_edit.getText().toString();
                String s_Location = _Location02_edit.getText().toString();
                update(s_Usertype, s_Username, s_House, s_House_Area, s_Tenant_tel_no, s_Job, s_Location);

                startActivity(new Intent(job_detail_edit_user.this, User.class));
                finish();
            }
        });
    }

    public void update(final String Usertype, final String Username, final String House, final String House_Area, final String Tenant_tel_no, final String Job, final String Location) {
        String url= "http://192.168.0.105/taskapp00/edit_job.php";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("Successfully update")) {
                    Toast.makeText(job_detail_edit_user.this, response, Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(job_detail_edit_user.this, response, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(job_detail_edit_user.this);
        requestQueue.add(request);
    }
}