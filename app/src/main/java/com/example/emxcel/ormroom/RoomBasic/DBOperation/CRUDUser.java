package com.example.emxcel.ormroom.RoomBasic.DBOperation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import com.example.emxcel.ormroom.RoomBasic.AppHelper.ProgressHelper;
import com.example.emxcel.ormroom.RoomBasic.Database.DB;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

/**
 * Created by emxcel on 7/12/17.
 */

public class CRUDUser {

    private DB appDatabase;
    private Activity activity;
    private ProgressHelper progressHelper;

    public CRUDUser(Activity activity) {
        this.activity = activity;
        appDatabase = Room.databaseBuilder(activity, DB.class, DB.DB_NAME)
                .allowMainThreadQueries().build();
        progressHelper = ProgressHelper.getInstance();
        progressHelper.initProgressDilog(activity);
    }

    public void insertUser(UserInfo user){

        new AsyncTask<UserInfo, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Inserting User");
            }

            @Override
            protected Void doInBackground(UserInfo... userInfos) {
                appDatabase.getUserInfoDao().insertUser(userInfos[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                progressHelper.dissmiss();
            }
        }.execute(user);
    }

}
