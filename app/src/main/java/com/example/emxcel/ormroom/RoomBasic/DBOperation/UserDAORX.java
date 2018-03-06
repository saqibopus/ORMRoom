package com.example.emxcel.ormroom.RoomBasic.DBOperation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * Created by demo on 4/12/17.
 */
@Dao
public interface UserDAORX {
    @Insert
    Long insertUser(UserInfo userInfo);

    @Update
    void updateAll(UserInfo userInfo);

    @Query("SELECT * FROM user_info")
    Flowable<List<UserInfo>> getAllUser();

    /*@Query("SELECT * FROM user_info WHERE id=:id")
    void getReposById(long id);*/

    @Query("SELECT * FROM user_info")
    List<UserInfo> getAllUserDummy();

    @Query("SELECT * FROM user_info WHERE id = :id")
    Single<UserInfo> getUserById(int id);




}
