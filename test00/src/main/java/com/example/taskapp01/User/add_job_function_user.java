package com.example.taskapp01.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.example.taskapp01.MainActivity;
import com.example.taskapp01.R;
import com.example.taskapp01.Userlist_Array;
import com.example.taskapp01.Userlist_ArrayAdapter;
import com.example.taskapp01.add_job_function2;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class add_job_function_user extends AppCompatActivity {

    TextView _Usertype_TV, _Username_TV, _House_TV, _House_area_TV, _Tenant_tel_no_TV;
    EditText _House_ET, _House_area_ET, _Tenant_tel_no_ET;
    Spinner _Spinner_selection02, _Spinner_selection03;
    Button _next02_but;
    String _Spinner_selection03s;

    public static ArrayList<Userlist_Array> userlist_arrays = new ArrayList<>();
    Userlist_ArrayAdapter userlist_ArrayAdapter;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_function_user);
        //Title of action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Job Description");

        //content from xml data
        _Usertype_TV = (TextView) findViewById(R.id.Usertype_TV2);
        _Username_TV = (TextView) findViewById(R.id.Username_TV2);
        _House_TV = (TextView) findViewById(R.id.House_TV2);
        _House_area_TV = (TextView) findViewById(R.id.House_area_TV2);
        _Tenant_tel_no_TV = (TextView) findViewById(R.id.Tenant_tel_no_TV2);
        _House_ET = (EditText) findViewById(R.id.House_ET2);
        _House_area_ET = (EditText) findViewById(R.id.House_area_ET2);
        _Tenant_tel_no_ET = (EditText) findViewById(R.id.Tenant_tel_no_ET2);
        _Spinner_selection02 = (Spinner) findViewById(R.id.Spinner_selection02);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_user_type,R.layout.support_simple_spinner_dropdown_item);
        _Spinner_selection02.setAdapter(adapter);

        retrieveUserlist();
        _Spinner_selection03 = (Spinner) findViewById(R.id.Spinner_selection03);
        userlist_ArrayAdapter = new Userlist_ArrayAdapter(this, userlist_arrays);
        _Spinner_selection03.setAdapter(userlist_ArrayAdapter);
        _Spinner_selection03.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Userlist_Array userlist_array = (Userlist_Array) adapterView.getItemAtPosition(i);
                _Spinner_selection03s = userlist_array.getUsername();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //shared preferences (Name, Mode)
        sharedPreferences = getSharedPreferences("Add_job_fx", MODE_PRIVATE);
        //handle next button, onClick: input data & save in shared preference & finally go to add_job_function2
        _next02_but = (Button) findViewById(R.id.next02_but);
        _next02_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input data to shared preference
                String _Spinner_selection02s = _Spinner_selection02.getSelectedItem().toString();
                String _House_ETs = _House_ET.getText().toString();
                String _House_area_ETs = _House_area_ET.getText().toString();
                String _Tenant_tel_no_ETs = _Tenant_tel_no_ET.getText().toString();
                //edit shared preference (put data)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //put data in shared preference
                editor.putString("Usertype", _Spinner_selection02s);
                editor.putString("Username", _Spinner_selection03s);
                editor.putString("House", _House_ETs);
                editor.putString("House Area", _House_area_ETs);
                editor.putString("Tenant tel no", _Tenant_tel_no_ETs);
                //apply changes to shared preference
                editor.apply();

                startActivity(new Intent(add_job_function_user.this, add_job_function2.class));
                finish();
            }
        });

        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.user_bot_navigator2);

        //Set Home Select
        bottomNavigationView.setSelectedItemId(R.id.user_nav_add_job);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.user_nav_add_job:
                        return true;
                    case R.id.user_nav_home:
                        startActivity(new Intent(getApplicationContext(), User.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.user_nav_logout:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void retrieveUserlist() {
        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.0.105/taskapp00/Userlist.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                userlist_arrays.clear();
                try{
                    JSONObject jObject = new JSONObject(response);
                    String success = jObject.getString("success");
                    JSONArray jArray = jObject.getJSONArray("phpreg");

                    if(success.equals("1")) {

                        for(int i=0;i<jArray.length();i++) {
                            JSONObject object = jArray.getJSONObject(i);

                            String username = object.getString("username");

                            userlist_arrays.add(new Userlist_Array(username));
                            userlist_ArrayAdapter.notifyDataSetChanged();
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
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
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}