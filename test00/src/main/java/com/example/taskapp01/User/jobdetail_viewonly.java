package com.example.taskapp01.User;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.taskapp01.R;

public class jobdetail_viewonly extends AppCompatActivity {

    TextView _Usertype_View, _Username_View, _House_View, _House_Area_View, _Tenant_tel_no_View, _Job_View, _Location_View;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdetail_viewonly);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Job Detail");

        _Usertype_View = (TextView) findViewById(R.id.Usertype_View);
        _Username_View = (TextView) findViewById(R.id.Username_View);
        _House_View = (TextView) findViewById(R.id.House_View);
        _House_Area_View = (TextView) findViewById(R.id.House_Area_View);
        _Tenant_tel_no_View = (TextView) findViewById(R.id.Tenant_tel_no_View);
        _Job_View = (TextView) findViewById(R.id.Job_View);
        _Location_View = (TextView) findViewById(R.id.Location_View);

        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");

        _Usertype_View.setText("Usertype: " + Landlord.UserArrayList.get(position).getUsertype());
        _Username_View.setText("Username: " + Landlord.UserArrayList.get(position).getUsername());
        _House_View.setText("House: " + Landlord.UserArrayList.get(position).getHouse());
        _House_Area_View.setText("House Area: " + Landlord.UserArrayList.get(position).getHouse_Area());
        _Tenant_tel_no_View.setText("Tenant tel no: " + Landlord.UserArrayList.get(position).getTenant_tel_no());
        _Job_View.setText("Job: " + Landlord.UserArrayList.get(position).getJob());
        _Location_View.setText("Location: " + Landlord.UserArrayList.get(position).getLocation());
    }
}