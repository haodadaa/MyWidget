package com.hao.floatingrecyclerview;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gavin.com.library.PowerfulStickyDecoration;
import com.gavin.com.library.StickyDecoration;
import com.gavin.com.library.listener.GroupListener;
import com.gavin.com.library.listener.PowerGroupListener;
import com.hao.floatingrecyclerview.bean.City;
import com.hao.floatingrecyclerview.util.DataUtil;
import com.hao.floatingrecyclerview.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    List<City> dataList = new ArrayList<>();
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        dataList.addAll(DataUtil.getCityList());

        GroupListener groupListener =new GroupListener() {
            @Override
            public String getGroupName(int position) {
                return null;
            }
        };

        PowerfulStickyDecoration decoration = PowerfulStickyDecoration.Builder
                .init(new PowerGroupListener() {
                    @Override
                    public String getGroupName(int position) {
                        //获取组名，用于判断是否是同一组
                        if (dataList.size() > position) {
                            return dataList.get(position).getProvince();
                        }
                        return null;
                    }

                    @Override
                    public View getGroupView(int position) {
                        //获取自定定义的组View
                        if (dataList.size() > position) {
                            View view = getLayoutInflater().inflate(R.layout.city_group, null, false);
                            ((TextView) view.findViewById(R.id.tv)).setText(dataList.get(position).getProvince());
                            ((ImageView)view.findViewById(R.id.iv)).setImageResource(dataList.get(position).getIcon());
                            return view;
                        } else {
                            return null;
                        }
                    }
                })
                .setGroupHeight(DensityUtil.dip2px(this, 40))   //设置高度
                .build();
        mAdapter = new SimpleAdapter(this, dataList);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
    }
}
