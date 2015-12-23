package com.example.sufian.livelocal;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;


/**
 * Created by Sufian on 11/19/2015.
 */
public class ActivityResultBus extends Bus {

    private static ActivityResultBus instance;

    public static ActivityResultBus getInstance() {
        if (instance == null)
            instance = new ActivityResultBus();
        return instance;
    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public void postQueue(final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                ActivityResultBus.getInstance().post(obj);
            }
        });
    }

}

