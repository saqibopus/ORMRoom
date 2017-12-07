package com.example.emxcel.ormroom.RoomBasic.AppHelper;

import android.app.Activity;
import android.widget.Toast;

import java.net.StandardSocketOptions;
import java.util.TooManyListenersException;

/**
 * Created by emxcel on 7/12/17.
 */

public class LogHelper {

    private Activity activity;
    private boolean isEnable;
    private String TAG = "----**";

    public LogHelper(Activity activity, boolean isEnable) {
        this.isEnable = isEnable;
        this.activity = activity;
    }

    public void p(String msg) {
        if(isEnable)
        System.out.println(TAG + msg);
    }

    public void t(String msg) {
        if(isEnable)
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
