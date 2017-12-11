package com.example.emxcel.ormroom.RoomBasic.DBOperation;

import android.app.Activity;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;

import com.example.emxcel.ormroom.RoomBasic.AppHelper.ProgressHelper;
import com.example.emxcel.ormroom.RoomBasic.Database.DB;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by emxcel on 7/12/17.
 */

public class CRUDUser {

    private DB appDatabase;
    private Activity activity;
    private ProgressHelper progressHelper;
    public interface CRUDOperationListner {
        void onInsert(String message,long id);
        void onUpdateUser(String message,int id);
        void onGetAllUser(String message,List<UserInfo> userInfos);
    }
    private CRUDOperationListner crudOperationListner;
    public CRUDUser(Activity activity,CRUDOperationListner crudOperationListner) {
        this.activity = activity;
        this.crudOperationListner=crudOperationListner;
        appDatabase = Room.databaseBuilder(activity, DB.class, DB.DB_NAME)
                .allowMainThreadQueries().build();
        progressHelper = ProgressHelper.getInstance();
        progressHelper.initProgressDilog(activity);
    }

    public void insertUser(final UserInfo user){

        new AsyncTask<Void, Void, Void>() {
            long id = -1;
            @Override
            protected Void doInBackground(Void... voids) {
                id = appDatabase.getUserInfoDao().insertUser(user);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Inserting User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crudOperationListner.onInsert("message",id);
                progressHelper.dissmiss();
            }
        }.execute();
    }
    public void getUsers() {
        new AsyncTask<Void, Void, Void>() {
            List<UserInfo> users = new ArrayList<>();
            @Override
            protected Void doInBackground(Void... params) {
                users = appDatabase.getUserInfoDao().getAllUser();
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Getting User");
            }

            @Override
            protected void onPostExecute(Void notes) {
                crudOperationListner.onGetAllUser("User List Success",users);
                progressHelper.dissmiss();

            }
        }.execute();
    }


    public void updateUser(final UserInfo userInfo){

        new AsyncTask<Void, Void, Void>() {
            int id = 00;
            @Override
            protected Void doInBackground(Void... voids) {
                id = appDatabase.getUserInfoDao().updateUserFull(userInfo);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Updating User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crudOperationListner.onUpdateUser("message",id);
                progressHelper.dissmiss();
            }
        }.execute();
    }
    /**
     * Following code will change all the records of name and age */
    public void updateUserNameAge(final String name, final int age,final long user_id){

        new AsyncTask<Void, Void, Void>() {
            int id = 00;
            @Override
            protected Void doInBackground(Void... voids) {
                id = appDatabase.getUserInfoDao().updateUserNameAge(name,age,user_id);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Updating User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crudOperationListner.onUpdateUser("message",id);
                progressHelper.dissmiss();
            }
        }.execute();
    }
    public void updateUserName(final String old_name, final String new_name){

        new AsyncTask<Void, Void, Void>() {
            int id = 00;
            @Override
            protected Void doInBackground(Void... voids) {
                id = appDatabase.getUserInfoDao().updateUserName(old_name,new_name);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Updating User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crudOperationListner.onUpdateUser("message",id);
                progressHelper.dissmiss();
            }
        }.execute();
    }

    public void deleteUser(final UserInfo userInfo){

        new AsyncTask<Void, Void, Void>() {
            int _id = 00;
            @Override
            protected Void doInBackground(Void... voids) {
                _id = appDatabase.getUserInfoDao().deleteUsers(userInfo);
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressHelper.show("Updating User");
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                crudOperationListner.onUpdateUser("message",_id);
                progressHelper.dissmiss();
            }
        }.execute();
    }
}
