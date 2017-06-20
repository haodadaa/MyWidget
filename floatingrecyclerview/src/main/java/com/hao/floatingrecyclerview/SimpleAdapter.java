package com.hao.floatingrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hao.floatingrecyclerview.bean.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hao on 2017/6/15.
 */

public class SimpleAdapter extends RecyclerView.Adapter {
    private List<City> mCities = new ArrayList<>();
    private Context mContext;

    public SimpleAdapter(Context context, List<City> cities) {
        mCities.addAll(cities);
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        int i = position % 5 + 1;
        if (i == 1) {
            holder.mIvCity.setImageResource(R.mipmap.subject1);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg1));
        } else if (i == 2) {
            holder.mIvCity.setImageResource(R.mipmap.subject2);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg2));
        } else if (i == 3) {
            holder.mIvCity.setImageResource(R.mipmap.subject3);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg3));
        } else if (i == 4) {
            holder.mIvCity.setImageResource(R.mipmap.subject4);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg4));
        } else {
            holder.mIvCity.setImageResource(R.mipmap.subject5);
            holder.mLlBg.setBackgroundColor(mContext.getResources().getColor(R.color.bg5));
        }
        holder.mTvCity.setText(mCities.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mIvCity;

        TextView mTvCity;

        TextView mTvBrief;

        LinearLayout mLlBg;

        ViewHolder(View view) {
            super(view);
            mIvCity = (ImageView) view.findViewById(R.id.iv_city);
            mTvCity = (TextView) view.findViewById(R.id.tv_city);
            mTvBrief = (TextView) view.findViewById(R.id.tv_brief);
            mLlBg = (LinearLayout) view.findViewById(R.id.ll_bg);
        }
    }
}
