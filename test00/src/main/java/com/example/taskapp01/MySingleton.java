package com.example.taskapp01;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;

//network function in background

public class MySingleton {

    private static MySingleton mInstence;
    private RequestQueue mRequestQueu;
    private Context mCtx;

    public MySingleton(Context mCtx) {
        this.mCtx = mCtx;
        mRequestQueu = getmRequestQueu();
    }

    public RequestQueue getmRequestQueu() {

        if(mRequestQueu == null) {
            Cache cache = new DiskBasedCache(mCtx.getCacheDir(), 1024*1024);
            Network network = new BasicNetwork(new HurlStack());
            mRequestQueu = new RequestQueue(cache, network);
            mRequestQueu = Volley.newRequestQueue(mCtx.getApplicationContext());
        }

        return mRequestQueu;
    }

    public static synchronized MySingleton getmInstence(Context context) {
        if(mInstence == null) {
            mInstence = new MySingleton(context);
        }
        return mInstence;
    }

    public  <I> void addToRequestQueue(Request<I> request) {
        mRequestQueu.add(request);
    }
}
