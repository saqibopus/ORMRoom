package com.example.emxcel.ormroom;

import android.app.Activity;
import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.emxcel.ormroom.RoomBasic.Adapters.UserListAdapter;
import com.example.emxcel.ormroom.RoomBasic.AppHelper.LogHelper;
import com.example.emxcel.ormroom.RoomBasic.AppHelper.RecyclerTouchListener;
import com.example.emxcel.ormroom.RoomBasic.DBOperation.CRUDUser;
import com.example.emxcel.ormroom.RoomBasic.Database.DB;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements CRUDUser.CRUDOperationListner {
    private DB appDatabase;
    private Button bt_get_users;
    private TextView tv_user_info;
    private FloatingActionButton fabAddUser;
    private CRUDUser crudUser;
    private RecyclerView recyclerView;
    private List<UserInfo> allUserData;
    private UserListAdapter userListAdapter;
    private LogHelper logHelper;
    private AlertDialog addDilog;
    private AlertDialog updateDilog;

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

        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDilog.show();
            }
        });
        crudUser.getUsers();
    }

    private void initUI() {
        fabAddUser = (FloatingActionButton) findViewById(R.id.fab_add_user);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_user_list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserInfo info = allUserData.get(position);
                logHelper.p("id :" + info.getId());
                updateDilog = alertWithCustomLayout(MainActivity.this, info);
                updateDilog.show();
            }

            @Override
            public void onLongClick(View view, int position) {
                UserInfo info = allUserData.get(position);
                simpleAlert(MainActivity.this,"Are you sure you want to delete?",info).show();
            }
        }));
    }

    private void initClass() {
        logHelper = new LogHelper(MainActivity.this, true);
        crudUser = new CRUDUser(MainActivity.this, this);
        addDilog = alertWithCustomLayout(MainActivity.this, "User Info");
    }

    private void prepareListData() {
        userListAdapter = new UserListAdapter(MainActivity.this, allUserData);
        recyclerView.setAdapter(userListAdapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true; // handled
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                userListAdapter.getFilter().filter(newText);

                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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

    private void getAllUerDataRX() {
        Flowable<List<UserInfo>> data = appDatabase.getUserInfoDaoRX().getAllUser();

        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<UserInfo>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        logHelper.p("----**onSubscribe");
                        logHelper.p("----**onSubscribe " + s.toString());

                    }

                    @Override
                    public void onNext(List<UserInfo> userInfos) {
                        logHelper.p("----**onNext");
                        logHelper.p("----**User info : " + userInfos.size());
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
        logHelper.p("----**getUserSingleRx(start)");
        Single<UserInfo> userData = appDatabase.getUserInfoDaoRX().getUserById(3);
        userData.observeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        logHelper.p("----**onSubscribe");

                    }


                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        logHelper.p("----**onSuccess");
                    }

                    @Override
                    public void onError(Throwable e) {
                        logHelper.p("----**onError");
                        logHelper.p("----**onError" + e.getLocalizedMessage());
                        logHelper.p("----**onError" + e.getCause());
                        logHelper.p("----**onError" + e.getStackTrace());
                    }
                });

        logHelper.p("----**getUserSingleRx(end)");
    }

    private void getUserById() {
        logHelper.p("----**getUserById(start)");

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
                    logHelper.p("----**id : " + userFromDb.getId());
                    logHelper.p("----**name : " + userFromDb.getName());
                    logHelper.p("----**age : " + userFromDb.getAge());
                }

                return null;

            }
        }.execute();

        logHelper.p("----**getUserById(end)");
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
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_user, null);

        final EditText userName = (EditText) view.findViewById(R.id.etUserName);
        final EditText userAge = (EditText) view.findViewById(R.id.etUserAge);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxPremium);
        final Button btOk = (Button) view.findViewById(R.id.bt_ok);
        final Button btCancel = (Button) view.findViewById(R.id.bt_cancel);

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                if (addDilog != null && addDilog.isShowing()) {
                    addDilog.dismiss();
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addDilog != null && addDilog.isShowing()) {
                    addDilog.dismiss();
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    private AlertDialog alertWithCustomLayout(Activity activity, final UserInfo userInfo) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_add_user, null);

        final EditText userName = (EditText) view.findViewById(R.id.etUserName);
        final EditText userAge = (EditText) view.findViewById(R.id.etUserAge);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkboxPremium);
        final Button btOk = (Button) view.findViewById(R.id.bt_ok);
        final Button btCancel = (Button) view.findViewById(R.id.bt_cancel);
        userName.setText(userInfo.getName());
        userAge.setText(String.valueOf(userInfo.getAge()));
        if (userInfo.isPremium()) {
            checkBox.setChecked(userInfo.isPremium());
        }
        logHelper.p("id : " + userInfo.getId());
        logHelper.p("name : " + userInfo.getName());
        logHelper.p("age : " + userInfo.getAge());
        logHelper.p("premium : " + userInfo.isPremium());


        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (userName.getText().toString().equals("")) {
                    userName.setError("Enter Name");
                    return;
                }
                if (userAge.getText().toString().equals("")) {
                    userAge.setError("Enter Age");
                    return;
                }
                UserInfo user = new UserInfo();
                user.setId(userInfo.getId());
                user.setName(userName.getText().toString());
                user.setAge(Integer.parseInt(userAge.getText().toString()));
                user.setPremium(checkBox.isChecked());

                /* Following code will update user by id */
                crudUser.updateUser(user);

                 /* Following code will change all the records of name and age */
                //crudUser.updateUserNameAge(user.getName(), user.getAge(),user.getId());
                //crudUser.updateUserName("saqib123","saqib_test");

                if (updateDilog != null && updateDilog.isShowing()) {
                    updateDilog.dismiss();
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (updateDilog != null && updateDilog.isShowing()) {
                    updateDilog.dismiss();
                }
            }
        });
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onInsert(String message, long id) {
        logHelper.p("----**onInsert(start)");
        logHelper.p("----**onInsert id : " + id);
        if (id == -1) {
            return;
        }
        crudUser.getUsers();
        logHelper.p("----**onInsert(end)");
    }

    @Override
    public void onUpdateUser(String message, int id) {
        logHelper.p("----**onUpdateName(start)");
        logHelper.p("----**onUpdateName id: " + id);
        if (id >= 1) {
            crudUser.getUsers();
        }
        logHelper.p("----**onUpdateName(end)");
    }


    @Override
    public void onGetAllUser(String message, List<UserInfo> userInfos) {
        logHelper.p("----**onGetAllUser(start)");
        allUserData = userInfos;
        prepareListData();
        logHelper.p("----**onGetAllUser(end)");
    }


    private AlertDialog simpleAlert(Activity activity, String message, final UserInfo userInfo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        crudUser.deleteUser(userInfo);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }

}
