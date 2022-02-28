package com.example.taskapp01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class add_job_function2 extends AppCompatActivity {

    TextView _Location_TV;
    EditText _Location_ET;
    Button _next02_but;

    TextView _Electric, _Pipe, _Door, _Window, _Paint, _Clean_house, _Others;
    CheckBox _electric_item01, _electric_item02, _electric_item03, _electric_item04, _electric_item05, _electric_item06, _electric_item07, _electric_item08, _electric_item09, _electric_item10, _electric_item11;
    CheckBox _pipe_item01, _pipe_item02, _pipe_item03, _pipe_item04, _pipe_item05, _pipe_item06, _pipe_item07;
    CheckBox _door_item01, _door_item02, _door_item03, _door_item04, _door_item05, _door_item06, _door_item07, _door_item08, _door_item09;
    CheckBox _window_item01, _window_item02, _paint_item01, _paint_item02, _paint_item03, _paint_item04, _paint_item05;
    CheckBox _clean_item01, _clean_item02, _clean_item03, _others_item01, _others_item02, _others_item03, _others_item04, _others_item05, _others_item06;
    EditText _others_ET;

    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_function2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Job Description");

        _Location_TV = (TextView) findViewById(R.id.Location_TV);
        _Location_ET = (EditText) findViewById(R.id.Location_ET);

        sharedPreferences = getSharedPreferences("Add_job_fx", MODE_PRIVATE);

        _Electric = (TextView) findViewById(R.id.Electric);
        _Pipe = (TextView) findViewById(R.id.Pipe);
        _Door = (TextView) findViewById(R.id.Door);
        _Window = (TextView) findViewById(R.id.Window);
        _Paint = (TextView) findViewById(R.id.Paint);
        _Clean_house = (TextView) findViewById(R.id.Clean_house);
        _Others = (TextView) findViewById(R.id.Others);

        _electric_item01 = (CheckBox) findViewById(R.id.electric_item01);
        checkbox(_electric_item01);
        _electric_item02 = (CheckBox) findViewById(R.id.electric_item02);
        checkbox(_electric_item02);
        _electric_item03 = (CheckBox) findViewById(R.id.electric_item03);
        checkbox(_electric_item03);
        _electric_item04 = (CheckBox) findViewById(R.id.electric_item04);
        checkbox(_electric_item04);
        _electric_item05 = (CheckBox) findViewById(R.id.electric_item05);
        checkbox(_electric_item05);
        _electric_item06 = (CheckBox) findViewById(R.id.electric_item06);
        checkbox(_electric_item06);
        _electric_item07 = (CheckBox) findViewById(R.id.electric_item07);
        checkbox(_electric_item07);
        _electric_item08 = (CheckBox) findViewById(R.id.electric_item08);
        checkbox(_electric_item08);
        _electric_item09 = (CheckBox) findViewById(R.id.electric_item09);
        checkbox(_electric_item09);
        _electric_item10 = (CheckBox) findViewById(R.id.electric_item10);
        checkbox(_electric_item10);
        _electric_item11 = (CheckBox) findViewById(R.id.electric_item11);
        checkbox(_electric_item11);

        _pipe_item01 = (CheckBox) findViewById(R.id.pipe_item01);
        checkbox(_pipe_item01);
        _pipe_item02 = (CheckBox) findViewById(R.id.pipe_item02);
        checkbox(_pipe_item02);
        _pipe_item03 = (CheckBox) findViewById(R.id.pipe_item03);
        checkbox(_pipe_item03);
        _pipe_item04 = (CheckBox) findViewById(R.id.pipe_item04);
        checkbox(_pipe_item04);
        _pipe_item05 = (CheckBox) findViewById(R.id.pipe_item05);
        checkbox(_pipe_item05);
        _pipe_item06 = (CheckBox) findViewById(R.id.pipe_item06);
        checkbox(_pipe_item06);
        _pipe_item07 = (CheckBox) findViewById(R.id.pipe_item07);
        checkbox(_pipe_item07);

        _door_item01 = (CheckBox) findViewById(R.id.door_item01);
        checkbox(_door_item01);
        _door_item02 = (CheckBox) findViewById(R.id.door_item02);
        checkbox(_door_item02);
        _door_item03 = (CheckBox) findViewById(R.id.door_item03);
        checkbox(_door_item03);
        _door_item04 = (CheckBox) findViewById(R.id.door_item04);
        checkbox(_door_item04);
        _door_item05 = (CheckBox) findViewById(R.id.door_item05);
        checkbox(_door_item05);
        _door_item06 = (CheckBox) findViewById(R.id.door_item06);
        checkbox(_door_item06);
        _door_item07 = (CheckBox) findViewById(R.id.door_item07);
        checkbox(_door_item07);
        _door_item08 = (CheckBox) findViewById(R.id.door_item08);
        checkbox(_door_item08);
        _door_item09 = (CheckBox) findViewById(R.id.door_item09);
        checkbox(_door_item09);

        _window_item01 = (CheckBox) findViewById(R.id.window_item01);
        checkbox(_window_item01);
        _window_item02 = (CheckBox) findViewById(R.id.window_item02);
        checkbox(_window_item02);

        _paint_item01 = (CheckBox) findViewById(R.id.paint_item01);
        checkbox(_paint_item01);
        _paint_item02 = (CheckBox) findViewById(R.id.paint_item02);
        checkbox(_paint_item02);
        _paint_item03 = (CheckBox) findViewById(R.id.paint_item03);
        checkbox(_paint_item03);
        _paint_item04 = (CheckBox) findViewById(R.id.paint_item04);
        checkbox(_paint_item04);
        _paint_item05 = (CheckBox) findViewById(R.id.paint_item05);
        checkbox(_paint_item05);

        _clean_item01 = (CheckBox) findViewById(R.id.clean_item01);
        checkbox(_clean_item01);
        _clean_item02 = (CheckBox) findViewById(R.id.clean_item02);
        checkbox(_clean_item02);
        _clean_item03 = (CheckBox) findViewById(R.id.clean_item03);
        checkbox(_clean_item03);

        _others_item01 = (CheckBox) findViewById(R.id.others_item01);
        checkbox(_others_item01);
        _others_item02 = (CheckBox) findViewById(R.id.others_item02);
        checkbox(_others_item02);
        _others_item03 = (CheckBox) findViewById(R.id.others_item03);
        checkbox(_others_item03);
        _others_item04 = (CheckBox) findViewById(R.id.others_item04);
        checkbox(_others_item04);
        _others_item05 = (CheckBox) findViewById(R.id.others_item05);
        checkbox(_others_item05);
        _others_item06 = (CheckBox) findViewById(R.id.others_item06);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(_others_item06.getText().toString());
        editor.apply();
        _others_item06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_others_item06.isChecked()) {
                    //edit shared preference (put data)
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    //put data in shared preference
                    editor.putString(_others_item06.getText().toString(), _others_ET.getText().toString());
                    //apply changes to shared preference
                    editor.apply();
                }
            }
        });
        _others_ET = (EditText) findViewById(R.id.others_ET);

        _next02_but = (Button) findViewById(R.id.next02_but);
        _next02_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from add_job_function
                String _Location_ETs = _Location_ET.getText().toString();
                //edit shared preference (put data)
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //put data in shared preference
                editor.putString("Location", _Location_ETs);
                //apply changes to shared preference
                editor.apply();

                startActivity(new Intent(add_job_function2.this, job_detail_addjob.class));
            }
        });
    }

    private void checkbox(final CheckBox check) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(check.getText().toString());
        editor.apply();
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(check.getText().toString(), check.getText().toString() + "\n");
                    editor.apply();
                } else if (!check.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(check.getText().toString());
                    editor.apply();
                }
            }
        });
    }
}

