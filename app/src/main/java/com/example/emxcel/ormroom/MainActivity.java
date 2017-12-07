package com.example.emxcel.ormroom;

import android.app.Activity;
import android.app.IntentService;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emxcel.ormroom.RoomBasic.DBOperation.CRUDUser;
import com.example.emxcel.ormroom.RoomBasic.Database.DB;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;


import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private DB appDatabase;
    private Button bt_get_users;
    private TextView tv_user_info;
    private FloatingActionButton fabAddUser;
    private CRUDUser crudUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        appDatabase = Room.databaseBuilder(this, DB.class, DB.DB_NAME)
                .allowMainThreadQueries().build();
        initUI();
        initClass();

        //loadUsers();
        //getAllUerDataRX();
        //getUserSingleRx();
        //getUserById();
        //dummy();
        //insert();

        //insertSingleRx();

        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertWithCustomLayout(MainActivity.this, "Enter User Detail").show();
            }
        });


    }

    private void initUI() {
        fabAddUser = (FloatingActionButton) findViewById(R.id.fab_add_user);
    }

    private void initClass() {
        crudUser = new CRUDUser(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void loadUsers() {
        new AsyncTask<Void, Void, Void>() {
            List<UserInfo> users = new ArrayList<>();

            @Override
            protected Void doInBackground(Void... params) {
                users = appDatabase.getUserInfoDao().getAllUser();
                return null;
            }

            @Override
            protected void onPostExecute(Void notes) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < users.size(); i++) {
                    stringBuilder.append(users.get(i).getId() + ":");
                    stringBuilder.append(users.get(i).getName() + ":");
                    stringBuilder.append(users.get(i).getAge() + "\n");
                }
                tv_user_info.setText(stringBuilder.toString());
            }
        }.execute();
    }

    private void saveNote() {
        UserInfo user = new UserInfo();
        user.setName("update");
        user.setAge(18);
        user.setPremium(true);

        new AsyncTask<UserInfo, Void, Void>() {

            @Override
            protected Void doInBackground(UserInfo... userInfos) {
                appDatabase.getUserInfoDao().insertUser(userInfos[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(user);
    }


    private void getAllUerDataRX() {
        Flowable<List<UserInfo>> data = appDatabase.getUserInfoDaoRX().getAllUser();

        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserInfo>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        System.out.println("----**onSubscribe");
                        System.out.println("----**onSubscribe " + s.toString());

                    }

                    @Override
                    public void onNext(List<UserInfo> userInfos) {
                        System.out.println("----**onNext");
                        System.out.println("----**User info : " + userInfos.size());
                    }

                    @Override
                    public void onError(Throwable t) {
                        System.out.println("----**onError");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("----**onComplete");
                    }
                });

    }

    private void getUserSingleRx() {
        System.out.println("----**getUserSingleRx(start)");
        Single<UserInfo> userData = appDatabase.getUserInfoDaoRX().getUserById(3);
        userData.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("----**onSubscribe");

                    }


                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        System.out.println("----**onSuccess");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("----**onError");
                        System.out.println("----**onError" + e.getLocalizedMessage());
                        System.out.println("----**onError" + e.getCause());
                        System.out.println("----**onError" + e.getStackTrace());
                    }
                });

        System.out.println("----**getUserSingleRx(end)");
    }

    private void getUserById() {
        System.out.println("----**getUserById(start)");

        new AsyncTask<Void, Void, Void>() {
            UserInfo userFromDb = null;

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                userFromDb = appDatabase.getUserInfoDao().getUserById(1);
            }

            @Override
            protected Void doInBackground(Void... voids) {
                if (userFromDb != null) {
                    System.out.println("----**id : " + userFromDb.getId());
                    System.out.println("----**name : " + userFromDb.getName());
                    System.out.println("----**age : " + userFromDb.getAge());
                }

                return null;

            }
        }.execute();

        System.out.println("----**getUserById(end)");
    }

    private void dummy() {
        final UserInfo user = new UserInfo();
        user.setName("single_callable");
        user.setAge(75);
        user.setPremium(true);

        Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return appDatabase.getUserInfoDaoRX().insertUser(user);
            }
        });
    }

    private AlertDialog alertWithCustomLayout(Activity activity, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Get the layout inflater
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_user, null);
        final EditText userName = (EditText) view.findViewById(R.id.etUserName);
        final EditText userAge = (EditText) view.findViewById(R.id.etUserAge);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxPremium);


        builder.setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (userName.getText().toString().equals("")) {
                            userName.setError("Enter Name");
                            return;
                        }
                        if (userAge.getText().toString().equals("")) {
                            userAge.setError("Enter Age");
                            return;
                        }

                        UserInfo user = new UserInfo();
                        user.setName(userName.getText().toString());
                        user.setAge(Integer.parseInt(userAge.getText().toString()));
                        user.setPremium(checkBox.isChecked());
                        crudUser.insertUser(user);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }


}
