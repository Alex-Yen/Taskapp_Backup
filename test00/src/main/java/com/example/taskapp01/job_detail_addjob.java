package com.example.taskapp01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskapp01.Image.ImagesActivity;

public class job_detail_addjob extends AppCompatActivity {

    TextView _Usertype_fx, _Username_fx, _House_fx, _House_Area_fx, _Tenant_tel_no_fx, _Job_fx, _Location_fx;
    String s_Usertype_fx, s_Username_fx, s_House_fx, s_House_Area_fx, s_Tenant_tel_no_fx, s_Location_fx;
    String s_electric_item01, s_electric_item02, s_electric_item03, s_electric_item04, s_electric_item05, s_electric_item06, s_electric_item07, s_electric_item08, s_electric_item09, s_electric_item10, s_electric_item11;
    String s_pipe_item01, s_pipe_item02, s_pipe_item03, s_pipe_item04, s_pipe_item05, s_pipe_item06, s_pipe_item07;
    String s_door_item01, s_door_item02, s_door_item03, s_door_item04, s_door_item05, s_door_item06, s_door_item07, s_door_item08, s_door_item09;
    String s_window_item01, s_window_item02, s_paint_item01, s_paint_item02, s_paint_item03, s_paint_item04, s_paint_item05;
    String s_clean_item01, s_clean_item02, s_clean_item03, s_others_item01, s_others_item02, s_others_item03, s_others_item04, s_others_item05, s_others_item06;
    Button _upload_img_but;

    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_addjob);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Job Description");

        //container for data
        _Usertype_fx = (TextView) findViewById(R.id.Usertype_fx);
        _Username_fx = (TextView) findViewById(R.id.Username_fx);
        _House_fx = (TextView) findViewById(R.id.House_fx);
        _House_Area_fx = (TextView) findViewById(R.id.House_Area_fx);
        _Tenant_tel_no_fx = (TextView) findViewById(R.id.Tenant_tel_no_fx);
        _Job_fx = (TextView) findViewById(R.id.Job_fx);
        _Location_fx = (TextView) findViewById(R.id.Location_fx);

        //shared preferences (Name, Mode)
        sharedPreferences = getSharedPreferences("Add_job_fx", MODE_PRIVATE);
        //get data from shared preference
        s_Usertype_fx = sharedPreferences.getString("Usertype", "");
        s_Username_fx = sharedPreferences.getString("Username", "");
        s_House_fx = sharedPreferences.getString("House", "");
        s_House_Area_fx = sharedPreferences.getString("House Area", "");
        s_Tenant_tel_no_fx = sharedPreferences.getString("Tenant tel no", "");
        s_Location_fx = sharedPreferences.getString("Location", "");

        s_electric_item01 = sharedPreferences.getString("Change lightbulb", "");
        s_electric_item02 = sharedPreferences.getString("Change starter", "");
        s_electric_item03 = sharedPreferences.getString("Change light set", "");
        s_electric_item04 = sharedPreferences.getString("Change fan + regulator", "");
        s_electric_item05 = sharedPreferences.getString("Change regulator", "");
        s_electric_item06 = sharedPreferences.getString("Change 13A socket", "");
        s_electric_item07 = sharedPreferences.getString("Change 15A socket", "");
        s_electric_item08 = sharedPreferences.getString("Change water heater", "");
        s_electric_item09 = sharedPreferences.getString("Change exhaust fan (set)", "");
        s_electric_item10 = sharedPreferences.getString("Fix DB Board (Day time)", "");
        s_electric_item11 = sharedPreferences.getString("Fix DB Board (Night time)", "");

        s_pipe_item01 = sharedPreferences.getString("Fix pipe leakage", "");
        s_pipe_item02 = sharedPreferences.getString("Change water tap", "");
        s_pipe_item03 = sharedPreferences.getString("Change sink water tap", "");
        s_pipe_item04 = sharedPreferences.getString("Change bottle trap", "");
        s_pipe_item05 = sharedPreferences.getString("Change cistern", "");
        s_pipe_item06 = sharedPreferences.getString("Change siphon", "");
        s_pipe_item07 = sharedPreferences.getString("Change controller", "");

        s_door_item01 = sharedPreferences.getString("Change door latch", "");
        s_door_item02 = sharedPreferences.getString("Change lock (circle)", "");
        s_door_item03 = sharedPreferences.getString("Change lock handle", "");
        s_door_item04 = sharedPreferences.getString("Fix door/frame", "");
        s_door_item05 = sharedPreferences.getString("Change door set", "");
        s_door_item06 = sharedPreferences.getString("Change wooden door frame to metal type", "");
        s_door_item07 = sharedPreferences.getString("Change swing type PVC door", "");
        s_door_item08 = sharedPreferences.getString("Change sliding type PVC door + cement frame", "");
        s_door_item09 = sharedPreferences.getString("Fix toilet/outlet blockage", "");

        s_window_item01 = sharedPreferences.getString("Fix/change naco window", "");
        s_window_item02 = sharedPreferences.getString("Change wooden window frame to metal type", "");

        s_paint_item01 = sharedPreferences.getString("Paint small room", "");
        s_paint_item02 = sharedPreferences.getString("Paint medium room", "");
        s_paint_item03 = sharedPreferences.getString("Paint big room", "");
        s_paint_item04 = sharedPreferences.getString("Paint metal grille", "");
        s_paint_item05 = sharedPreferences.getString("Paint metal gate", "");

        s_clean_item01 = sharedPreferences.getString("Clean house apartment", "");
        s_clean_item02 = sharedPreferences.getString("Throw rubbish", "");
        s_clean_item03 = sharedPreferences.getString("Hire 1 ton lorry", "");

        s_others_item01 = sharedPreferences.getString("Fix channel and roof", "");
        s_others_item02 = sharedPreferences.getString("Fix netting/ change house padlock", "");
        s_others_item03 = sharedPreferences.getString("Fix padlock", "");
        s_others_item04 = sharedPreferences.getString("Lock room for unpaid rent", "");
        s_others_item05 = sharedPreferences.getString("Spray white ants/grass poison", "");
        s_others_item06 = sharedPreferences.getString("Others:", "");

        //pass data to container for view
        _Usertype_fx.setText("Usertype: " + s_Usertype_fx);
        _Username_fx.setText("Username: " + s_Username_fx);
        _House_fx.setText("House: " + s_House_fx);
        _House_Area_fx.setText("House Area: " + s_House_Area_fx);
        _Tenant_tel_no_fx.setText("Tenant tel no: " + s_Tenant_tel_no_fx);
        _Job_fx.setText("Job: " + s_electric_item01 + s_electric_item02 + s_electric_item03 + s_electric_item04 + s_electric_item05 + s_electric_item06 + s_electric_item07 + s_electric_item08 + s_electric_item09 + s_electric_item10 + s_electric_item11
                + s_pipe_item01 + s_pipe_item02 + s_pipe_item03 + s_pipe_item04 + s_pipe_item05 + s_pipe_item06 + s_pipe_item07 + s_door_item01 + s_door_item02 + s_door_item03 + s_door_item04 + s_door_item05 + s_door_item06 + s_door_item07 + s_door_item08 + s_door_item09
                + s_window_item01 + s_window_item02 + s_paint_item01 + s_paint_item02 + s_paint_item03 + s_paint_item04 + s_paint_item05 + s_clean_item01 + s_clean_item02 + s_clean_item03
                + s_others_item01 + s_others_item02 + s_others_item03 + s_others_item04 + s_others_item05 + s_others_item06);
        _Location_fx.setText("Location: " + s_Location_fx);

        //sent data to next image activity
        _upload_img_but = (Button) findViewById(R.id.upload_img_but);
        _upload_img_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //input data to shared preference
                String Job = s_electric_item01 + s_electric_item02 + s_electric_item03 + s_electric_item04 + s_electric_item05 + s_electric_item06 + s_electric_item07 + s_electric_item08 + s_electric_item09 + s_electric_item10 + s_electric_item11
                        + s_pipe_item01 + s_pipe_item02 + s_pipe_item03 + s_pipe_item04 + s_pipe_item05 + s_pipe_item06 + s_pipe_item07 + s_door_item01 + s_door_item02 + s_door_item03 + s_door_item04 + s_door_item05 + s_door_item06 + s_door_item07 + s_door_item08 + s_door_item09
                        + s_window_item01 + s_window_item02 + s_paint_item01 + s_paint_item02 + s_paint_item03 + s_paint_item04 + s_paint_item05 + s_clean_item01 + s_clean_item02 + s_clean_item03
                        + s_others_item01 + s_others_item02 + s_others_item03 + s_others_item04 + s_others_item05 + s_others_item06;
                //edit shared preference (put data)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //put data in shared preference
                editor.putString("Job", Job);
                //apply changes to shared preference
                editor.apply();
                if (TextUtils.isEmpty(s_Usertype_fx) || TextUtils.isEmpty(s_Username_fx) || TextUtils.isEmpty(s_House_fx) || TextUtils.isEmpty(s_House_Area_fx) || TextUtils.isEmpty(Job) || TextUtils.isEmpty(s_Location_fx)) {
                    Toast.makeText(job_detail_addjob.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(job_detail_addjob.this, ImagesActivity.class));
                    Toast.makeText(job_detail_addjob.this, "Select not more than 3 pictures", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}