package com.example.taskapp01.User;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskapp01.Homepage_ListView;
import com.example.taskapp01.R;

import java.util.ArrayList;
import java.util.List;

public class User_Adapter extends ArrayAdapter<Homepage_ListView> implements Filterable {

    List<Homepage_ListView> arrayhpJobList;

    public User_Adapter(@NonNull Context context, List<Homepage_ListView> arrayhpJobList) {
        super(context, R.layout.user_listview_detail, arrayhpJobList);

        this.arrayhpJobList =arrayhpJobList;
        notifyDataSetChanged();
    }

    //**must have for search function**
    @Override
    public int getCount() {//return the no. of list items
        return arrayhpJobList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //inflate layout and pass

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_listview_detail, null, true);

        TextView _username = view.findViewById(R.id.username_LV);
        TextView _usertype = view.findViewById(R.id.usertype_LV);
        TextView _house_area = view.findViewById(R.id.house_area_UL);

        _username.setText(arrayhpJobList.get(position).getUsername());
        _usertype.setText(arrayhpJobList.get(position).getUsertype());
        _house_area.setText(arrayhpJobList.get(position).getHouse_Area());
        return view;
    }

    public void update(ArrayList<Homepage_ListView> results) {
        arrayhpJobList = new ArrayList<>();
        arrayhpJobList.addAll(results);
        notifyDataSetChanged();
    }
}