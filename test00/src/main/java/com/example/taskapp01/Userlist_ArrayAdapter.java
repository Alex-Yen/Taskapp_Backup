package com.example.taskapp01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Userlist_ArrayAdapter extends ArrayAdapter<Userlist_Array> {

    public Userlist_ArrayAdapter(Context context, ArrayList<Userlist_Array> userlist_array) {
        super(context, 0, userlist_array);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.userlist_spinner, parent, false);
        }

        TextView _spinner_text = convertView.findViewById(R.id.spinner_text);

        Userlist_Array userlist_array = getItem(position);

        _spinner_text.setText(userlist_array.getUsername());

        return convertView;
    }
}
