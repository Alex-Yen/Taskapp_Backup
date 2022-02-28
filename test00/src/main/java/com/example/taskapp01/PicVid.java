package com.example.taskapp01;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class PicVid implements Parcelable{
    private String path;
    private int selectCount;
    //để biets vị trí nào trong adapter để refresh đúng index đó
    private int position;

    public PicVid() {
    }

    public PicVid(String path) {
        this.path = path;
    }

    protected PicVid(Parcel in) {
        path = in.readString();
        selectCount = in.readInt();
        position = in.readInt();
    }

    public static final Creator<PicVid> CREATOR = new Creator<PicVid>() {
        @Override
        public PicVid createFromParcel(Parcel in) {
            return new PicVid(in);
        }

        @Override
        public PicVid[] newArray(int size) {
            return new PicVid[size];
        }
    };

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSelectCount() {
        return selectCount;
    }

    public void setSelectCount(int selectCount) {
        this.selectCount = selectCount;
    }


    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        return this.getSelectCount()==((PicVid)obj).getSelectCount();
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static ArrayList<PicVid> getGalleryPhotos(Context context) {
        ArrayList<PicVid> pictures = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media._ID;

        Cursor cursorPhotos = context.getContentResolver().query(uri, columns, null, null, orderBy);
        if (cursorPhotos != null && cursorPhotos.getCount() > 0) {
            while (cursorPhotos.moveToNext()) {
                PicVid picture=new PicVid();

                int indexPath=cursorPhotos.getColumnIndex(MediaStore.MediaColumns.DATA);
                picture.setPath(cursorPhotos.getString(indexPath));

                //Log.d("Duongdananh",picture.getPath());
                pictures.add(picture);
            }
        }
        Collections.reverse(pictures);
        return pictures;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(selectCount);
        dest.writeInt(position);
    }
}