package com.example.emxcel.ormroom.RoomBasic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.emxcel.ormroom.R;
import com.example.emxcel.ormroom.RoomBasic.AppHelper.LogHelper;
import com.example.emxcel.ormroom.RoomBasic.Tables.UserInfo;

import java.util.ArrayList;
import java.util.List;


public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHoler> implements Filterable {
    private Context context;
    private List<UserInfo> userList;
    private List<UserInfo> userListFiltered;
    private LogHelper logHelper;
    private ContactsFilter mContactsFilter;
    private UserAdapterListener userAdapterListener;
    public UserListAdapter(Context context, List<UserInfo> userList,UserAdapterListener userAdapterListener) {
        this.context = context;
        this.userList = userList;
        this.userListFiltered = userList;
        this.userAdapterListener=userAdapterListener;
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
        UserInfo userInfo = userListFiltered.get(position);
        holder.tvUserId.setText(String.valueOf(userInfo.getId()));
        holder.tvUserName.setText(userInfo.getName());
        holder.tvUserAge.setText(String.valueOf(userInfo.getAge()));
        holder.tvUserSalary.setText(String.valueOf(userInfo.getSalary()));
        holder.tvUserPremium.setText(String.valueOf(userInfo.isPremium()));
    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        if (mContactsFilter == null)
            mContactsFilter = new ContactsFilter();
        return mContactsFilter;
    }


    public class ViewHoler extends RecyclerView.ViewHolder {
        public TextView tvUserId, tvUserName, tvUserAge, tvUserPremium,tvUserSalary;


        public ViewHoler(View itemView) {
            super(itemView);
            tvUserId = itemView.findViewById(R.id.tv_user_id);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvUserAge = itemView.findViewById(R.id.tv_user_age);
            tvUserSalary = itemView.findViewById(R.id.tv_user_salary);
            tvUserPremium = itemView.findViewById(R.id.tv_user_premium);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    userAdapterListener.onUserSelected(userListFiltered.get(getAdapterPosition()));
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    userAdapterListener.onUserDeleteSelected(userListFiltered.get(getAdapterPosition()));
                    return false;
                }
            });
        }
    }

    private class ContactsFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // Create a FilterResults object

            String charString = constraint.toString();
            // If the constraint (search string/pattern) is null
            // or its length is 0, i.e., its empty then
            // we just set the `values` property to the
            // original contacts list which contains all of them
            if (charString.isEmpty()) {
                /*results.values = userList;
                results.count = userList.size();*/
                userListFiltered = userList;
            } else {
                // Some search copnstraint has been passed
                // so let's filter accordingly
                ArrayList<UserInfo> filteredUsers = new ArrayList<>();

                // We'll go through all the contacts and see
                // if they contain the supplied string
                for (UserInfo c : userList) {
                    if (c.getName().toLowerCase().contains(constraint.toString())) {
                        // if `contains` == true then add it
                        // to our filtered list
                        filteredUsers.add(c);
                    }
                }
                userListFiltered = filteredUsers;
                // Finally set the filtered values and size/count

            }
            FilterResults results = new FilterResults();
            results.values = userListFiltered;
            // Return our FilterResults object
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            logHelper.p("count : "+results.count);
            userListFiltered = (ArrayList<UserInfo>) results.values;
            notifyDataSetChanged();
        }
    }
    public interface UserAdapterListener {
        void onUserSelected(UserInfo userInfo);
        void onUserDeleteSelected(UserInfo userInfo);
    }
}



