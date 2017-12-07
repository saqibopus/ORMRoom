package com.example.emxcel.ormroom.RoomBasic.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.emxcel.ormroom.RoomBasic.DBOperation.UserDAO;
import com.example.emxcel.ormroom.RoomBasic.DBOperation.UserDAORX;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

/**
 * Created by emxcel on 4/12/17.
 */

@Database(entities = {UserInfo.class}, version = 1)
public abstract class DB extends RoomDatabase {
    public static final String DB_NAME = "app_db";

    public abstract UserDAO getUserInfoDao();
    public abstract UserDAORX getUserInfoDaoRX();
}
