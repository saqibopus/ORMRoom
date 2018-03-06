package com.example.emxcel.ormroom.RoomBasic.AppHelper;

import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by demo on 7/12/17.
 */

public class ProgressHelper {

    private ProgressDialog progressDialog;
    private static ProgressHelper progressHelper;

    public static ProgressHelper getInstance(){
        if(progressHelper == null){
            progressHelper = new ProgressHelper();
        }
        return progressHelper;
    }

    public void initProgressDilog(Activity activity){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
    }
    public void show(String message){
        progressDialog.setMessage(message);
        progressDialog.show();
    }
    public void dissmiss(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();

    }

}
