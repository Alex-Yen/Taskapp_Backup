package com.example.taskapp01;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.example.taskapp01.Admin.Admin;
import com.example.taskapp01.User.Contractor;
import com.example.taskapp01.User.Landlord;
import com.example.taskapp01.User.User;


import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText _login_id, _password_id;
    Spinner _user_type;
    Button _login_but;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Login");

        _login_id = (EditText) findViewById(R.id.login_id);
        _password_id = (EditText) findViewById(R.id.password_id);
        _user_type = (Spinner) findViewById(R.id.user_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_user_type, R.layout.support_simple_spinner_dropdown_item);
        _user_type.setAdapter(adapter);

        _login_but = (Button) findViewById(R.id.login_but);
        _login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*startActivity(new Intent(MainActivity.this, Admin.class));*/
                String _login_ids = _login_id.getText().toString();
                String _password_ids = _password_id.getText().toString();
                String _user_types = _user_type.getSelectedItem().toString();
                if (TextUtils.isEmpty(_login_ids) || TextUtils.isEmpty(_password_ids) || TextUtils.isEmpty(_user_types)) {
                    Toast.makeText(MainActivity.this, "All fields required", Toast.LENGTH_LONG).show();
                } else {
                    login(_login_ids, _password_ids, _user_types);
                }
                //shared preferences (Name, Mode)
                sharedPreferences = getSharedPreferences("Check_user", MODE_PRIVATE);
                //edit shared preference (put data)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //put data in shared preference
                editor.putString("Username", _login_ids);
                editor.apply();
            }
        });
    }

    private void login(final String username, final String password, final String usertype) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Login successful");
        progressDialog.show();
        String url = "http://192.168.0.105/taskapp00/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            //conditions to login & to which usertype
            @Override
            public void onResponse(String response) {
                if (response.equals("Login success") && usertype.matches("admin")) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Admin.class));
                } else if (response.equals("Login success") && usertype.matches("user")) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, User.class));
                } else if (response.equals("Login success") && usertype.matches("contractor")) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Contractor.class));
                } else if (response.equals("Login success") && usertype.matches("landlord")) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainActivity.this, Landlord.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
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
        request.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstence(MainActivity.this).addToRequestQueue(request);
    }
}
//network error in from errorvolley response