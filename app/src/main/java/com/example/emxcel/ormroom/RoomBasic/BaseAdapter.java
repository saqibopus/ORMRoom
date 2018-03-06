package com.example.emxcel.ormroom.RoomBasic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by demo on 12/12/17.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {
    private List<T> list;
    protected abstract View createView(Context context, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, BaseAdapter.ViewHolder viewHolder);
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public static class ViewHolder<V> extends RecyclerView.ViewHolder {
        private V v;


        public ViewHolder(View itemView) {
            super(itemView);
            v= (V) itemView;
        }
    }

}
