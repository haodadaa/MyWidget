package com.hao.radiobuttonrecyclerview;


import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hao.radiobuttonrecyclerview.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by hao on 2017/5/11.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ShopVH> {

    private List<ShopBean> mList = new ArrayList<>();
    private Context mContext;
    private LayoutInflater mInflater;
    private RecyclerView mRV;

    private int mSelectedPos = -1;

    public RecyclerAdapter(List<ShopBean> mList, Context context, RecyclerView recyclerView) {
        this.mList = mList;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mRV = recyclerView;

        for (int i = 0; i < mList.size(); i++) {
            if (mList.get(i).isSelected()) {
                this.mSelectedPos = i;
            }
        }
    }

    @Override
    public ShopVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShopVH(mInflater.inflate(R.layout.item_shop, parent, false));
    }

    @Override
    public void onBindViewHolder(final ShopVH holder, final int position) {
        holder.tvShopName.setSelected(mList.get(position).isSelected());
        holder.tvShopName.setText(mList.get(position).getShopName());//TextView
        holder.tvShopName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现单选方法三： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                ShopVH shopVH = (ShopVH) mRV.findViewHolderForLayoutPosition(mSelectedPos);
                if (shopVH != null) {//还在屏幕里
                    shopVH.tvShopName.setSelected(false);
                    shopVH.tvShopName.setTextColor(Color.LTGRAY);
                }else {//add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                    //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                    notifyItemChanged(mSelectedPos);
                }
                mList.get(mSelectedPos).setSelected(false);//不管在不在屏幕里 都需要改变数据
                //设置新Item的勾选状态
                mSelectedPos = position;
                mList.get(mSelectedPos).setSelected(true);
                holder.tvShopName.setSelected(true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ShopVH extends RecyclerView.ViewHolder {
        private TextView tvShopName;

        public ShopVH(View itemView) {
            super(itemView);
            tvShopName = (TextView) itemView.findViewById(R.id.tvShopName);
        }
    }
}
