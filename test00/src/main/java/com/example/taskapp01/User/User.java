package com.example.taskapp01.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
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

import com.example.taskapp01.Homepage_ListView;
import com.example.taskapp01.MainActivity;
import com.example.taskapp01.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class User extends AppCompatActivity {

    ListView _user_listview;
    User_Adapter UA;
    String url = "http://192.168.0.105/taskapp00/User_joblist.php";
    Homepage_ListView homepageListView;
    private final Handler handler = new Handler();

    public static ArrayList<Homepage_ListView> UserArrayList = new ArrayList<>();

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("User Homepage");

        _user_listview = (ListView) findViewById(R.id.user_listview);
        UA = new User_Adapter(this, UserArrayList);
        _user_listview.setAdapter(UA);
        UA.notifyDataSetChanged();

        _user_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long Username) {
                startActivity(new Intent(User.this, job_detail_user.class).putExtra("position", position));
            }
        });

        retrieveData();
        doTheAutoRefresh();

        //Initialise and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.user_bot_navigator);

        //Set Home Select
        bottomNavigationView.setSelectedItemId(R.id.user_nav_home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.user_nav_home:
                        return true;
                    case R.id.user_nav_add_job:
                        //shared preferences (Name, Mode)
                        sharedPreferences = getSharedPreferences("User_type", MODE_PRIVATE);
                        //edit shared preference (put data)
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //put data in shared preference
                        editor.putString("User_type", "User");
                        //apply changes to shared preference
                        editor.apply();
                        startActivity(new Intent(getApplicationContext(), add_job_function_user.class));
                        overridePendingTransition(0, 0);
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

    public void retrieveData() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                UserArrayList.clear();
                try{
                    JSONObject jObject = new JSONObject(response);
                    String success = jObject.getString("success");
                    JSONArray jArray = jObject.getJSONArray("phpjob");

                    if(success.equals("1")) {

                        for(int i=0;i<jArray.length();i++) {
                            JSONObject object = jArray.getJSONObject(i);

                            String usertype = object.getString("Usertype");
                            String username = object.getString("Username");
                            String house = object.getString("House");
                            String house_area = object.getString("House_Area");
                            String tenant_tel_no = object.getString("Tenant_tel_no");
                            String job = object.getString("Job");
                            String location = object.getString("Location");
                            String message = object.getString("Message");
                            String status = object.getString("Status");
                            String image0 = object.getString("Image0");
                            String image1 = object.getString("Image1");
                            String image2 = object.getString("Image2");

                            homepageListView = new Homepage_ListView(username, usertype, house, house_area, tenant_tel_no, job, location, message, status, image0, image1, image2);
                            UserArrayList.add(homepageListView);
                            UA.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_function, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();//returns the object of class that is specified within the "actionviewclass"

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //it gets called with every new IP string. newText=Ip string
                ArrayList<Homepage_ListView> results = new ArrayList<>();

                for (Homepage_ListView x: UserArrayList) {
                    if (x.getUsername().toLowerCase().contains(newText) || x.getUsertype().toLowerCase().contains(newText) || x.getHouse_Area().toLowerCase().contains(newText))
                        results.add(x);
                }

                ((User_Adapter)_user_listview.getAdapter()).update(results);//refresh listview

                return false;
            }
        });
        return true;
    }

    private void doTheAutoRefresh() {

        if (UserArrayList!=null && UserArrayList.size()>0) {
            UserArrayList.clear();
        }

        Runnable refresh = new Runnable() {
            @Override
            public void run() {
                retrieveData();
                handler.postDelayed(this, 10000);
            }
        };
        handler.postDelayed(refresh, 10000);
    }
}