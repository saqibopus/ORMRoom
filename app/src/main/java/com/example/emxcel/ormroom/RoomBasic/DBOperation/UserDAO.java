package com.example.emxcel.ormroom.RoomBasic.DBOperation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import java.util.List;


@Dao
public interface UserDAO {

    @Insert
    long insertUser(UserInfo userInfo);

    @Query("UPDATE user_info SET name = :new_user_name WHERE name = :old_user_name")
    int updateUserName(String  old_user_name,String new_user_name);
    /**
     * Following code will change all the records of name and age */
    @Query("UPDATE user_info SET name = :new_user_name, age = :new_user_age WHERE id = :id")
    int updateUserNameAge(String new_user_name,int new_user_age,long id);

    @Update
    int updateUserFull(UserInfo userInfo);

    @Update
    void updateAll(UserInfo userInfo);

    @Query("SELECT * FROM user_info")
    List<UserInfo> getAllUser();

    @Query("SELECT * FROM user_info WHERE id = :id")
    UserInfo getUserById(long id);

    @Delete
    public int deleteUsers(UserInfo userInfo);

    @Query("DELETE FROM user_info WHERE name = :user_name")
    int deleteUserByName(String user_name);
}
