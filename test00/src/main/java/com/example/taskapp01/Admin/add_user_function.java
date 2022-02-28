package com.example.taskapp01.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.taskapp01.MainActivity;
import com.example.taskapp01.MySingleton;
import com.example.taskapp01.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class add_user_function extends AppCompatActivity {
    EditText _register_id, _register_password;
    Spinner _register_user_type;
    Button _register_user_but;

//widget function in admin_add_user_function (only to be used by admin)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_user_function);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add User");

        _register_id = (EditText) findViewById(R.id.register_id);
        _register_password = (EditText) findViewById(R.id.register_password);
        _register_user_type = (Spinner) findViewById(R.id.register_user_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_user_type, R.layout.support_simple_spinner_dropdown_item);
        _register_user_type.setAdapter(adapter);
        _register_user_but = (Button) findViewById(R.id.register_user_but);
        _register_user_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = _register_id.getText().toString();
                String password = _register_password.getText().toString();
                String user_type = _register_user_type.getSelectedItem().toString();
                if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    Toast.makeText(add_user_function.this,"All fields required", Toast.LENGTH_LONG).show();
                }
                else {
                    BackGroundTask(username, password, user_type);
                }
            }
        });

        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bot_navigator);

        //Set Home Select
        bottomNavigationView.setSelectedItemId(R.id.nav_add_user);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_add_user:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(), Admin.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_add_job:
                        startActivity(new Intent(getApplicationContext(), add_job_function.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

//this is for android version 8 & below, need to set up security measure for android version 9 & above (error message at desktop)
    private void BackGroundTask(final String username, final String password, final String usertype) {
        final ProgressDialog progressDialog = new ProgressDialog(add_user_function.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering New Account");
        progressDialog.show();
        String url = "http://192.168.0.105/taskapp00/register_user.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Successfully Registered")) {
                    progressDialog.dismiss();
                    Toast.makeText(add_user_function.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(add_user_function.this, MainActivity.class));
                    finish();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(add_user_function.this,response ,Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Communication Error!", Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Server Side Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(), "Network Error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Parse Error!", Toast.LENGTH_LONG).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("username", username);
                param.put("password", password);
                param.put("usertype", usertype);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstence(add_user_function.this).addToRequestQueue(request);

    }
}
