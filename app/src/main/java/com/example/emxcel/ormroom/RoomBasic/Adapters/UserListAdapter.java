package com.example.emxcel.ormroom.RoomBasic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.emxcel.ormroom.R;
import com.example.emxcel.ormroom.RoomBasic.AppHelper.LogHelper;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import java.util.List;
/**
 * Created by emxcel on 7/12/17.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHoler> {
    private Context context;
    private List<UserInfo> userList;
    private LogHelper logHelper;

    public UserListAdapter(Context context, List<UserInfo> userList) {
        this.context = context;
        this.userList = userList;
        Activity a = (Activity) context;
        logHelper = new LogHelper(a, true);
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_user_list, parent, false);


        return new ViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        UserInfo userInfo = userList.get(position);
        holder.tvUserId.setText(String.valueOf(userInfo.getId()));
        holder.tvUserName.setText(userInfo.getName());
        holder.tvUserAge.setText(String.valueOf(userInfo.getAge()));
        holder.tvUserPremium.setText(String.valueOf(userInfo.isPremium()));


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        public TextView tvUserId, tvUserName, tvUserAge, tvUserPremium;


        public ViewHoler(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tv_user_id);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserAge = itemView.findViewById(R.id.tv_user_age);
            tvUserPremium = itemView.findViewById(R.id.tv_user_premium);

        }
    }
}
